package persistence.game.character;

import business.characters.Character;
import business.characters.NonPlayableCharacter;
import business.characters.PlayableCharacter;
import persistence.exceptions.PersistenceException;

import java.util.ArrayList;

public interface PlayerDAO {

    /**
     * Function that adds a player into player_in_game table
     * this table relates players (NPC and playable character) and games
     * @param character Character to add (it can be Playable or NPC)
     * @param GameID ID of the game selected
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void addPlayerInGame(Character character, int GameID) throws PersistenceException;

    /**
     * Function that deletes a player from player_in_game table
     * this table relates players (NPC and playable character) and games
     * @param characterID Character to delete (it can be Playable or NPC)
     * @param gameID ID of the game selected
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void deletePlayerInGame(int characterID, int gameID) throws PersistenceException;

    /**
     * Function that adds a new player to the database
     * @param character new Character to add to the database
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    int addPlayer(Character character, boolean isPlayable) throws PersistenceException;
    /**
     * Function that deletes a player to the database
     * @param characterID new Character to add to the database
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void deletePlayer(int characterID) throws PersistenceException;

    /**
     * Function that gets a character instance by its ID
     * @param playerID id of the player to get
     * @return Instance of Character class according to character ID
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    Character getPlayer(int playerID) throws PersistenceException;

    /**
     * Function that gets the main character instance by the game ID
     * @param gameID id of the game
     * @return Instance of PlayableCharacter class according to character ID
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    PlayableCharacter getMainCharacter(int gameID) throws PersistenceException;

    /**
     * Function that gets an instance of all the NPC in game given the game
     * @param gameID id of the game to get
     * @return Arraylist of NPC Character class that are int eh game with matching ID
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    ArrayList<NonPlayableCharacter> getNPC(int gameID) throws PersistenceException;

    /**
     * Function that deletes al movements done by the player with matching ID
     * @param playerID ID of the player in game
     * @throws PersistenceException
     */
    void deleteMovementHistorial(int playerID) throws PersistenceException;


    /**
     * Function that adds a new movement done by a player in a game
     * @param playerID ID of the player that done the movement
     * @param roomName name of the new room entered
     * @param instant instant on where the character moved
     * @param rol actual role of the character in game
     * @throws PersistenceException is thrown when db cannot be accessed
     */
    void addMovementHistorial(int playerID, String roomName, int instant, String rol) throws PersistenceException;



    /**
     * Function used to update character's attributes on a created game
     * @param character character to be updated
     */
    void updatePlayer(Character character) throws PersistenceException;
}
