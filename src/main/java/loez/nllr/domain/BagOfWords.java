package loez.nllr.domain;

import java.util.Set;

/**
 * A bag of words.
 * Represents a non-ordered group of tokens.
 * @author loezi
 */
public interface BagOfWords {

    /**
     * @return Total amount of non-unique token in the bag.
     */
    int getTotalTokens();

    /**
     * @param token A token in the bag.
     * @return      The frequency of queried token in bag.
     */
    int getFrequency(String token);

    /**
     * @return  A HashSet containing all unique tokens found in the bag.
     */
    Set<String> getUniqueTokens();

    /**
     * @param token A token
     * @return      True if token is present in the bag, false if not.
     */
    boolean containsToken(String token);
}
