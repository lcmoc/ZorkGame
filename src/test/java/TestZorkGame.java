import ch.bbw.zorkgame.Game;
import ch.bbw.zorkgame.Inventory;
import ch.bbw.zorkgame.Item;
import ch.bbw.zorkgame.Room;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestZorkGame {

    private Game game;
    private Room room, room2, room3;
    private Inventory inventory;
    private Item item, item2, item3;

    @BeforeEach
    public void before(){
        game = new Game();
        inventory = new Inventory();
        room = new Room("You're in the sitting room. It's a big room with a television and a sofa.","sitting room");
        room2 = new Room("You're in the kidsroom. There are toys on the ground and a little kid sleeping on the couch.","kidsroom");
        room3 = new Room("You're in the attic. It has spiders and insects everywhere.","attic");

        room.setExits(null,room2,room3,null);
        room2.setExits(null,null,null,room);
        room3.setExits(room,null,null,null);


        item = new Item("pen",2,5);
        item2 = new Item("vase",1,40);
        item3 = new Item("chair",3,100);

        room.setItem(item);
        room2.setItem(item2);
        room3.setItem(item3);

        inventory.setItem(item);
        inventory.setItem(item2);
        inventory.setItem(item3);

    }
    @Test
    public void testGame() throws Exception {

    }
    @Test
    public void testRoom() throws Exception {
    }
    @Test
    public void testInventory() throws Exception {
        assertTrue(inventory.getTotalItemWeight() == 145);
        assertTrue(inventory.getItemList().size() == 3 );
    }
    @Test
    public void testItem() throws Exception {
        assertEquals("pen", item.getName());
        assertEquals(2, item.getUsability());
        assertEquals(5, item.getWeight());
    }
}
