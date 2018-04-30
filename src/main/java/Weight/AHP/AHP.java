package Weight.AHP;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by supc on 2018/1/27 0027.
 * 在实例化对象的时候，需要初始化三个对象：
 * 1.层次分析的总层数
 * 2.节点列表，节点分为两字段，一个name，一个level
 * 3.关系矩阵，关系矩阵中的节点的顺序必须与节点列表中的一致
 * <p>
 * 输出：初始化完成之后，调用m_AHP返回一个列表，这个列表示按序排好的一个权重节点，包含了名称name以及权重weight
 */
//层次分析法
public class AHP {
    private static final Logger log = Logger.getLogger(AHP.class);

    private int totalLevel; //总层数
    private List<NodeAHP> listNodeAHP; //节点列表，包含节点名称，所属层，
    private List<RelationMatrix> listRM; //关系矩阵，表示i+1层的节点对i层的第j个节点的重要程度的比较矩阵

    private List<VectorAHP> listVector; //存放单矩阵的权重向量
    private List<WeightNodeAHP> listWeightNode; //存放最终结果
    private static final double[] RI = {0, 0, 0.58, 0.96, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49};

    public AHP() {

    }

    public AHP(int tLevel, List<NodeAHP> nodeAHPs, List<RelationMatrix> relationMatrices) {
        log.info("创建一个AHP对象");
        setTotalLevel(tLevel);
        setListNodeAHP(nodeAHPs);
        setListRM(relationMatrices);
        listVector = new ArrayList<VectorAHP>();
    }


    //单矩阵的权重向量，并进行一致性校验，如果符合一致性，则返回权重矩阵，否则返回null；
    private double[] weightVector(double[][] matrix) throws Exception {
        double[] weight = new double[matrix.length];
        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            weight[i] = 1.0 / n;
        }
        // 一般向量W（k+1）
        double[] w1 = new double[n];
        // W（k+1）的归一化向量
        double[] w2 = new double[n];
        double sum = 1.0;
        //两次迭代之间的实际最大误差
        double d = 1.0;
        // 误差
        double delta = 0.00001;

        while (d > delta) {
            d = 0.0;
            sum = 0;
            // 获取向量
            //int index = 0;
            for (int j = 0; j < n; j++) {
                double t = 0.0;
                for (int l = 0; l < n; l++) {
                    t += matrix[j][l] * weight[l];
                    // w1[j] += matrix[j][l] * weight[l];
                }
                w1[j] = t;
                sum += w1[j];
            }
            // 向量归一化
            for (int k = 0; k < n; k++) {
                w2[k] = w1[k] / sum;
                // 最大差值
                d = Math.max(Math.abs(w2[k] - weight[k]), d);
                // 用于下次迭代使用
                weight[k] = w2[k];
            }
        }
        double lambda = 0.0;
        for (int k = 0; k < n; k++) {
            lambda += w1[k] / (n * weight[k]);
        }
        double CI = (lambda - n) / (n - 1);
        double CR = 0.0;
        if (RI[n - 1] != 0) {
            CR = CI / RI[n - 1];
            if (CR > 0.1) {
                //如果CR大，说明一致性不符合，则返回null，表示由问题；
                log.error("使用AHP是关系矩阵的一致性不符合，请重新输入");
                throw new Exception("使用AHP是关系矩阵的一致性不符合，请重新输入");
            }
        }
        return weight;
    }

    /**
     * 四舍五入
     *
     * @param v
     * @param scale
     * @return
     */
    private double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    //把关系矩阵的权重全部算出来，填入一个列表中，准备总排序；
    private void createListWeightVector() throws Exception {
        //确定有多少个关系矩阵
        int listRmLen = this.listRM.size();
        //根据关系矩阵，进行单排序，得到权重向量vectorAHP
        for (int i = 0; i < listRmLen; i++) {
            //获取第i个关系矩阵对象
            RelationMatrix rm = this.listRM.get(i);
            double[] weight = this.weightVector(rm.getRelationMatrix());
            int upLevel = 0;
            int levelNodeNum = 0;
            String upName = rm.getUpName();

            for (int j = 0; j < this.listNodeAHP.size(); j++) {
                if (this.listNodeAHP.get(j).getName().equals(upName)) {
                    upLevel = this.listNodeAHP.get(j).getLevel();
                    break;
                }
            }
            int curLevel = upLevel + 1;
            List<String> curNodeList = new ArrayList<String>();
            //获取当前层有多少节点，如果权重向量的节点缺失，需要补0；
            for (int j = 0; j < this.listNodeAHP.size(); j++) {
                if (this.listNodeAHP.get(j).getLevel() == curLevel) {
                    levelNodeNum++;
                    curNodeList.add(this.listNodeAHP.get(j).getName());
                }
            }

            //如果对上层影响的节点小于实际的当层节点数；则需要把没有影响的节点位置置0，在全局排序的时候需要
            if (weight.length == levelNodeNum) {
                VectorAHP vectorAHP = new VectorAHP();
                vectorAHP.setUpLevel(upLevel);
                vectorAHP.setUpName(upName);
                vectorAHP.setWeightVector(weight);
                this.listVector.add(vectorAHP);

            } else if (weight.length < levelNodeNum) {
                double[] newWeight = compareTwoList(curNodeList, rm.getCompareList());
                int k = 0;
                for (int j = 0; j < newWeight.length; j++) {
                    if (newWeight[j] < 0) {
                        newWeight[j] = 0;
                    } else {
                        newWeight[j] = weight[k];
                        k++;
                    }
                }

                weight = newWeight;
                VectorAHP vectorAHP = new VectorAHP();
                vectorAHP.setUpLevel(upLevel);
                vectorAHP.setUpName(upName);
                vectorAHP.setWeightVector(weight);
                this.listVector.add(vectorAHP);

            } else {
                //实际的节点数小于关系矩阵比较的节点数，说明输入出现问题，抛出异常
                throw new Exception("AHP struct error! Relation matrix node is bigger than actual number!， the" + Integer.toString(i + 1) + "th Matrix!");
            }
        }
    }

    private void totalSort() {
        int curLevel = 1;
        if (this.totalLevel < 2) {
            return;
        } else if (this.totalLevel == 2) {//层次只有两层
            double[] tmpWeight = null;

            for (int i = 0; i < this.listVector.size(); i++) {
                if (this.listVector.get(i).getUpLevel() == 1) {
                    tmpWeight = this.listVector.get(i).getWeightVector();
                }
            }
            List<String> listDecisionNodeName = new ArrayList<String>();
            for (int i = 0; i < this.listNodeAHP.size(); i++) {
                if (this.listNodeAHP.get(i).getLevel() == 2) {
                    listDecisionNodeName.add(this.listNodeAHP.get(i).getName());
                }
            }
            if (tmpWeight.length == listDecisionNodeName.size()) {
                this.listWeightNode = new ArrayList<WeightNodeAHP>();
                for (int i = 0; i < tmpWeight.length; i++) {
                    WeightNodeAHP weightNodeAHP = new WeightNodeAHP();
                    weightNodeAHP.setName(listDecisionNodeName.get(i));
                    weightNodeAHP.setWeight(tmpWeight[i]);
                    this.listWeightNode.add(weightNodeAHP);
                }
            }

        } else {//大于两层的情况，先把第二层的权重向量取出来；
            double[] upVector = null;
            double[][] currentMatrix = null;
            double[] tmp = null;
            for (int i = 0; i < this.listVector.size(); i++) {
                if (this.listVector.get(i).getUpLevel() == 1) {
                    upVector = this.listVector.get(i).getWeightVector();
                }
            }
            //从第3层开始排序
            for (int i = 3; i <= totalLevel; i++) {
                int upNodeNum = 0, curNodeNum = 0;
                for (int j = 0; j < this.listNodeAHP.size(); j++) {
                    if (this.listNodeAHP.get(j).getLevel() == i) {
                        curNodeNum++; // 当前层的节点数
                    }
                    if (this.listNodeAHP.get(j).getLevel() == (i - 1)) {
                        upNodeNum++; //上层节点数
                    }
                }
                currentMatrix = new double[upNodeNum][curNodeNum];
                int k = 0;
                for (int j = 0; j < listVector.size(); j++) {
                    if (this.listVector.get(j).getUpLevel() == (i - 1)) {
                        tmp = this.listVector.get(j).getWeightVector();
                        currentMatrix[k++] = tmp;
                    }

                }

                upVector = multiMatrix(currentMatrix, upVector);
            }

            List<String> listDecisionNodeName = new ArrayList<String>();
            for (int i = 0; i < this.listNodeAHP.size(); i++) {
                if (this.listNodeAHP.get(i).getLevel() == totalLevel) {
                    listDecisionNodeName.add(this.listNodeAHP.get(i).getName());
                }
            }
            if (upVector.length == listDecisionNodeName.size()) {

                if (this.listWeightNode == null) {
                    this.listWeightNode = new ArrayList<WeightNodeAHP>();
                } else {
                    this.listWeightNode.clear();
                }
                for (int i = 0; i < upVector.length; i++) {
                    WeightNodeAHP w = new WeightNodeAHP();
                    w.setName(listDecisionNodeName.get(i));
                    w.setWeight(upVector[i]);
                    this.listWeightNode.add(w);
                }

            }

        }

    }

    //输出节点的权重，从大到小输出
    public List<WeightNodeAHP> m_AHP() {
        log.info("开始AHP的计算");

        try {
            this.createListWeightVector();
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("输入出现问题");
            e.printStackTrace();
        }
        this.totalSort();
        Collections.sort(this.listWeightNode);

        return this.listWeightNode;
    }

    //总排序是，下层矩阵乘以上层向量
    //左矩阵的列乘以右矩阵的列
    private double[] multiMatrix(double[][] current, double[] up) {
        if (current.length == 0 || current.length != up.length) {
            return null;
        }
        double[] res = new double[current[0].length];
        for (int j = 0; j < current[0].length; j++) {
            double tmp = 0;
            for (int i = 0; i < current.length; i++) {
                tmp += current[i][j] * up[i];
            }
            res[j] = tmp;
        }
        return res;
    }

    /*比较两个列表，l1，l2
    * 如果l1中有的在l2中没有，res对应的位置置-1， 否则置1
     */
    private double[] compareTwoList(List<String> l1, List<String> l2) {
        double[] res = new double[l1.size()];
        for (int i = 0; i < l1.size(); i++) {
            int j = 0;
            for (j = 0; j < l2.size(); j++) {
                if (l1.get(i).equals(l2.get(j))) {
                    res[i] = 1;
                    break;
                }
            }
            if (j == l2.size()) {
                res[i] = -1;
            }
        }
        return res;
    }


    public int getTotalLevel() {
        return totalLevel;
    }

    public void setTotalLevel(int totalLevel) {
        this.totalLevel = totalLevel;
    }

    public List<RelationMatrix> getListRM() {
        return listRM;
    }

    public void setListRM(List<RelationMatrix> listRM) {
        this.listRM = listRM;
    }

    public List<NodeAHP> getListNodeAHP() {
        return listNodeAHP;
    }

    public void setListNodeAHP(List<NodeAHP> listNodeAHP) {
        this.listNodeAHP = listNodeAHP;
    }

    public List<WeightNodeAHP> getListWeightNode() {
        return listWeightNode;
    }

    public void setListWeightNode(List<WeightNodeAHP> listWeightNode) {
        this.listWeightNode = listWeightNode;
    }

    public List<VectorAHP> getListVector() {
        return listVector;
    }

    public void setListVector(List<VectorAHP> listVector) {
        this.listVector = listVector;
    }
}
