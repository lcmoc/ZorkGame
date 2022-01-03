package ch.bbw.zorkgame;

public class Item {
    private String itemName;
    private int itemUsablility;

    public Item(String name, int value, int usablility) {
        setItemName(name);
        setItemUsablility(usablility);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemUsablility() {
        return itemUsablility;
    }

    public void setItemUsablility(int itemUsablility) {
        this.itemUsablility = itemUsablility;
    }
}
