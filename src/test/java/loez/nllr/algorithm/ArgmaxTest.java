package loez.nllr.algorithm;

import java.util.ArrayList;
import loez.nllr.algorithm.Argmax.Result;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author loezi
 */
public class ArgmaxTest {

    public class Same implements Algorithm{
        @Override
        public double calculate(Object[] args){
            return ((double) args[0]) / 2;
        }
    }

    @Test
    public void argmaxTest(){
        ArrayList<Double> args = new ArrayList<>();
        args.add(2.0);
        args.add(1.0);
        args.add(1.5);

        Object[] constants = new Object[]{1};

        Result<Double> max = new Argmax().single(new Same(), args, constants);

        assertEquals("argmax result should have corret argument",
                2.0, max.getArgument(), 0.001);

        assertEquals("argmax result should have correct value",
                1.0, max.getValue(), 0.001);
    }

    @Test
    public void argmaxTestMultiple(){
        ArrayList<Double> args = new ArrayList<>();
        args.add(2.0);
        args.add(1.0);
        args.add(1.5);
        args.add(2.5);
        args.add(2.5);
        args.add(100.0);
        args.add(0.0);
        args.add(-15.0);

        Object[] constants = new Object[]{1};

        ArrayList<Result<Double>> results = new Argmax().multiple(new Same(), 5, args, constants);

        assertEquals("argmax multiple should return correct arguments in correct order",
                100.0, results.get(0).getArgument(), 0.001);
        assertEquals("argmax multiple should return correct arguments in correct order",
                2.5, results.get(1).getArgument(), 0.001);
        assertEquals("argmax multiple should return correct arguments in correct order",
                2.5, results.get(2).getArgument(), 0.001);
        assertEquals("argmax multiple should return correct arguments in correct order",
                2.0, results.get(3).getArgument(), 0.001);
        assertEquals("argmax multiple should return correct arguments in correct order",
                1.5, results.get(4).getArgument(), 0.001);

        assertEquals("argmax multiple should return correct values in correct order",
                50.0, results.get(0).getValue(), 0.001);
        assertEquals("argmax multiple should return correct values in correct order",
                1.25, results.get(1).getValue(), 0.001);
        assertEquals("argmax multiple should return correct values in correct order",
                1.25, results.get(2).getValue(), 0.001);
        assertEquals("argmax multiple should return correct values in correct order",
                1.0, results.get(3).getValue(), 0.001);
        assertEquals("argmax multiple should return correct values in correct order",
                0.75, results.get(4).getValue(), 0.001);
    }

}
