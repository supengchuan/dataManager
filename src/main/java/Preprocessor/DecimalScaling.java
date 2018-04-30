package Preprocessor;

import Score.DescribeType;
import org.apache.log4j.Logger;
import weka.core.Instances;

/**
 * Created by supc on 2018/1/20 0020.
 */
//Decimal Scaling小数定标标准化
public class DecimalScaling {
    private static final Logger log = Logger.getLogger(DecimalScaling.class);

    public static Instances m_DecimalScaling(Instances ins) {
        double[] dataSet = new double[ins.numInstances()];

        for (int j = 0; j < ins.numAttributes() - 1; j++) {
            for (int i = 0; i < ins.numInstances(); i++) {
                dataSet[i] = ins.instance(i).value(j);
            }

            double min = Statistics.getMin(dataSet);
            double max = Statistics.getMax(dataSet);
            double absMax = Math.abs(min) > Math.abs(max) ? Math.abs(min) : Math.abs(max);

            double scaling = 1;

            while (scaling < absMax) {
                scaling *= 10;
            }

            for (int i = 0; i < ins.numInstances(); i++) {
                ins.instance(i).setValue(j, dataSet[i] / scaling);
            }
        }

        return ins;
    }

    public static double[] m_DecimalScaling(double[] dataSet) {
        log.info("小数定标标准化方法被调用");
        double max = Statistics.getMax(dataSet);
        double min = Statistics.getMin(dataSet);
        double absMax = Math.abs(min) > Math.abs(max) ? Math.abs(min) : Math.abs(max);
        double scaling = 1;

        while (scaling < absMax) {
            scaling *= 10;
        }

        for (int i = 0; i < dataSet.length; i++) {
            dataSet[i] /= scaling;
        }
        return dataSet;
    }

}
