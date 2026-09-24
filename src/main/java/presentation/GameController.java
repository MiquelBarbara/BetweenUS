package presentation;

import business.characters.attributes.Direction;
import business.exceptions.BusinessException;
import business.game.Game;
import business.game.GameManager;
import business.game.GameMaster;
import presentation.views.panels.PanelKey;
import presentation.views.panels.game.GamePanel;
import presentation.views.panels.game.LogsPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;
/**
 * The class controls everything related to the Game
 */
public class GameController implements KeyListener, GameHandler {
    /**
     * private variable that stores the id of the user
     */
    private int userID;
    /**
     * private variable that stores the id of the game
     */
    private int gameID;
    /**
     * private variable that stores game
     */
    private Game game;
    /**
     * private variable the panel of the game
     */
    private GamePanel gamePanel;
    /**
     * private variable the panel of the logs
     */
    private LogsPanel logsPanel;
    /**
     * private variable that stores gameManager
     */
    private GameManager gameManager;
    /**
     * private variable that stores the gameMaster
     */
    private GameMaster gameMaster;
    /**
     * private variable that stores the masterController
     */
    private MasterController masterController;
    /**
     * private variable that stores if up is pressed
     */
    private boolean upPressed;
    /**
     * private variable that stores if down is pressed
     */
    private boolean  downPressed;
    /**
     * private variable that stores if left is pressed
     */
    private boolean leftPressed;
    /**
     * private variable that stores if right is pressed
     */
    private boolean rightPressed;

    /**
     * class that creates the gameController
     * @param masterController the masterController class
     * @param userID the id of the user
     * @param gameID the id of the game
     * @param gamePanel the panel of the game
     * @param logsPanel the panel of the logs
     */
    public GameController(MasterController masterController, int userID, int gameID, GamePanel gamePanel, LogsPanel logsPanel) {
        this.masterController = masterController;
        this.userID = userID;
        this.gameID = gameID;
        this.gamePanel = gamePanel;
        this.logsPanel = logsPanel;
        this.gameManager = new GameManager();
    }

    /**
     * function that starts the game and starts the threads of it
     */
    public void startGame() {
        gameMaster = new GameMaster(game, this);

        Thread thread = new Thread(gamePanel);
        thread.start();
        Thread thread2 = new Thread(gameMaster);
        thread2.start();
    }

    /**
     * function that puts the game up to date
     */
    public void feedGamePanel() {
        try {
            game = gameManager.loadGame(gameID);
            gamePanel.setGame(game, masterController);
            logsPanel.updateLogs(game.getLogs());
        } catch (BusinessException e) {

        }

    }

    /**
     * function to know if a key has been pressed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * function to know which key has been pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case KeyEvent.VK_ESCAPE:

                saveGame();

                gamePanel.removeKeyListener(this);

                masterController.changeToMenuController(PanelKey.MAIN_MENU);

        }

        updateDirection();
    }

    /**
     * function to know which key has been released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch (code) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }

        updateDirection();
    }

    /**
     * function that updates the direction
     */
    public void updateDirection() {
        if(upPressed && rightPressed) {
            gamePanel.updatePlayerDirection(Direction.UP_RIGHT);
        } else if(upPressed && leftPressed) {
            gamePanel.updatePlayerDirection(Direction.UP_LEFT);
        } else if(downPressed && rightPressed) {
            gamePanel.updatePlayerDirection(Direction.DOWN_RIGHT);
        } else if(downPressed && leftPressed) {
            gamePanel.updatePlayerDirection(Direction.DOWN_LEFT);
        } else if(upPressed) {
            gamePanel.updatePlayerDirection(Direction.UP);
        } else if(rightPressed) {
            gamePanel.updatePlayerDirection(Direction.RIGHT);
        } else if(downPressed) {
            gamePanel.updatePlayerDirection(Direction.DOWN);
        } else if(leftPressed) {
            gamePanel.updatePlayerDirection(Direction.LEFT);
        } else {
            gamePanel.updatePlayerDirection(Direction.IDLE);
        }
    }

    private void saveGame() {
        try {
            gameManager.saveGame(game);
            gameMaster.endGame();
        } catch (BusinessException e) {

        }

    }

    /**
     * function that ends the game and returns to the menu
     */
    @Override
    public void endGame() {
        masterController.changePanel(PanelKey.DEFEAT.label, this);
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {

        }
        masterController.changeToMenuController(PanelKey.MAIN_MENU);
    }
}
