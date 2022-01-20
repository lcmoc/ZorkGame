package ch.bbw.zorkgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

import static ch.bbw.zorkgame.Constants.COMMAND_BACK;


public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room outside, lab, tavern, gblock, office;
    private String currentDirection;
    private Prints prints;
    private Item key, knife;

    public Game() {

        parser = new Parser(System.in);

        outside = new Room("outside G block on Peninsula campus");
        lab = new Room("lab, a lecture theatre in A block");
        tavern = new Room("the Seahorse Tavern (the campus pub)");
        gblock = new Room("the G Block");
        office = new Room("the computing admin office");

        outside.setExits(null, lab, gblock, tavern);
        lab.setExits(null, null, null, outside);
        tavern.setExits(null, outside, null, null);
        gblock.setExits(outside, office, null, null);
        office.setExits(null, null, null, gblock);

        key = new Item("knife", 2, 20);

        outside.setItem(key);

        outside.showItems();

        currentRoom = outside; // start game outside
        currentDirection = "";
        prints = new Prints();
    }


    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() {
        prints.printWelcome(currentRoom);

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
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
        if (commandWord.equals("help")) {
            prints.printHelp(parser);

        } else if (commandWord.equals("go")) {
            goRoom(command);

        } else if(commandWord.equals("back")) {
            goLastRoom();

        } else if (commandWord.equals("quit")) {
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

            // Try to leave current room.
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
        String back = lastRoom(currentDirection);
        Room lastRoom = currentRoom.nextRoom(back);
        currentRoom = lastRoom;
        System.out.println(currentRoom.longDescription());
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
            System.out.println("there is no door");
            return null;
        }
    }

}