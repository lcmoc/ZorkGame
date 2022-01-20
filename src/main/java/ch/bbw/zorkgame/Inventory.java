package ch.bbw.zorkgame;

import java.util.HashMap;

public class Inventory {
    private HashMap<String, Integer> itemList;

    public Inventory(Item item) {
        itemList = new HashMap<String, Integer>();
        itemList.put(item.getName(), item.getUsability());
    }
}
