package ProcessControl.SPC;

import Preprocessor.Statistics;
import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/2/10 0010.
 */
public class XS {
    public static final double[] B4 = {3.276, 2.568, 2.266, 2.089, 1.97, 1.882, 1.815, 1.761, 1.716, 1.679, 1.64,
            1.618, 1.594, 1.572, 1.552, 1.534, 1.518, 1.503, 1.49, 1.477, 1.466, 1.455, 1.445, 1.435};

    public static final double[] B3 = {0, 0, 0, 0, 0.03, 0.118, 0.185, 0.239, 0.284, 0.321, 0.354, 0.382, 0.406,
            0.428, 0.448, 0.446, 0.482, 0.497, 0.51, 0.523, 0.534, 0.545, 0.555, 0.565};

    public static final double[] A3 = {2.659, 1.954, 1.628, 1.427, 1.287, 1.182, 1.099, 1.032, 0.975, 0.927, 0.886,
            0.85, 0.817, 0.789, 0.763, 0.736, 0.718, 0.698, 0.68, 0.663, 0.647, 0.633, 0.619, 0.606};

    public static final double[] c4 = {0.7979, 0.8862, 0.9213, 0.94, 0.9515, 0.9594, 0.965, 0.9693, 0.9727, 0.9754,
            0.9776, 0.9794, 0.981, 0.9823, 0.9835, 0.9845, 0.9854, 0.9862, 0.9869, 0.9876, 0.9882, 0.9887, 0.9892, 0.9896};

    private double uclS;
    private double clS;
    private double lclS;
    private double uclX;
    private double clX;
    private double lclX;
    private double cP;
    private double cPk;

    private double[] X;
    private double[] S;

    private void init() {
        cP = 0;
        cPk = 0;

    }

    private static final Logger log = Logger.getLogger(XR.class);

    public boolean getParameterXR(double[][] observation, double Tl, double Tu) {
        int nRow = observation.length;
        if (nRow == 0) {
            log.error("数据集为空");
            return false;
        }
        int nColumn = observation[0].length;
        double factB4, factB3, factA3;

        init();

        while (true) {
            nRow = observation.length;
            double[] meanValue = new double[nRow];
            double[] sd = new double[nRow];
            //获取均值和标准差
            for (int i = 0; i < nRow; i++) {
                meanValue[i] = Statistics.getAverage(observation[i]);
                sd[i] = Statistics.getSampleStandardDiviation(observation[i]);
            }

            //如果一个样本的数据只有一份，则无法进行spc控制
            if (nColumn < 2) {
                log.error("一个样本的数据只有一份，则无法进行spc控制");
                return false;
            }


            factB4 = B4[nColumn - 2];
            factB3 = B3[nColumn - 2];

            clS = Statistics.getAverage(sd);
            uclS = factB4 * clS;
            lclS = factB3 * clS;
            if (!isStandardDeviationStable(sd, nRow)) {
                observation = deleteObservationBySD(observation, sd);
                continue;
            }
            //计算X图参数；
            factA3 = A3[nColumn - 2];
            clX = Statistics.getAverage(meanValue);
            uclX = clX + factA3 * clS;
            lclX = clX - factA3 * clS;
            if (!isMeanStable(meanValue, nRow)) {
                observation = deleteObservationByMean(observation, meanValue);
                continue;
            }
            double delta = clS / c4[nColumn - 2];
            cP = (Tu - Tl) / (6 * delta);
            double M = (Tu + Tl) / 2;
            if (clX != M) {
                double K = Math.abs(M - clX) / ((Tu - Tl) / 2);
                cPk = (1 - K) * cP;
            }

            X = meanValue;
            S = sd;
            break;
        }

        return true;
    }

    private boolean isStandardDeviationStable(double[] SD, int length) {
        for (int i = 0; i < length; i++) {
            if (SD[i] < lclS || SD[i] > uclS)
                return false;
        }
        return true;
    }

    private double[][] deleteObservationBySD(double[][] observation, double[] SD) {
        int nRow = observation.length;
        int nColumn = observation[0].length;
        for (int i = 0; i < observation.length; i++) {
            if (SD[i] < lclS || SD[i] > uclS)
                nRow--;
        }
        double[][] res = new double[nRow][nColumn];
        int k = 0;
        for (int i = 0; i < observation.length; i++) {
            if (SD[i] <= uclS && SD[i] >= lclS)
                res[k++] = observation[i];
        }
        return res;
    }

    private boolean isMeanStable(double[] mean, int length) {
        for (int i = 0; i < length; i++) {
            if (mean[i] < lclX || mean[i] > uclX)
                return false;
        }
        return true;
    }

    private double[][] deleteObservationByMean(double[][] observation, double[] mean) {
        int nRow = observation.length;
        int nColumn = observation[0].length;
        for (int i = 0; i < observation.length; i++) {
            if (mean[i] < lclX || mean[i] > uclX)
                nRow--;
        }
        double[][] res = new double[nRow][nColumn];
        int k = 0;
        for (int i = 0; i < observation.length; i++) {
            if (mean[i] >= lclX && mean[i] <= uclX)
                res[k++] = observation[i];
        }

        return res;
    }


    public double getUclS() {
        return uclS;
    }

    public double getClS() {
        return clS;
    }

    public double getLclS() {
        return lclS;
    }

    public double getUclX() {
        return uclX;
    }

    public double getClX() {
        return clX;
    }

    public double getLclX() {
        return lclX;
    }

    public double getcP() {
        return cP;
    }

    public double getcPk() {
        return cPk;
    }

    public double[] getX() {
        return X;
    }

    public double[] getS() {
        return S;
    }
}
