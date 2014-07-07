package loez.nllr.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;
import loez.nllr.datastructure.HashSet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Document
 * @author ljleppan@cs
 */
public class DocumentTest {
    private Document doc;
    
    
    @Before
    public void setUp() throws Exception {
        String docBody = "auto asia auto auto auto";
        Calendar docDate = new GregorianCalendar();
        doc = new Document(docDate, docBody);
    }
    
    @Test
    public void testDatelessConstructorDoesNotGenerateDates(){
        Document d = new Document("asd");
        assertNull("Creating a new Document without a date resulted in a non-null date",
                d.getDate());

    }
    
    @Test
    public void testGetUniqueTokens(){
        HashSet<String> uniqueTokens = doc.getUniqueTokens();
        assertEquals("getUniqueTokens() returned an incorrect amount of unique tokens.",
                uniqueTokens.size(), 2);
        assertTrue("getUniqueTokens() should return a hashset that includes the String \"asia\"",
                uniqueTokens.contains("asia"));
        assertTrue("getUniqueTokens() should return a hashset that includes the String \"auto\"",
                uniqueTokens.contains("auto"));
    }
    
    @Test
    public void testGetTotalTokens(){
        assertEquals("getTotalTokens should return 5 when the document body has 5 tokens", 
               doc.getTotalTokens(), 5);
    }
    
    @Test
    public void testGetFrequency(){
        assertEquals("getFrequency should return 4 when the document has 4 of the queried token",
                doc.getFrequency("auto"), 4);
        assertEquals("getFrequency should return 1 when the document has 1 of the queried token",
                doc.getFrequency("asia"), 1);
        assertEquals("getFrequency should return 0 when the document has 0 of the queried token",
                doc.getFrequency("tokenThatDoesNotExist"), 0);
    }
}
