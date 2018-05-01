package ProductQuality;

import junit.framework.TestCase;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class LogisticRegressionTest extends TestCase {
    public void testM_LogisticRegression() throws Exception {
        double[][] X = {{165, 166, 166, 165, 165},
                {167, 163, 169, 161, 169},
                {167, 164, 163, 163, 166},
                {166, 163, 164, 164, 167},
                {169, 170, 160, 166, 166},
                {160, 169, 160, 160, 165},
                {164, 163, 167, 170, 161},
                {166, 163, 168, 165, 161},
                {162, 161, 165, 161, 160},
                {161, 161, 160, 169, 169},
                {160, 167, 168, 167, 166},
                {164, 160, 167, 169, 161},
                {164, 164, 166, 165, 168},
                {160, 170, 165, 166, 169},
                {172, 172, 172, 172, 172}
        };
        double[] y = {1, 1, -1, 1, 1, -1, 1, 1, 1, -1, 1, 1, -1, 1, -1, -1};
        double[] res = LogisticRegression.m_LogisticRegression(X, y);
        for (double i : res)
            System.out.print(i + " ");
        System.out.println();
    }

}