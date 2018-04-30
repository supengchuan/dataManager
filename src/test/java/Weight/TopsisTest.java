package Weight;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/4/30 0030.
 */
public class TopsisTest extends TestCase {
    public void testM_Topsis() throws Exception {
        double[][] decision = {{2.0, 1.5, 20, 5.5, 5, 9},
                {2.5, 2.7, 18, 6.5, 3, 5},
                {1.8, 2.0, 21, 4.5, 7, 7},
                {2.2, 1.8, 20, 5.0, 5, 5}};
        double[] w = {3, 1, 2, 1, 1, 2};
        String[] impacts = {"+", "+", "+", "-", "+", "+"};
        double[] res = Topsis.m_Topsis(decision, w, impacts);

        for (double i : res) {
            System.out.println(i);
        }
    }

}