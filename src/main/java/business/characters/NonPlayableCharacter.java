package business.characters;

import business.Luck;
import business.characters.attributes.Colour;
import business.characters.attributes.Perception;
import business.characters.roles.Role;
import business.exceptions.BusinessException;
import business.exceptions.ThreadException;
import business.game.GlobalTimer;
import persistence.exceptions.PersistenceException;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class used to represent the NPC class playing the game
 */
public class NonPlayableCharacter extends Character implements Runnable, Observable {
    /**
     * actual perception of the character
     */
    private Perception perception;

    /**
     * Point according to the previous room visited by the NPC
     */
    private Point previousRoom;

    /**
     * integer that indicates the last time the NPC killed (instant)
     */
    private int lastTimeKilled;

    /**
     * Constructor of NPC class
     * @param colour colour of the character
     * @param role role of the character
     * @param point initial position of the character
     * @throws BusinessException occurs when data can not be accessed
     */
    public NonPlayableCharacter(Colour colour, Role role, Point point) throws BusinessException {
        super(colour, role, point);
        this.perception = Perception.UNKNOWN;
        this.isDead = false;
        this.previousRoom = point;
        this.lastTimeKilled = Integer.MIN_VALUE / 2; // initially it doesn't have to reload


    }

    /**
     * Function that runs NPC thread, starting its simulation
     */
    @Override
    public void run() {

        sleep();

        while (running) {

            if (Luck.MoveChance(role.getMoveChance())) {
                // move to the next room
                previousRoom = new Point(room); // update previous room

                // notify the NPCObserver that a new movement is to be done
                // after this the NPCObserver will have set a new location(room) for the npc
                notifyNewMovement();

                // After changing room we check if the NPC has to do an action
                notifyNewAction();

            }
            sleep();
        }
    }

    /**
     * Function used to sleep threads on NPC simulations
     * (aim is to reuse code)
     */
    private void sleep(){
        try {
            long timeToSleep = Luck.MoveInterval(role.getMinMoveTime(), role.getMaxMoveTime());
            Thread.sleep(timeToSleep);
        } catch (InterruptedException e) {

        }
    }

    /**
     * Function used to get NPC perception
     * @return Perception of the NPC
     */
     @Override
    public Perception getPerception() {
        return perception;
    }

    /**
     * Function that returns the state (dead/alive) of the NPC
     * @return true if the NPC is dead
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Function that returns the NPC role (crewmate, impostor)
     * @return Role of the NPC
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Function that returns a boolean indicating if the NPC has any room limitation
     * @return true if the NPC has any room limitation
     */
    public boolean hasRoomLimits() {
        return role.hasRoomLimits();
    }

    /**
     * Function that returns a boolean indicating if the NPC is able to make killing actions
     * @return true if the NPC is able to make killing actions
     */
    @Override
    public boolean canKill() {
        return role.canKill();
    }

    /**
     * Function used to check if the killing time has been reloaded
     * @return
     */
    public boolean killingReloaded() {
        return GlobalTimer.getInstant() - lastTimeKilled > role.getReloadTime()/1000;
    }

    /**
     * Function that returs the previous room visited by the NPC
     * @return Point according to the previous room visited
     */
    public Point getPreviousRoom() {
        return previousRoom;
    }


    @Override
    public synchronized void notifyNewMovement() {
        for (Observer observer:observers) {
            observer.updateLocation(this, room, previousRoom);
        }
    }

    @Override
    public synchronized void notifyNewAction() {
        try {
            for (Observer observer:observers) {
                observer.makeAction( this);
            }
        } catch (BusinessException e) {

        }

    }

    /**
     * Function used to set and update the last time killed value
     * @param instant new instant to set
     */
    public void setLastTimeKilled(int instant) {
        this.lastTimeKilled = instant;
    }

    /**
     * Function used to set the player's perception
     * @param perception New perception to be set
     */
    public void setPerception(Perception perception) {
        this.perception = perception;
    }


}
