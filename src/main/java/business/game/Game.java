package business.game;

import business.Luck;
import business.characters.NonPlayableCharacter;
import business.characters.Observer;
import business.characters.PlayableCharacter;
import business.characters.attributes.Colour;
import business.game.config.GameConfig;
import business.game.map.GameMap;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class used to represent a Game, this game is the game that
 * is going to be played and simulated by the user.
 * It contains all the necessary information about the game
 */
public class Game implements Runnable {
    /**
     * ID of the game to be played (the id corresponds to the ID assigned into DB)
     */
    private int IDGame;
    /**
     * Playable character that is going to be controlled by the user
     */
    private final PlayableCharacter playableCharacter;
    /**
     * NPCs of the game that are going to be on the game
     */
    private final ArrayList<NonPlayableCharacter> NPCs;
    /**
     * Game configuration of the selected game
     */
    private final GameConfig gameConfig;
    /**
     * ArrayList of logs (events) that happened on during game simulation
     */
    private final ArrayList<Log> logs;

    private final Thread[] threads;

    /**
     * Constructor of Game class.
     * @param playableCharacter Main character that is going to be played by the user
     * @param NPCs Arraylist of NPCs that are on the game
     * @param gameConfig Game configuration of the created game
     */
    public Game(PlayableCharacter playableCharacter, ArrayList<NonPlayableCharacter> NPCs, GameConfig gameConfig) {
        this.playableCharacter = playableCharacter;
        this.NPCs = NPCs;
        this.gameConfig = gameConfig;
        this.threads = new Thread[NPCs.size()];
        this.logs = new ArrayList<>();
    }

    /**
     * Function that runs the game and starts simulating it
     */
    @Override
    public void run() {


        for (int i = 0; i < NPCs.size(); i++) {

            threads[i] = new Thread(NPCs.get(i));
            threads[i].start();

            try {
                // Agregar un pequeño retraso aleatorio antes de iniciar el siguiente hilo
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * function used to get the playable character of the game
     * @return game's playable character
     */
    public PlayableCharacter getPlayableCharacter() {
        return playableCharacter;
    }

    /**
     * function used to get the NonPlayable characters of the game
     * @return game's NonPlayable characters
     */
    public ArrayList<NonPlayableCharacter> getNPCs() {
        return NPCs;
    }

    /**
     * function used to get the game configuration
     * @return game's configuration
     */
    public GameConfig getGameConfig() {
        return gameConfig;
    }
    /**
     * function used to get the configuration's game name
     * @return configuration's game name
     */
    public String getConfigurationName() {
        return gameConfig.getName();
    }
    /**
     * function used to get the ID of the game
     * @return game's ID
     */
    public int getIDGame() {
        return IDGame;
    }

    /**
     * Function used to set the ID of the actual game
     * @param IDGame id of the game to be set
     */
    public void setIDGame(int IDGame) {
        this.IDGame = IDGame;
    }

    /**
     * Function that returns a random adjacent room of the map with
     * the limitations of the actual room
     * this function is designed to be used by the Impostors
     * @param actualRoom actual room of the character
     * @return new location (point, room) of the character
     */
    public Point getImpostorRoom(Point actualRoom, ArrayList<Point> hiddenRooms, ArrayList<Point> adjacentRooms, NonPlayableCharacter character) {

        if (!hiddenRooms.isEmpty() && anyPlayerOnThisRoom(actualRoom, character)) {
            // check for going to hidden room
            int selected = Luck.RandomRoom(1);
            if (selected == 0) {
                ArrayList<Point> possibleRooms = getRoomsWithoutCrewmates(hiddenRooms);
                if (!possibleRooms.isEmpty() && character.killingReloaded()) {
                    int index = Luck.RandomRoom(possibleRooms.size()-1);
                    return possibleRooms.get(index);
                }
            }
        }

        // return random point on "hidden" rooms
        return getRoomWithLimits(actualRoom, adjacentRooms);
    }

    private ArrayList<Point> getRoomsWithoutCrewmates(ArrayList<Point> rooms) {
        ArrayList<Point> foundRooms = new ArrayList<>(rooms);

        for (NonPlayableCharacter npc:NPCs) {
            if (rooms.contains(npc.getRoom())) {
                foundRooms.remove(npc.getRoom());
            }
        }


        return foundRooms;
    }

    private boolean anyPlayerOnThisRoom(Point actualRoom, NonPlayableCharacter character) {
        for (NonPlayableCharacter npc:NPCs) {
            if (!npc.equals(character) && npc.getRoom().equals(actualRoom)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Function that returns a random adjacent room of the map with
     * the limitations of the actual room and the previous room
     * this function is designed to be used by the crewmates
     * @param previousRoom previous room of the user
     * @return new location (point, room) of the character
     */
    public Point getRoomWithLimits(Point previousRoom, ArrayList<Point> adjacentsRooms) {
        adjacentsRooms.remove(previousRoom);
        int index = Luck.RandomRoom(adjacentsRooms.size() - 1);
        if (adjacentsRooms.isEmpty()) {
            return previousRoom;
        }
        return adjacentsRooms.get(index);
    }

    /**
     * Function that gets the game's amount of NPC
     * @return game's amount of NPC
     */
    public int getAmountOfNPC() {
        return NPCs.size();
    }

    /**
     * Function that is used to set the Observer onto the game's NPC
     * @param observer Observer to be set
     */
    public void setNPCObserver(Observer observer) {
        for (NonPlayableCharacter npc:NPCs) {
            npc.addObserver(observer);
        }
    }

    /**
     * Function that returns the game configuration game Map
     * @return game map of the configuration
     */
    public GameMap getGameMap() {
        return gameConfig.getGameMap();
    }

    /**
     * Function that sets the game map to the game configuration
     * @param gameMap game map to be set
     */
    public void setGameMap(GameMap gameMap) {
        gameConfig.setGameMap(gameMap);
    }

    /**
     * function that returns the map name of the game configuration
     * @return name of the map on the game
     */
    public String getMapName() {
        return gameConfig.getMapName();
    }

    /**
     * Function used to get game logs
     * @return logs of the game
     */
    public ArrayList<Log> getLogs() {
        return logs;
    }

    /**
     * Function that returns the adjacents rooms connected to the actual room
     * @param actualRoom actual room in
     * @return arraylist of adjacent rooms conected
     */
    public ArrayList<Point> getAdjacent(Point actualRoom) {
        return gameConfig.getAdjacent(actualRoom);
    }

    /**
     * Function used to get all rooms connected by hidden corridors to the actual room
     * @param actualRoom actual room in
     * @return arraylist of hidden rooms connected
     */
    public ArrayList<Point> getHiddenRooms(Point actualRoom) {
        return gameConfig.getHidden(actualRoom);
    }

    /**
     * Function used to get playable's character Room
     * @return playable's character room point
     */
    public Point getPlayableCharacterRoom() {
        return playableCharacter.getRoom();
    }

    /**
     * Function that stops all game threats
     */
    public void stopThreads() {


        // NOTE: There's no need to do try catch on thread.interrupt because it's already check on NPC running simulation
        System.out.println("stopping threads");
        int i = 0;
        for (NonPlayableCharacter npc : NPCs) {
            npc.die();
            threads[i].interrupt();
            i++;
        }
        playableCharacter.die();
        System.out.println("threads stopped");


    }

    /**
     * Function used to check if the player's classification done by user is correct
     * (the selected impostors matches)
     * @param impostorsColours array of selected impostors
     * @return true if the classification is correct
     */
    public boolean checkClassification(String[] impostorsColours) {
        int amountOfImpostors = gameConfig.getImpostorCount();
        if (impostorsColours.length != amountOfImpostors) {
            return false;
        }

        /*for (int i = 0; i < amountOfImpostors; i++) {
            for (int j = 0; j < NPCs.size(); j++) {
                if (NPCs.get(j).getColour().label.equals(impostorsColours[i])) {

                }
            }
        }*/
        return true;
    }

    /**
     * Function used to register a new log for the player
     * @param colour colour of the player
     * @param perception actual perception of the player
     * @param newRoom new room entered by the player
     */
    public void addNewLog(Colour colour, String perception,Point newRoom) {
        String roomName = gameConfig.getRoomName(newRoom);
        if (roomName != null) {
            logs.add(new Log(colour.label, perception, roomName, GlobalTimer.getInstant()));
        }
    }

    /**
     * Function used to get all colour labels of the players in a game
     * @return all game player's colour label
     */
    public ArrayList<String> getCharactersColourLabel() {
        ArrayList<String> strings = new ArrayList<>();
        for (NonPlayableCharacter npc:NPCs) {
            strings.add(npc.getColourLabel());
        }
        strings.add(playableCharacter.getColourLabel());

        return strings;
    }
}
