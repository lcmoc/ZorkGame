package ch.bbw.zorkgame;

public class Item {
    private String name;
    private int weight;

    public Item(String name, int weight) {
        setName(name);
        setWeight(weight);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
