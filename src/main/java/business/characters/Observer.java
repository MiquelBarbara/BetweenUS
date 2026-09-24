package business.characters;

import business.exceptions.BusinessException;

import java.awt.*;

public interface Observer {

    /**
     * Function that updates the position of a character given the character, its actual room and its previous room
     * @param character instance of the character to be moved
     * @param actualRoom point according to the actual player room
     * @param previousRoom point according to the previous character room (the las room visited by NPC)
     */
    void updateLocation(NonPlayableCharacter character, Point actualRoom, Point previousRoom);

    /**
     * Function that given a NPC checks if the NPC can perform an action
     * (in this case the unique action to do is kill)
     * in case the NPC can perform an action it'll check the reload time and kill and impostor if it can
     * @param NPC NPC to make the action
     */
    void makeAction(NonPlayableCharacter NPC) throws BusinessException;

    /**
     * Function used to log new locations on character moving
     * @param character Character to save
     * @param actualRoom actual room entered in
     */
    void saveLocation(Character character, Point actualRoom);
}
