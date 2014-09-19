package loez.nllr.algorithm;

import java.util.ArrayList;
import java.util.List;
import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TfidfTest {

    private static final String TOKEN = "token";

    private Document docA;
    private Document docB;
    private Document docC;
    private Document docD;
    private List<Document> refDocs;
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
    public void testIdf() {
        /**
         * IDF for a corpus of 4 texts, of which 3 contain the token:
         * log (4 / 3) = 0.287682072451780
         */
        double expected = 0.287682072451780;
        double got = Tfidf.idf(reference, "auto");

        assertTrue("IDF test #1 failed, see test code for further details. Expected " + expected + " got " + got,
                equal(got , expected));

        /**
         * IDF for a corpus of 4 texts, of which 1 contain the token:
         * log (4 / 1) = 1.386294361119890
         */
        expected = 1.386294361119890;
        got = Tfidf.idf(reference, "asia");
        assertTrue("IDF test #2 failed, see test code for further details. Expected " + expected + " got " + got,
                equal(got , expected));
    }

    @Test
    public void testTfidf() {
        /**
         * TF-IDF for a corpus of 4 texts, of which 3 contain the token that has a TF of 2:
         * 2 * log (4 / 3) = 0.5753641449035618548784
         */
        double expected = 0.5753641449035618548784;
        double got = Tfidf.tfidf("auto", docA, reference);
        assertTrue("TF-IDF test #1 failed, see test code for further details. Expected " + expected + " got " + got,
                equal(got , expected));

        /**
         * TF-IDF for a corpus of 4 texts, of which 1 contain the token that has a TF of 1:
         * (1 / 5) * log (4 / 1) = 1.386294361119890
         */
        expected = 1.386294361119890;
        got = Tfidf.tfidf(TOKEN, docD, reference);
        assertTrue("TF-IDF test #2 failed, see test code for further details. Expected " + expected + " got " + got,
                equal(got , expected));
    }

    @Test
    public void calculateTest() {

        final double expected = 1.386294361119890;

        final Object[] args = {TOKEN, docD, reference};
        final double actual = new Tfidf().calculate(args);

        assertTrue("calculate() result didn't match TFIDF calculation #2 result. Expected " + expected + " got " + actual,
                equal(expected, actual));
    }

    public boolean equal(final double a, final double b) {

        final double epsilon = 0.00000001;

        return Math.abs(a - b) < epsilon;
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateTooFewArgs() {

        final Object[] args = {TOKEN, docD};

        new Tfidf().calculate(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateTooManyArgs() {

        final Object[] args = {TOKEN, docD, reference, docD};

        new Tfidf().calculate(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateWrongArgTypes1() {

        final Object[] args = {null, docD, reference};

        new Tfidf().calculate(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateWrongArgTypes2() {

        final Object[] args = {TOKEN, null, reference};

        new Tfidf().calculate(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateWrongArgTypes3() {

        final Object[] args = {TOKEN, docD, null};

        new Tfidf().calculate(args);
    }
}
