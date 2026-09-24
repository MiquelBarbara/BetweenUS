package presentation.views.panels.game;

import business.characters.PlayableCharacter;
import business.characters.attributes.Direction;
import business.exceptions.BusinessException;
import business.game.Game;
import business.game.config.GameConfig;
import business.game.map.GameMap;
import business.game.map.Room;
import business.game.map.Tile;
import business.game.map.TileManager;
import presentation.MasterController;
import presentation.views.CharacterClassificationTable;
import presentation.views.panels.menu.BetweenUsPanel;

import javax.swing.*;

import java.awt.*;

import java.awt.event.KeyListener;
import java.util.EventListener;

/**
 * The GamePanel class represents a panel that displays the game screen and handles user input.
 */
public class GamePanel extends JPanel implements Runnable, BetweenUsPanel {
    /**
     * the game is being played
     */
    private Game game;
    /**
     * the gameMap is being played
     */
    private GameMap gameMap;
    /**
     * the character is playing
     */
    private PlayableCharacter playableCharacter;
    /**
     * the table to classify all the NPC
     */
    private CharacterClassificationTable characterClassificationTable;
    /**
     * the with of the frame
     */
    private final int width = Tile.TILE_SIZE * 27; //Amount of Tiles in a column.
    /**
     * the height of the frame
     */
    private final int height = Tile.TILE_SIZE * 18; //Amount of Tiles in a row.
    /**
     * the FPS we play
     */
    private final int FPS = 60;
    /**
     * the graphics the panel use
     */
    private Graphics2D graphics2D;
    /**
     * the class that prints the map
     */
    private MapPrinter mapPrinter;
    /**
     * the class that prints the character
     */
    private CharacterPrinter characterPrinter;
    /**
     * the class that prints the NPC
     */
    private NpcPrinter npcPrinter;
    /**
     * to know if the panel is being shown or not
     */
    private boolean isActive;
    /**
     * the class that controls everything
     */
    private MasterController masterController;

    /**
     * Constructs a GamePanel object.
     * Initializes the panel with the specified dimensions, background color, and settings.
     */
    public GamePanel() {
       // this.setBackground();
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.mapPrinter = new MapPrinter(width, height);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.requestFocusInWindow(true);
    }

    /**
     * Sets the game and the master controller for the panel.
     * @param game the Game object representing the current game
     * @param masterController the MasterController object handling the game events and actions
     * @throws BusinessException if there is an error in setting up the game
     */
    public void setGame(Game game, MasterController masterController) throws BusinessException {
        this.game = game;


        TileManager tileManager = new TileManager();
        this.gameMap = tileManager.getMap(game.getMapName());

        playableCharacter = game.getPlayableCharacter();
        this.masterController = masterController;
        this.characterPrinter = new CharacterPrinter(playableCharacter, width, height, masterController);
        this.npcPrinter = new NpcPrinter(game.getNPCs(), width, height);

        this.setSize(1300, 900);
    }

    /**
     * Overrides the paintComponent method to draw the game elements on the panel.
     * @param graphics  the Graphics object used for drawing
     */
    public void paintComponent(Graphics graphics) {
        GameConfig gameConfig = game.getGameConfig();
        PlayableCharacter playableCharacter = game.getPlayableCharacter();
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        mapPrinter.draw(graphics2D, gameConfig.getGameMap(), playableCharacter.getLocation());
        npcPrinter.draw(graphics2D, playableCharacter.getLocation());
        characterPrinter.draw(graphics2D);
        graphics2D.dispose();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public synchronized void run() {
        double interval = 1000000000.0/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        isActive = true;

        while (isActive) {
            //Check the current time
            currentTime = System.nanoTime();
            //Check how much time has past
            delta += (currentTime - lastTime) / interval;

            lastTime = currentTime;

            if(delta >= 1) {
                // 1 UPDATE: update player position

                    update();

                // 2 DRAW the screen the update information
                repaint();
                delta--;
            }
        }
    }

    /**
     * Updates the game state, including the player position and character printing.
     */
    public void update() {
        //GameMap gameMap = game.getGameConfig().getGameMap();
        PlayableCharacter playableCharacter = game.getPlayableCharacter();
        //Room room = gameMap.getRooms().get(playableCharacter.getRoom());
        Room room = gameMap.getPlayableCharacterRoom(playableCharacter.getRoom());
        characterPrinter.update(room, this.isVisible());
    }
    /**
     * Updates the direction of the playable character.
     * @param direction the new direction of the playable character
     */
    public void updatePlayerDirection(Direction direction) {
        playableCharacter.setDirection(direction);
    }

    @Override
    public void attachListener(EventListener listener) {
        this.addKeyListener((KeyListener) listener);
        characterPrinter.setKeyListener((KeyListener) listener);
    }

    @Override
    public void detachListener(EventListener listener) {

    }
}
