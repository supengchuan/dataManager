package Score;

import Preprocessor.Statistics;
import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/1/21 0021.
 */
//上限型指标值评分
public class UpperLimit {
    private static final Logger log = Logger.getLogger(UpperLimit.class);

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

    public static double[] m_UpperLimit(double[] dataSet, double Lu, double delta, double u) {
        log.info("默认方法基于高斯基函数上限型打分方法被调用");

        double sixDelta = 6 * delta;

        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] > Lu) {
                dataSet[i] = 0;
            } else if (dataSet[i] < Lu - sixDelta) {
                dataSet[i] = 100;
            } else {
                dataSet[i] = 95 + 5 * F(dataSet[i], Lu, delta, u) / F(Lu - sixDelta, Lu, delta, u);
            }
        }

        return dataSet;
    }

    public static double[] m_UpperLimitWithLinearBase(double[] dataSet, double Lu, double delta) {
        log.info("基于线性基函数上限型打分方法被调用");

        double sixDelta = 6 * delta;
        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] > Lu) {
                dataSet[i] = 0;
            } else if (dataSet[i] < Lu - sixDelta) {
                dataSet[i] = 100;
            } else {
                dataSet[i] = 95 + 5 / sixDelta * (Lu - dataSet[i]);
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

    public static double[] m_UpperLimitWithSquareBase(double[] dataSet, double Lu, double delta, double u) {
        log.info("基于二次基函数上限型打分方法被调用");

        double sixDelta = 6 * delta;
        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] > Lu) {
                dataSet[i] = 0;
            } else if (dataSet[i] < Lu - sixDelta) {
                dataSet[i] = 100;
            } else {
                dataSet[i] = 95 + 5 * Square(dataSet[i], Lu, delta, u) / Square(Lu - sixDelta, Lu, delta, u);
            }
        }

        return dataSet;
    }

    public static double m_UpperLimitWithDEA(double upper, double[] dataSet) {
        double min = Statistics.getMin(dataSet);
        double max = Statistics.getMax(dataSet);
        double mean = Statistics.getAverage(dataSet);

        double S = 95 * (1 + (1 - max / upper) * (1 - mean / upper));
        if (S > 100) S = 100;

        return S;
    }
}
