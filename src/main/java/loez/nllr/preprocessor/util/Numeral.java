package loez.nllr.preprocessor.util;

/**
 * A preprocessor utility for handling numerals
 * @author ljleppan
 */
public class Numeral implements PreprocessorUtil {

    /**
     * Replaces all numerals from the input with the given string.
     * @param input         Input
     * @param replacement   Replacement string
     * @return              String with numeral replaces with replacement string
     */
    @Override
    public String replace(String input, String replacement){
        return input.replaceAll("\\p{Digit}+", replacement);
    }

    /**
     * Removes all numerals from the input.
     * @param input Input
     * @return      Input with all numerals removed
     */
    @Override
    public String remove(String input){
        return replace(input, "");
    }
}
