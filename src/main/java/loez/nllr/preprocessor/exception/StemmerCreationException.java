package loez.nllr.preprocessor.exception;

/**
 * An exception thrown when a problem was found while creating or setting up a stemmer.
 * @author ljleppan
 */
public class StemmerCreationException extends Exception {

    /**
     * Creates a new StemmerCreationException with a message
     * @param message   The message
     */
    public StemmerCreationException(String message) {
        super(message);
    }
    
    /**
     * Creates a new StemmerCreationException.
     */
    public StemmerCreationException(){
        super();
    }
}
