package ch.bbw.zorkgame;

import java.util.HashMap;

public class Room {

        private String description;
        private HashMap<String, Room> exits;

        public Room(String description) {
            this.description = description;
            this.exits = new HashMap<>();
        }

        public void setExits(Room north, Room east, Room south, Room west) {
            exits.put("north", north);
            exits.put("east", east);
            exits.put("south", south);
            exits.put("west", west);
        }

        public String shortDescription() {
            return description;
        }

        public String longDescription() {
            StringBuilder stringBuilder = new StringBuilder("You are in " + description + ".\n");
            stringBuilder.append(exitString());
            return stringBuilder.toString();
        }

        private String exitString() {
            return "Exits:" + String.join(" ", exits.keySet());
        }

        public Room nextRoom(String direction) {
            System.out.println(exits.get(direction));
            return exits.get(direction);
        }
}
