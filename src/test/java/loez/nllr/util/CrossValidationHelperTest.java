package loez.nllr.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import loez.nllr.domain.Corpus;
import loez.nllr.domain.Document;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CrossValidationHelperTest {

    private Corpus master;

    @Before
    public void setUp() {

    }

    private Corpus createCorpusWithDocuments(final int documents) {

        final Corpus corpus = new Corpus();

        for (int i = 0; i < documents; i++) {
            corpus.add(new Document(""));
        }

        return corpus;
    }

    private void assertAllOfSize(final List<Corpus> corpuses, final int size) {

        for (Corpus corpus : corpuses) {
            assertEquals(size, corpus.getDocuments().size());
        }
    }

    private void assertAllOfSizeOrOneBigger(final List<Corpus> corpuses, final int target) {

        for (Corpus corpus : corpuses) {
            assertTrue(target == corpus.getDocuments().size() || target + 1 == corpus.getDocuments().size());
        }
    }

    private void assertTotalSize(final List<Corpus> corpuses, final int target) {

        int actualSize = 0;

        for (Corpus corpus : corpuses) {
            actualSize += corpus.getDocuments().size();
        }

        assertEquals(target, actualSize);
    }

    private void assertNoDuplicateElements(final List<Corpus> corpuses) {

        final Set<Document> documents = new HashSet<>();

        for (Corpus corpus : corpuses) {
            for (Document document : corpus.getDocuments()) {
                if (documents.contains(document)) {
                    fail("Duplicate element found");
                } else {
                    documents.add(document);
                }
            }
        }
    }

    private void assertNoDuplicateElements(final Corpus corpusA, final Corpus corpusB) {

        for (Document document : corpusA.getDocuments()) {
            assertFalse(corpusB.getDocuments().contains(document));
        }
        for (Document document : corpusB.getDocuments()) {
            assertFalse(corpusA.getDocuments().contains(document));
        }
    }

    @Test
    public void splitsEvenAmountEvenly() {

        final Corpus corpus = createCorpusWithDocuments(20);

        final List<Corpus> split = CrossValidationHelper.split(corpus, 10);

        assertAllOfSize(split, 2);
        assertTotalSize(split, 20);
        assertNoDuplicateElements(split);
    }

    @Test
    public void splitsNonEvenAmountEvenly() {
        final Corpus corpus = createCorpusWithDocuments(25);

        final List<Corpus> split = CrossValidationHelper.split(corpus, 10);

        assertAllOfSizeOrOneBigger(split, 2);
        assertTotalSize(split, 25);
        assertNoDuplicateElements(split);
    }

    @Test
    public void handlesLargerSplitThanSizeGracefully() {

        final Corpus corpus = createCorpusWithDocuments(5);

        final List<Corpus> split = CrossValidationHelper.split(corpus, 10);

        assertAllOfSizeOrOneBigger(split, 0);
        assertTotalSize(split, 5);
        assertNoDuplicateElements(split);
    }

    @Test
    public void buildTrainingCorpusReturnsCorpusOfAllDocumentsNotInTestCorpus() {

        final Corpus corpus = createCorpusWithDocuments(50);

        final List<Corpus> split = CrossValidationHelper.split(corpus, 5);

        final Corpus testCorpus = split.get(0);

        final Corpus trainingCorpus = CrossValidationHelper.buildTrainingCorpus(split, testCorpus);

        assertNoDuplicateElements(testCorpus, trainingCorpus);
        assertEquals(10, testCorpus.getDocuments().size());
        assertEquals(40, trainingCorpus.getDocuments().size());
    }
}
