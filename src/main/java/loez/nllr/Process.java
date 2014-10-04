package loez.nllr;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import loez.nllr.algorithm.Argmax;
import loez.nllr.algorithm.Nllr;
import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;
import loez.nllr.domain.TimeSpan;
import loez.nllr.domain.TimeSpan.Length;
import loez.nllr.preprocessor.PreProcessor;
import loez.nllr.preprocessor.SimplePreprocessor;
import loez.nllr.preprocessor.SnowballPreprocessor;
import loez.nllr.preprocessor.exception.StemmerCreationException;
import loez.nllr.reader.CorpusReader;
import loez.nllr.util.CrossValidationHelper;

public class Process {

    private final SimpleDateFormat dateFormat;
    private final PreProcessor preprocessor;
    private final Corpus corpus;
    private final Length timespanLength;
    private final Level loggingLevel;

    public Process(final Level loggingLevel, final String corpus, final String language, final String preprocessor, final String dateFormat, final String timespanLength) throws StemmerCreationException, FileNotFoundException {

        this.loggingLevel = loggingLevel;
        this.preprocessor = getPreprocessor(preprocessor, language);
        this.dateFormat = new SimpleDateFormat(dateFormat, Locale.US);
        this.corpus = new CorpusReader().readCorpus(corpus, this.dateFormat, this.preprocessor);
        this.timespanLength = Length.forString(timespanLength);
    }

    private PreProcessor getPreprocessor(final String preprocessor, final String language) throws StemmerCreationException {

        if (preprocessor.equals("snowball")) {
            log(Level.INFO, "preprocessor: snowball, language: " + language);
            return new SnowballPreprocessor(language);
        } else {
            log(Level.INFO, "preprocessor: simple, language: N/A");
            return new SimplePreprocessor();
        }
    }

    public void run(final int times, final int crossValidation) {

        log(Level.INFO, "Running " + times + " times with CV" + crossValidation);
        for (int i = 0; i < times; i++) {
            run(crossValidation);
        }
    }

    private void run(final int crossValidation) {

        log(Level.INFO, "\n-----------------\nRunning with CV" + crossValidation);

        final List<Corpus> parts = CrossValidationHelper.split(corpus, crossValidation);

        log(Level.FINE, "\tSplit input corpus to " + parts.size() + " parts");

        for (Corpus testCorpus : parts) {

            log(Level.FINE, "\n\tBuilt training and test corpuses");

            final Corpus trainingCorpus = CrossValidationHelper.buildTrainingCorpus(parts, testCorpus);
            final TimeSpan timeSpan = new TimeSpan((Calendar) corpus.getStartDate().clone(), timespanLength);

            runSingle(testCorpus, trainingCorpus, timeSpan);
        }
    }

    private void runSingle(final Corpus testCorpus, final Corpus trainingCorpus, final TimeSpan timeSpan) {

        log(Level.FINE, "\tTest corpus is " +
                testCorpus.getDocuments().size() +
                " documents, training corpus is " +
                trainingCorpus.getDocuments().size() +
                " documents.");

        final List<Corpus> timePartitions = new ArrayList<>();

        trainingCorpus.refreshStats();
        testCorpus.refreshStats();
        log(Level.FINE, "\tRefreshed corpus stats.");

        log(Level.FINE, "\tBuilding time partitions.");
        log(Level.FINE, "\t\tTime partition size is " + timespanLength.name());

        final Calendar startDate = (Calendar) trainingCorpus.getStartDate().clone();
        final Calendar endDate = (Calendar) trainingCorpus.getEndDate().clone();

        log(Level.FINE, "\t\tStart date is " + print(startDate));
        log(Level.FINE, "\t\tEnd date is " + print(endDate));

        while (!timeSpan.getStart().after(endDate)) {
            final Corpus timePartition = trainingCorpus.getTimePartition(timeSpan.getStart(), timeSpan.getEnd());

            if (!timePartition.getDocuments().isEmpty()) {
                log(Level.FINE, "\t\t\tCreated time partition from " + print(timePartition.getStartDate()) + " to " + print(timePartition.getEndDate()) + " containing " + timePartition.getDocuments().size() + " documents.");
                timePartitions.add(timePartition);
            } else {
                log(Level.FINE, "\t\t\tPartition was empty, skipping.");
            }

            timeSpan.advance();
        }

        final Nllr nllr = new Nllr(trainingCorpus);

        int correct = 0;
        int wrong = 0;

        log(Level.FINE, "\tRunning NLLR over test corpus.");

        for (Document document : testCorpus.getDocuments()) {

            final Object[] argMaxArgs = {document};

            final Argmax.Result<Corpus> result = new Argmax<Corpus>().single(nllr, timePartitions, argMaxArgs);

            final Corpus resultCorpus = result.getArgument();
            final double resultNllr = result.getValue();

            log(Level.FINER, result(document, resultCorpus, resultNllr));

            if (document.getDate() != null && resultCorpus.getEndDate() != null && resultCorpus.getStartDate() != null) {
                if (!document.getDate().before(resultCorpus.getStartDate()) && !document.getDate().after(resultCorpus.getEndDate())) {
                    correct++;
                } else {
                    wrong++;
                }
            }
        }

        log(Level.INFO, "\tCorrect: " + correct + ", Wrong:" + wrong + ", Other: " + (testCorpus.getDocuments().size() - correct - wrong));
    }

    private String result(final Document document, final Corpus resultCorpus, final double resultNllr) {

        String docDate = "UNKNOWN";
        if (document.getDate() != null) {
            docDate = dateFormat.format(document.getDate().getTime());
        }

        String corpStartDate = "UNKNOWN";
        if (resultCorpus != null && resultCorpus.getStartDate() != null) {
            corpStartDate = dateFormat.format(resultCorpus.getStartDate().getTime());
        }

        String corpEndDate = "UNKNOWN";
        if (resultCorpus != null && resultCorpus.getEndDate() != null) {
            corpEndDate = dateFormat.format(resultCorpus.getEndDate().getTime());
        }

        final StringBuilder out = new StringBuilder();
        out.append("Actual date: ");
        out.append(docDate);
        out.append("\n\t");
        out.append(corpStartDate);
        out.append(" - ");
        out.append(corpEndDate);
        out.append(" : ");
        out.append(resultNllr);
        out.append("\n");

        return out.toString();
    }

    private String print(final Calendar calendar) {

        return dateFormat.format(calendar.getTime());
    }

    private void log(final Level level, final String log) {
        if (level.intValue() >= loggingLevel.intValue()) {
            System.out.println(log);
        }
    }
}
