package loez.nllr.preprocessor.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PunctuationTest {
    private final Punctuation p = new Punctuation();

    @Test
    public void testRemove() {

        assertEquals("remove() should remove all punctuation",
                "sana  sana",
                p.remove("sana. , sana"));
        assertEquals("replace() should replace all punctuation",
                "sana PUNCT sana",
                p.replace("sana !\"#!# sana", "PUNCT"));
    }
}
