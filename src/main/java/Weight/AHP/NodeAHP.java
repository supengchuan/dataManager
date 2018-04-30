package Weight.AHP;

/**
 * Created by supc on 2018/1/29 0029.
 */
public class NodeAHP {
    private int level;
    private String name;

    public NodeAHP() {
    }

    public NodeAHP(int level, String name) {
        setName(name);
        setLevel(level);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
