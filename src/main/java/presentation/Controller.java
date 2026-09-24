package presentation;

import presentation.views.MainView;
import presentation.views.panels.game.GamePanel;
import presentation.views.panels.game.LogsPanel;
import presentation.views.panels.menu.*;
import presentation.views.panels.PanelKey;
import javax.swing.*;
import java.util.EventListener;
import java.util.HashMap;

/**
 * Controller controls all the views of the program
 */
public class Controller implements MasterController {

    /**
     * private variable that stores the initial panel
     */
    private InitialPanel initialPanel;
    /**
     * private variable that stores the logIn panel
     */
    private LogInPanel logInPanel;
    /**
     * private variable that stores the register panel
     */
    private RegisterPanel registerPanel;
    /**
     * private variable that stores the mainMenu panel
     */
    private MainMenuPanel mainMenuPanel;
    /**
     * private variable that stores the settings panel
     */
    private SettingsPanel settingsPanel;
    /**
     * private variable that stores the gameConfig panel
     */
    private GameConfigPanel gameConfigPanel;
    /**
     * private variable that stores the game panel
     */
    private GamePanel gamePanel;
    /**
     * private variable that stores the logs panel
     */
    private LogsPanel logsPanel;
    /**
     * private variable that stores the defeat panel
     */
    private DefeatPanel defeatPanel;
    /**
     * private variable that stores the main panel
     */
    private MainView mainView;
    /**
     * private variable that stores the manageExistingConfigs panel
     */
    private ManageExistingConfigsPanel manageExistingConfigsPanel;
    /**
     * private variable that stores the manageExistingGames panel
     */
    private ManageExistingGamesPanel manageExistingGamesPanel;
    /**
     * private variable that stores the manageGamesToDelete panel
     */
    private ManageGamesToDeletePanel manageGamesToDeletePanel;
    /**
     * private variable that stores the menuController
     */
    private MenuController menuController;
    /**
     * private variable that stores the gameController
     */
    private GameController gameController;
    /**
     * private variable that stores the statistics panel
     */
    private StatisticsPanel statisticsPanel;

    /**
     * this function creates the controller
     */
    public Controller() {

            initializePanels();
            initializeView();

            menuController = new MenuController(this, logInPanel, registerPanel, gameConfigPanel, settingsPanel, manageExistingConfigsPanel);
            initialPanel.attachListener(menuController);

    }

    private void initializePanels() {
        initialPanel = new InitialPanel();
        logInPanel = new LogInPanel();
        registerPanel = new RegisterPanel();
        mainMenuPanel = new MainMenuPanel();
        gameConfigPanel = new GameConfigPanel();
        gamePanel = new GamePanel();
        logsPanel = new LogsPanel();
        defeatPanel = new DefeatPanel();
        settingsPanel = new SettingsPanel();
        manageExistingConfigsPanel = new ManageExistingConfigsPanel();
        manageExistingGamesPanel = new ManageExistingGamesPanel();
        manageGamesToDeletePanel = new ManageGamesToDeletePanel();
        statisticsPanel = new StatisticsPanel();
    }

    private void initializeView() {
        HashMap<PanelKey, JPanel> panels = new HashMap<>();

        panels.put(PanelKey.INITIAL, initialPanel);
        panels.put(PanelKey.LOGIN, logInPanel);
        panels.put(PanelKey.REGISTER, registerPanel);
        panels.put(PanelKey.MAIN_MENU, mainMenuPanel);
        panels.put(PanelKey.GAME_CONFIG, gameConfigPanel);
        panels.put(PanelKey.GAME, gamePanel);
        panels.put(PanelKey.LOGS, logsPanel);
        panels.put(PanelKey.DEFEAT, defeatPanel);
        panels.put(PanelKey.SETTINGS, settingsPanel);
        panels.put(PanelKey.COPY_CONFIGS, manageExistingConfigsPanel);
        panels.put(PanelKey.CONTINUE_GAME, manageExistingGamesPanel);
        panels.put(PanelKey.DELETE_GAME, manageGamesToDeletePanel);
        panels.put(PanelKey.STATISTICS, statisticsPanel);

        mainView = new MainView(panels);
    }

    /**
     * this function starts the program
     */
    public void run() {
        try {
            mainView.start();
        } catch (NullPointerException e) {
            System.out.println("error, db cant be loaded");
        }

    }

    /**
     * this function changes to the panel needed
     * @param panelKey the key to the panel we have to swap
     * @param listener the listener that needs the panel
     */
    @Override
    public void changePanel(String panelKey, EventListener listener) {
        mainView.changePanel(panelKey, listener);
    }

    /**
     * This function changes to gameController
     * @param userID the id of the user
     * @param gameID the id of the game
     */
    @Override
    public void changeToGameController(int userID, int gameID) {
        gameController = new GameController(this, userID, gameID, gamePanel, logsPanel);

        gameController.feedGamePanel();

        changePanel(PanelKey.GAME.label, gameController);
        gameController.startGame();
    }

    /**
     * This function changes to the menuController
     * @param panelKey the key that represents the new panel
     */
    @Override
    public void changeToMenuController(PanelKey panelKey) {
        changePanel(panelKey.label, menuController);
    }
}
