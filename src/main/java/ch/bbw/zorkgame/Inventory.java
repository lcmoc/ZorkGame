package ch.bbw.zorkgame;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private HashMap<String, Item> itemList;
    private int totalItemWeight = 0;

    public Inventory() {
        itemList = new HashMap<>();
    }

    public void setItem(Item item) {
        itemList.put(item.getName(), item);
        setTotalWeight(item.getWeight());
    }

    private void setTotalWeight(int weight) {
        totalItemWeight = totalItemWeight + weight;
    }

    public int getTotalItemWeight() {
        return totalItemWeight;
    }

    public HashMap<String, Item> getItemList() {
        return itemList;
    }

    public void showItems() {
        System.out.println();
        System.out.println("------------------------------");
        System.out.println("  |       Inventory        |  ");
        System.out.println("------------------------------");

        for (Map.Entry<String, Item> entry : itemList.entrySet()) {
            String name = entry.getKey();
            Item item = entry.getValue();

            System.out.println(name + " Weight: " + item.getWeight());
        }

        System.out.println("------------------------------");
        System.out.println("total weight: " + totalItemWeight);
        System.out.println("max weight: 100");
        System.out.println("------------------------------");
    }

    public void drop(String name) {
        Item currentItem = itemList.get(name);

        itemList.remove(name);
        totalItemWeight = totalItemWeight - currentItem.getWeight();
    }

}
