package ch.bbw.zorkgame;

import java.util.Arrays;
import java.util.List;

public class CommandWords {

    private List<String> validCommands = Arrays.asList("go", "quit", "help" );

    public boolean isCommand(String commandWord) {
        return validCommands.stream()
                .filter(command -> command.equals(commandWord))
                .count()>0;
    }

    public String showAll() {
        return String.join(" ", validCommands);
    }

}
