package loez.nllr.domain;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Document is a single text.
 * @author ljleppan@cs
 */
public class Document implements BagOfWords {

    private String body;
    private Calendar date;
    private Map<String, Integer> tokenAmounts;
    private int numTokens;

    /**
     * Creates a new document with known date.
     * @param date  Date of document's creation.
     * @param body  The text of the document.
     */
    public Document(final Calendar date, final String body) {

        this.body = body;
        this.date = date;

        if (this.date != null) {
            this.date.clear(Calendar.HOUR);
            this.date.clear(Calendar.MINUTE);
            this.date.clear(Calendar.SECOND);
            this.date.clear(Calendar.MILLISECOND);
        }

        this.tokenAmounts = new HashMap<>();
        this.numTokens = 0;

        countTokenFrequencies();
    }

    /**
     * Creates a new document without a known date.
     * @param body  The text of the document.
     */
    public Document(final String body) {

        this(null, body);
    }

    private void countTokenFrequencies() {

        numTokens = 0;

        final String[] tokens = body.split(" ");
        for (String t : tokens) {
            numTokens++;

            if (tokenAmounts.containsKey(t)) {
                final int amountNow = tokenAmounts.get(t);
                tokenAmounts.put(t, amountNow + 1);
            } else {
                tokenAmounts.put(t, 1);
            }
        }
    }

    /**
     * Get the frequency of a given token in the document.
     * @param token The query token.
     * @return      The frequency of the token.
     */
    @Override
    public int getFrequency(final String token) {

        if (tokenAmounts.containsKey(token)) {
            return tokenAmounts.get(token);
        } else {
            return 0;
        }
    }

    /**
     * Get the total amount of tokens (non-unique) in the document.
     * @return  The total amount of token in the document body.
     */
    @Override
    public int getTotalTokens() {

        return numTokens;
    }

    /**
     * Get the document's unique tokens.
     * @return  A HashSet of unique tokens in the documents. Order is not specified.
     */
    @Override
    public Set<String> getUniqueTokens() {

        final Set<String> uniqueTokens = new HashSet<>();
        uniqueTokens.addAll(tokenAmounts.keySet());

        return uniqueTokens;
    }

    /**
     * Checks if a token is present in the document.
     * @param token Token to look for
     * @return      True if token is present, false if not
     */
    @Override
    public boolean containsToken(final String token) {

        return tokenAmounts.containsKey(token);
    }

    /**
     * Get the date of creation of the document.
     * @return The date of creation.
     */
    public Calendar getDate() {

        return date;
    }
}
