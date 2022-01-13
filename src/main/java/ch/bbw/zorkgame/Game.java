package ch.bbw.zorkgame;

import ch.bbw.zorkgame.CommandComponents.Command;
import ch.bbw.zorkgame.CommandComponents.CommandExecutions;


public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room outside, lab, tavern, gblock, office;
    private CommandExecutions commandExecutions;
    private Prints prints;

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

        currentRoom = outside; // start game outside
        commandExecutions = new CommandExecutions();
        prints = new Prints();
    }

    public void play() {
        prints.printWelcome(currentRoom);
        prints.printHelp(parser);

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = commandExecutions.isGameFinished(command, parser, currentRoom);
        }

        System.out.println("Thank you for playing. Good bye.");
    }
}
