package business.characters;

import business.Luck;
import business.characters.attributes.Colour;
import business.characters.attributes.Direction;
import business.characters.attributes.Perception;
import business.characters.roles.Role;
import business.exceptions.BusinessException;
import business.game.ImageManager;
import business.game.map.Room;
import business.game.map.Tile;
import persistence.exceptions.PersistenceException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Class used to represent the characters that are playing into a game
 */
public abstract class Character implements Observable{
    /**
     * ID of the player (provided by DB)
     */
    protected int ID;
    /**
     * colour of the player
     */
    protected Colour colour;
    /**
     * Role of the player
     */
    protected Role role;
    /**
     * Location of the player
     */
    protected Point location;
    /**
     * actual room of the player
     */
    protected Point room;
    /**
     * actual direction of the player
     */
    protected Direction direction;
    /**
     * player's speed
     */
    protected int speed = 4;
    /**
     * default image to be displayed to represent the player
     */
    protected BufferedImage idle;
    /**
     * boolean that indicates if the character is dead or alive
     */
    protected boolean isDead;
    /**
     * Arraylist of images that contains all character's walking animation
     */
    private ArrayList<BufferedImage> walk = new ArrayList<>();
    /**
     * Arraylist of images that contains all inverted character's walking animations
     */
    private ArrayList<BufferedImage> invertedWalk = new ArrayList<>();
    /**
     * Arraylist of observers that are observing the character
     */
    protected ArrayList<Observer> observers;
    protected boolean running;

    /**
     * Constructor of the character class
     * @param colour colour of the character
     * @param role role of the character
     * @param room initial room of the character
     */
    public Character (Colour colour, Role role, Point room) throws BusinessException {
        this.colour = colour;
        this.role = role;
        this.room = room;
        setLocation();
        ImageManager imageManager = new ImageManager();
        idle = imageManager.getCharacterSprite(colour, idle, walk, invertedWalk);
        this.running = true;
    }

    /**
     * Function that returns character's location
     * @return character's location
     */
    public Point getLocation() {
        return location;
    }
    /**
     * Function that returns character's room
     * @return character's room
     */
    public Point getRoom() {
        return room;
    }
    /**
     * Function that returns character's direction
     * @return character's direction
     */
    public Direction getDirection() {
        return direction;
    }
    /**
     * Function that returns character's speed
     * @return character's speed
     */
    public int getSpeed() {
        return speed;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    /**
     * Function that returns character's image (idle)
     * @return character's image
     */
    public BufferedImage getIdle() {
        return idle;
    }
    /**
     * Function that returns character's images list
     * @return character's images list
     */
    public ArrayList<BufferedImage> getWalk() {
        return walk;
    }
    /**
     * Function that returns character's inverted images list
     * @return character's inverted images list
     */
    public ArrayList<BufferedImage> getInvertedWalk() {
        return invertedWalk;
    }

    /**
     * Function that sets character's location
     */
    public void setLocation(){
        int x = ((room.x * Tile.TILE_SIZE * Room.ROOM_SIZE) + (Tile.TILE_SIZE * Room.ROOM_SIZE)/2) + Luck.RandomPixel(-10,10);
        int y = ((room.y * Tile.TILE_SIZE * Room.ROOM_SIZE) + (Tile.TILE_SIZE * Room.ROOM_SIZE)/2) + Luck.RandomPixel(-10,10);
        this.location = new Point(x,y);
    }

    /**
     * Function that sets Character's room
     * @param newRoom new room to be set
     */
    public void setRoom(Point newRoom){
        this.room = newRoom;
        setLocation();
    }
    /**
     * Function that returns character's ID
     * @return character's location
     */
    public int getID() {
        return ID;
    }

    /**
     * Function that sets character's ID
     * @param ID ID to be set
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * Function that checks if character is dead
     * @return character's location
     */
    public boolean isDead() {
        return false;
    }
    /**
     * Function that returns character's colour
     * @return character's location
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Function used to get the character's default perception
     * @return character's default perception
     */
    public String getDefaultPerception() {
        return Perception.getDefaultPerception();
    }
    /**
     * Function that sets the NPC alive state to dead (isDead = true)
     */
    public void die() {
        this.isDead = true;
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void addObserver(Observer observer) {
        if (this.observers == null) {
            this.observers = new ArrayList<>();
        }

        observers.add(observer);
    }

    /**
     * Function that returns a boolean indicating if the NPC is able to make killing actions
     * @return true if the NPC is able to make killing actions
     */
    public boolean canKill() {
        return false;
    }

    /**
     * Function used to get the label of the colour of the character
     * @return character's colour label
     */
    public String getColourLabel() {
        return this.colour.label;
    }

    /**
     * Function used to get character perception
     * @return Character's perception
     */
    public abstract Perception getPerception();



}
