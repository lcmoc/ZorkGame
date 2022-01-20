package ch.bbw.zorkgame;

public class Item {
    private String name;
    private int usability;
    private int weight;

    public Item(String name, int usablility, int weight) {
        setName(name);
        setUsability(usablility);
        setWeight(weight);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUsability() {
        return usability;
    }

    public void setUsability(int usability) {
        this.usability = usability;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
