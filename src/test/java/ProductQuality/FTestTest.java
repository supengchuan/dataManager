package ProductQuality;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class FTestTest extends TestCase {
    public void testM_FTest() throws Exception {
        double [] sampleOne = {10,11,17,13,9,9,6};
        double [] sampleTwo = {9,7,5,11,13};

        boolean isDifferent = FTest.m_FTest(sampleOne,sampleTwo);
        System.out.println(isDifferent);
    }

}