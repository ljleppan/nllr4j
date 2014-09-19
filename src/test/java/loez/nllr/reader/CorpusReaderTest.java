package loez.nllr.reader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import loez.nllr.preprocessor.PreProcessor;
import loez.nllr.preprocessor.SimplePreprocessor;

import org.junit.Before;

public class CorpusReaderTest {

    private CorpusReader cr;
    private DateFormat df;
    private PreProcessor pp;

    @Before
    public void setUp() {

        df = new SimpleDateFormat("d-MMM-yyyy", Locale.US);
        pp = new SimplePreprocessor();
        cr = new CorpusReader();
    }
}
