package Weight;

import Preprocessor.MinMax;
import Preprocessor.Statistics;
import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/1/21 0021.
 * 标准离差法
 */
public class StandardDeviation {
    private static final Logger log = Logger.getLogger(StandardDeviation.class);

    public static double[] m_StandardDeviation(double[][] dataSet) throws  Exception{
        log.info("标准离差法被调用");

        int row = dataSet.length;
        if (row < 1) {
            log.error("使用标准离差法时，矩阵为行数小于1");
            throw new Exception("使用标准离差法时，矩阵为行数小于1");
        }
        int column = dataSet[0].length;

        double[] res = new double[column];
        double[] tmp = new double[row];
        double[] sd = new double[column];
        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {
                tmp[i] = dataSet[i][j];
            }
            //数据标准化
            tmp = MinMax.m_MinMax(tmp);

            sd[j] = Statistics.getStandardDiviation(tmp);
        }

        for (int i = 0; i < column; i++) {
            res[i] = sd[i] / Statistics.getSum(sd);
        }
        return res;
    }
}
