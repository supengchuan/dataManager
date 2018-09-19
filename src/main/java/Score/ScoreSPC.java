package Score;

import Preprocessor.Statistics;
import ProcessControl.SPC.XR;

/**
 * Created by supc on 2018/6/11 0011.
 */
public class ScoreSPC {
    public double m_getScore(double[][] dataSet, double Tl, double Tu) {

        XR xr = new XR();
        double S = 0;
        if (xr.getParameterXR(dataSet, Tl, Tu)) {
            double cPk = xr.getcPk();
            if (cPk > 1) {
                S = 80 * cPk;
            } else if (cPk >= 0.67) {
                S = 60 * cPk;
            } else {
                S = 0;
            }
        }

        return (S > 100) ? 100 : S;
    }
}
