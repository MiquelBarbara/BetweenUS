package business.game;


import business.characters.PlayableCharacter;
import business.game.map.Room;
import business.game.map.Tile;

import java.awt.*;

/**
 *EventHandler handles the game events and collision detection for those.
 *It contains methods for checking events, handling collisions and execute them.
 */

public class EventHandler {
    private int previousEventX, previousEventY;

    /**
     * Constructs an EventHandler object with the given GamePanel reference.
     * Initializes the eventRect array to the maximum world columns and rows and sets each EventRect's default values.
     */
    public EventHandler(){

    }

    /**
     * Checks if an event can occur based on the distance between the player character's current location and the previous event location.
     * If the distance is greater than one tile size, the canTouchEvent flag is set to true.
     */
    public boolean checkEvent(PlayableCharacter playableCharacter, Room room) {
        //Check if the player character is more 1 tile away from the last event
        int xDistance = Math.abs(playableCharacter.getLocation().x - previousEventX);
        int yDistance = Math.abs(playableCharacter.getLocation().y - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > Tile.TILE_SIZE) {
            playableCharacter.setTriggerEvent(true);
        }
        if (playableCharacter.isTriggerEvent()) {
            //Check room events based on the players location
            if (!room.eventsIsEmpty()) {
                for (int i = 0; i < room.getEventsSize(); i++) {
                    if (triggerEvent(room.getEvent(i), playableCharacter)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the player character collides with the event rectangle at the given column and row coordinates and in the specified direction.
     * Gets player's collision area and event rectangle's x and y coordinates to check for collision.
     * If a collision occurs and the event is not already done, sets the previous event location to the player's current location.
     * @return true if a collision occurs and the event is triggered, false otherwise
     */
    public boolean triggerEvent(Event event, PlayableCharacter playableCharacter) {
        boolean trigger = false;
        //Get relative position of the hitBox of the player in the room
        int hitBoxPositionX = ((playableCharacter.getLocation().x + playableCharacter.getHitBox().x) - (playableCharacter.getRoom().x * Room.ROOM_SIZE * Tile.TILE_SIZE))/Tile.TILE_SIZE;
        int hitBoxPositionY = ((playableCharacter.getLocation().y + playableCharacter.getHitBox().y) - (playableCharacter.getRoom().y * Room.ROOM_SIZE * Tile.TILE_SIZE))/Tile.TILE_SIZE;
        Rectangle auxiliaryHitBox = new Rectangle(hitBoxPositionX, hitBoxPositionY, playableCharacter.getHitBox().width, playableCharacter.getHitBox().y);

        //Check if the player hitBox is colliding with the event rectangle area
        if (auxiliaryHitBox.intersects(event.getEventHitBox())) {
            trigger = true;
            //We prevent to trigger the event more than 1 time before leaving the event area
            previousEventX = hitBoxPositionX;
            previousEventY = hitBoxPositionY;

        }
        return trigger;
    }

}
