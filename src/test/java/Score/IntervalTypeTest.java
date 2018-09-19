package Score;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by supc on 2018/4/30 0030.
 */
public class IntervalTypeTest extends TestCase {

    public void testM_IntervalType() throws Exception {
        double [] dataSet = {30, 29, 29.5, 29.6, 29,7, 31, 30.5};
        dataSet = IntervalType.m_IntervalType(dataSet, 30, 1);
        for (double i: dataSet
             ) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void testM_IntervalTypeWithLinarBase() throws Exception {
        double [] dataSet = {30, 29, 29.5, 29.6, 29,7, 31, 30.5};
        dataSet = IntervalType.m_IntervalTypeWithLinarBase(dataSet, 30,1);
        for (double i: dataSet
                ) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void testM_IntervalTypeWithSquareBase() throws Exception {
        double [] dataSet = {30, 29, 29.5, 29.6, 29,7, 31, 30.5};
        dataSet = IntervalType.m_IntervalTypeWithSquareBase(dataSet, 30,1);
        for (double i: dataSet
                ) {
            System.out.print(i + " ");
        }
        System.out.println();
    }



}