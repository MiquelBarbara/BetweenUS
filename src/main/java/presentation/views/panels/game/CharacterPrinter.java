package presentation.views.panels.game;

import business.characters.PlayableCharacter;
import business.characters.attributes.Direction;
import business.game.CollisionChecker;
import business.game.EventHandler;
import business.game.map.Room;
import business.game.map.Tile;
import presentation.MasterController;
import presentation.views.panels.PanelKey;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * Class that prints the user in the map
 */
public class CharacterPrinter {
    /**
     * the value of the with of the frame
     */
    private int screenX;
    /**
     * the value of the height of the frame
     */
    private int screenY;
    /**
     * the character has to be printed
     */
    private PlayableCharacter playableCharacter;
    /**
     * the class that checks if there's a collision
     */
    private CollisionChecker collisionChecker;
    /**
     * the class that checks when an event occurs
     */
    private EventHandler eventHandler;
    /**
     * the value of the sprite
     */
    private int playerSpriteCounter = 0;
    /**
     * the value of the current sprite
     */
    private int currentPLayerSprite = 0;
    /**
     * The class that controls everything
     */
    private MasterController masterController;
    /**
     * a key listener to know which buttons do the user press
     */
    private KeyListener keyListener;
    /**
     * the class to know what to do with the panel
     */
    private ThreeStates threeStates;

    /**
     * Constructs a CharacterPrinter object.
     * @param playableCharacter  the PlayableCharacter object representing the character being printed
     * @param viewWidth the width of the game view
     * @param viewHeight the height of the game view
     * @param masterController  the MasterController object handling the game events and actions
     */
    public CharacterPrinter(PlayableCharacter playableCharacter, int viewWidth, int viewHeight, MasterController masterController) {
        this.playableCharacter = playableCharacter;
        this.masterController = masterController;
        collisionChecker = new CollisionChecker();
        eventHandler = new EventHandler();
        screenX = viewWidth/2 - Tile.TILE_SIZE/2;
        screenY = viewHeight/2 - Tile.TILE_SIZE/2;
        threeStates = ThreeStates.WAIT;
    }

    /**
     * Draws the playable character on the screen.
     * @param graphics2D  the Graphics2D object used for drawing
     */
    public void draw(Graphics2D graphics2D) {
        drawPlayer(graphics2D);
    }

    /**
     * Updates the playable character based on the current room and game visibility.
     * @param room the current Room object the character is in
     * @param isVisible a boolean value indicating if the game screen is visible
     */
    public void update(Room room, boolean isVisible) {
        updatePlayer(room, isVisible);
    }
    /**
     * Updates the playable character checking if the is any collision or event.
     * @param room the current Room object the character is in
     * @param isGameVisible a boolean value indicating if the game screen is visible
     */
    private void updatePlayer(Room room, boolean isGameVisible) {
        if (playableCharacter.isRunning()) {
            playableCharacter.setCollision(false);
            collisionChecker.checkTile(playableCharacter, room);
            if (eventHandler.checkEvent(playableCharacter, room) && isGameVisible) {
                //masterController.changeToMenuController();

                masterController.changePanel(PanelKey.LOGS.label, keyListener);
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {

                }
                playableCharacter.setDirection(Direction.LEFT);
                threeStates = ThreeStates.ACTIVATE;


            }

            if (!eventHandler.checkEvent(playableCharacter, room) && threeStates == ThreeStates.ACTIVATE) {
                masterController.changePanel(PanelKey.GAME.label, keyListener);
                threeStates = ThreeStates.DEACTIVATE;
            }

            playerSpriteCounter++;

            if (!playableCharacter.isCollision()) {
                playableCharacter.move();
            }

            if (playerSpriteCounter > 10) {
                if (currentPLayerSprite != 3) {
                    currentPLayerSprite++;
                } else {
                    currentPLayerSprite = 0;
                }
                playerSpriteCounter = 0;
            }
        }
    }

    /**
     * Draws the playable character on the screen with the pertinent sprite.
     * @param graphics2D  the Graphics2D object used for drawing
     */
    private void drawPlayer(Graphics2D graphics2D) {
        BufferedImage sprite;

        if (playableCharacter.getDirection() == Direction.IDLE) {
            sprite = playableCharacter.getIdle();
        } else if (isPlayerLookingRight()) {
            sprite = playableCharacter.getWalk().get(currentPLayerSprite);
        } else {
            sprite = playableCharacter.getInvertedWalk().get(currentPLayerSprite);
        }

        graphics2D.drawImage(sprite, screenX, screenY, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
    }

    /**
     * Checks if the player is moving right
     * return true if it is and false if it is moving Left
     */
    private boolean isPlayerLookingRight() {
        boolean right = false;

        if (playableCharacter.getDirection() == Direction.RIGHT ||
                playableCharacter.getDirection() == Direction.DOWN_RIGHT ||
                playableCharacter.getDirection() == Direction.UP_RIGHT) {
            right = true;
        }

        return right;
    }
    /**
     * Sets the key listener for the playable character.
     * @param listener the key listener object
     */
    public void setKeyListener(KeyListener listener) {
        this.keyListener = listener;
    }


}
