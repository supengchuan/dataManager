package Weight.PCA;

import Weight.AHP.WeightNodeAHP;

/**
 * Created by supc on 2018/2/2 0002.
 */
public class WeightNodePCA implements Comparable {
    private String name;
    private double weight;

    public WeightNodePCA(String name, double weight) {
        setName(name);
        setWeight(weight);
    }
    public int compareTo(Object o) {
        WeightNodePCA weightNodePCA = (WeightNodePCA) o;
        if (this.weight < weightNodePCA.getWeight())
            return 1;
        if (this.weight > weightNodePCA.getWeight())
            return -1;
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
