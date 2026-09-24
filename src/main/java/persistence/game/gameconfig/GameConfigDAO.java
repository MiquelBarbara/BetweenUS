package persistence.game.gameconfig;

import business.game.config.GameConfig;
import persistence.exceptions.PersistenceException;

import java.util.ArrayList;

public interface GameConfigDAO {

    /**
     * Function that saves the game configuration for the new game created
     * adding it to the database into Game table
     * @param gameConfig GameConfig instance that contains basic game information
     * @param userID ID of the user creating game
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void saveGameConfig(GameConfig gameConfig, int userID) throws PersistenceException;

    /**
     * Function that reads the game configuration for the specified game of the user
     * @param configName Name of the configuration saved by the user
     * @param userID ID of the user who created the game
     * @return Instance of the game Configuration
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    GameConfig getGameConfig(String configName, int userID) throws PersistenceException;

    /**
     * Function that gets all game configurations from the specified user
     * @param userID ID of the user from where we want to get all game configurations
     * @return arraylist of game configurations from the selected user
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    ArrayList<GameConfig> getGameConfigurations(int userID) throws PersistenceException;

    /**
     * Function that given the configurationGame name and the username deletes the specified configuration from the database
     * @param configurationName name of the configuration saved by user
     * @param userID ID of the user who is going to delete its configuration Game
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void deleteGameConfigurations(String configurationName, int userID) throws PersistenceException;

    /**
     * Function used to delete all user configuration games
     * from the user with matching ID
     * @param userID id of the user to delete game configurations
     */
    void deleteUserConfigurations(int userID) throws PersistenceException;
}