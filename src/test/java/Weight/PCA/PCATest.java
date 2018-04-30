package Weight.PCA;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supc on 2018/4/14 0014.
 */
public class PCATest extends TestCase {

    @Test
    public void testM_PCA() throws Exception {
        double[][] matrix = {
                {91, 99, 98, 94, 95, 93},
                {98, 89, 99, 88, 82, 91},
                {99, 98, 97, 96, 95, 93},
                {89, 88, 79, 98, 87, 89}
        };
        List<String> targetName = new ArrayList<String>();
        targetName.add("A");
        targetName.add("B");
        targetName.add("C");
        targetName.add("D");
        targetName.add("E");
        targetName.add("F");

        PCA myPCA = new PCA(matrix, targetName);
        List<WeightNodePCA> res = myPCA.m_PCA();

        for (WeightNodePCA a: res
             ) {
            System.out.println(a.getName() + " " + a.getWeight());
        }
    }

}