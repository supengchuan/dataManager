package ProductQuality;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/4/22 0022.
 */
public class OneWayANOVATest extends TestCase {
    public void testM_OneWayANOVA() throws Exception {
        double[][] dataSet = {
                {29.6, 27.3, 5.8, 21.6, 29.2},
                {24.3, 32.6, 6.2, 17.4, 32.8},
                {28.5, 30.8, 11, 18.3, 25},
                {32, 34.8, 8.3, 19, 24.2}
        };
        OneWayANOVA one = new OneWayANOVA();
        boolean isDifferent = one.m_OneWayANOVA(dataSet);

        System.out.println(isDifferent);

    }

}