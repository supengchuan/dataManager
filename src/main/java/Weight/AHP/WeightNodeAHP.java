package Weight.AHP;

import sun.awt.windows.WEmbeddedFrame;

/**
 * Created by supc on 2018/1/31 0031.
 */
public class WeightNodeAHP implements Comparable{
    private String name;
    private double weight;


    public int compareTo(Object o) {
        WeightNodeAHP weightNodeAHP = (WeightNodeAHP) o;
        if (this.weight < weightNodeAHP.weight)
            return 1;
        else if (this.weight > weightNodeAHP.weight)
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
