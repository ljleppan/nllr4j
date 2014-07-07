package loez.nllr.preprocessor;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author loezi
 */
public class SimplePreprocessorTest {
    private SimplePreprocessor pp = new SimplePreprocessor();

    @Test
    public void canCallSetLanguageWithoutException(){
        pp.setLanguage("");
    }
    
    @Test
    public void getLanguage(){
        assertEquals("Simple preprocessor should return correct string when getLanguage is called",
                "Simple preprocessor is language independent",
                pp.getLanguage());
    }
    
    @Test
    public void correctlyProcesses(){
        assertEquals("a string should be correctly processed",
                "THIS IS A STRING",
                pp.process("tHiS, is; a string...."));
        assertEquals("a string should be correctly processed",
                "ALSO A STRING THIS TIME WITH NUMERAL",
                pp.process("a.l.so A- string this time with 1233321312"));
    }
}
