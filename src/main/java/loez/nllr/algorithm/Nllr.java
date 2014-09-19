package loez.nllr.algorithm;

import java.util.ArrayList;
import java.util.List;

import loez.nllr.algorithm.Argmax.Result;
import loez.nllr.domain.BagOfWords;
import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;

/**
 * An NLLR-calculator.
 * @author ljleppan@cs
 */
public class Nllr implements Algorithm {

    /**
     * A small near-zero constant that is used in place of zero to prevent divisions by zero.
     */
    public static final double NONZERO = 0.0000001;

    private static final int NUMBER_OF_TOKENS_TO_ANALYZE = 10;

    private final Corpus corpus;

    /**
     * Creates a new NLLR-calculator for the specified corpus.
     * @param corpus    The corpus this NLLR-calculator is tied to.
     */
    public Nllr(final Corpus corpus) {

        this.corpus = corpus;
    }

    /**
     * Calculates the NLLR-score for the Query document and the Candidate corpus.
     * The candidate corpus MUST be a sub-corpus of the corpus the NLLR-instance is tied to.
     * @param query     The query document.
     * @param candidate The candidate corpus.
     * @return          The NLLR-score for the query document and the candidate corpus.
     */
    public double calculateNllr(final Document query, final Corpus candidate) {

        double nllr = 0;

        final Object[] constants = {query, corpus};
        final List tokens = new ArrayList<>(query.getUniqueTokens());

        final List<Result<String>> results = new Argmax().multiple(
                new Tfidf(),
                NUMBER_OF_TOKENS_TO_ANALYZE,
                tokens,
                constants);

        for (Result<String> result : results) {

            final String uniqueToken = result.getArgument();

            final double tokenProbQuery = calculateTokenProbability(uniqueToken, query);
            final double tokenProbCandidate = calculateTokenProbability(uniqueToken, candidate);
            final double tokenProbCorpus = calculateTokenProbability(uniqueToken, corpus);

            nllr += tokenProbQuery * Math.log(tokenProbCandidate / tokenProbCorpus);
        }

        return nllr;
    }

    /**
     * Calculates the token probability for the given token in the given BagOfWords.
     * Token probability is defined as the frequency of the token divided by the total amount of tokens.
     * @param token      The token.
     * @param bagOfWords The BagOfWords
     * @return           The probability of the token in the document.
     */
    public double calculateTokenProbability(final String token, final BagOfWords bagOfWords) {

        final double prob =  (double) bagOfWords.getFrequency(token) / bagOfWords.getTotalTokens();

        if (prob == 0) {
            return NONZERO;
        } else {
            return prob;
        }
    }

    /**
     * A wrapper of the nllr() method for the Algorithm interface.
     * @param args  Arguments
     * @return      Result
     */
    @Override
    public double calculate(final Object[] args) {

        if (args.length != 2) {
            throw new IllegalArgumentException();
        }

        if (args[1] instanceof Document && args[0] instanceof Corpus) {
            final Document query = (Document) args[1];
            final Corpus candidate = (Corpus) args[0];

            return calculateNllr(query, candidate);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
