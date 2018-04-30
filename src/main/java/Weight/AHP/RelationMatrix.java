package Weight.AHP;

import java.util.List;

/**
 * Created by supc on 2018/1/27 0027.
 */
/*层次分析的关系矩阵
*有个三个成员，一个是上层对象名称，第二个比较列表，第三个是关系矩阵
 */

public class RelationMatrix {
    private String upName;
    private List<String> compareList;
    private double[][] relationMatrix;

    public RelationMatrix() {
    }

    public RelationMatrix(String upName, List<String> compareList, double[][] relationMatrix) {
        setUpName(upName);
        setCompareList(compareList);
        setRelationMatrix(relationMatrix);
    }

    public String getUpName() {
        return upName;
    }

    public void setUpName(String upName) {
        this.upName = upName;
    }

    public List<String> getCompareList() {
        return compareList;
    }

    public void setCompareList(List<String> compareList) {
        this.compareList = compareList;
    }

    public double[][] getRelationMatrix() {
        return relationMatrix;
    }

    public void setRelationMatrix(double[][] relationMatrix) {
        this.relationMatrix = relationMatrix;
    }
}
