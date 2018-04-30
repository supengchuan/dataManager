package ProductQuality;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/4/22 0022.
 */
public class TwoWayANOVATest extends TestCase {
    public void testM_TwoWayANOVA() throws Exception {
        double[][] dataSet = {
                {365,350,342,340,323},
                {345,368,363,330,333},
                {358,323,353,343,308},
                {288,280,298,260,298}
        };
        TwoWayANOVA two = new TwoWayANOVA();
        boolean isDifferent = two.m_TwoWayANOVA(dataSet);
        System.out.println(isDifferent);
    }

}