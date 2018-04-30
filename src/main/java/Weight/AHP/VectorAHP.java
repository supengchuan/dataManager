package Weight.AHP;

/**
 * Created by supc on 2018/1/29 0029.
 */
public class VectorAHP {
    private String upName;
    private int upLevel;
    double[] weightVector;


    public String getUpName() {
        return upName;
    }

    public void setUpName(String upName) {
        this.upName = upName;
    }

    public int getUpLevel() {
        return upLevel;
    }

    public void setUpLevel(int upLevel) {
        this.upLevel = upLevel;
    }

    public double[] getWeightVector() {
        return weightVector;
    }

    public void setWeightVector(double[] weightVector) {
        this.weightVector = weightVector;
    }
}
