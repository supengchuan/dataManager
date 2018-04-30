package Weight;

import Preprocessor.Statistics;
import jdk.internal.dynalink.beans.StaticClass;
import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/2/2 0002.
 * 专家赋权法
 * 输入一个二维数组，列表示指标，行表示专家打分
 */
public class ExpertWeight {
    private static final Logger log = Logger.getLogger(ExpertWeight.class);

    public static double[] m_ExpertWeight(double [][] dataSet) throws Exception {
        log.info("专家赋权法被调用");
        int expertNum = dataSet.length;
        if (expertNum == 0) {
            log.error("使用专家赋权法时，专家数为0");
            throw new Exception("使用专家赋权法时，专家数为0");
        }

        int targetNum = dataSet[0].length;
        if (targetNum == 0) {
            log.error("使用专家赋权法时，指标数为0");
            throw new Exception("使用专家赋权法时，指标数为0");
        }

        double [] res = new double[targetNum];
        double [] tmp = new double[dataSet.length];
        for (int j = 0; j < targetNum; j++) {
            for (int i= 0; i< expertNum; i++){
                tmp[i] = dataSet[i][j];
            }

            res[j] = Statistics.getAverage(tmp);
        }
        double sum = Statistics.getSum(res);
        for (int i = 0; i< targetNum;i++){
            res[i] /=sum;
        }

        return res;
    }
}
