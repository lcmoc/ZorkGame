package ch.bbw.zorkgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

public class Room {

    private String description;
    private HashMap<String, Room> exits;
    private String name;
    private HashMap<String, Item> items;

    public Room(String description, String name) {
        this.description = description;
        this.exits = new HashMap<>();
        this.name = name;
        this.items = new HashMap<>();
    }

    public void setExits(Room north, Room east, Room south, Room west) {
        exits.put("north", north);
        exits.put("east", east);
        exits.put("south", south);
        exits.put("west", west);
    }

    public void showExits() {
        for (Map.Entry<String, Room> entry : exits.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value != null) {
                System.out.println("- " + key);
            }
        }
    }

    public String shortDescription() {
        return name;
    }

    public String longDescription() {
        StringBuilder stringBuilder = new StringBuilder(description + ".\n");
        return stringBuilder.toString();
    }

    public Room nextRoom(String direction) {
        System.out.println(exits.get(direction));
        return exits.get(direction);
    }

    public void setItem(Item item) {
        items.put(item.getName(), item);
    }

    public void removeItem(String itemName) {
        items.remove(itemName);
    }

    public void listItems() {
        if (items.isEmpty()) {
            System.out.println("Here are no items");
        } else {
            int number = 0;
            for (Item item : items.values()) {
                number++;
                System.out.println("Item " + number + ": " + item.getName());
            }
        }
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––";
    }
}
