package ProductQuality;

import Preprocessor.Statistics;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by supc on 2018/2/20 0020.
 * 相关系数法
 */
public class CorrelationCoefficient {
    private static final Logger log = Logger.getLogger(CorrelationCoefficient.class);

    public static List<String> m_CorrelationCoefficient(double[][] dataSet,
                                                        double[] goal, String[] name) throws Exception {
        log.info("相关系数法被调用");
        if (dataSet.length != goal.length || dataSet.length == 0) {
            log.error("每个指标的样本数需要与目标的数值个数一致");
            throw new Exception("每个指标的样本数需要与目标的数值个数一致");
            //我们期待每个指标的数值个数与目标的数值个数一致；
        }

        List<String> featureName = new ArrayList<String>();
        int nRow = dataSet.length;
        int nColumn = dataSet[0].length;
        double[] cc = new double[nColumn];
        double[] tmp = new double[nRow];
        for (int j = 0; j < nColumn; j++) {
            for (int i = 0; i < nRow; i++)
                tmp[i] = dataSet[i][j];

            cc[j] = getCorrelationCoefficient(tmp, goal);
        }
        Map<String, Double> map = new TreeMap<String, Double>();
        for (int j = 0; j < nColumn; j++) {
            map.put(name[j], cc[j]);
        }
        //这里将map.entrySet()转换成list
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(map.entrySet());
        //然后通过比较器来实现排序
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                //降序排序
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        for (int i = 0; i < list.size(); i++) {
            featureName.add(list.get(i).getKey());
        }
        return featureName;
    }

    private static double getCorrelationCoefficient(double[] dataSet, double[] goal) {
        double res = 0;
        double meanX = Statistics.getAverage(dataSet);
        double meanY = Statistics.getAverage(goal);
        double sum = 0;
        for (int i = 0; i < dataSet.length; i++) {
            sum += (dataSet[i] - meanX) * (goal[i] - meanY);
        }
        //协方差
        double cov = sum / dataSet.length;
        double varX = Statistics.getVariance(dataSet);
        double varY = Statistics.getVariance(goal);
        //相关系数
        res = cov / Math.sqrt(varX * varY);


        return res;
    }


}
