package BIT;

/**
 * Created by supc on 2017/12/22 0022.
 * 用来存储一个指标的名称，两个类别的期望差的绝对值，和方差差的绝对值，支持按照方差的大小进行排序
 */
public class TargetInstance implements Comparable {
    private String name;
    private double qualifiedVariance;
    private double disQualifiedVariance;
    private double distanceVariance;


    public int compareTo(Object o) {
        TargetInstance targetInstance = (TargetInstance) o;
        if (this.distanceVariance < targetInstance.distanceVariance) {
            return 1;
        } else if (this.distanceVariance > targetInstance.distanceVariance) {
            return -1;
        }

        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQualifiedVariance() {
        return qualifiedVariance;
    }

    public void setQualifiedVariance(double qualifiedVariance) {
        this.qualifiedVariance = qualifiedVariance;
    }

    public double getDisQualifiedVariance() {
        return disQualifiedVariance;
    }

    public void setDisQualifiedVariance(double disQualifiedVariance) {
        this.disQualifiedVariance = disQualifiedVariance;
    }

    public double getDistanceVariance() {
        return distanceVariance;
    }

    public void setDistanceVariance(double distanceVariance) {
        this.distanceVariance = distanceVariance;
    }
}




