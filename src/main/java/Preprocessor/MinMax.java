package Preprocessor;

import org.apache.log4j.Logger;
import weka.core.Instances;

/**
 * Created by supc on 2018/1/7 0007.
 */

//Min-Max标准化
public class MinMax {
    private static final Logger log = Logger.getLogger(MinMax.class);
    public static Instances m_MinMax(Instances ins) {
        double[] minData = new double[ins.numAttributes() - 1];
        double[] maxData = new double[ins.numAttributes() - 1];

        for (int i = 0; i < minData.length; i++) {
            minData[i] = Double.MAX_VALUE;
            maxData[i] = Double.MIN_VALUE;
        }


        //get the max and min of each attribute
        for (int i = 0; i < ins.numInstances(); i++) {
            for (int j = 0; j < ins.numAttributes() - 1; j++) {
                if (ins.instance(i).value(j) < minData[j])
                    minData[j] = ins.instance(i).value(j);

                if (ins.instance(i).value(j) > maxData[j])
                    maxData[j] = ins.instance(i).value(j);
            }
        }

        //normalization
        for (int i = 0; i < ins.numInstances(); i++) {
            for (int j = 0; j < ins.numAttributes() - 1; j++) {
                double tmp = (ins.instance(i).value(j) - minData[j]) / (maxData[j] - minData[j]);
                ins.instance(i).setValue(j, tmp);

            }
        }

        return ins;
    }

    public static double[] m_MinMax(double[] dataSet) {
        log.info("MIN_MAX标准化方法被调用");
        double min = Statistics.getMin(dataSet);
        double max = Statistics.getMax(dataSet);

        for (int i = 0; i < dataSet.length; i++) {
            dataSet[i] = (dataSet[i] - min) / (max - min);
        }

        return dataSet;
    }
}
