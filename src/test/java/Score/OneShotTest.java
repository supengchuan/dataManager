package Score;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class OneShotTest extends TestCase {
    public void testM_OneShot() throws Exception {
        double[] a = {1,0, 1, 1, 0, 1, 1, 0, 1, 1};
        double [] res = OneShot.m_OneShot(a);
        for (double i : res)
            System.out.print(i + " ");
        System.out.println();
    }

}