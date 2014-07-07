package loez.nllr;

import loez.nllr.datastructure.ArrayList;
import loez.nllr.preprocessor.PreProcessor;
import loez.nllr.preprocessor.SimplePreprocessor;
import loez.nllr.preprocessor.SnowballPreprocessor;
import loez.nllr.preprocessor.exception.StemmerCreationException;
import loez.nllr.userinterface.CommandLineInterface;
import loez.nllr.userinterface.UserInterface;

public class Main 
{

    public static void main( String[] args ) throws StemmerCreationException
    {       
        //Add preprocessors
        ArrayList<String> ppNames = new ArrayList<>();
        ArrayList<PreProcessor> pps = new ArrayList<>();
        
        ppNames.add("");
        pps.add(new SimplePreprocessor());
        
        ppNames.add("simple");
        pps.add(new SimplePreprocessor());
        
        try {
            pps.add(new SnowballPreprocessor());
            ppNames.add("snowball");
        } catch (StemmerCreationException e) { }
        
        //Initialize UI
        UserInterface ui = new CommandLineInterface();
        ui.setupPreprocessors(pps, ppNames);
        ui.run();
    }
}
