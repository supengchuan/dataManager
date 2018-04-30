package Score;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/4/30 0030.
 */
public class UpperLimitTest extends TestCase {
    public void testM_UpperLimit() throws Exception {
        double [] dataSet = {150, 140, 127, 128,129, 135, 139};
        dataSet = UpperLimit.m_UpperLimit(dataSet, 140, 2, 138);
        for (double i : dataSet)
            System.out.print(i + " ");

        System.out.println();
    }

    public void testM_UpperLimitWithLinearBase() throws Exception {
        double [] dataSet = {150, 140, 127, 128,129, 135, 139};
        dataSet = UpperLimit.m_UpperLimitWithLinearBase(dataSet, 140, 2);
        for (double i : dataSet)
            System.out.print(i + " ");

        System.out.println();
    }

    public void testM_UpperLimitWithSquareBase() throws Exception {
        double [] dataSet = {150, 140, 127, 128,129, 135, 139};
        dataSet = UpperLimit.m_UpperLimitWithSquareBase(dataSet, 140, 2, 138);
        for (double i : dataSet)
            System.out.print(i + " ");

        System.out.println();
    }

}