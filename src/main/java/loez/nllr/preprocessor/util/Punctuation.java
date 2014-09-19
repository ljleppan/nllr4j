package loez.nllr.preprocessor.util;

public class Punctuation implements PreprocessorUtil {

    @Override
    public String replace(final String input, final String replacement) {

        return input.replaceAll("\\p{Punct}+", replacement);
    }

    @Override
    public String remove(final String input) {

        return replace(input, "");
    }
}
