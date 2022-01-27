import ch.bbw.zorkgame.Game;
import ch.bbw.zorkgame.Inventory;
import ch.bbw.zorkgame.Item;
import ch.bbw.zorkgame.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestZorkGame {

    private Room room, room2, room3;
    private Inventory inventory;
    private Item item, item2, item3;

    @BeforeEach
    public void before(){
        item = new Item("pen",5);
        item2 = new Item("vase",40);
        item3 = new Item("chair",100);
    }
    @Test
    public void testRoom() throws Exception {
        room = new Room("You're in the sitting room. It's a big room with a television and a sofa.","sitting room");
        room2 = new Room("You're in the kidsroom. There are toys on the ground and a little kid sleeping on the couch.","kidsroom");
        room3 = new Room("You're in the attic. It has spiders and insects everywhere.","attic");

        room.setExits(null,room2,room3,null);
        room2.setExits(null,null,null,room);
        room3.setExits(room,null,null,null);

        room.setItem(item);
        room2.setItem(item2);
        room3.setItem(item3);

        assertEquals( room2,room.nextRoom("east"));
        assertEquals(room3,room.nextRoom("south"));
    }
    @Test
    public void testInventory() throws Exception {
        inventory = new Inventory();
        inventory.setItem(item);
        inventory.setItem(item2);
        inventory.setItem(item3);
        assertTrue(inventory.getTotalItemWeight() == 145);
        assertTrue(inventory.getItemList().size() == 3 );
    }
    @Test
    public void testItem() throws Exception {
        assertEquals("pen", item.getName());
        assertEquals(5, item.getWeight());
    }
}
