package loez.nllr.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the argmax algorithm.
 * @author loezi
 * @param <T>
 */
public class Argmax<T> {

    /**
     * An argument - result pair.
     * @param <T>
     */
    public static class Result<T> {

        private final T argument;
        private final double value;

        /**
         * Creates a new result.
         * @param argument  The argument that was used
         * @param value     The result value that was gotten
         */
        public Result(final T argument, final double value) {

            this.argument = argument;
            this.value = value;
        }

        /**
         * @return the argument that was used
         */
        public T getArgument() {

            return argument;
        }

        /**
         * @return the value that was returned from the algorithm
         */
        public double getValue() {

            return value;
        }
    }

    /**
     * Calculates argmax for given algorithm and arguments.
     * The method call made will be algorithm(arg, constants) where arg is from args and constants is the array of constants.
     * @param algorithm Algorithm
     * @param args      A list of changing arguments
     * @param constants An array of contants.
     * @return
     */
    public Result<T> single(final Algorithm algorithm, final List<T> args, final Object[] constants) {

        final Object[] argList = new Object[constants.length + 1];
        System.arraycopy(constants, 0, argList, 1, constants.length);

        argList[0] = args.get(0);

        double maxVal = algorithm.calculate(argList);
        T maxArg = args.get(0);

        for (int i = 1; i < args.size(); i++) {
            argList[0] = args.get(i);
            final double result = algorithm.calculate(argList);
            if (maxVal < result) {
                maxVal = result;
                maxArg = args.get(i);
            }
        }

        return new Result(maxArg, maxVal);
    }

    /**
     * Calculates argmax for given algorithm and arguments, returning AMOUNT highest arguments.
     * @param algorithm The algorithm
     * @param amount    Number of results to return
     * @param args      Arguments to iterate over
     * @param constants The constants of the algorithm call
     * @return          A list of the AMOUNT best results
     */
    public List<Result<T>> multiple(final Algorithm algorithm, final int amount, final List<T> args, final Object[] constants) {

        final List<Result<T>> results = new ArrayList<>();

        final Object[] argList = new Object[constants.length + 1];
        System.arraycopy(constants, 0, argList, 1, constants.length);

        for (T arg : args) {

            argList[0] = arg;
            final double result = algorithm.calculate(argList);

            if (results.size() < amount) {
                results.add(new Result(arg, result));
                sort(results);
            } else if (results.get(amount - 1).getValue() < result) {
                results.remove(amount - 1);
                results.add(new Result(arg, result));
                sort(results);
            }
        }

        return results;
    }


    private void sort(final List<Result<T>> results) {

        for (int i = 1; i < results.size(); i++) {

            final Result x = results.get(i);

            int j = i;
            while (j > 0 && results.get(j - 1).getValue() < x.getValue()) {
                results.set(j, results.get(j - 1));
                j = j - 1;
            }

            results.set(j, x);
        }
    }
}
