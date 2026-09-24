package business.characters;

import business.Luck;
import business.characters.attributes.Colour;
import business.characters.roles.CrewMate;
import business.characters.roles.Impostor;
import business.exceptions.BusinessException;
import business.game.config.GameConfig;
import business.game.map.TileManager;

import java.awt.*;
import java.util.ArrayList;

public class CharacterManager {

    public CharacterManager() {

    }

    /**
     * Function used to create the NPCs into the game to create
     * @param gameConfig Game configuration to use
     * @return Arraylist og NPCs instanced with al attributes associated
     * @throws BusinessException Occurs when there's a problem during NPCs creation
     */
    public ArrayList<NonPlayableCharacter> createNPCs(GameConfig gameConfig) throws BusinessException {
        ArrayList<NonPlayableCharacter> characters = new ArrayList<>();
        TileManager tileManager = new TileManager();
        String mapName = gameConfig.getMapName();

        ArrayList<Colour> selectedColours = new ArrayList<>();
        Colour[] colours = Colour.getColours();
        selectedColours.add(gameConfig.getPlayerColour());

        //S'han de randomitzar els colors dels NPCs
        for (int i = 0; i < gameConfig.getImpostorCount(); i++) {
            Colour colour = getRandomColour(selectedColours, colours);
            characters.add(new NonPlayableCharacter(colour, new Impostor(), tileManager.getMapInitialPosition(mapName)));
        }

        for (int i = 0; i < gameConfig.getCharacterCount() - 1 - gameConfig.getImpostorCount(); i++) {
            Colour colour = getRandomColour(selectedColours, colours);
            characters.add(new NonPlayableCharacter(colour, new CrewMate(), tileManager.getMapInitialPosition(mapName)));
        }

        return characters;
    }


    private Colour getRandomColour(ArrayList<Colour> selectedColours, Colour[] colours) {

        int colourIndex = Luck.getRandomInt(colours.length);
        while (selectedColours.contains(colours[colourIndex])) {
            colourIndex = Luck.getRandomInt(colours.length);
        }
        selectedColours.add(colours[colourIndex]);
        return colours[colourIndex];
    }

    /**
     * Function used to create the Playable character of the specified game configuration
     * @param gameConfig game configuration to be used
     * @return Playable character instance with all attributes set
     * @throws BusinessException occurs when there's a problem during player creation
     */
    public PlayableCharacter createPlayableCharacter(GameConfig gameConfig) throws BusinessException{
        TileManager tileManager = new TileManager();
        String mapName = gameConfig.getMapName();

        return new PlayableCharacter(gameConfig.getPlayerColour(), new CrewMate(), tileManager.getMapInitialPosition(mapName));
    }

}
