package loez.nllr;

import java.io.FileNotFoundException;
import java.util.logging.Level;

import loez.nllr.preprocessor.exception.StemmerCreationException;

public class Main {

    private static final String DEFAULT_DATE_FORMAT = "d-MMM-yyyy";
    private static final String DEFAULT_PREPROCESSOR = "snowball";
    private static final String DEFAULT_LANGUAGE = "Finnish";
    private static final String DEFAULT_TIMESPAN_LENGTH = "biweekly";
    private static final Level DEFAULT_LOGGING_LEVEL = Level.INFO;

    public static void main(final String[] args) throws StemmerCreationException, FileNotFoundException {

        String corpus = null;
        String language = DEFAULT_LANGUAGE;
        String preprocessor = DEFAULT_PREPROCESSOR;
        String dateFormat = DEFAULT_DATE_FORMAT;
        String timespanLength = DEFAULT_TIMESPAN_LENGTH;
        Level loggingLevel = DEFAULT_LOGGING_LEVEL;


        if (!hasArg("-f", args)) {
            System.out.println("Must specify file with -f");
            System.exit(1);
        } else {
            corpus = getArgValue("-f", args);
        }

        if (hasArg("-l", args)) {
            language = getArgValue("-l", args);
        }

        if (hasArg("-pp", args)) {
            preprocessor = getArgValue("-pp", args);
        }

        if (hasArg("-df", args)) {
            dateFormat = getArgValue("-df", args);
        }

        if (hasArg("-ts", args)) {
            timespanLength = getArgValue("-ts", args);
        }

        if (hasArg("-log", args)) {
            loggingLevel = Level.parse(getArgValue("-log", args).toUpperCase().trim());
        }

        final Process process = new Process(loggingLevel, corpus, language, preprocessor, dateFormat, timespanLength);

        int times = 1;
        if (hasArg("-t", args)) {
            times = getArgValueAsInt("-t", args);
        }

        int crossValidation = 10;
        if (hasArg("-cv", args)) {
            crossValidation = getArgValueAsInt("-cv", args);
        }

        process.run(times, crossValidation);
    }

    public static int getArgValueAsInt(final String needle, final String[] args) {

        return Integer.parseInt(getArgValue(needle, args));
    }

    public static String getArgValue(final String needle, final String[] args) {

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(needle)) {
                return args[i + 1];
            }
        }

        return null;
    }

    public static boolean hasArg(final String needle, final String[] args) {

        for (String arg : args) {
            if (arg.equals(needle)) {
                return true;
            }
        }

        return false;
    }
}
