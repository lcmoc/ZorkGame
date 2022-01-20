package ch.bbw.zorkgame;

public class Prints {

    public void printWelcome(Room currentRoom) {
        System.out.println();
        System.out.println("Welcome to Adventure House!");
        System.out.println("It is a simple Zork game.");
        System.out.println("The story is that you got kidnapped by a man, after you accidently killed his whole family, who keeps you in his basement.");
        System.out.println("You could get rid of the handcuff and know you try to escape from his house.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.longDescription());
    }

    public void printHelp(Parser parser) {
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }
}
