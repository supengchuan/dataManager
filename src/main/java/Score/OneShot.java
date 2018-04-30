package Score;

import org.apache.log4j.Logger;

/**
 * Created by supc on 2018/1/20 0020.
 */
//成败型指标值评分
public class OneShot {
    private static final Logger log = Logger.getLogger(OneShot.class);

    public static double [] m_OneShot(double [] dataSet) {
        log.info("成败型打分方法被调用！");

        for(int i= 0; i < dataSet.length; i++) {
            if(dataSet[i] == 1)
                dataSet[i] = 100;
            else
                dataSet[i] = 0;
        }
        return dataSet;
    }
}
