package Score;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supc on 2018/5/1 0001.
 */
public class CountTypeTest extends TestCase {
    public void testM_CountType() throws Exception {
        List<ProdocutNode> list = new ArrayList<ProdocutNode>();
/*第一个参数表示是否合格
* 第二个参数表示报废
* 第三个参数表示是否存在超差，第四和第五参数只在存在超差时有效
* 第四参数是审理数
* 第五个参数表示产品总共有多少个指标*/
        ProdocutNode p1 = new ProdocutNode(true, false, false, 0, 20);
        ProdocutNode p2 = new ProdocutNode(false, true, false, 0, 20);
        ProdocutNode p3 = new ProdocutNode(false, false, true, 1, 20);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        double[] res = CountType.m_CountType(list);
        for (double i : res)
            System.out.print(i + " ");
        System.out.println();

    }

}