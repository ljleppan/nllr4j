package loez.nllr.algorithm;

import loez.nllr.datastructure.ArrayList;
import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author loezi
 */
public class TfidfTest{
    
    private Document docA;
    private Document docB;
    private Document docC;
    private Document docD;   
    private ArrayList<Document> refDocs;
    private Corpus reference;
    
    @Before
    public void setUp() {
        docA = new Document(null, "auto juttu juttu auto asia");
        docB = new Document(null, "auto hämminki kiva hässäkkä");
        docC = new Document(null, "testaus olla aina tosi kiva auto");
        docD = new Document(null, "mutta token keksiä joskus vaikea");
        
        refDocs = new ArrayList<>();
        refDocs.add(docA); 
        refDocs.add(docB); 
        refDocs.add(docC); 
        refDocs.add(docD);
        reference = new Corpus(refDocs);
    }
    
    @Test
    public void testIdf(){
        /**
         * IDF for a corpus of 4 texts, of which 3 contain the token:
         * log (4 / 3) = 0.287682072451780
         */
        double expected = 0.287682072451780;
        double got = Tfidf.idf(reference, "auto");
        assertTrue("IDF test #1 failed, see test code for further details. Expected "+expected+" got "+got,
                equal(got , expected));
        
        /**
         * IDF for a corpus of 4 texts, of which 1 contain the token:
         * log (4 / 1) = 1.386294361119890
         */
        expected = 1.386294361119890;
        got = Tfidf.idf(reference, "asia");
        assertTrue("IDF test #2 failed, see test code for further details. Expected "+expected+" got "+got,
                equal(got , expected));
    }
    
    @Test
    public void testTfidf(){
        /**
         * TF-IDF for a corpus of 4 texts, of which 3 contain the token that has a TF of 2:
         * 2 * log (4 / 3) = 0.5753641449035618548784
         */
        double expected = 0.5753641449035618548784;
        double got = Tfidf.tfidf("auto", docA, reference);
        assertTrue("TF-IDF test #1 failed, see test code for further details. Expected "+expected+" got "+got,
                equal(got , expected));
        
        /**
         * TF-IDF for a corpus of 4 texts, of which 1 contain the token that has a TF of 1:
         * (1 / 5) * log (4 / 1) = 1.386294361119890
         */
        expected = 1.386294361119890;
        got = Tfidf.tfidf("token", docD, reference);
        assertTrue("TF-IDF test #2 failed, see test code for further details. Expected "+expected+" got "+got,
                equal(got , expected));
    }
    
    @Test
    public void calculateTest(){
        double expected = 1.386294361119890;
        
        Object[] args = {"token", docD, reference};
        double actual = new Tfidf().calculate(args);
                
        assertTrue("calculate() result didn't match TFIDF calculation #2 result. Expected "+ expected +" got "+ actual,
                equal(expected, actual));
    } 
    
    public boolean equal(double a, double b){
        double epsilon = 0.00000001;
        return Math.abs(a - b) < epsilon;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void calculateTooFewArgs(){
        Object[] args = {"token", docD};
        new Tfidf().calculate(args);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void calculateTooManyArgs(){
        Object[] args = {"token", docD, reference, docD};
        new Tfidf().calculate(args);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void calculateWrongArgTypes1(){
        Object[] args = {null, docD, reference};
        new Tfidf().calculate(args);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void calculateWrongArgTypes2(){
        Object[] args = {"token", null, reference};
        new Tfidf().calculate(args);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void calculateWrongArgTypes3(){
        Object[] args = {"token", docD, null};
        new Tfidf().calculate(args);
    }
    
}
