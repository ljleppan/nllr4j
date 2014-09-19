package loez.nllr;

import java.util.ArrayList;
import java.util.List;

import loez.nllr.preprocessor.PreProcessor;
import loez.nllr.preprocessor.SimplePreprocessor;
import loez.nllr.preprocessor.SnowballPreprocessor;
import loez.nllr.preprocessor.exception.StemmerCreationException;
import loez.nllr.userinterface.CommandLineInterface;
import loez.nllr.userinterface.UserInterface;

public class Main {

    public static void main(final String[] args) throws StemmerCreationException {

        //Add preprocessors
        final List<String> ppNames = new ArrayList<>();
        final List<PreProcessor> pps = new ArrayList<>();

        ppNames.add("");
        pps.add(new SimplePreprocessor());

        ppNames.add("simple");
        pps.add(new SimplePreprocessor());

        try {
            pps.add(new SnowballPreprocessor());
            ppNames.add("snowball");
        } finally {
            final UserInterface ui = new CommandLineInterface();
            ui.setupPreprocessors(pps, ppNames);
            ui.run();
        }
    }
}
