package ProcessControl.SPC;

import Preprocessor.Statistics;
import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/2/15 0015.
 */
public class P {
    private double uclP;
    private double clP;
    private double lclP;
    private double Cmk;
    private double [] p;

    private static final Logger log = Logger.getLogger(P.class);

    public boolean getParameterP(double[][] observation) {

        int nRow = observation.length;
        if (nRow == 0) {
            log.error("数据集为空");
            return false;
        }


        double[] rejectRatio = new double[nRow];
        for (int i = 0; i < nRow; i++) {
            rejectRatio[i] = observation[i][1] / observation[i][0];
        }
        double meanRejectRatio = Statistics.getAverage(rejectRatio);
        double[] sampleNum = new double[observation.length];
        for (int i = 0; i < nRow; i++)
            sampleNum[i] = observation[i][0];

        double nMean = Statistics.getAverage(sampleNum);
        double nMax = Statistics.getMax(sampleNum);
        double nMin = Statistics.getMin(sampleNum);
        if (!(nMax <= 2 * nMean && nMin >= nMean / 2)) {
            log.error("数据超标");
            return false;
        }
        clP = meanRejectRatio;
        uclP = clP + 3 * Math.sqrt(clP * (1 - clP) / nMean);
        lclP = clP - 3 * Math.sqrt(clP * (1 - clP) / nMean);

        double delta = Statistics.getSampleStandardDiviation(rejectRatio);
        Cmk = (uclP - lclP) / (6*delta);
        p = rejectRatio;
        return true;
    }

    public double getUclP() {
        return uclP;
    }

    public double getClP() {
        return clP;
    }

    public double getLclP() {
        return lclP;
    }

    public double getCmk() {
        return Cmk;
    }

    public double[] getP() {
        return p;
    }
}
