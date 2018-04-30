package ProductQuality;

import Preprocessor.MinMax;
import Preprocessor.Statistics;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by supc on 2018/2/20 0020.
 * 信息增益法
 */
public class InformationGain {
    private static final Logger log = Logger.getLogger(InformationGain.class);

    public static List<String> m_InformationGain(double[][] dataSet, String[] name) throws Exception {
        log.info("信息增益法被调用");
        List<String> featureName = new ArrayList<String>();
        int nRow = dataSet.length;
        if (nRow == 0) {
            log.error("信息增益法被调用时，数据集不能为空");
            throw new Exception("信息增益法被调用时，数据集不能为空");
        }
        int nColumn = dataSet[0].length;
        double[] tmp = new double[nRow];

        Map<String, Double> map = new TreeMap<String, Double>();
        //计算信息熵
        for (int j = 0; j < nColumn; j++) {
            for (int i = 0; i < nRow; i++) {
                tmp[i] = dataSet[i][j];
            }
            tmp = MinMax.m_MinMax(tmp);
            double y_sum = Statistics.getSum(tmp);
            double sum = 0;
            for (int i = 0; i < nRow; i++) {
                double p = tmp[i] / y_sum;
                if (p == 0) continue;
                sum += p * Math.log(p);
            }
            double entropy = -1 / Math.log(nRow) * sum;
            map.put(name[j], entropy);
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
}
