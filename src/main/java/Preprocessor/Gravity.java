package Preprocessor;

import org.apache.log4j.Logger;
import weka.core.Instances;

/**
 * Created by supc on 2018/1/20 0020.
 */

//比重法
public class Gravity {
    private static final Logger log = Logger.getLogger(Gravity.class);

    public static Instances m_Gravity(Instances ins) {
        double[] dataSet = new double[ins.numInstances()];
        for (int j = 0; j < ins.numAttributes() - 1; j++) {
            for (int i = 0; i < ins.numInstances(); i++) {
                dataSet[i] = ins.instance(i).value(j);
            }

            double squareSum = Statistics.getSquareSum(dataSet);

            for (int i = 0; i < ins.numInstances(); i++) {
                ins.instance(i).setValue(j, dataSet[i] / Math.sqrt(squareSum));
            }
        }

        return ins;
    }

    public static double []  m_Gravity(double []  dataSet) {
        log.info("比重法标准化方法被调用");
        double squareSum = Statistics.getSquareSum(dataSet);
        for (int i = 0; i < dataSet.length; i++) {
            dataSet[i] /= Math.sqrt(squareSum);
        }

        return dataSet;
    }
}
