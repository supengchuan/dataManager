package Weight;

import Preprocessor.Statistics;
import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/2/3 0003.
 * 加权平均法求体系值
 * 决策矩阵一列表示一个指标值，一行表示一种方案；
 * 输出每种方案的体系值
 */
public class WeightedAverage {
    private static final Logger log = Logger.getLogger(WeightedAverage.class);

    public static double[] m_WeightedAverage(double[][] decision, double[] weight) throws Exception {
        log.info("加权平均法求体系值被调用");
        int nRow = decision.length;

        if (nRow == 0) {
            log.error("使用加权平均法求体系值，决策矩阵为空，不符合要求，需要m*n矩阵");
            throw new Exception("决策矩阵为空，不符合要求，需要m*n矩阵");
        }

        int nColumn = decision[0].length;
        if (nColumn != weight.length) {
            log.error("使用加权平均法求体系值，权重向量的长度需要和决策矩阵的列一致");
            throw new Exception("权重向量的长度需要和决策矩阵的列一致");
        }

        //权重向量标准化
        double weightSum = Statistics.getSum(weight);
        for (int i = 0; i < weight.length; i++) {
            weight[i] /= weightSum;
        }
        //计算体系值
        double[] res = new double[nRow];
        for (int i = 0; i < nRow; i++) {
            res[i] = 0;
            for (int j = 0; j < nColumn; j++) {
                res[i] += decision[i][j] * weight[j];
            }

        }

        return res;
    }
}
