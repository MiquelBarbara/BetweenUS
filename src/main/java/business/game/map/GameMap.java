package business.game.map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Class that represents the whole map of the game that is going to be displayed
 */
public class GameMap {
    /**
     * integer that indicates the amount of columns of the map
     */
    private final int mapWordColumns;
    /**
     * integer that indicates the amount of rows of the map
     */
    private final int mapWordRows;
    /**
     * String that indicates the name of the map
     */
    private final String mapName;
    /**
     * Hashmap that indicates the rooms contained by the map
     */
    private final Map<Point, Room> rooms;
    /**
     * Point that indicates the initial position (room) of the map
     */
    private final Point initialRoom;
    /**
     * ArrayList of all the scuttles of the map
     */
    private final ArrayList<Scuttle> scuttles;

    /**
     * GameMap class constructor
     * @param mapWordCol map amount of columns
     * @param mapWordRow map amount of rows
     * @param MAP_NAME map name
     * @param rooms map rooms
     * @param initialRoom map initial room
     */
    public GameMap(int mapWordCol, int mapWordRow, String MAP_NAME, Map<Point, Room> rooms, Point initialRoom, ArrayList<Scuttle> scuttles) {
        this.mapName = MAP_NAME;
        this.rooms = rooms;
        this.mapWordRows = mapWordCol;
        this.mapWordColumns = mapWordRow;
        this.initialRoom = initialRoom;
        this.scuttles = scuttles;
    }

    /**
     * Function that returns the map amount of columns
     * @return map amount of columns
     */
    public int getMapWordColumns() {
        return mapWordColumns;
    }
    /**
     * Function that returns the map amount of rows
     * @return map amount of rows
     */
    public int getMapWordRows() {
        return mapWordRows;
    }
    /**
     * Function that returns the map rooms
     * @return map rooms
     */
    public Map<Point, Room> getRooms() {
        return rooms;
    }

    /**
     * Function that returns the tile number of a specified room on the map
     * @param roomIn specified room to get the tile number
     * @param entityLeftCol entity X position
     * @param entityTopRow entity Y position
     * @return number of the tile
     */
    public int getRoomTileNum(Point roomIn, int entityLeftCol, int entityTopRow) {
        Room room = rooms.get(roomIn);
        return room.getTileByCoordinates(entityLeftCol, entityTopRow);
    }

    /**
     * Funtion used to check if a tile has a collision given the tile location and the tile number
     * @param location location of the tile to check
     * @param tile tile number
     * @return true if the tile has a collision
     */
    public boolean tileIsColliding(Point location, int tile) {
        Room room = rooms.get(location);
        return room.tileIsColliding(tile);
    }
    /**
     * Function that gets the map initial position
     * @return point according to the map initial position
     */
    public Point getInitialRoom() {
        return initialRoom;
    }

    /**
     * Function that returns the map name
     * @return map name
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Function that returns the scuttles in the map
     * @return all the scuttles
     */
    public ArrayList<Scuttle> getScuttles() {
        return scuttles;
    }


    /**
     * Function that returns a random point of the map (room) according to the actual room in
     * this function is designed to be used by impostors
     * @param actualRoom actual room of the character
     * @param nonPossibleRooms amount of rooms that are not disponible
     * @return new location (point,room) of the character
     */
    public Point getRandomRoom(Point actualRoom, ArrayList<Point> nonPossibleRooms) {
        if (rooms.isEmpty()) {
            return null; // La tabla de hash está vacía, retorna null
        }

        ArrayList<Point> eligibleRooms = new ArrayList<>();

        for (Point room : rooms.keySet()) {
            if (!room.equals(actualRoom) && !nonPossibleRooms.contains(room)) {
                //if (!room.equals(actualRoom) && room.canBeChosen()) { //això es per quan tinguem l'atribut que especifica
                // si es pot anar a una habitació (no passadis, no habitacio nul.la)
                eligibleRooms.add(room);
            }
        }

        if (eligibleRooms.isEmpty()) {
            return null; // No hay habitaciones elegibles diferentes a la actual, retorna null
        }

        int randomIndex = (int) (Math.random() * eligibleRooms.size());
        return eligibleRooms.get(randomIndex);
    }

    /**
     * Function that returns a random adjacent room of the map with
     * the limitations of the actual room and the previous room
     * this function is designed to be used by the crewmates
     * @param actualRoom actual room of the character
     * @param previousRoom previous room of the user
     * @return new location (point, room) of the character
     */
    public Point getRoomWithLimits(Point actualRoom, Point previousRoom) {
        if (rooms.isEmpty()) {
            return null; // La tabla de hash està buida, retorna null
        }

        ArrayList<Point> eligibleRooms = new ArrayList<>();

        for (Point room : rooms.keySet()) {
            if (!room.equals(actualRoom) && !room.equals(previousRoom)) {
                //if (!room.equals(actualRoom) && room.canBeChosen()) { //això es per quan tinguem l'atribut que especifica
                // si es pot anar a una habitació (no passadis, no habitacio nul.la)
                eligibleRooms.add(room);
            }
        }

        if (eligibleRooms.isEmpty()) {
            return null; // No hay habitaciones elegibles diferentes a la actual, retorna null
        }

        int randomIndex = (int) (Math.random() * eligibleRooms.size());
        return eligibleRooms.get(randomIndex);
    }

    /**
     * Function used to get the playable character's room according to its point on the map
     * @param playerRoom point that specifies the map room
     * @return Instance of class room according to the player point room
     */
    public Room getPlayableCharacterRoom(Point playerRoom) {
        return rooms.get(playerRoom);
    }

    /**
     * Function used to get the adjacent rooms to the actual room (points)
     * @param actualRoom point indicating the actual room on map
     * @return Arraylist of points indicating the adjacent rooms of the room
     */
    public ArrayList<Point> getAdjacentRooms(Point actualRoom) {
        ArrayList<Point> adjacentRooms = new ArrayList<>();
        Point topRoom = new Point(actualRoom.x, actualRoom.y - 1);
        Point bottomRoom = new Point(actualRoom.x, actualRoom.y + 1);
        Point rightRoom = new Point(actualRoom.x + 1, actualRoom.y);
        Point leftRoom = new Point(actualRoom.x - 1, actualRoom.y);

        if(rooms.get(topRoom).canWalkIn() && roomContainsConnection(rooms.get(topRoom), "S")) {
            adjacentRooms.add(topRoom);
        }
        if(rooms.get(bottomRoom).canWalkIn() && roomContainsConnection(rooms.get(bottomRoom), "N")) {
            adjacentRooms.add(bottomRoom);
        }
        if(rooms.get(rightRoom).canWalkIn() && roomContainsConnection(rooms.get(rightRoom), "W")) {
            adjacentRooms.add(rightRoom);
        }
        if(rooms.get(leftRoom).canWalkIn() && roomContainsConnection(rooms.get(leftRoom), "E")) {
            adjacentRooms.add(leftRoom);
        }
        return adjacentRooms;
    }

    private boolean roomContainsConnection(Room room, String value) {
        return room.getRoomName().contains(value);
    }

    /**
     * Function used to get all connected hidden rooms to the actual room
     * @param actualRoom actual room to look for
     * @return arraylist of points that contains the hidden rooms
     */
    public ArrayList<Point> getHiddenRooms(Point actualRoom) {
        ArrayList<Point> hiddenRooms = new ArrayList<>();
        for(int i = 0; i < scuttles.size(); i++){
            if(getScuttleRoom(i,1).equals(actualRoom)){
                hiddenRooms.add(getScuttleRoom(i,2));
            } else if (getScuttleRoom(i,2).equals(actualRoom)) {
                hiddenRooms.add(getScuttleRoom(i,1));
            }
        }
        return hiddenRooms;
    }

    private Scuttle getScuttlesByIndex(int index) {
        return scuttles.get(index);
    }

    private Point getScuttleRoom(int index, int number) {
        if (number == 1) {
            return getScuttlesByIndex(index).getRoom1();
        }
        return getScuttlesByIndex(index).getRoom2();
    }

    /**
     * Function used to get roomName of the actual room
     * @param newRoom room to get the name of
     * @return Name of the room in
     */
    public String getRoomName(Point newRoom) {
        String roomName = getName(rooms.get(newRoom));

        if (roomName.contains("pass_")) {
            return null;
        }
        return roomName;
    }

    private String getName(Room room) {
        return room.getRoomName();
    }
}