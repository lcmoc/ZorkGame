import ch.bbw.zorkgame.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ch.bbw.zorkgame.Constants.COMMAND_HELP;
import static ch.bbw.zorkgame.Constants.COMMAND_QUIT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestZorkGame {
    Game game;
    private Room room, room2, room3;
    private Inventory inventory;
    private Item item, item2, item3;

    @BeforeEach
    public void before() {
        game = new Game();
        item = new Item("pen", 5);
        item2 = new Item("vase", 40);
        item3 = new Item("chair", 100);
        inventory = new Inventory();
        room = new Room("You're in the sitting room. It's a big room with a television and a sofa.", "sitting room");
        room2 = new Room("You're in the kidsroom. There are toys on the ground and a little kid sleeping on the couch.", "kidsroom");
        room3 = new Room("You're in the attic. It has spiders and insects everywhere.", "attic");
    }

    @Test
    public void testRoom() throws Exception {
        room.setExits(null, room2, room3, null);
        room2.setExits(null, null, null, room);
        room3.setExits(room, null, null, null);
        room.setItem(item);
        room2.setItem(item2);
        room3.setItem(item3);

        assertEquals(room2, room.nextRoom("east"));
        assertEquals(room3, room.nextRoom("south"));
        assertEquals(null, room3.nextRoom("south"));
        assertEquals("sitting room", room.shortDescription());
        assertEquals("kidsroom", room2.shortDescription());
        assertEquals("attic", room3.shortDescription());
        room.removeItem("pen");
        assertNull(room.getItems().get("pen"));
    }

    @Test
    public void testInventory() throws Exception {
        inventory = new Inventory();
        inventory.setItem(item);
        inventory.setItem(item2);
        inventory.setItem(item3);
        assertTrue(inventory.getTotalItemWeight() == 145);
        assertTrue(inventory.getItemList().size() == 3);
        assertTrue(inventory.getItemList().get("pen") == item);

        inventory.drop("chair");
        assertNull(inventory.getItemList().get("chair"));
    }

    @Test
    public void testItem() throws Exception {
        assertEquals("pen", item.getName());
        assertEquals(5, item.getWeight());
        assertEquals("vase", item2.getName());
        assertEquals(40, item2.getWeight());
        assertEquals("chair", item3.getName());
        assertEquals(100, item3.getWeight());
    }

    @Test
    public void goRoom() throws Exception {
        Command command = new Command("go", "east");
        game.goRoom(command); // should print longDescription of the stairway
    }

    @Test
    public void testLastRoom() throws Exception {
        assertEquals(game.lastRoom("east"), "west");
        assertEquals(game.lastRoom("north"), "south");
    }

    @Test
    public void testTakeItem() {
        Command take = new Command("take", "pen");
        room.setItem(item);
        game.setCurrentRoom(room);
        game.takeItem(take);

        assertTrue(game.getInventory().getItemList().containsKey("pen"));
        assertFalse(game.getCurrentRoom().getItems().containsKey("pen"));
    }

    @Test
    public void testDropItem() {
        Command drop = new Command("drop", "pen");
        Command take = new Command("take", "pen");
        room.setItem(item);
        game.setCurrentRoom(room);
        game.takeItem(take);
        game.dropItem(drop);

        assertFalse(game.getInventory().getItemList().containsKey("pen"));
        assertTrue(game.getCurrentRoom().getItems().containsKey("pen"));
    }

    @Test
    public void testProcessCommand() {
        Command command1 = new Command("sthTheMethodDontKnow");
        Command command2 = new Command(COMMAND_HELP);
        Command command3 = new Command(COMMAND_QUIT, "game");
        Command command4 = new Command(COMMAND_QUIT);

        assertFalse(game.processCommand(command1));
        assertFalse(game.processCommand(command2));
        assertFalse(game.processCommand(command3));
        assertTrue(game.processCommand(command4));
    }
}
