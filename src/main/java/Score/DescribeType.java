package Score;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by supc on 2018/2/2 0002.
 * 描述型打分，传入三个参数
 *
 * @discribeName 描述的类型
 * @score 对应描述的分数
 * @dataSet 需要打分的描述
 */
public class DescribeType {
    private static final Logger log = Logger.getLogger(DescribeType.class);

    public static double[] m_DescribeType(String[] discribeName, double[] score, String[] dataSet) {
        log.info("描述型打分方法被调用");

        double[] res = new double[dataSet.length];

        Map<String, Double> scoreMap = new TreeMap<String, Double>();
        for (int i = 0; i < discribeName.length; i++) {
            scoreMap.put(discribeName[i], score[i]);
        }

        for (int i = 0; i < dataSet.length; i++) {
            res[i] = scoreMap.get(dataSet[i]);
        }

        return res;
    }
}
