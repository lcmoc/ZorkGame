package ch.bbw.zorkgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

import static ch.bbw.zorkgame.Constants.*;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room outside, bathroom, basement, stairway, bedroom, kitchen ;
    private Prints prints;
    private String currentDirection;

    public Game() {
        parser = new Parser(System.in);
        outside = new Room("Finally you escaped from the house. But you still have to hurry and so he can't catch you. There is the small car of your kidnapper in front of the door. 10 meters away there is a scooter and also a bus station", "outside");
        bathroom = new Room("bathroom, a dark room with one small window. There is a wardrobe next to the bathtub. Do you want to open it?", "bathroom");
        basement = new Room("You're in the basement. On the left side and on the right side is a door. The door on the right side is locked and you can't open it with your hands.", "basement");
        stairway = new Room("You can see the stairs and the dog of your kidnapper. It's a bulldog. He's happily sleeping on the carpet. Wait, he has a key on his collar", "stairway");
        kitchen = new Room("The kitchen and the living room are together. The old man is in the kitchen and preparing food for you.", "kitchen");
        bedroom = new Room("That's the room of this old, blind man. There is a huge bed and a wardrobe.", "bedroom");

        outside.setExits(null, null, kitchen, null);
        bathroom.setExits(null, basement, null, null);
        basement.setExits(null, stairway, null, bathroom);
        stairway.setExits(null, null, kitchen, basement);
        bedroom.setExits(null, kitchen, null, null);
        kitchen.setExits(stairway,null, outside, bedroom);

        currentRoom = basement; // start game outside
        prints = new Prints();
    }

    /**
     *  Main play routine.  Loops until end of play.
     */

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
        } else if(commandWord.equals(COMMAND_BACK)) {
            goLastRoom();
        } else if(commandWord.equals(COMMAND_MAP)) {
            printMap();
        } else if (commandWord.equals(COMMAND_QUIT)) {
            if (command.hasSecondWord()) {
                System.out.println("Quit what?");
            } else {
                return true; // signal that we want to quit
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
        if(currentDirection != null) {
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
        if(currentDirection.equals("north")) {
            return "south";
        } else if(currentDirection.equals("south")) {
            return "north";
        } else if(currentDirection.equals("east")) {
            return "west";
        } else if(currentDirection.equals("west")) {
            return "east";
        } else {
            return null;
        }
    }

    public void printMap() {
        if(currentDirection != null) {
            System.out.println("you came from " + lastRoom(currentDirection));
        }
        System.out.println("you are in " + currentRoom.shortDescription());
        System.out.println();

    }

}