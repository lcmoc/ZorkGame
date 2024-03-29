package ch.bbw.zorkgame;

import java.util.Arrays;
import java.util.List;

import static ch.bbw.zorkgame.Constants.*;

public class CommandWords {
    private List<String> validCommands = Arrays.asList(
            COMMAND_GO,
            COMMAND_QUIT,
            COMMAND_HELP,
            COMMAND_BACK,
            COMMAND_MAP,
            COMMAND_SHOW,
            COMMAND_TAKE,
            COMMAND_DROP,
            COMMAND_KILL
    );

    public boolean isCommand(String commandWord) {
        return validCommands.stream()
                .filter(command -> command.equals(commandWord))
                .count() > 0;
    }

    public String showAll() {
        return String.join(" ", validCommands);
    }
}
