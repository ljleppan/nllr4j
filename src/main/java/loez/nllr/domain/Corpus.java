package loez.nllr.domain;

import loez.nllr.datastructure.HashMap;
import java.util.Calendar;
import java.util.GregorianCalendar;
import loez.nllr.datastructure.ArrayList;
import loez.nllr.datastructure.HashSet;


/**
 * Corpus is a collection of Documents.
 * @author ljleppan@cs
 */
public class Corpus implements BagOfWords{
    private ArrayList<Document> documents = new ArrayList<>();
    private Calendar startDate;
    private Calendar endDate;
    private int totalTokens;
    private HashMap<String, Integer> tokenFrequensies;
    private HashMap<String, Integer> numOfDocumentsContainingToken;
    
    /**
     * Creates a new corpus from a list of documents.
     * @param documents The documents the corpus is comprised of.
     */
    public Corpus(ArrayList<Document> documents){
        if (documents != null){
            this.documents = documents;
        }

        refreshStats();
    }
    
    /**
     * Create an empty corpus.
     */
    public Corpus(){
        this(new ArrayList<Document>());
    }
    
    /**
     * Add a document to the corpus.
     * @param document  The document to be added.
     */
    public void add(Document document){
        if (document != null){
            documents.add(document);
        }
    }
    
    /**
     * Gets the document with the specified index.
     * @param index Index of the document.
     * @return      The document with the specified index.
     */
    public Document get(int index){
        if (documents.size() > index){
            return documents.get(index);
        } else {
            return null;
        }
    }
    
    /**
     * Removes a document from the corpus.
     * @param index The index of the document.
     */
    public void remove(int index){
        documents.remove(index);
    }

    /**
     * Get the total amount of (non-unique) tokens in the corpus's documents.
     * @return  The total amount of (non-unique) tokens.
     */
    @Override
    public int getTotalTokens() {
        return this.totalTokens;
    }
    
    /**
     * Get the frequency of a token within the corpus.
     * @param token The token.
     * @return      The frequency of the token withing the corpus.
     */
    @Override
    public int getFrequency(String token) {
        if (this.tokenFrequensies.containsKey(token)){
            return this.tokenFrequensies.get(token);
        }
        return 0;
    }
    
    /**
     * Checks if a token is present in the corpus.
     * @param token Token to look for.
     * @return      True if token is present in the Corpus.
     */
    @Override
    public boolean containsToken(String token){
        return tokenFrequensies.containsKey(token);
    }
    
    /**
     * Creates a sub-corpus of all the documents that were created between start and end dates.
     * @param start Start date, inclusive
     * @param end   End date, inclusive
     * @return      Corpus containing relevant documents
     */
    public Corpus getTimePartition(Calendar start, Calendar end){
        Corpus timePartition = new Corpus();
        for (Document d : documents){
            if (!d.getDate().before(start) && !d.getDate().after(end)){
                timePartition.add(d);
            }
        }
        timePartition.refreshStats();
        return timePartition;
    }

    /**
     * Refreshes the stats of the corpus.
     */
    public void refreshStats() {
        totalTokens = 0;
        tokenFrequensies = new HashMap<>();
        numOfDocumentsContainingToken = new HashMap<>();
        
        if (!documents.isEmpty()){
            for (Document doc : documents){
                refreshDates(doc);
                refreshFrequencies(doc);
                refreshNumberOfDocumentsContainingToken(doc);
                totalTokens += doc.getTotalTokens();
            }
        }        
    }
    
    /**
     * Returns the number of documents in this corpus that contain the queried token.
     * @param token Token
     * @return      Number of documents containing token
     */
    public int numOfDocsContainingToken(String token){
        if (containsToken(token)){
            return numOfDocumentsContainingToken.get(token);
        } else {
            return 0;
        }
    }
    
    private void refreshNumberOfDocumentsContainingToken(Document doc) {
        for(String token : doc.getUniqueTokens()){
            int amountNow = 0;
            if (numOfDocumentsContainingToken.containsKey(token)){
                amountNow = numOfDocumentsContainingToken.get(token);
            }
            numOfDocumentsContainingToken.put(token, amountNow+1);
        }
    }

    private void refreshFrequencies(Document doc) {
        for (String token : doc.getUniqueTokens()){    
            int docTokenAmount = doc.getFrequency(token);
            
            int amountNow = 0;
            if (tokenFrequensies.containsKey(token)) {
                amountNow = tokenFrequensies.get(token);
            }

            tokenFrequensies.put(token, amountNow + docTokenAmount);
        }
    }

    private void refreshDates(Document doc) {
        if (doc.getDate() != null){
            if (this.startDate == null){
                this.startDate = (Calendar) documents.get(0).getDate().clone();
            } else if (getStartDate().after(doc.getDate())){
                startDate.setTime(doc.getDate().getTime());
            }
            
            if (this.endDate == null){
                this.endDate = (Calendar) documents.get(0).getDate().clone();
            } else if (getEndDate().before(doc.getDate())){
                endDate.setTime(doc.getDate().getTime());
            }
        }
    }

    /**
     * @return the startDate
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * @return the endDate
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * @return A list of the documents that the corpus consists of.
     */
    public ArrayList<Document> getDocuments() {
        return documents;
    }
    
    /**
     * @return A HashSet of unique tokens found in the corpus.
     */
    @Override
    public HashSet<String> getUniqueTokens() {
        HashSet<String> uniqueTokens = new HashSet<>();
        for (Document d : documents){
            for (String token : d.getUniqueTokens()){
                uniqueTokens.add(token);
            }
        }
        return uniqueTokens;
    }
}