package business.game;

/**
 * Class that is used to show the character's Log into rooms
 */
public class Log {
    /**
     * Colour of the player
     */
    private final String playerColour;
    /**
     * actual perception of the player
     */
    private final String perception;
    /**
     * name of the room at
     */
    private final String roomName;
    /**
     * moment of the log
     */
    private final int instant;

    /**
     * Constructor of Log class
     * @param playerColour colour of the player
     * @param perception actual perception of the player
     * @param roomName actual room of the player
     * @param instant instant of the log
     */
    public Log(String playerColour, String perception, String roomName, int instant) {
        this.playerColour = playerColour;
        this.perception = perception;
        this.roomName = roomName;
        this.instant = instant;
    }

    /**
     * Function used to get player's colour
     * @return player's colour
     */
    public String getPlayerColour() {
        return playerColour;
    }

    /**
     * Function used to get log's instant
     * @return log's instant
     */
    public int getInstant() {
        return instant;
    }
    /**
     * Function used to get log's perception
     * @return log's perception to string
     */
    public String getPerception() {
        return perception;
    }
    /**
     * Function used to get log's room name
     * @return log's room name
     */
    public String getRoomName() {
        return roomName;
    }
}
