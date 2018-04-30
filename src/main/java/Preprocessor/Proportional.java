package Preprocessor;

import org.apache.log4j.Logger;
import weka.core.Instances;

/**
 * Created by supc on 2018/1/20 0020.
 */

//比例变化
public class Proportional {
    private static final Logger log = Logger.getLogger(Proportional.class);
    public static Instances m_Proportional(Instances ins) {
        double[] dataSet = new double[ins.numInstances()];
        for (int j = 0; j < ins.numAttributes() - 1; j++) {
            for (int i = 0; i < ins.numInstances(); i++) {
                dataSet[i] = ins.instance(i).value(j);
            }
            double max = Statistics.getMax(dataSet);

            //望大特性
            for (int i = 0; i < ins.numInstances(); i++) {
                ins.instance(i).setValue(j, dataSet[i] / max);
            }
        }

        return ins;
    }

    public static double[] m_Proportional(double[] dataSet) {
        log.info("比例变换标准化方法被调用");
        double max = Statistics.getMax(dataSet);
        for (int i = 0; i < dataSet.length; i++) {
            dataSet[i] /= max;
        }

        return dataSet;
    }
}
