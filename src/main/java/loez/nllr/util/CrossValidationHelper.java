package loez.nllr.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;

public class CrossValidationHelper {

    public static List<Corpus> split(final Corpus corpus, final int parts) {

        final List<Document> documents = corpus.getDocuments();
        Collections.shuffle(documents);

        final int count = documents.size();
        final int perCorpus = count / parts;

        final List<Corpus> result = new ArrayList<>();
        for (int i = 0; i < parts; i++) {

            final int startIndex = i * perCorpus;
            final int endIndex = (i + 1) * perCorpus;

            final List<Document> subList = new ArrayList<>(documents.subList(startIndex, endIndex));
            result.add(new Corpus(subList));
        }

        final int leftOvers = count % parts;

        if (leftOvers != 0) {

            final int lastAddedIndex = parts * perCorpus;

            for (int i = 0; i < leftOvers; i++) {

                final Corpus current = result.get(i);

                final Document document = documents.get(lastAddedIndex + i);

                current.getDocuments().add(document);
            }
        }

        return result;
    }

    public static Corpus buildTrainingCorpus(final List<Corpus> parts, final Corpus testCorpus) {

        final List<Corpus> trainingCorpuses = new ArrayList<>(parts);
        trainingCorpuses.remove(testCorpus);

        final Corpus resultCorpus = new Corpus();
        for (Corpus corpus : trainingCorpuses) {
            resultCorpus.addAll(corpus.getDocuments());
        }

        return resultCorpus;
    }
}
