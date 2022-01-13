package ch.bbw.zorkgame;

import java.util.Arrays;
import java.util.List;

import static ch.bbw.zorkgame.Constants.*;

public class CommandWords {
    private List<String> validCommands = Arrays.asList(COMMAND_GO, COMMAND_QUIT, COMMAND_HELP, COMMAND_BACK);

    public boolean isCommand(String commandWord) {
        return validCommands.stream()
                .filter(c -> c.equals(commandWord))
                .count()>0;
    }

    public String showAll() {
        return String.join(" ", validCommands);
    }
}
