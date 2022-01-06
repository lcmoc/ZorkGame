package ch.bbw.zorkgame.CommandComponents;

import ch.bbw.zorkgame.Parser;
import ch.bbw.zorkgame.Prints;
import ch.bbw.zorkgame.Room;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import static ch.bbw.zorkgame.Constants.*;

public class CommandExecutions {
    private boolean isGameFinished;
    private Prints prints;
    private String currentDirection;

    public CommandExecutions() {
        prints = new Prints();
        currentDirection = new String();
    }

    public boolean isGameFinished(Command command, Parser parser, Room currentRoom) {
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();

        if (commandWord.equals(COMMAND_HELP)) {
            prints.printHelp(parser);

        } else if (commandWord.equals(COMMAND_GO)) {
            goRoom(command, currentRoom);

        } else if (commandWord.equals(COMMAND_QUIT)) {
            if (command.hasSecondWord()) {
                System.out.println("Quit what?");

            } else {
                return true; // signal that we want to quit
            }

        } else if (commandWord.equals(COMMAND_BACK)) {
            goLastRoom(currentRoom);
            return false;
        }
        return false;
    }

    public void goRoom(Command command, Room currentRoom) {

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







    public void goLastRoom(Room currentRoom) {
        System.out.println(currentDirection);
        String back = lastRoom(currentDirection);
        System.out.println(back);
        // Room lastRoom = currentRoom.nextRoom(back);
       // System.out.println(currentRoom);
      //  currentRoom = lastRoom;
      //  System.out.println(currentRoom);
      //  System.out.println(currentRoom.shortDescription());
    }

    public String lastRoom(String currentDirection) {
        if(currentDirection == "north") {
            return "south";
        } else if(currentDirection == "south") {
            return "north";
        } else if(currentDirection == "east") {
            return "west";
        } else if(currentDirection == "west") {
            return "east";
        } else {
            System.out.println("there is no way back");
            return null;
        }
    }
}
