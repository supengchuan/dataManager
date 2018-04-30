package Weight;

import Jama.Matrix;
import Preprocessor.Gravity;
import Preprocessor.Statistics;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by supc on 2018/2/3 0003.
 */
public class Topsis {
    private static final Logger log = Logger.getLogger(Topsis.class);

    //@决策矩阵，权重向量，影响因子
    public static double[] m_Topsis(double[][] decision, double[] weight, String[] impacts) throws Exception {
        log.info("Topsis方法被调用");
        int nRow = decision.length;
        if (nRow == 0) {
            log.error("使用Topsis时，决策矩阵为空，不符合要求，需要m*n矩阵");
            throw new Exception("决策矩阵为空，不符合要求，需要m*n矩阵");
        }


        int nColumn = decision[0].length;
        if (weight.length != nColumn || impacts.length != nColumn) {
            log.error("使用Topsis时，权重向量和影响因子向量的长度需要和决策矩阵的列一致");
            throw new Exception("权重向量和影响因子向量的长度需要和决策矩阵的列一致");

        }


        double weightSum = Statistics.getSum(weight);
        for (int i = 0; i < weight.length; i++) {
            weight[i] /= weightSum;
        }
        double[] res = new double[nRow];
        double[][] normalize = new double[nRow][nColumn];
        double[] tmp = new double[nRow];
        //决策矩阵标准化；
        for (int j = 0; j < nColumn; j++) {
            for (int i = 0; i < nRow; i++)
                tmp[i] = decision[i][j];

            tmp = Gravity.m_Gravity(tmp);
            for (int i = 0; i < nRow; i++)
                normalize[i][j] = tmp[i];

        }
        //建权重对角矩阵
        double[][] weightArray = new double[nColumn][nColumn];
        for (int i = 0; i < nColumn; i++) {
            weightArray[i][i] = weight[i];
        }

        Matrix weightMatrix = new Matrix(weightArray);
        Matrix normalMatrix = new Matrix(normalize);
        //构造加权规范化矩阵
        double[][] weightNormal = normalMatrix.times(weightMatrix).getArray();

        double[] positive = new double[nColumn];
        double[] negative = new double[nColumn];

        for (int j = 0; j < nColumn; j++) {
            for (int i = 0; i < nRow; i++)
                tmp[i] = weightNormal[i][j];
            double max = Statistics.getMax(tmp);
            double min = Statistics.getMin(tmp);

            positive[j] = (impacts[j].equals("+") ? 1 : 0) * max + (impacts[j].equals("-") ? 1 : 0) * min;
            negative[j] = (impacts[j].equals("-") ? 1 : 0) * max + (impacts[j].equals("+") ? 1 : 0) * min;
        }

        double[] disPostive = new double[nRow];
        double[] disNegetive = new double[nRow];

        //计算相对接近度并排序
        for (int i = 0; i < nRow; i++) {
            disPostive[i] = distance(weightNormal[i], positive);
            disNegetive[i] = distance(weightNormal[i], negative);
        }


        for (int i = 0; i < nRow; i++) {
            res[i] = disNegetive[i] / (disNegetive[i] + disPostive[i]);
        }

        return res;

    }

    private static double distance(double[] original, double[] idealSolution) {
        if (original.length != idealSolution.length)
            return -1;

        double sum = 0;
        for (int i = 0; i < original.length; i++) {
            sum += Math.pow(original[i] - idealSolution[i], 2);
        }
        return Math.sqrt(sum);
    }

}


