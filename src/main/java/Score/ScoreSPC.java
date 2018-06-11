package Score;

import Preprocessor.Statistics;
import ProcessControl.SPC.XR;

/**
 * Created by supc on 2018/6/11 0011.
 */
public class ScoreSPC {
    public double m_getScore(double[] dataSet) {
        int len = dataSet.length;
        if (len > 100) len = 100;

        int n = len / 5;
        int k = 0;
        double[][] observation = new double[n][5];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 5; j++) {
                observation[i][j] = dataSet[k++];
            }
        }
        XR xr = new XR();
        double S = 0;
        double max = Statistics.getMax(dataSet);
        double min = Statistics.getMin(dataSet);
        if (xr.getParameterXR(observation, min, max)) {
            double cPk = xr.getcPk();
            if (cPk > 1) {
                S = 80 * cPk;
            } else if (cPk < 0.67) {
                S = 60 * cPk;
            } else {
                S = 0;
            }
        }

        return S;
    }
}
