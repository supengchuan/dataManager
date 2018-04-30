package ProductQuality;

import Preprocessor.MinMax;
import Preprocessor.Statistics;
import org.apache.log4j.Logger;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supc on 2018/1/21 0021.
 */

//方差选择法
public class VarianceSelection {
    private static final Logger log = Logger.getLogger(VarianceSelection.class);

    public static List<String> m_VarianceSelection(Instances ins, double threshold) {
        List<String> featureName = new ArrayList<String>();

        //数据标准化
        ins = MinMax.m_MinMax(ins);

        double[] dataSet = new double[ins.numInstances()];
        for (int j = 0; j < ins.numAttributes() - 1; j++) {
            for (int i = 0; i < ins.numInstances(); i++) {
                dataSet[i] = ins.instance(i).value(j);
            }
            double variance = Statistics.getVariance(dataSet);
            if (variance > threshold) {
                featureName.add(ins.attribute(j).name());
            }
        }
        return featureName;
    }

    public static List<String> m_VarianceSelection(double[][] dataSet, String[] name, double threshold) throws Exception {
        log.info("方差选择法被调用");
        List<String> featureName = new ArrayList<String>();
        //数据标准化
        int nColumn = dataSet[0].length;
        int nRow = dataSet.length;
        double[] tmp = new double[nRow];
        for (int j = 0; j < nColumn; j++) {
            for (int i = 0; i < nRow; i++)
                tmp[i] = dataSet[i][j];

            tmp = MinMax.m_MinMax(tmp);
            double variance = Statistics.getVariance(tmp);
            if (variance >= threshold) {
                featureName.add(name[j]);
            }
        }
        if (featureName.isEmpty()) {
            log.error("方差选择法中，threshold过高，无结果输出");
            throw new Exception("方差选择法中，threshold过高，无结果输出");
        }

        return featureName;
    }

}
