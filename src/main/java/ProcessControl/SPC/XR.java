package ProcessControl.SPC;

import Preprocessor.Statistics;
import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/2/9 0009.
 * 均值极差控制图，调用需要传入的参数有三个
 *
 *  @observation ：观测值，比如25个样本，每个样本有n个观测值n>=2
 *  @Tl： 质量规范的下限
 *  @Tu： 质量规范的上限
 */
public class XR {
    //系数表
    public static final double[] A2 = {1.88, 1.023, 0.729, 0.557, 0.483, 0.419, 0.373, 0.337, 0.308, 0.285, 0.266,
            0.249, 0.235, 0.223, 0.212, 0.203, 0.194, 0.187, 0.18, 0.173, 0.167, 0.162, 0.157, 0.153};

    public static final double[] d2 = {1.128, 1.693, 2.059, 2.326, 2.543, 2.704, 2.847, 2.97, 3.078, 3.173, 3.258,
            3.336, 3.407, 3.472, 3.532, 3.588, 3.64, 3.689, 3.735, 3.778, 3.819, 3.858, 3.895, 3.931};

    public static final double[] D3 = {0, 0, 0, 0, 0, 0.076, 0.136, 0.184, 0.223, 0.256, 0.283, 0.307, 0.328, 0.347,
            0.363, 0.378, 0.391, 0.403, 0.415, 0.425, 0.434, 0.443, 0.451, 0.459};

    public static final double[] D4 = {3.267, 2.571, 2.282, 2.114, 2.004, 1.924, 1.864, 1.816, 1.777, 1.744, 1.717,
            1.693, 1.672, 1.653, 1.637, 1.622, 1.608, 1.597, 1.585, 1.575, 1.566, 1.557, 1.548, 1.541};

    private double uclR;
    private double clR;
    private double lclR;
    private double uclX;
    private double clX;
    private double lclX;
    private double cP;
    private double cPk;

    private double [] X;
    private double [] R;


    private void init() {
        cP = 0;
        cPk = 0;

    }

    private static final Logger log= Logger.getLogger(XR.class);
    /*功能：根据数据获取XR控制图的参数
    *@observation ：观测值
    * @Tl：质量规范的下限
    * @Tu质量规范的上限
     */
    public boolean getParameterXR(double[][] observation, double Tl, double Tu) {
        int nRow = observation.length;
        if (nRow == 0) {

            return false;
        }
        int nColumn = observation[0].length;
        double factD3, factD4, factA2;
        init();
        while (true) {
            nRow = observation.length;
            double[] meanValue = new double[nRow];
            double[] range = new double[nRow];

            //获取均值和极差
            for (int i = 0; i < nRow; i++) {
                meanValue[i] = Statistics.getAverage(observation[i]);
                double max = Statistics.getMax(observation[i]);
                double min = Statistics.getMin(observation[i]);
                range[i] = max - min;
            }

            //如果一个样本的数据只有一份，则无法进行spc控制
            if (nColumn < 2) {
                log.error("一个样本的数据只有一份，则无法进行spc控制");
                return false;
            }


            factD3 = D3[nColumn - 2];
            factD4 = D4[nColumn - 2];

            clR = Statistics.getAverage(range);
            uclR = factD4 * clR;
            lclR = factD3 * clR;
            if (!isRangeStable(range, nRow)) {
                observation = deleteObservationByRange(observation, range);
                continue;
            }
            //计算X图参数；
            factA2 = A2[nColumn - 2];
            clX = Statistics.getAverage(meanValue);
            uclX = clX + factA2 * uclR;
            lclX = clX - factA2 * uclR;
            if (!isMeanStable(meanValue, nRow)) {
                observation = deleteObservationByMean(observation, meanValue);
                continue;
            }

            double delta = clR / d2[nColumn - 2];
            cP = (Tu - Tl) / (6 * delta);
            double M = (Tu + Tl) / 2;
            if (clX != M) {
                double K = Math.abs(M - clX) / ((Tu - Tl) / 2);
                cPk = (1 - K) * cP;
            }

            X = meanValue;
            R = range;
            break;
        }


        return true;
    }

    private boolean isRangeStable(double[] range, int length) {
        for (int i = 0; i < length; i++) {
            if (range[i] < lclR || range[i] > uclR)
                return false;
        }

        return true;
    }

    private double[][] deleteObservationByRange(double[][] observation, double[] range) {
        int nRow = observation.length;
        int nColumn = observation[0].length;
        for (int i = 0; i < observation.length; i++) {
            if (range[i] < lclR || range[i] > uclR)
                nRow--;
        }
        double[][] res = new double[nRow][nColumn];
        int k = 0;
        for (int i = 0; i < observation.length; i++) {
            if (range[i] >= lclR && range[i] <= uclR) {
                res[k++] = observation[i];
            }
        }
        return res;
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

    private boolean isMeanStable(double[] mean, int length) {
        for (int i = 0; i < length; i++) {
            if (mean[i] < lclX || mean[i] > uclX)
                return false;
        }
        return true;
    }

    public double getUclR() {
        return uclR;
    }

    public double getClR() {
        return clR;
    }

    public double getLclR() {
        return lclR;
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

    public double[] getR() {
        return R;
    }
}
