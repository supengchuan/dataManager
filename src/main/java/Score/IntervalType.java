package Score;

import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/1/20 0020.
 */
//区间型指标值评分;
//均值和标准准差是实测数据还是给的？？
//默认采用高斯基函数

public class IntervalType {
    private static final Logger log = Logger.getLogger(IntervalType.class);

    /*
    * dataSet: 需要被打分的数据
    * mathException： 数学期望
    * mathSD： 数学标准差
    */
    private static double gaussian(double x, double delta, double u) {
        return 1 / (Math.sqrt(2*3.14) * delta) * Math.exp(-((x - u) * (x - u)) / (2 * delta * delta));
    }


    public static double[] m_IntervalType(double[] dataSet, double mathException, double mathSD) {
        log.info("默认区间型打分方法，基于高斯基函数被调用");

        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] >= (mathException - mathSD)
                    && dataSet[i] <= (mathException + mathSD)) {
                dataSet[i] = 95 + 5 *
                        ((gaussian(dataSet[i], mathSD, mathException) - gaussian(mathException - mathSD, mathSD, mathException))
                                / (gaussian(mathException, mathSD, mathException) - gaussian(mathException - mathSD, mathSD, mathException)));
            } else {
                dataSet[i] = 0;
            }
        }

        return dataSet;
    }

    public static double[] m_IntervalTypeWithLinarBase(double[] dataSet, double mathException, double mathSD) {
        log.info("基于线性基函的区间型打分方法被调用");
        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] >= (mathException - mathSD)
                    && dataSet[i] <= (mathException + mathSD)) {
                dataSet[i] = 100 - 5 / mathSD * Math.abs(dataSet[i] - mathException);
            } else {
                dataSet[i] = 0;
            }
        }

        return dataSet;
    }

    public static double[] m_IntervalTypeWithSquareBase(double[] dataSet, double mathException, double mathSD) {
        log.info("基于二次基函的区间型打分方法被调用");
        for (int i = 0; i < dataSet.length; i++) {
            if (dataSet[i] >= (mathException - mathSD)
                    && dataSet[i] <= (mathException + mathSD)) {
                dataSet[i] = 100 - 5 / Math.pow(mathSD,2) * Math.pow((dataSet[i] - mathException), 2);
            } else {
                dataSet[i] = 0;
            }
        }
        return dataSet;
    }

}
