package business.game.map;

import java.awt.*;

/**
 * Class used to specify hidden connections between rooms
 */
public class Scuttle {
    /**
     * attribute that indicates the first room of the hidden connection
     */
    private Point room1;
    /**
     * attribute that indicates the second room of the hidden connection
     */
    private Point room2;

    /**
     * Constructor of the Scuttle class
     * @param room1 first room with connection
     * @param room2 secod room with connection
     */
    public Scuttle(Point room1, Point room2){
        this.room1 = room1;
        this.room2 = room2;
    }

    /**
     * Function that returns the first connection room
     * @return first connection room
     */
    public Point getRoom1() {
        return room1;
    }

    /**
     * Function that returns the second connection room
     * @return second connection room
     */
    public Point getRoom2() {
        return room2;
    }
}
