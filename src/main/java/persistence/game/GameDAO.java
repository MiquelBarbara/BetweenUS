package persistence.game;


import business.game.Game;
import persistence.exceptions.PersistenceException;

import java.util.ArrayList;

public interface GameDAO {
    /**
     * Function that registers the new game provided into database
     * @param userID id of the user that creates a new game
     * @param game Game to save
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    int createGame(int userID, Game game) throws PersistenceException;

    /**
     * Function that given a Game instance deletes it from the database
     * @param gameID Game to delete from database
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void deleteGame(int gameID) throws PersistenceException;


    /**
     * Function that adds to the historial table a new finished game by the user,
     * specifying if the player has win or losed the game
     * @param game Game played by the user
     * @param userID ID of the user that played the game
     * @param win boolean that indicates if the player winned or loosed the game
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void addGameHistorial(Game game, int userID, boolean win) throws PersistenceException;

    /**
     * Function that deletes the historial record of a game from database of the user specified
     * @param userID ID of the user from which we want to delete the game created
     * @param gameID ID of the Game that the user has played we want to delete
     *               @throws PersistenceException is thrown when db cannot be accessed
     */
    void deleteGameHistorial(int userID, int gameID) throws PersistenceException;

    /**
     * Function that gets all the games created by a user
     * @param userID ID of the user we want to get its games created
     * @return Arraylist of games created by user
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    ArrayList<Game> getUserGames(int userID) throws PersistenceException;

    /**
     * Function that given the ID of the game, returns the game saved in database with matching ID
     * @param GameID ID of the game to get
     * @return Instance of game Class of the game with matching ID
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    Game getGameByID(int GameID) throws PersistenceException;

    /**
     * Function that deletes all game information from the user specified
     * @param userID User from who we want to delete all games created and games historial
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void deleteUserGames(int userID) throws PersistenceException;

    /**
     * Function that is used to delete all game information of the game with matching ID
     * @param gameID ID of the game whose information is going to be deleted
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void deleteGameInformation(int gameID) throws PersistenceException;

    /**
     * Function used to save and update the game previously created and played
     * in fact we have to update all character status and character logs
     * @param game
     */
    void saveGameProgress(Game game) throws PersistenceException;
}