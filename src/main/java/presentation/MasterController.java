package presentation;


import presentation.views.panels.PanelKey;

import java.util.EventListener;
/**
 * Interface that choose which controller is being used
 */
public interface MasterController {
    /**
     * This function changes the panel is being shown in the frame
     * @param panelKey the key that represents the new panel
     * @param listener the listener that needs the panels
     */
    void changePanel(String panelKey, EventListener listener);
    /**
     * This function changes to gameController
     * @param userID the id of the user
     * @param gameID the id of the game
     */
    void changeToGameController(int userID, int gameID);

    /**
     * This function changes to the menuController
     * @param panelKey the key that represents the new panel
     */
    void changeToMenuController(PanelKey panelKey);
}
