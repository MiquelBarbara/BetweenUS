package persistence.game;


import business.characters.NonPlayableCharacter;
import business.characters.PlayableCharacter;
import business.game.Game;
import business.game.Log;
import business.game.config.GameConfig;
import persistence.exceptions.DBNotAccessibleException;
import persistence.exceptions.PersistenceException;
import persistence.database.SQLConnector;
import persistence.game.character.SQLPlayerDAO;
import persistence.game.gameconfig.GameConfigDAO;
import persistence.game.gameconfig.SQLGameConfigDAO;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * DAO of Game connected to DB
 */
public class SQLGameDAO implements GameDAO {
    /**
     * Instance of the gameConfiguration of a game
     */
    private final GameConfigDAO gameConfigDAO;
    /**
     * Instance of the playerDAO that will help to operate on game information
     */
    private final SQLPlayerDAO playerDAO;

    /**
     * Constructor of SQLGameDAO
     */
    public SQLGameDAO() {
            this.gameConfigDAO = new SQLGameConfigDAO();
            this.playerDAO = new SQLPlayerDAO();
    }

    @Override
    public int createGame(int userID, Game game) throws PersistenceException {
        // 1st create the game
        int gameID = generateGameID();
        String query ="INSERT INTO Partida(ID_Partida, ID_Creador, configurationName) VALUES ('" +
                gameID + "', '" +
                userID + "', '" +
                game.getConfigurationName() +
                "');";

        SQLConnector.getInstance().insertQuery(query);

        // add every player in game
        PlayableCharacter userPlayer = game.getPlayableCharacter(); // 1st get the playable character
        int playerID = playerDAO.addPlayer(userPlayer, true);             // 2nd insert the character and get the associated ID
        userPlayer.setID(playerID);                                 // 3rd set the ID to the character object
        playerDAO.addPlayerInGame(userPlayer,gameID);     // 4th add the relation between player and game

        ArrayList<NonPlayableCharacter> npc = game.getNPCs();       // get NPCs of the game
        for (NonPlayableCharacter character:npc) {                  // for each character repeat insertion process
            playerID = playerDAO.addPlayer(character, false);
            character.setID(playerID);
            playerDAO.addPlayerInGame(character,gameID);
        }
        return gameID;
    }

    private int generateGameID() throws PersistenceException {
        String query = "SELECT MAX(ID_Partida) as maxID FROM Partida;";
        ResultSet result = null;
        try {
            result = SQLConnector.getInstance().selectQuery(query);
        } catch (DBNotAccessibleException e) {
            throw new RuntimeException(e);
        }
        int id = 1;
        try {
            while (result.next()) {
                int maxID = result.getInt("maxID");
                id = maxID + 1;
            }
        } catch (SQLException e) {
            throw new DBNotAccessibleException();
        }
        return id;
    }


    @Override
    public void deleteGame(int gameID) throws PersistenceException {
        String query = "DELETE FROM Partida WHERE ID_Partida = '" + gameID + "';";
        SQLConnector.getInstance().deleteQuery(query);
    }


    @Override
    public void addGameHistorial(Game game, int userID, boolean win) throws PersistenceException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String query ="INSERT INTO Historial(ID_Partida, ID_Jugador, win, dataFinalitzacio) VALUES ('" +
                game.getIDGame() + "', '" +
                userID + "', '" +
                win + "', '" +
                timestamp.toString() +
                "');";

        SQLConnector.getInstance().insertQuery(query);
    }
    @Override
    public ArrayList<Game> getUserGames(int userID) throws PersistenceException {
        ArrayList<Game> games = new ArrayList<>();
        String query = "SELECT ID_Partida, ID_Creador, ConfigurationName FROM Partida WHERE ID_Creador = '" + userID + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                int gameID = result.getInt("ID_Partida");
                String configurationName = result.getString("ConfigurationName");

                GameConfig gameConfig = gameConfigDAO.getGameConfig(configurationName,userID);
                PlayableCharacter mainCharacter = playerDAO.getMainCharacter(gameID);
                ArrayList<NonPlayableCharacter> npcCharacters = playerDAO.getNPC(gameID);

                Game game = new Game(mainCharacter, npcCharacters, gameConfig);
                game.setIDGame(gameID);
                games.add(game);
            }
        } catch (SQLException | PersistenceException e) {
            throw new DBNotAccessibleException();
        }
        return games;
    }

    @Override
    public Game getGameByID(int gameID) throws PersistenceException {
        String query = "SELECT ID_Partida, ID_Creador, configurationName FROM Partida WHERE ID_Partida = '" + gameID + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);
        Game game = null;
        try {
            while (result.next()) {
                int ID_Game = result.getInt("ID_Partida");
                int ID_Creador = result.getInt("ID_Creador");
                String configurationName = result.getString("configurationName");

                GameConfig gameConfig = gameConfigDAO.getGameConfig(configurationName,ID_Creador);
                PlayableCharacter mainCharacter = playerDAO.getMainCharacter(ID_Game);
                ArrayList<NonPlayableCharacter> npcCharacters = playerDAO.getNPC(ID_Game);

                game = new Game(mainCharacter, npcCharacters, gameConfig);
            }
        } catch (SQLException | PersistenceException e) {
            throw new DBNotAccessibleException();
        }
        return game;
    }



    @Override
    public void deleteUserGames(int userID) throws PersistenceException {
        // 1st get all games created by user
        ArrayList<Game> games = getUserGames(userID);
        for (Game game:games) {
            deleteGameHistorial(userID, game.getIDGame());
            deleteGameInformation(game.getIDGame());

        }
    }

    @Override
    public void deleteGameHistorial(int userID, int gameID) throws PersistenceException {
        String query = "DELETE FROM Historial WHERE ID_Partida = '" + gameID + "' AND ID_Jugador = '" + userID + "';";
        SQLConnector.getInstance().deleteQuery(query);
    }


    @Override
    public void deleteGameInformation(int gameID) throws PersistenceException {
        // before deleting the game we should delete all information related to it in other tables
        ArrayList<Integer> playersID = getGamePlayersID(gameID);
        // (player locations, player historials etc)
        for (int playerID:playersID) {
            // delete assassinats, moviments & relation with game
            playerDAO.deleteMovementHistorial(playerID);
            playerDAO.deletePlayerInGame(playerID, gameID);

            // delete player
            playerDAO.deletePlayer(playerID);

        }
        deleteGame(gameID);
    }

    private ArrayList<Integer> getGamePlayersID(int gameID) throws PersistenceException {
        ArrayList<Integer> playersID = new ArrayList<>();

        String query = "SELECT ID_Player FROM Player_In_Game WHERE ID_Game = '" + gameID + "';";
        ResultSet result = SQLConnector.getInstance().selectQuery(query);

        try {
            while (result.next()) {
                int ID = result.getInt("ID_Player");
                playersID.add(ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playersID;
    }

    @Override
    public void saveGameProgress(Game game) throws PersistenceException {
        int gameID = game.getIDGame();
        ArrayList<Log> logs = game.getLogs();
        // update player attributes (location, status, etc etc)
        for (NonPlayableCharacter npc:game.getNPCs()) {
            playerDAO.updatePlayer(npc);
            ArrayList<Log> playerLogs = getPlayerLogs(logs, npc);
            for (Log log:playerLogs) {
                playerDAO.addMovementHistorial(npc.getID(), log.getRoomName(), log.getInstant(), log.getPerception());
            }

        }
        // update main player attributes
        playerDAO.updatePlayer(game.getPlayableCharacter());


    }

    private ArrayList<Log> getPlayerLogs(ArrayList<Log> logs, NonPlayableCharacter npc) {
        ArrayList<Log> logArrayList = new ArrayList<>();
        for (Log log: logs) {
            if (log.getPlayerColour().equals(npc.getColourLabel())) {
                logs.add(log);
            }
        }
        return logArrayList;
    }
}
