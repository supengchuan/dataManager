package Weight;

import Preprocessor.Statistics;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by supc on 2018/4/30 0030.
 */
public class EntropyWeightTest extends TestCase {
    @Test
    public void testM_EntropyWeight() throws Exception {
        double[][] dataSet = {
                {91, 99, 98, 94, 95, 93},
                {100, 89, 99, 88, 82, 91},
                {99, 98, 97, 96, 95, 93},
                {89, 88, 79, 98, 87, 89}
        };

        double [] res = EntropyWeight.m_EntropyWeight(dataSet);
        for (double i : res) {
            System.out.println(i);
        }
        double sum = Statistics.getSum(res);
        System.out.print(sum);
    }

}