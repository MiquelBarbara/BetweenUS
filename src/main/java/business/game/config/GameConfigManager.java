package business.game.config;

import business.characters.attributes.Colour;
import business.exceptions.BusinessException;
import business.exceptions.ResourcesNotAccessedException;
import persistence.game.gameconfig.GameConfigDAO;
import persistence.exceptions.PersistenceException;
import persistence.game.gameconfig.SQLGameConfigDAO;

import java.util.ArrayList;

/**
 * Class used to manage GameConfiguration class (enables CRUD operations)
 */
public class GameConfigManager {
    /**
     * GameConfigDAO used to persist, get and delete configurations saved
     */
    private final GameConfigDAO gameConfigDAO;

    /**
     * Constructor of the GameConfigurationManager
     */
    public GameConfigManager() {
        gameConfigDAO = new SQLGameConfigDAO();
    }

    /**
     * Function that creates a new game configuration
     * @param data String array containing the necessary data to create a GameConfig.
     * @param userID ID of the user that creates a game
     */
    public GameConfig createGameConfig(String[] data,  int userID) throws BusinessException {
        GameConfig gameConfig = new GameConfig(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]),
                Colour.fromString(data[3]), data[4]);
        try {
            gameConfigDAO.saveGameConfig(gameConfig, userID);
            return gameConfig;
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }
    }

    /**
     * Function used to get the userGame configurations given the user ID
     * @param userID id of the user to get the game configurations
     * @return Arraylist of game configurations
     */
    public ArrayList<GameConfig> getUserGameConfigs (int userID) {
        try {
            return gameConfigDAO.getGameConfigurations(userID);
        } catch (PersistenceException e) {

        }

        return null;
    }
}
