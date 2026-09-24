package presentation;

import business.exceptions.BusinessException;
import business.exceptions.LogInException;
import business.exceptions.ResourcesNotAccessedException;
import business.exceptions.format.FormatException;
import business.game.GameManager;
import business.game.config.GameConfigManager;
import business.user.UserManager;
import presentation.views.panels.menu.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static presentation.views.panels.PanelKey.*;

public class MenuController implements ActionListener {
    /**
     * private variable which is the panel of the logIn
     */
    private LogInPanel logInPanel;
    /**
     * private variable which is the panel of the Register
     */
    private RegisterPanel registerPanel;
    /**
     * private variable which is the panel of the gameConfig
     */
    private GameConfigPanel gameConfigPanel;
    /**
     * private variable which is the panel of the settings
     */
    private SettingsPanel settingsPanel;
    /**
     * private variable which stores part of the string that will return the logout button when pressed
     */
    private final String logOutString = "text=LogOut";
    /**
     * private variable which stores the class UserManager
     */
    private UserManager userManager;
    /**
     * private variable which stores the class GameConfigManager
     */
    private GameConfigManager gameConfigManager;
    /**
     * private variable which is the panel of the manageExistingConfigs
     */
    private ManageExistingConfigsPanel manageExistingConfigsPanel;
    /**
     * private variable which stores the class GameManager
     */
    private GameManager gameManager;
    /**
     * private variable which stores the id of the user
     */
    private int userID;
    /**
     * private variable which stores the class MasterController
     */
    private MasterController masterController;

    /**
     * Creates the MenuController
     * @param masterController is the controller that controls the other controllers
     * @param logInPanel the login view
     * @param registerPanel the register view
     * @param gameConfigPanel the gameConfig view
     * @param settingsPanel the settings view
     * @param manageExistingConfigsPanel the manageExistingConfigs view
     */
    public MenuController(MasterController masterController, LogInPanel logInPanel, RegisterPanel registerPanel,
                          GameConfigPanel gameConfigPanel, SettingsPanel settingsPanel, ManageExistingConfigsPanel manageExistingConfigsPanel) {
        initializePanels(logInPanel, registerPanel, gameConfigPanel, settingsPanel, manageExistingConfigsPanel);

        this.masterController = masterController;

        userManager = new UserManager();
        gameConfigManager = new GameConfigManager();
        gameManager = new GameManager();
    }

    private void initializePanels(LogInPanel logInPanel, RegisterPanel registerPanel, GameConfigPanel gameConfigPanel, SettingsPanel settingsPanel, ManageExistingConfigsPanel manageExistingConfigsPanel) {
        this.logInPanel = logInPanel;
        this.registerPanel = registerPanel;
        this.gameConfigPanel = gameConfigPanel;
        this.settingsPanel = settingsPanel;
        this.manageExistingConfigsPanel = manageExistingConfigsPanel;
    }

    /**
     * The class overrides the actionPerformed function to control the changes in the views that need part of the Business layer
     * @param e the actionEvent is happening
     */
    @Override
    public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand()) {
                case "mainMenu":
                    if (logInPanel.isVisible()) {
                        logIn();
                    } else if (registerPanel.isVisible()){
                        register();
                    } else {
                        masterController.changePanel(e.getActionCommand(), this);
                    }
                    break;
                case "initial":
                    if (settingsPanel.isVisible()) {
                        if (e.getSource().toString().contains(logOutString)) {
                            logOut();
                        } else {
                            deleteAccount();
                        }
                    }
                    else {
                        masterController.changePanel(e.getActionCommand(), this);
                    }
                    break;
                case "game":
                    if (gameConfigPanel.isVisible()) {
                        int gameID = 0;
                        try {
                            gameID = gameManager.createGame(userID, gameConfigManager.createGameConfig(gameConfigPanel.getData(), userID));
                            masterController.changeToGameController(userID, gameID);
                        } catch (BusinessException exception) {
                            System.out.println("error, name already get");
                        }




                    }
                    break;
                case "copyConfig":
                    manageExistingConfigsPanel = new ManageExistingConfigsPanel();
                    masterController.changePanel(COPY_CONFIGS.label, this);
                    break;
                default:
                    masterController.changePanel(e.getActionCommand(), this);
            }

    }

    private void logIn() {
        try {
            userID = userManager.logIn(logInPanel.getCredentials());
            masterController.changePanel(MAIN_MENU.label, this);
        } catch (BusinessException e) {
            logInPanel.showCredentialsError(e.getMessage());
        }
    }

    private void logOut () {
        if (settingsPanel.askLogOut()) {
            masterController.changePanel(INITIAL.label, this);
        }
    }

    private void deleteAccount () {
        if (settingsPanel.askDelete()) {
            try {
                userManager.deleteUserAccount(userID);
                masterController.changePanel(INITIAL.label, this);
            } catch (ResourcesNotAccessedException e) {

            }

        }
    }

    private void register() {
        try {
            userID = userManager.createUser(registerPanel.getCredentials());
            masterController.changePanel(MAIN_MENU.label, this);
        } catch (FormatException e) {
            registerPanel.showFormatError(e.getMessage());
        } catch (ResourcesNotAccessedException e) {
            throw new RuntimeException(e);
        }
    }
}
