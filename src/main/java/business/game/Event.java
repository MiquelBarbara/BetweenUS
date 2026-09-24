package business.game;

import java.awt.*;

/**
 *The EventRect class extends the Rectangle class and adds properties for handling events.
 */

public class Event {
    /**
     * hitbox of the event
     */
    private Rectangle eventHitBox;

    public Event(Rectangle eventHitBox){
        this.eventHitBox = eventHitBox;
    }


    /**
     * Function that returns the Event hitbox
     * @return event's hitbox
     */
    public Rectangle getEventHitBox() {
        return eventHitBox;
    }


    // TODO JAVADOC HERE
    public void execute(){
        //Whatever the event does
    }
}
