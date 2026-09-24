package business.game.config;

import business.characters.attributes.Colour;
import business.exceptions.BusinessException;
import business.game.map.GameMap;
import business.game.map.TileManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

/**
 * Class that represents the game configuration created by a user
 */
public class GameConfig {
    /**
     * String that indicates the gameConfiguration name
     */
    private final String name;
    /**
     * integer that indicates the gameConfiguration character amount (crewmates)
     */
    private final int characterCount;
    /**
     * integer that indicates the gameConfiguration character amount (impostor)
     */
    private final int impostorCount;
    /**
     * Colour that indicates the gameConfiguration main character colour
     */
    private final Colour playerColour;
    /**
     * GameMap that indicates the gameConfiguration GameMap selected
     */
    private GameMap gameMap;
    /**
     * String that indicates the gameConfiguration selectedMap name
     */
    private final String selectedMap;

    /**
     * Constructor of GameConfig class
     * @param name name of the game configuration
     * @param characterCount amount of crewmates to add
     * @param impostorCount amount of impostors to add
     * @param playerColour selected colour of the main character
     * @param selectedMap selected map by the user
     */
    public GameConfig(String name, int characterCount, int impostorCount, Colour playerColour, String selectedMap) throws BusinessException {
        this.name = name;
        this.characterCount = characterCount;
        this.impostorCount = impostorCount;
        this.playerColour = playerColour;
        this.selectedMap = selectedMap;
        TileManager tileManager = new TileManager();
        this.gameMap = tileManager.getMap(selectedMap);
    }

    /**
     * Function that returns the GameConfig name
     * @return GameConfig name
     */
    public String getName() {
        return name;
    }

    /**
     * Function that returns the GameConfig crewMate amount
     * @return GameConfig crewMate amount
     */
    public int getCharacterCount() {
        return characterCount;
    }

    /**
     * Function that returns the GameConfig impostor amount
     * @return GameConfig impostor amount
     */
    public int getImpostorCount() {
        return impostorCount;
    }

    /**
     * Function that returns the GameConfig main character colour
     * @return GameConfig main character colour
     */
    public Colour getPlayerColour() {
        return playerColour;
    }

    /**
     * Function that returns the GameConfig GameMap
     * @return the GameConfig GameMap
     */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Function that returns the GameConfig sleected map name
     * @return GameConfig selected map name
     */
    public String getMapName() {
        return selectedMap;
    }

    /**
     * Function that returns a random adjacent room of the map with
     * the limitations of the actual room
     * this function is designed to be used by Impostors
     * @param actualRoom actual room of the character
     * @return new location (point, room) of the character
     */
    public Point getRandomRoom(Point actualRoom, ArrayList<Point> nonPossibleRooms) {
        return gameMap.getRandomRoom(actualRoom, nonPossibleRooms);
    }

    /**
     * Function that returns a random adjacent room of the map with
     * the limitations of the actual room and the previous room
     * this function is designed to be used by the crewmates
     * @param actualRoom actual room of the character
     * @param previousRoom previous room of the user
     * @return new location (point, room) of the character
     */
    public Point getRoomWithLimits(Point actualRoom, Point previousRoom) {
        return gameMap.getRoomWithLimits(actualRoom,previousRoom);
    }

    /**
     * Function that sets the gameMap instance to the game configuration
     * @param gameMap Game map to set
     */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Function used to get adjacent rooms of the actual room
     * @param actualRoom actual room of the played
     * @return arraylist of adjacent rooms
     */
    public ArrayList<Point> getAdjacent(Point actualRoom) {
        return gameMap.getAdjacentRooms(actualRoom);
    }

    /**
     * function used to get the hidden paths to the actual specified room
     * @param actualRoom actual room to get hidden paths
     * @return Arraylist of hidden rooms connected to actual room
     */
    public ArrayList<Point> getHidden(Point actualRoom) {
        return gameMap.getHiddenRooms(actualRoom);
    }

    /**
     * Function used to get the room name given its point on the map
     * @param newRoom point of the room
     * @return name of the room
     */
    public String getRoomName(Point newRoom) {
        return gameMap.getRoomName(newRoom);
    }
}
