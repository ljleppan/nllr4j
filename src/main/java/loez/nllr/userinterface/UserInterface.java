package loez.nllr.userinterface;

import java.util.List;
import loez.nllr.preprocessor.PreProcessor;

/**
 * A generic user interface.
 * @author ljleppan
 */
public interface UserInterface {

    /**
     * Starts up the user interface.
     */
    void run();

    /**
     * Sets up possible preprocessors and their names.
     * A preprocessor and its name should have same index in both lists.
     * @param preProcessors     ArrayList of preprocessors
     * @param preProcessorNames ArrayList of names of preprocessor
     */
    void setupPreprocessors(List<PreProcessor> preProcessors, List<String> preProcessorNames);
}
