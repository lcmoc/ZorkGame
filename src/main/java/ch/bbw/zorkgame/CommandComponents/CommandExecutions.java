package ch.bbw.zorkgame.CommandComponents;

import ch.bbw.zorkgame.Parser;
import ch.bbw.zorkgame.Prints;
import ch.bbw.zorkgame.Room;
import static ch.bbw.zorkgame.Constants.*;

public class CommandExecutions {
    private boolean isGameFinished;
    private Prints prints;

    public CommandExecutions() {
        isGameFinished = false;
        prints = new Prints();
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
            goRoom(command, currentRoom);
            return false;
        }
        return false;
    }

    public void goRoom(Command command, Room currentRoom) {

        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
        } else {
            String direction = command.getSecondWord();
            Room nextRoom = currentRoom.nextRoom(direction);

            if (nextRoom == null)
                System.out.println("There is no door!");
            else {
                currentRoom = nextRoom;
                System.out.println(currentRoom.longDescription());
            }
        }
    }

}
