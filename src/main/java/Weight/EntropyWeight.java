package Weight;

import Preprocessor.MinMax;
import Preprocessor.Statistics;
import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.log4j.Logger;
import weka.core.Instances;

/**
 * Created by supc on 2018/1/21 0021.
 */

/*
* 熵权法
* 输入一个二维数组，列表示指标，行表示样本数据
* 返回每个指标的权重

 */
public class EntropyWeight {
    private static final Logger log = Logger.getLogger(ExpertWeight.class);

    public static double[] m_EntropyWeight(double[][] dataSet) throws Exception{
        log.info("熵权法被调用");

        int row = dataSet.length;
        if (row == 0) {
            log.error("使用熵权法时，数据矩阵的行数为0");
            throw new Exception("使用熵权法时，数据矩阵的行数为0");
        }


        int column = dataSet[0].length;

        double[] res = new double[column];
        double[] tmp = new double[row];
        double[] entropy = new double[column];

        for (int j = 0; j < column; j++) {
            for (int i = 0; i < row; i++) {
                tmp[i] = dataSet[i][j];
            }
            //数据标准化
            tmp = MinMax.m_MinMax(tmp);
            //获取一个指标标准化之后所有值的和
            double y_Sum = Statistics.getSum(tmp);

            double sum = 0;

            //计算一个指标的信息熵
            for (int i = 0; i < row; i++) {
                double p = tmp[i] / y_Sum;
                if (p == 0) //定义
                    continue;
                sum += p * Math.log(p);
            }
            entropy[j] = -1 / Math.log(row) * sum;
        }

        for (int j = 0; j < column; j++) {
            res[j] = (1 - entropy[j]) / (column - Statistics.getSum(entropy));
        }

        return res;
    }
}
