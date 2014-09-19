package loez.nllr.reader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import loez.nllr.domain.Document;
import loez.nllr.preprocessor.PreProcessor;
import loez.nllr.preprocessor.SimplePreprocessor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentConverterTest {

    private DocumentConverter dc;
    private DateFormat df;
    private PreProcessor pp;

    @Before
    public void setUp() {

        df = new SimpleDateFormat("d-MMM-yyyy", Locale.US);
        pp = new SimplePreprocessor();
        dc = new DocumentConverter();
    }

    @Test
    public void testConversionNotNullForCorrectInputLength() {

        final String raw = "TEST;8-APR-1987 00:38:26.78;    DATELINE ;TITLE;  BODY";
        final Document parsed = DocumentConverter.rawStringToDocument(raw, df, pp);

        assertNotNull("The resulting document should not be null if the input string contains all necessary parts",
                parsed);
    }

    @Test
    public void testConversionDate() throws ParseException {

        final String raw = "TEST;8-APR-1987 00:38:26.78;    DATELINE ;TITLE;  BODY";
        final Document parsed = DocumentConverter.rawStringToDocument(raw, df, pp);

        assertEquals("Parsed date should have correct year",
                1987, parsed.getDate().get(Calendar.YEAR));
        assertEquals("Parsed date should have correct month",
                3, parsed.getDate().get(Calendar.MONTH));
        assertEquals("Parsed date should have correct day of month",
                8, parsed.getDate().get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testConversionBody() throws ParseException {

        final String raw = "TEST; 8-APR-1987 00:38:26.78;    DATELINE ;TITLE;  BODY";
        final Document parsed = DocumentConverter.rawStringToDocument(raw, df, pp);

        assertTrue("Parsed document's body should contain the dateline",
                parsed.getUniqueTokens().contains("DATELINE"));
        assertTrue("Parsed document's body should contain the title",
                parsed.getUniqueTokens().contains("TITLE"));
        assertTrue("Parsed document's body should contain the body",
                parsed.getUniqueTokens().contains("BODY"));

    }

    @Test
    public void testConversionTooFewParts() throws ParseException {

        final String raw = "TEST; 8-APR-1987 00:38:26.78";
        final Document parsed = DocumentConverter.rawStringToDocument(raw, df, pp);

        assertNull("The resulting document should be null if the input string doesn't contain all necessary information",
                parsed);
    }
}
