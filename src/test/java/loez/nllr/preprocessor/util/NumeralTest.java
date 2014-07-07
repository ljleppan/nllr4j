package loez.nllr.preprocessor.util;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ljleppan
 */
public class NumeralTest {
    private final Numeral n = new Numeral();
    
    @Test
    public void testRemove(){
        assertEquals("remove() should remove all numerals",
                "sana  sana",
                n.remove("sana 123 sana"));
        assertEquals("replace() should replace all numerals",
                "sana NUMERAL sana",
                n.replace("sana 123 sana", "NUMERAL"));
    }
}