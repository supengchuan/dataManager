package Preprocessor;

import org.apache.log4j.Logger;
import weka.core.Instances;

/**
 * Created by supc on 2018/1/20 0020.
 */

//Z-Score标准化
public class ZScore {
    private static final Logger log = Logger.getLogger(ZScore.class);

    public static Instances m_ZScore(Instances ins) {
        int items = ins.numInstances();
        double[] dataSet = new double[items];
        for (int j = 0; j < ins.numAttributes() - 1; j++) {
            for (int i = 0; i < ins.numInstances(); i++) {
                dataSet[i] = ins.instance(i).value(j);
            }
            double average = Statistics.getAverage(dataSet);
            double sd = Statistics.getStandardDiviation(dataSet);

            for (int i = 0; i < ins.numInstances(); i++) {
                ins.instance(i).setValue(j, (dataSet[i] - average) / sd);
            }
        }

        return ins;
    }

    public static double[] m_ZScore(double[] dataSet) {
        log.info("ZScore标准化方法被调用");
        double average = Statistics.getAverage(dataSet);
        double sd = Statistics.getStandardDiviation(dataSet);

        for (int i = 0; i < dataSet.length; i++) {
            dataSet[i] = (dataSet[i] - average) / sd;
        }
        return dataSet;
    }

}
