package persistence.game.gameconfig;


import business.characters.attributes.Colour;
import business.exceptions.BusinessException;
import business.game.config.GameConfig;
import persistence.exceptions.DBNotAccessibleException;
import persistence.exceptions.PersistenceException;
import persistence.database.SQLConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO of the gameConfiguration connected to DB
 */
public class SQLGameConfigDAO implements GameConfigDAO {

    /**
     * Constructor of SQLGameConfigDAO
     */
    public SQLGameConfigDAO() {
    }

    @Override
    public void saveGameConfig(GameConfig gameConfig, int userID) throws PersistenceException {

        String query = "INSERT INTO GameConfiguration(name, numberCrewmates, numberImpostors, playerColour, selectedMap, ID_Creador) VALUES ('" +
                gameConfig.getName() + "', '" +
                gameConfig.getCharacterCount() + "', '" +
                gameConfig.getImpostorCount() + "', '" +
                gameConfig.getPlayerColour() + "', '" +
                gameConfig.getMapName() + "', '" +
                userID + "');";

        SQLConnector.getInstance().insertQuery(query);
    }

    @Override
    public GameConfig getGameConfig(String configName, int userID) throws PersistenceException {

        String query = "SELECT name, numberCrewmates, numberImpostors, playerColour, selectedMap FROM GameConfiguration WHERE name = '" + configName + "' AND ID_Creador = '" + userID + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);
        GameConfig gameConfig = null;
        try {
            while (result.next()) {

                String name = result.getString("name");
                int numberCharacters = result.getInt("numberCrewmates");
                int numberImpostors = result.getInt("numberImpostors");
                String playerColour = result.getString("playerColour");
                String selectedMap = result.getString("selectedMap");

                gameConfig = new GameConfig(name, numberCharacters, numberImpostors, Colour.valueOf(playerColour), selectedMap);
            }
        } catch (SQLException | BusinessException e ) {
            throw new DBNotAccessibleException();
        }
        return gameConfig;
    }

    @Override
    public ArrayList<GameConfig> getGameConfigurations(int UserID) throws PersistenceException {

        String query = "SELECT name, numberCrewmates, numberImpostors, playerColour, selectedMap FROM GameConfiguration WHERE ID_Creador = '" + UserID + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);
        ArrayList<GameConfig> gameConfigArrayList = new ArrayList<>();
        try {
            while (result.next()) {

                String name = result.getString("name");
                int numberCharacters = result.getInt("numberCrewmates");
                int numberImpostors = result.getInt("numberImpostors");
                String playerColour = result.getString("playerColour");
                String selectedMap = result.getString("selectedMap");

                gameConfigArrayList.add(new GameConfig(name, numberCharacters, numberImpostors, Colour.valueOf(playerColour), selectedMap));
            }
        } catch (SQLException | BusinessException e) {
            throw new DBNotAccessibleException();
        }
        return gameConfigArrayList;
    }

    @Override
    public void deleteGameConfigurations(String configurationName, int userID) throws PersistenceException {

        String query = "DELETE FROM GameConfiguration WHERE ID_Creador = '" + userID + "' AND name = '" + configurationName + "';";
        SQLConnector.getInstance().deleteQuery(query);
    }

    @Override
    public void deleteUserConfigurations(int userID) throws PersistenceException {
        ArrayList<GameConfig> gameConfigs = getGameConfigurations(userID);
        for (GameConfig gameConfig:gameConfigs) {
            deleteGameConfigurations(gameConfig.getName(), userID);
        }
    }
}
