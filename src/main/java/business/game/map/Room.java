package business.game.map;

import business.game.Event;
import business.game.map.attributes.RoomType;

import java.util.ArrayList;

/**
 * Class that represents a room contained into a Map
 */
public class Room {
    /**
     * integer that indicates the room size (amount of tiles)
     */
    public final static int ROOM_SIZE = 13;
    /**
     * name of the room
     */
    private final String roomName;

    private final RoomType roomType;
    /**
     * Array of tiles saved into the room
     */
    private final Tile[] tiles;
    /**
     * matrix that indicates the tile num
     */
    private final int[][] mapTileNum;
    /**
     * arraylist of events contained into the room
     */
    private final ArrayList<Event> events;

    /**
     * Constructor of the Room class
     * @param ROOM_NAME name of the room
     * @param tiles array of tiles
     * @param mapTileNum matrix that indicates the map tile number
     */
    public Room(String ROOM_NAME, RoomType roomType, Tile[] tiles, int[][] mapTileNum, ArrayList<Event> events) {
        this.roomName = ROOM_NAME;
        this.roomType = roomType;
        this.tiles = tiles;
        this.mapTileNum = mapTileNum;
        this.events = events;
    }

    /**
     * Function used to get the Room name
     * @return Room's name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Function used to get the Room Type
     * @return Room's type
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Function used to get the Room mapTileNum
     * @return Room's mapTileNum
     */
    public int[][] getMapTileNum() {
        return mapTileNum;
    }
    /**
     * Function used to get the Room tiles
     * @return Room's tiles
     */
    public Tile[] getTiles() {
        return tiles;
    }
    /**
     * Function used to get the tile by its coordinates on matrix
     * @return number of the map tile on the coordinates
     */
    public int getTileByCoordinates(int entityLeftCol, int entityTopRow) {
        return mapTileNum[entityLeftCol][entityTopRow];
    }
    /**
     * Function used to check if a tile is a collision tile
     * @return true if the tile is a collision tile
     */
    public boolean tileIsColliding(int tile) {
        return tiles[tile].isCollision();
    }
    /**
     * Function used to get the Room's events
     * @return Room's events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Function used to check if room events are empty
     * @return true when room events are empty
     */
    public boolean eventsIsEmpty() {
        return events.isEmpty();
    }

    /**
     * Function used to check if a character can walk into a room according to its type
     * @return true when it can
     */
    public boolean canWalkIn(){
        return !RoomType.VOID.equals(roomType);
    }


    /**
     * Function used to get room events size
     * @return room's events size
     */
    public int getEventsSize() {
        return events.size();
    }

    /**
     * Function used to get an event by its index
     * @param index index of the event to get
     * @return Event selected
     */
    public Event getEvent(int index) {
        return events.get(index);
    }
}
