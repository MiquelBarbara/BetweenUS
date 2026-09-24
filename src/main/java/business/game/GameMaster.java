package business.game;

import business.characters.Character;
import business.characters.NonPlayableCharacter;
import business.characters.Observer;
import business.characters.PlayableCharacter;
import business.exceptions.BusinessException;
import presentation.GameHandler;
import presentation.views.panels.game.LogsPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class used to "control" and manage the game simulation
 */
public class GameMaster implements Observer, Runnable{
    /**
     * Game that is going to play the user
     */
    private final Game game;
    /**
     * Boolean used to indicate if the game master is currently running a game
     */
    private boolean isRunning;
    /**
     * Game handler of the game and game master
     */
    private final GameHandler gameHandler;

    /**
     * Constructor of the game master class
     * @param game Game to be "controlled" by the gameMaster
     */
    public GameMaster(Game game, GameHandler gameHandler) {
        this.game = game;
        game.setNPCObserver(this);
        this.gameHandler = gameHandler;
    }

    /**
     * Function used to run game (and so NPC) threads
     */
    public void runThreads() {
        // run game
        game.run();
        GlobalTimer.startTimer();

    }

    /**
     * Function that returns the new location of the character that is going to move to a new room
     * @param actualRoom actual room of the character
     * @param limits boolean that indicates if the character has any limitation on movements
     * @param previousRoom previous room of the character
     * @return new location of the character
     */
    public Point getRoomToMove(Point actualRoom, boolean limits, Point previousRoom, NonPlayableCharacter character) {
        ArrayList<Point> adjacentsRooms = game.getAdjacent(actualRoom);
        if (limits) {
            return game.getRoomWithLimits(previousRoom, adjacentsRooms);
        }
        ArrayList<Point> hiddenRooms = game.getHiddenRooms(actualRoom);
        return game.getImpostorRoom(actualRoom, hiddenRooms, adjacentsRooms, character);
    }

    private void moveNPC(NonPlayableCharacter npc, Point newLocation) {
        npc.setRoom(newLocation);
    }


    /**
     * Function that returns the timer actual value
     * @return int that represents the value of the game clock (timer)
     */
    public int getActualTime() {
        return GlobalTimer.getInstant();
    }

    @Override
    public synchronized void updateLocation(NonPlayableCharacter character, Point actualRoom, Point previousRoom) {
        Point newRoom = getRoomToMove(actualRoom, character.hasRoomLimits(), previousRoom, character);
        moveNPC(character, newRoom);
        game.addNewLog(character.getColour(), character.getPerception().label, newRoom);

    }

    @Override
    public synchronized void makeAction(NonPlayableCharacter npc) throws BusinessException {
        // 1st check if the npc can do an action or not (is impostor)
        if (npc.canKill()) {
            // 2nd check if the npc can kill (currentTime (sec) - lastTimeKilled > npc.getReloadTime())
            if (npc.killingReloaded()) {
                Character theOtherNPCInRoom = getCharactersInRoom(npc.getRoom(), npc);
                if (theOtherNPCInRoom != null) {
                    killCharacter(npc, theOtherNPCInRoom);
                    npc.setLastTimeKilled(GlobalTimer.getInstant());
                }
            }
        }
    }

    @Override
    public void saveLocation(Character character, Point actualRoom) {

    }

    /**
     * Function that given a room and a character returns the other crewmate that may be in the same room
     * at the same moment.
     * @param room actual room of the Impostor
     * @param npc Instance of the impostor that is going to perform an action (kill)
     * @return instance of the npc in room. In case there's no other NPC, there are more than 1, or the NPC found is Impostor it returns null.
     */
    private Character getCharactersInRoom(Point room, NonPlayableCharacter npc) {

        int amountOfNPC = 0;
        Character npcInRoom = null;

        for (NonPlayableCharacter actualNPC:game.getNPCs()) {
            if (actualNPC.getRoom().equals(room) && !actualNPC.equals(npc)) {
                if (!actualNPC.isDead() && !actualNPC.canKill()) {
                    npcInRoom = actualNPC;
                }
                amountOfNPC++;
            }
        }

        if (game.getPlayableCharacterRoom().equals(room)) {
            amountOfNPC++;
            npcInRoom = game.getPlayableCharacter();
        }

        return amountOfNPC == 1 ? npcInRoom : null;
    }

    /**
     * Function that manages the killing action done by a NPC to another NPC
     * @param killer NPC that is the killer
     * @param defeated NPC that is the killed one
     */
    private void killCharacter(NonPlayableCharacter killer, Character defeated) throws BusinessException {
        //2nd update the defeated npc status
        dieCharacter(defeated);
        //3rd move to new room
        if (defeated instanceof NonPlayableCharacter) {
            updateLocation(killer, killer.getRoom(), killer.getPreviousRoom());
        }
    }

    /**
     * Function used to set the specified NPC to dead
     * @param character NPC to be dead
     */
    private void dieCharacter(Character character) throws BusinessException {
        character.die();
        if (character instanceof PlayableCharacter) {
            endGame();

        }
    }

    /**
     * Function used to end a game
     */
    public void endGame() throws BusinessException {
        // DONE stop NPC threads
        // DONE stop playableCharacter thread (stop moving and deactivate keys)
        game.stopThreads();
        GlobalTimer.stop();

        // TODO save game Result
        // gameManager.saveFinishedGame(game, false);
        // TODO show end message and stopGame
        gameHandler.endGame();
        // TODO changeView
    }

    /**
     * Function used to check if the classification done by the user is correct
     * @return true if the colours provided of the Impostors matches with the colours of the impostors
     */
    public boolean checkClassification(String[] impostorsColours) {
        return game.checkClassification(impostorsColours);
    }

    /**
     * Function used to run game and game master
     */
    @Override
    public void run() {
        runThreads();
    }
}
