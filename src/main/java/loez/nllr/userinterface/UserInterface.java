package loez.nllr.userinterface;

import loez.nllr.datastructure.ArrayList;
import loez.nllr.preprocessor.PreProcessor;

/**
 * A generic user interface
 * @author ljleppan
 */
public interface UserInterface {

    /**
     * Starts up the user interface.
     */
    public void run();

    /**
     * Sets up possible preprocessors and their names.
     * A preprocessor and its name should have same index in both lists.
     * @param preProcessors     ArrayList of preprocessors
     * @param preProcessorNames ArrayList of names of preprocessor
     */
    public void setupPreprocessors(ArrayList<PreProcessor> preProcessors, ArrayList<String> preProcessorNames);
}
