package ch.bbw.zorkgame;

import java.util.*;

import static ch.bbw.zorkgame.Constants.*;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room outside, bathroom, basement, kitchen, stairway, bedroom;
    private String currentDirection;
    private ArrayList<Room> rooms;
    private Item key, hammer, winebottle, bed, wardrobe, wd2, plant;
    private Prints prints;
    private Inventory inventory;

    public Game() {
        parser = new Parser(System.in);
        outside = new Room("Finally you escaped from the house. But you still have to hurry and so he can't catch you. There is the small car of your kidnapper in front of the door. 10 meters away there is a scooter and also a bus station", "outside");
        bathroom = new Room("bathroom, a dark room with one small window. There is a wardrobe next to the bathtub. Do you want to open it?", "bathroom");
        basement = new Room("You're in the basement. On the left side and on the right side is a door. The door on the right side is locked and you can't open it with your hands.", "basement");
        stairway = new Room("You can see the stairs and the dog of your kidnapper. It's a bulldog. He's happily sleeping on the carpet. Wait, he has a key on his collar", "stairway");
        kitchen = new Room("You're in the kitchen. The old man is in the kitchen and preparing food for dinner. He is deaf so you don't have to worry of making noices.", "kitchen");
        bedroom = new Room("That's the room of this old, blind man. There is a huge bed and a wardrobe.", "bedroom");

        outside.setExits(kitchen, null, null, null);
        bathroom.setExits(null, basement, null, null);
        basement.setExits(null, stairway, null, bathroom);
        stairway.setExits(null, null, kitchen, basement);
        bedroom.setExits(null, kitchen, null, null);
        kitchen.setExits(stairway, null, outside, bedroom);

        key = new Item("key", 10);
        hammer = new Item("hammer", 60);
        winebottle = new Item("winebottle", 80);
        bed = new Item("bed", 1000);
        wardrobe = new Item("wardrobe", 1500);
        wd2 = new Item("wardrobe", 500);
        plant = new Item("plant", 10);

        stairway.setItem(key);
        stairway.setItem(plant);
        bathroom.setItem(hammer);
        bathroom.setItem(wardrobe);
        kitchen.setItem(winebottle);
        bedroom.setItem(wd2);
        bedroom.setItem(bed);

        currentRoom = basement;

        prints = new Prints();

        rooms = new ArrayList<Room>();
        rooms.add(outside);
        rooms.add(bathroom);
        rooms.add(basement);
        rooms.add(stairway);
        rooms.add(kitchen);
        rooms.add(bedroom);

        inventory = new Inventory();
    }

    public void play() {
        prints.printWelcome(currentRoom);
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private boolean processCommand(Command command) {
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();

        if (commandWord.equals(COMMAND_HELP)) {
            prints.printHelp(parser);
        } else if (commandWord.equals(COMMAND_GO)) {
            goRoom(command);
        } else if (commandWord.equals(COMMAND_BACK)) {
            goLastRoom();
        } else if (commandWord.equals(COMMAND_MAP)) {
            printMap();
        } else if (commandWord.equals(COMMAND_SHOW)) {
            showItems(command);
        } else if (commandWord.equals(COMMAND_TAKE)) {
            takeItem(command);
        } else if (commandWord.equals(COMMAND_DROP)) {
            dropItem(command);
        } else if (commandWord.equals(COMMAND_QUIT)) {
            if (command.hasSecondWord()) {
                System.out.println("Quit what?");
            } else {
                return true;
            }
        }
        return false;
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
        } else {
            String direction = command.getSecondWord();
            currentDirection = direction;
            Room nextRoom = currentRoom.nextRoom(direction);
            if (nextRoom == null)
                System.out.println("There is no door!");
            else {
                currentRoom = nextRoom;
                System.out.println(currentRoom.longDescription());
            }
        }
    }

    public void goLastRoom() {
        if (currentDirection != null) {
            System.out.println(currentRoom);
            String back = lastRoom(currentDirection);
            Room lastRoom = currentRoom.nextRoom(back);

            currentRoom = lastRoom;
            System.out.println(currentRoom.longDescription());
        } else {
            System.out.println("you can't go back");
        }
    }

    public String lastRoom(String currentDirection) {
        if (currentDirection.equals("north")) {
            return "south";
        } else if (currentDirection.equals("south")) {
            return "north";
        } else if (currentDirection.equals("east")) {
            return "west";
        } else if (currentDirection.equals("west")) {
            return "east";
        } else {
            return null;
        }
    }

    public void printMap() {
        if (currentDirection != null) {
            System.out.println("you came from " + lastRoom(currentDirection));
        }
        System.out.println("you are in " + currentRoom.shortDescription());
        System.out.println();
        System.out.println("Other rooms and items:");
        for (Room room : rooms) {
            System.out.println(room.shortDescription());
            System.out.println();
            System.out.println("Exits:");
            room.showExits();
            System.out.println();
            room.listItems();
            System.out.println("------------------------------");
        }
    }

    private void showItems(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("show what?");
        } else {
            String secondCommad = command.getSecondWord();
            if (secondCommad.equals("items")) {
                currentRoom.listItems();
            } else if (secondCommad.equals("inventory")) {
                inventory.showItems();
            } else {
                System.out.println("There is a spelling mistake in your command. Your second command is " + secondCommad);
            }
        }
    }

    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("take what?");
        } else {
            String secondCommad = command.getSecondWord();
            HashMap<String, Item> currentItems = currentRoom.getItems();

            for (Item item : currentItems.values()) {
                if (inventory.getTotalItemWeight() > 100 && item.getWeight() > 100) {
                    System.out.println("you are to heavy, you cant take anything more with you");
                    System.out.println("type drop and the item name, to lose weight");
                } else if (secondCommad.equals(item.getName())) {
                    inventory.setItem(item);
                    currentRoom.removeItem(item.getName());
                    inventory.showItems();
                }
            }
        }
    }

    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("drop what?");
        } else {
            String secondCommad = command.getSecondWord();
            HashMap<String, Item> currentItems = inventory.getItemList();

            for (Map.Entry<String, Item> entry : currentItems.entrySet()) {
                String name = entry.getKey();
                Item item = entry.getValue();

                if (secondCommad.equals(name)) {
                    inventory.drop(name);
                    currentRoom.setItem(item);
                }
            }
        }
    }
}
