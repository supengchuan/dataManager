package Score;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by supc on 2018/2/2 0002.
 * 计数型产品打分
 */
public class CountType {
    private static final Logger log = Logger.getLogger(CountType.class);

    public static double[] m_CountType(List<ProdocutNode> listData) {
        log.info("计数型打分方法被调用！");

        double[] res = new double[listData.size()];
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).isQualified()) {
                res[i] = 100;
                continue;
            }
            if (listData.get(i).isScrap()) {
                res[i] = 0;
                continue;
            }
            if (listData.get(i).isOverproof()) {

                res[i] = 100 - listData.get(i).getTrial() / listData.get(i).getN() * 100;
            }
        }
        return res;
    }

}
