package Weight.AHP;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by supc on 2018/4/14 0014.
 */
public class AHPTest extends TestCase {
    @Test
    public void testM_AHP() throws Exception {
        List<NodeAHP> listNode = new ArrayList<NodeAHP>();
        NodeAHP A = new NodeAHP(1, "A");
        listNode.add(A);

        NodeAHP B1 = new NodeAHP(2, "B1");
        NodeAHP B2 = new NodeAHP(2, "B2");
        NodeAHP B3 = new NodeAHP(2, "B3");
        listNode.add(B1);
        listNode.add(B2);
        listNode.add(B3);

        NodeAHP C1 = new NodeAHP(3, "C1");
        NodeAHP C2 = new NodeAHP(3, "C2");
        NodeAHP C3 = new NodeAHP(3, "C3");
        NodeAHP C4 = new NodeAHP(3, "C4");
        NodeAHP C5 = new NodeAHP(3, "C5");
        listNode.add(C1);
        listNode.add(C2);
        listNode.add(C3);
        listNode.add(C4);
        listNode.add(C5);
        //添加关系矩阵
        List<RelationMatrix> listRM = new ArrayList<RelationMatrix>();
        List<String> listNameB2A = new ArrayList<String>();
        listNameB2A.add("B1");
        listNameB2A.add("B2");
        listNameB2A.add("B3");
        double[][] matrixB2A = {{1, (double) 1 / 5, (double) 1 / 3}, {5, 1, 3}, {3, (double) 1 / 3, 1}};
        RelationMatrix B2A = new RelationMatrix("A", listNameB2A, matrixB2A);
        listRM.add(B2A);
        List<String> listNameC2B1 = new ArrayList<String>();
        listNameC2B1.add("C1");
        listNameC2B1.add("C2");
        listNameC2B1.add("C3");
        listNameC2B1.add("C4");
        listNameC2B1.add("C5");
        double[][] matrixC2B1 = {{1, 3, 5, 4, 7},
                {(double) 1 / 3, 1, 3, 2, 5},
                {(double) 1 / 5, (double) 1 / 3, 1, (double) 1 / 2, 2},
                {(double) 1 / 4, (double) 1 / 2, 2, 1, 3},
                {(double) 1 / 7, (double) 1 / 5, (double) 1 / 2, (double) 1 / 3, 1}};
        RelationMatrix C2B1 = new RelationMatrix("B1", listNameC2B1, matrixC2B1);
        listRM.add(C2B1);

        List<String> listNameC2B2 = new ArrayList<String>();
        listNameC2B2.add("C2");
        listNameC2B2.add("C3");
        listNameC2B2.add("C4");
        listNameC2B2.add("C5");
        double[][] matrixC2B2 = {{1, (double) 1 / 7, (double) 1 / 3, (double) 1 / 5},
                {7, 1, 5, 3},
                {3, (double) 1 / 5, 1, (double) 1 / 3},
                {5, (double) 1 / 3, 3, 1}};
        RelationMatrix C2B2 = new RelationMatrix("B2", listNameC2B2, matrixC2B2);
        listRM.add(C2B2);

        List<String> listNameC2B3 = new ArrayList<String>();
        listNameC2B3.add("C1");
        listNameC2B3.add("C2");
        listNameC2B3.add("C3");
        listNameC2B3.add("C4");

        double[][] matrixC2B3 = {{1, 1, 3, 3},
                {1, 1, 3, 3},
                {(double) 1 / 3, (double) 1 / 3, 1, 1},
                {(double) 1 / 3, (double) 1 / 3, 1, 1}};
        RelationMatrix C2B3 = new RelationMatrix("B3", listNameC2B3, matrixC2B3);
        listRM.add(C2B3);
        AHP myAHP = new AHP(3, listNode, listRM);
        List<WeightNodeAHP> res = myAHP.m_AHP();

        for (WeightNodeAHP a: res
             ) {
            System.out.println("AHP result:" +
                    a.getName() + " " + a.getWeight());
        }
    }

}