package Score;

/**
 * Created by supc on 2018/2/2 0002.
 */
public class ProdocutNode {
    private boolean qualified; //合格
    private boolean scrap; //报废
    private boolean overproof; //是否超差
    private double trial; //审理数
    private double N; //总数

    public ProdocutNode(boolean qualified, boolean scrap,boolean overproof, double trial, double N) {
        setQualified(qualified);
        setScrap(scrap);
        setOverproof(overproof);
        setTrial(trial);
        setN(N);
    }


    public boolean isQualified() {
        return qualified;
    }

    public void setQualified(boolean qualified) {
        this.qualified = qualified;
    }

    public boolean isScrap() {
        return scrap;
    }

    public void setScrap(boolean scrap) {
        this.scrap = scrap;
    }

    public boolean isOverproof() {
        return overproof;
    }

    public void setOverproof(boolean overproof) {
        this.overproof = overproof;
    }

    public double getTrial() {
        return trial;
    }

    public void setTrial(double trial) {
        this.trial = trial;
    }

    public double getN() {
        return N;
    }

    public void setN(double n) {
        N = n;
    }
}
