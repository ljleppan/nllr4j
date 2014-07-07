package loez.nllr.algorithm;

import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;

/**
 *
 * @author ljleppan@cs
 */
public class Tfidf implements Algorithm{
    
    /**
     * Calculates a tf-idf (text frequency - inverse document frequency) score.
     * The tf-idf weights the frequency of a token based on how well it represents the text in question.
     * F.ex. the word "the" is common in all texts, so its high tf is weighted down by the low idf.
     * Similarly, the word "Abracadabra" is uncommon, so its low tf is weighted up by the high idf.
     * 
     * Here, raw frequency is used for tf:
     *      tf = (#_of_instances_of_token_in_text) / (#_of_total_tokens_in_text)
     * The definition for idf is:
     *      idf = log ( #_of_texts / #_of_texts_containing_token )
     * And tf-idf is finally calculated by:
     *      tf-idf = tf * idf
     * @param token     The token for which the tf-idf score is calculated
     * @param query The text, as an instance of Document
     * @param reference The document, as an instance of Corpus
     * @return          A tf-idf score for the given parameters, as a double
     */
    public static double tfidf(String token, Document query, Corpus reference){
        int tf = query.getFrequency(token); 
        double idf = idf(reference, token);
        return tf * idf;
    }

    /**
     * Calculate the idf (Inverse Document Frequency) of a token
     * @param reference     The corpus as a collection of documents
     * @param token         The token     
     * @return              idf score for the token and the corpus
     */
    public static double idf(Corpus reference, String token) {
        int totalDocs = reference.getDocuments().size();
        int docsContainingTerm = reference.numOfDocsContainingToken(token);
        
        return Math.log( (double) totalDocs / docsContainingTerm);
    }
    
    /**
     * A wrapper of tfidf() for the Algorithm interface.
     * @param args  Arguments
     * @return      Result
     */
    @Override
    public double calculate(Object[] args){
        if (args.length != 3){
            throw new IllegalArgumentException();
        }
        
        if (args[0] instanceof String && args[1] instanceof Document && args[2] instanceof Corpus){
            String token = (String) args[0];
            Document query = (Document) args[1];
            Corpus reference = (Corpus) args[2];
            return tfidf(token, query, reference);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
