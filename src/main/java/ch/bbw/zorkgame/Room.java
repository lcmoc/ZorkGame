package ch.bbw.zorkgame;

import java.util.HashMap;

public class Room {

        private String description;
        private HashMap<String, Room> exits;
        private String name;

        public Room(String description, String name) {
            this.description = description;
            this.exits = new HashMap<>();
            this.name = name;
        }

        public void setExits(Room north, Room east, Room south, Room west) {
            exits.put("north", north);
            exits.put("east", east);
            exits.put("south", south);
            exits.put("west", west);
        }

        public String shortDescription() {
            return name;
        }

        public String longDescription() {
            StringBuilder stringBuilder = new StringBuilder(description + ".\n");
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

        @Override
        public String toString() {
            return "––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––";
        }


}
