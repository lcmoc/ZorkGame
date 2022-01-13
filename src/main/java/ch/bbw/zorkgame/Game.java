package ch.bbw.zorkgame;

import ch.bbw.zorkgame.CommandComponents.Command;
import ch.bbw.zorkgame.CommandComponents.CommandExecutions;


public class Game {
    private Parser parser;
    private Room currentRoom;
    private Room outside, bathroom, basement, stairway, bedroom, kitchen ;
    private CommandExecutions commandExecutions;
    private Prints prints;

    public Game() {

        parser = new Parser(System.in);

        outside = new Room("Finally you escaped from the house. But you still have to hurry and so he can't catch you. There is the small car of your kidnapper in front of the door. 10 meters away there is a scooter and also a bus station");
        bathroom = new Room("bathroom, a dark room with one small window. There is a wardrobe next to the bathtub. Do you want to open it? ");
        basement = new Room("You're in the basement. On the left side and on the right side is a door. The door on the right side is locked and you can't open it with your hands.");
        stairway = new Room("You can see the stairs and the dog of your kidnapper. It's a bulldog. He's happily sleeping on the carpet. Wait, he has a key on his collar");
        kitchen = new Room("The kitchen and the living room are together. The old man is in the kitchen and preparing food for you.");
        bedroom = new Room("That's the room of this old, blind man. There is a huge bed and a wardrobe.");

        outside.setExits(null, null, kitchen, null);
        bathroom.setExits(null, basement, null, null);
        basement.setExits(null, stairway, null, bathroom);
        stairway.setExits(null, null, kitchen, null);
        bedroom.setExits(null, kitchen, null, null);
        kitchen.setExits(stairway,null, outside, bedroom);

        currentRoom = basement; // start game outside
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
