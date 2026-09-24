package presentation.views.panels.menu.components;

import business.characters.attributes.Colour;

/**
 * Class used to create a game configuration data table
 */
public class GameConfigDataTable {
    /**
     * name of the configuration
     */
    private String name;
    /**
     * Colour of the character
     */
    private Colour colour;
    /**
     * amount of impostors of the configuration
     */
    private int impostorCount;
    /**
     * amount of players in the configuration
     */
    private int playerCount;

    /**
     * Constructor of the GameConfigDataTable class
     * @param name name to be set
     * @param color colour to be set
     * @param impostorCount amount of impostors to be set
     * @param playerCount amount of players to be set
     */
    public GameConfigDataTable (String name, Colour color, Integer impostorCount, Integer playerCount) {
        this.name = name;
        this.colour = color;
        this.impostorCount = impostorCount;
        this.playerCount = playerCount;
    }

    /**
     * Function used to get the config name
     * @return config name
     */
    public String getName() {
        return name;
    }

    /**
     * Function used to get the plaeyr colour
     * @return player colour
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Function used to get the amount of impostors
     * @return amount of impostors
     */
    public int getImpostorCount() {
        return impostorCount;
    }

    /**
     * Function used to get the players amount of the config
     * @return config players amount
     */
    public int getPlayerCount() {
        return playerCount;
    }
}
