/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package loez.nllr.reader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import loez.nllr.preprocessor.PreProcessor;
import loez.nllr.preprocessor.SimplePreprocessor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ljleppan
 */
public class CorpusReaderTest {
    private CorpusReader cr;
    private DateFormat df;
    private PreProcessor pp;
    
    @Before
    public void setUp() {
        df = new SimpleDateFormat("d-MMM-yyyy", Locale.US); //8-APR-1987 01:12:53.29
        pp = new SimplePreprocessor();
        cr = new CorpusReader();
    }

    @Test
    public void testReading(){
        
    }
}
