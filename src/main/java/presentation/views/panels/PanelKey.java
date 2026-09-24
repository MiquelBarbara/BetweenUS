package presentation.views.panels;

/**
 * enum of all the views we have in the program
 */
public enum PanelKey {
    /**
     * initial panel
     */
    INITIAL("initial"),
    /**
     * login panel
     */
    LOGIN("logIn"),
    /**
     * register panel
     */
    REGISTER("register"),
    /**
     * mainMenu panel
     */
    MAIN_MENU("mainMenu"),
    /**
     * gameConfig panel
     */
    GAME_CONFIG("gameConfig"),
    /**
     * loadGame panel
     */
    LOAD_GAME("loadGame"),
    /**
     * logs panel
     */
    LOGS("logs"),
    /**
     * defeat panel
     */
    DEFEAT("defeat"),
    /**
     * continueGame panel
     */
    CONTINUE_GAME("continueGame"),
    /**
     * deleteGame panel
     */
    DELETE_GAME("deleteGame"),
    /**
     * copyConfigs panel
     */
    COPY_CONFIGS("copyConfig"),
    /**
     * settings panel
     */
    SETTINGS("settings"),
    /**
     * game panel
     */
    GAME("game"),
    /**
     * statistics panel
     */
    STATISTICS ("statistics");
    /**
     * label to bre returned
     */
    public final String label;

    private PanelKey(String label) {
        this.label = label;
    }


}
