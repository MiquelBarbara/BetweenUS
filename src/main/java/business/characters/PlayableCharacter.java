package business.characters;

import business.characters.attributes.Colour;
import business.characters.attributes.Direction;
import business.characters.attributes.Perception;
import business.characters.roles.Role;
import business.exceptions.BusinessException;
import persistence.exceptions.PersistenceException;

import java.awt.*;

/**
 * Class used to represent the character that is going to be controlled by user
 */
public class PlayableCharacter extends Character {
    /**
     * Hitbox of the character to be moved
     */
    private final Rectangle hitBox;
    /**
     * boolean that indicates if the character is colliding
     */
    private boolean collision;
    /**
     * boolean that indicates if the character has any trigger event
     */
    private boolean triggerEvent;

    /**
     * Constructor of the Playable character class
     * @param colour Colour of the character
     * @param role Role of the character (Impostor/crewmate)
     * @param location Location of the character
     * @throws BusinessException occurs when there's an error while loading resources
     */
    public PlayableCharacter (Colour colour, Role role, Point location) throws BusinessException {
        super(colour, role, location);
        hitBox = new Rectangle(8, 16, 32,32);
        collision = false;
        triggerEvent = false;
        setDirection(Direction.IDLE);
    }

    /**
     * Function that gets the playableCharacter's hitbox
     * @return playableCharacter's hitbox
     */
    public Rectangle getHitBox() {
        return hitBox;
    }
    /**
     * Function that check the playableCharacter's collision
     * @return playableCharacter's collision
     */
    public boolean isCollision() {
        return collision;
    }
    /**
     * Function that sets the playableCharacter's collision
     * @param collision value to be set
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }
    /**
     * Function that gets the playableCharacter's triggerEvent
     * @return playableCharacter's triggerEvent
     */
    public boolean isTriggerEvent() {
        return triggerEvent;
    }
    /**
     * Function that sets the playableCharacter's triggerEvent
     * @param  triggerEvent value to be set
     */
    public void setTriggerEvent(boolean triggerEvent) {
        this.triggerEvent = triggerEvent;
    }

    /**
     * Function that moves the character according to its direction
     */
    public void move() {
        switch (direction) {
            case UP -> location.y -= speed;
            case DOWN -> location.y += speed;
            case LEFT -> location.x -= speed;
            case RIGHT -> location.x += speed;
           // case UP_LEFT -> { location.y -= 0.71 * speed; location.x -= 0.71 * speed; }
           // case UP_RIGHT -> { location.y -= 0.71 * speed; location.x += 0.71 * speed; }
           // case DOWN_LEFT -> { location.y += 0.71 * speed; location.x -= 0.71 * speed; }
           // case DOWN_RIGHT -> { location.y += 0.71 * speed; location.x += 0.71 * speed; }
        }
    }

    @Override
    public synchronized void notifyNewMovement() {
        for (Observer observer:observers) {
            observer.saveLocation(this,location);
        }
    }

    @Override
    public void notifyNewAction() {

    }

    public Perception getPerception() {
        return Perception.UNKNOWN;
    }
}
