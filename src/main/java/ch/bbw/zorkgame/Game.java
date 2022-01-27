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

    public boolean processCommand(Command command) {
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
        } else if (commandWord.equals(COMMAND_KILL)) {
            kill(command);
        } else if (commandWord.equals(COMMAND_QUIT)) {
            if (command.hasSecondWord()) {
                System.out.println("Quit what?");
            } else {
                return true;
            }
        }
        return false;
    }

    public void kill(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("kill what?");
        } else {
            String secondCommad = command.getSecondWord();
            if (secondCommad.equals("dog")) {
                if (currentRoom.equals(basement) || currentRoom.equals(bathroom)) {
                    System.out.println("You're not in the same room as the dog");
                } else if (inventory.getItemList().containsKey("hammer") || inventory.getItemList().containsKey("winebottle")) {
                    System.out.println("The dog was following you, so you killed the dog.");
                    System.out.println("Now you can take the key from his collar");
                    stairway.setItem(key);
                } else {
                    System.out.println("you dont have a weapon to kill him, you would have no chance");
                }
            } else if (secondCommad.equals("man")) {
                if (currentRoom.equals(kitchen)) {
                    System.out.println("You tried to kill him, but he killed you.");
                    System.exit(0);
                } else {
                    System.out.println("You're not in the same room as your kidnapper");
                }
            } else {
                System.out.println("There is a spelling mistake in your command. Your second command is " + secondCommad);
            }
        }
    }

    public void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
        } else {
            String direction = command.getSecondWord();
            currentDirection = direction;
            Room nextRoom = currentRoom.nextRoom(direction);
            if (nextRoom == null)
                System.out.println("There is no door!");
            else {
                if (currentRoom.shortDescription().equals("kitchen") && !inventory.getItemList().containsKey("key")) {
                    System.out.println("the dore is closed, you need a key to pass");
                } else {
                    currentRoom = nextRoom;
                    System.out.println(currentRoom.longDescription());
                    if (currentRoom.shortDescription().equals("outside")) {
                        System.exit(0);
                    }
                }
            }
        }
    }

    public void goLastRoom() {
        if (currentDirection != null) {
            System.out.println(currentRoom);
            String back = lastRoom(currentDirection);
            Room lastRoom = currentRoom.nextRoom(back);

            if (lastRoom != null) {
                currentRoom = lastRoom;
                System.out.println(currentRoom.longDescription());
            }
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

    public void showItems(Command command) {
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

    public void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("take what?");
        } else {
            String secondCommad = command.getSecondWord();
            HashMap<String, Item> currentItems = currentRoom.getItems();

            if (currentItems.containsKey(secondCommad)) {
                Item currentItem = currentItems.get(secondCommad);

                if (inventory.getTotalItemWeight() > 100 && currentItem.getWeight() < 100) {
                    System.out.println("you are to heavy, you cant take anything more with you");
                    System.out.println("type drop and the item name, to lose weight");
                } else if (secondCommad.equals(currentItem.getName())) {
                    inventory.setItem(currentItem);
                    currentRoom.removeItem(currentItem.getName());
                    inventory.showItems();
                }
            } else {
                System.out.println("there is no such item in this room");
            }
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("drop what?");
        } else {
            String secondCommad = command.getSecondWord();
            HashMap<String, Item> currentItems = inventory.getItemList();

            if (currentItems.containsKey(secondCommad)) {
                Item currentItem = currentItems.get(secondCommad);
                if (secondCommad.equals(currentItem.getName())) {
                    inventory.drop(currentItem.getName());
                    currentRoom.setItem(currentItem);
                    inventory.showItems();
                }
            } else {
                System.out.println("you dont have that item in your inventory");
            }
        }


    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
