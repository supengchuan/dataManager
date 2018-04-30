package ProductQuality;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by supc on 2018/2/21 0021.
 * SVM系数法
 * 输入X[][]各个指标对应的数据性能，y[]在该指标性能的情况下的综合性能1，-1；
 * 返回各个指标的重要程度
 */
public class SVMCoefficient {
    private static final Logger log = Logger.getLogger(SVMCoefficient.class);

    private int exampleNum;
    private int exampleDim;
    private double[] w;
    private double lambda=0.0001;
    private double lr = 0.001;//0.00001
    private double threshold = 0.001;
    private double cost;
    private double[] grad;
    private double[] yp;

/*    public SVMCoefficient(double paramLambda) {
        lambda = paramLambda;
    }*/

    private void CostAndGrad(double[][] X, double[] y) {
        cost = 0;
        for (int m = 0; m < exampleNum; m++) {
            yp[m] = 0;
            for (int d = 0; d < exampleDim; d++) {
                yp[m] += X[m][d] * w[d];
            }

            if (y[m] * yp[m] - 1 < 0) {
                cost += (1 - y[m] * yp[m]);
            }

        }

        for (int d = 0; d < exampleDim; d++) {
            cost += 0.5 * lambda * w[d] * w[d];
        }


        for (int d = 0; d < exampleDim; d++) {
            grad[d] = Math.abs(lambda * w[d]);
            for (int m = 0; m < exampleNum; m++) {
                if (y[m] * yp[m] - 1 < 0) {
                    grad[d] -= y[m] * X[m][d];
                }
            }
        }
    }

    private void update() {
        for (int d = 0; d < exampleDim; d++) {
            w[d] -= lr * grad[d];
        }
    }

    private void Train(double[][] X, double[] y, int maxIters) {
        log.info("SVM训练权重向量");
        exampleNum = X.length;
        if (exampleNum <= 0) {
            System.out.println("num of example <=0!");
            return;
        }
        exampleDim = X[0].length;
        w = new double[exampleDim];
        grad = new double[exampleDim];
        yp = new double[exampleNum];

        for (int iter = 0; iter < maxIters; iter++) {

            CostAndGrad(X, y);
            System.out.println("cost:" + cost);
            if (cost < threshold) {
                break;
            }
            update();

        }
    }

    public double[] m_getCoefficient(double[][] X, double[] y) {
        log.info("SVM系数法被调用");
        Train(X, y, 7000);
        return this.w;
    }
}
