package ProductQuality;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by supc on 2018/2/22 0022.
 * 逻辑回归系数法
 * 输入：指标值，分类结果，1合格，-1不合格
 * 输出：各个指标对应的系数
 */
public class LogisticRegression {
    private static final Logger log = Logger.getLogger(LogisticRegression.class);

    public static double[] m_LogisticRegression(double[][] X, double[] y) {
        log.info("逻辑回归系数法被调用");
        if(X.length != y.length) {
            log.error("");
        }
        return train(X, y, 0.01f, 200, (short) 1);
    }

    private static double[] train(double[][] datas, double[] labels, double step, int maxIt, short algorithm) {
        log.info("逻辑回归训练权重向量");
        int size = datas.length;
        int dim = datas[0].length;
        double[] w = new double[dim];

        for (int i = 0; i < dim; i++) {
            w[i] = 1;
        }

        switch (algorithm) {
            //批量梯度下降
            case 1:
                for (int i = 0; i < maxIt; i++) {
                    //求输出
                    double[] out = new double[size];
                    for (int s = 0; s < size; s++) {
                        double lire = innerProduct(w, datas[s]);
                        out[s] = sigmoid(lire);
                    }
                    for (int d = 0; d < dim; d++) {
                        double sum = 0;
                        for (int s = 0; s < size; s++) {
                            sum += (labels[s] - out[s]) * datas[s][d];
                        }
                        w[d] = w[d] + step * sum;
                    }
                    //System.out.println(Arrays.toString(w));
                }
                break;
            //随机梯度下降
            case 2:
                for (int i = 0; i < maxIt; i++) {
                    for (int s = 0; s < size; s++) {
                        double lire = innerProduct(w, datas[s]);
                        double out = sigmoid(lire);
                        double error = labels[s] - out;
                        for (int d = 0; d < dim; d++) {
                            w[d] += step * error * datas[s][d];
                        }
                    }
                    //System.out.println(Arrays.toString(w));
                }
                break;
        }

        return w;
    }

    private static double innerProduct(double[] w, double[] x) {

        double sum = 0;
        for (int i = 0; i < w.length; i++) {
            sum += w[i] * x[i];
        }

        return sum;
    }

    private static double sigmoid(double src) {
        return (double) (1.0 / (1 + Math.exp(-src)));
    }
}
