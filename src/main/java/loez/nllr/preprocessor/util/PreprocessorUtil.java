package loez.nllr.preprocessor.util;

/**
 * A generic preprocessor utility.
 * @author ljleppan
 */
public interface PreprocessorUtil {

    /**
     * Replaces the relevant parts of the input with the replacement string.
     * @param input         Input
     * @param replacement   Replacement string
     * @return              Input with relevant parts replaced with replacement string
     */
    String replace(String input, String replacement);

    /**
     * Removes the relevant parts of the input.
     * @param input Input
     * @return      Input without the relevant parts
     */
    String remove(String input);
}
