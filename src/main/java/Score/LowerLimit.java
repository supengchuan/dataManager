package Score;

import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/1/21 0021.
 */
//下限型指标值评分
public class LowerLimit {
    private static final Logger log = Logger.getLogger(LowerLimit.class);

    private static double gaussian(double x, double delta, double u) {
        return 1 / (Math.sqrt(2 * 3.14) * delta) * Math.exp(-((x - u) * (x - u)) / (2 * delta * delta));
    }

    private static double F(double begin, double end, double delta, double u) {
        double sum = 0.0;
        double tmp = begin;
        while (tmp < end) {
            sum += gaussian(tmp, delta, u);
            tmp += 1.0 / 1000;
        }
        return sum;
    }
    public static double[] m_LowerLimit(double[] dataSet, double Ll, double delta, double u) {
        log.info("默认高斯基函下限型打分方法被调用。");

        double sixDelta = 6 * delta;

        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] < Ll) {
                dataSet[i] = 0;
            } else if (dataSet[i] > Ll + sixDelta) {
                dataSet[i] = 100;
            } else {
                dataSet[i] = 95 + 5 * F(Ll, dataSet[i], delta, u) / F(Ll, Ll + sixDelta, delta, u);
            }
        }
        return dataSet;
    }
    public static double[] m_LowerLimitWithLinearBase(double[] dataSet, double Ll, double delta) {
        log.info("线性基函数下限型打分方法被调用。");

        double sixDelta = 6 * delta;

        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] < Ll) {
                dataSet[i] = 0;
            } else if (dataSet[i] > Ll + sixDelta) {
                dataSet[i] = 100;
            } else {
                dataSet[i] = 95 + 5 / sixDelta * (dataSet[i] - Ll);
            }
        }
        return dataSet;
    }

    private static double Square(double begin, double end, double delta, double u) {
        double sum = 0.0, tmp = begin;
        while (tmp < end) {
            sum += 100 - 5 / (36 * delta * delta) * Math.pow(tmp - u, 2);
            tmp += 1.0 / 1000;
        }
        return sum;
    }

    public static double[] m_LowerLimitWithSquareBase(double[] dataSet, double Ll, double delta, double u) {
        log.info("二次基函数下限型打分方法被调用。");

        double sixDelta = 6 * delta;

        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] < Ll) {
                dataSet[i] = 0;
            } else if (dataSet[i] > Ll + sixDelta) {
                dataSet[i] = 100;
            } else {
                dataSet[i] = 95 + 5 * Square(Ll, dataSet[i], delta, u) / Square(Ll, Ll + sixDelta, delta, u);
            }
        }
        return dataSet;
    }
}
