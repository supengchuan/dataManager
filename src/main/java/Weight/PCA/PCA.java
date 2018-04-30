package Weight.PCA;

import Jama.Matrix;
import Preprocessor.Statistics;
import Preprocessor.ZScore;
import jdk.nashorn.internal.runtime.ECMAException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by supc on 2018/2/2 0002.
 * n个区域p个指标的原始数据矩阵Mij(i=1，2，...，n；j=1，2，...，p)
 */
public class PCA {
    private static final Logger log = Logger.getLogger(PCA.class);
    private double[][] matrix;
    private List<String> listTargetName;

    private double[][] rMatrix;


    public PCA(double[][] matrix, List<String> listTargetName) {
        log.info("创建一个PCA对象");
        this.matrix = matrix;
        this.listTargetName = listTargetName;
    }

    public PCA() {

    }

    ////计算相关系数矩阵
    private void computeRelationMatrix() {
        log.info("PCA：计算关系矩阵");
        int n = matrix[0].length;
        double tmp = 0;
        rMatrix = new double[matrix[0].length][matrix[0].length];

        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                tmp = 0;
                for (int i = 0; i < matrix.length; i++) {
                    tmp += matrix[i][j] * matrix[i][k];
                }
                tmp /= matrix.length;
                rMatrix[j][k] = tmp;
            }
        }


    }

    public List<WeightNodePCA> m_PCA() throws Exception{
        log.info("开始PCA计算");
        if (matrix.length < 1 || matrix[0].length < 1) {
            log.error("使用PCA时关系矩阵不满足使用要求，其行或者列数为0");
            throw new Exception("使用PCA时关系矩阵不满足使用要求，其行或者列数为0");
        }
        //Z-score数据标准化
        double[] tmp = new double[matrix.length];
        for (int j = 0; j < matrix[0].length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                tmp[i] = matrix[i][j];
            }
            tmp = ZScore.m_ZScore(tmp);
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][j] = tmp[i];
            }
        }
        //计算相关系数矩阵Rjk
        computeRelationMatrix();


        Matrix jamaMatrxi = new Matrix(rMatrix);
        //疑问：这个算出来的lambda与指标是否具有对应关系；
        //特征向量lambda
        double[] lambda = jamaMatrxi.eig().getRealEigenvalues();


        double[] T = new double[lambda.length];
        double lambdaSum = Statistics.getSum(lambda);
        for (int i = 0; i < T.length; i++) {
            T[i] = lambda[i] / lambdaSum;
        }

        List<WeightNodePCA> weightTmp = new ArrayList<WeightNodePCA>();
        for (int i = 0; i < T.length; i++) {
            WeightNodePCA weightNodePCA = new WeightNodePCA(listTargetName.get(i), T[i]);
            weightTmp.add(weightNodePCA);
        }
        Collections.sort(weightTmp);
        int count = 0;
        double D = 0;
        List<WeightNodePCA> res = new ArrayList<WeightNodePCA>();
        for (int i = 0; i < weightTmp.size(); i++) {
            D += weightTmp.get(i).getWeight();
            count++;
            res.add(weightTmp.get(i));
            if (D >= 0.85)
                break;
        }
        double sum = 0.0;
        for (int i = 0; i < count; i++) {
            sum += res.get(i).getWeight();
        }
        for (int i = 0; i < count; i++) {
            res.get(i).setWeight(res.get(i).getWeight() / sum);
        }


        return res;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public List<String> getListTargetName() {
        return listTargetName;
    }

    public void setListTargetName(List<String> listTargetName) {
        this.listTargetName = listTargetName;
    }

    public double[][] getrMatrix() {
        return rMatrix;
    }

    public void setrMatrix(double[][] rMatrix) {
        this.rMatrix = rMatrix;
    }
}
