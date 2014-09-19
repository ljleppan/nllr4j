package loez.nllr.preprocessor;

import loez.nllr.preprocessor.exception.StemmerCreationException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SnowballPreprocessorTest {

    PreProcessor pp;

    @Before
    public void setUp() throws StemmerCreationException {

        pp = new SnowballPreprocessor();
    }

    @Test
    public void constructionWithLanguageSetsLanguage() throws StemmerCreationException {
        PreProcessor p = new SnowballPreprocessor("English");

        assertEquals("Constructing with language set as 'English' should set language to 'English'",
                "English",
                p.getLanguage());
        p = new SnowballPreprocessor("Finnish");

        assertEquals("Constructing with language set as 'Finnish' should set language to 'Finnish'",
                "Finnish",
                p.getLanguage());
    }

    @Test
    public void canSetLanguage() throws StemmerCreationException {
        pp.setLanguage("Finnish");

        assertEquals("Setting language to 'Finnish' should set language to 'Finnish'",
                "Finnish",
                pp.getLanguage());

        pp.setLanguage("English");

        assertEquals("Setting language to 'English' should set language to 'English'",
                "English",
                pp.getLanguage());
    }

    @Test
    public void invalidLanguageDoesNotThrowError() throws StemmerCreationException {

        pp.setLanguage("ASDSADSAd");
    }

    @Test
    public void processesEnglishSentencesCorrectly() throws StemmerCreationException {

        pp.setLanguage("English");

        assertEquals("SnowballPreprocessor should process sentences correctly",
                "THIS IS CORRECT",
                pp.process("ThiS is... CoRReCtly"));

        assertEquals("SnowballPreprocessor should process sentences correctly",
                "CAR ARE FUNNI",
                pp.process("cars are funny"));

        assertEquals("SnowballPreprocessor should process sentences correctly",
                "SENTENC WITH NUMERAL IN IT NUMERAL",
                pp.process("sentences with 123123 in it 54325234"));
    }

    @Test
    public void processesFinnishSentencesCorrectly() throws StemmerCreationException {

        pp.setLanguage("Finnish");

        assertEquals("SnowballPreprocessor should process sentences correctly",
                "AUTO ON KIVO",
                pp.process("autot on kivoja"));
    }
}
