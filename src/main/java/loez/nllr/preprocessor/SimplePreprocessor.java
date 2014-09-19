package loez.nllr.preprocessor;

import loez.nllr.preprocessor.util.Numeral;
import loez.nllr.preprocessor.util.Punctuation;

/**
 * An extremely simple preprocessor, for use as a fallback.
 * @author loezi
 */
public class SimplePreprocessor implements PreProcessor {

    private final Punctuation punctuation = new Punctuation();
    private final Numeral numeral = new Numeral();

    /**
     * Processes the input string into tokens.
     * @param input String of words
     * @return      String of tokens
     */
    @Override
    public String process(final String input) {

        String output = input;

        output = punctuation.remove(output);
        output = numeral.replace(output, "NUMERAL");
        output = output.trim().toUpperCase();

        return output;
    }

    /**
     * A dummy setLanguage() to fulfill the contract of PreProcessor.
     * Any value set is ignored.
     * @param language
     */
    @Override
    public void setLanguage(final String language) { }

    /**
     * @return The string "Simple preprocessor is language independent".
     */
    @Override
    public String getLanguage() {

        return "Simple preprocessor is language independent";
    }
}
