package business.game;

import business.characters.CharacterManager;
import business.characters.NonPlayableCharacter;
import business.characters.PlayableCharacter;
import business.exceptions.BusinessException;
import business.exceptions.ResourcesNotAccessedException;
import business.game.config.GameConfig;
import persistence.game.GameDAO;
import persistence.exceptions.PersistenceException;
import persistence.game.SQLGameDAO;

import java.util.ArrayList;

/**
 * Class used to manage games of the user logged
 */
public class GameManager {
    /**
     * DAO of the game to be used
     */
    private final GameDAO gameDAO;
    /**
     * manager of the characters to be used to create ans instanciate characters
     */
    private final CharacterManager characterManager;

    /**
     * Constructor of Game manager class
     */
    public GameManager() {
        this.gameDAO = new SQLGameDAO();
        this.characterManager = new CharacterManager();
    }

    /**
     * Function used to create a new game and save id into the DB
     * @param userID id of the logged user
     * @param gameConfig game configuration selecter by user
     * @return integer that indicates the game ID
     * @throws BusinessException occurs when there's an error on game creation (thrown when db can not be accessed)
     */
    public int createGame(int userID, GameConfig gameConfig) throws BusinessException {
        PlayableCharacter playableCharacter = characterManager.createPlayableCharacter(gameConfig);
        ArrayList<NonPlayableCharacter> NPCs = characterManager.createNPCs(gameConfig);

        Game game = new Game(playableCharacter, NPCs, gameConfig);
        try {
            return gameDAO.createGame(userID, game);
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }
    }

    /**
     * Function used to load a previously created game by the user
     * @param gameID id of the game to load
     * @return Created game by player
     * @throws BusinessException Occurs when there's an error on game loading
     */
    public Game loadGame(int gameID) throws BusinessException {
        try {
            return gameDAO.getGameByID(gameID);
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }
    }

    /**
     * Function used to save a Created game into database
     * @param game game to be saved
     * @throws BusinessException Occurs when there's an error on game saving process
     */
    public void saveGame(Game game) throws BusinessException {
        try {
            gameDAO.saveGameProgress(game);
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }
    }

    /**
     * Function used to delete a game previously created by user
     * @param game Game to delete
     * @throws BusinessException occurs when game cannot be deleted
     */
    public void deleteGame(Game game) throws BusinessException {
        try {
            gameDAO.deleteGame(game.getIDGame());
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }
    }
}
