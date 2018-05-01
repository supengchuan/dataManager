package Weight;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class StandardDeviationTest extends TestCase {
    public void testM_StandardDeviation() throws Exception {
        double[][] dataSet = {
                {91, 99, 98, 94, 95, 93},
                {100, 89, 99, 88, 82, 91},
                {99, 98, 97, 96, 95, 93},
                {89, 88, 79, 98, 87, 89}
        };
        double[] res = StandardDeviation.m_StandardDeviation(dataSet);
        for (double i : res)
            System.out.print(i + " ");
        System.out.println();
    }

}