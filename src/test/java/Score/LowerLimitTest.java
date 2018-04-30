package Score;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/4/30 0030.
 */
public class LowerLimitTest extends TestCase {
    public void testM_LowerLimit() throws Exception {
        double [] dataSet = {110, 120, 121,125,127,129,131,132,133};
        dataSet = LowerLimit.m_LowerLimit(dataSet, 120, 2, 125);
        for (double i : dataSet)
            System.out.print(i + " ");
        System.out.println();
    }

    public void testM_LowerLimitWithLinearBase() throws Exception {
        double [] dataSet = {110, 120, 121,125,127,129,131,132,133};
        dataSet = LowerLimit.m_LowerLimitWithLinearBase(dataSet, 120, 2);
        for (double i : dataSet)
            System.out.print(i + " ");
        System.out.println();
    }

    public void testM_LowerLimitWithSquareBase() throws Exception {
        double [] dataSet = {110, 120, 121,125,127,129,131,132,133};
        dataSet = LowerLimit.m_LowerLimitWithSquareBase(dataSet, 120, 2, 125);
        for (double i : dataSet)
            System.out.print(i + " ");
        System.out.println();
    }

}