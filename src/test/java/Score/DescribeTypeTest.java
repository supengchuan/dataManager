package Score;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class DescribeTypeTest extends TestCase {
    public void testM_DescribeType() throws Exception {
        String[] describe = {"极好", "好", "一般", "差", "极差"};
        double[] score = {100, 90, 80, 70, 60};
        String[] dataSet = {"好", "极好", "好", "一般", "好", "一般", "差", "极差", "差", "差", "极差"};
        double[] res = DescribeType.m_DescribeType(describe, score, dataSet);
        for (double i : res)
            System.out.print(i + " ");
        System.out.println();
    }

}