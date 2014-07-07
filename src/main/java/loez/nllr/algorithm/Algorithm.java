
package loez.nllr.algorithm;

/**
 * Interface for all algorithms
 * @author loezi
 */
public interface Algorithm {

    /**
     * Calculates the algorithm with given arguments.
     * Argument count must match algorithm argument count.
     * @param args  Arguments
     * @return      Algorithm result.
     */
    public double calculate(Object[] args);
}
