package presentation.views.panels.game;

import business.characters.NonPlayableCharacter;
import business.game.map.Room;
import business.game.map.Tile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 *The NpcPrinter class is responsible for drawing NonPlayableCharacters (NPCs) on the game panel.
 */
public class NpcPrinter {
    /**
     * Location X to print
     */
    private int screenX;
    /**
     * Location Y to print
     */
    private int screenY;
    /**
     * Arraylist of NPC to be printed
     */
    private ArrayList<NonPlayableCharacter> NPCs;

    /**
     * Constructs a NpcPrinter object with the specified NPCs, view width, and view height.
     * @param NPCs the list of NonPlayableCharacter objects to be drawn
     * @param viewWidth the width of the view
     * @param viewHeight the height of the view
     */
    public NpcPrinter(ArrayList<NonPlayableCharacter> NPCs, int viewWidth, int viewHeight) {
        this.NPCs = NPCs;
        screenX = viewWidth/2 - Tile.TILE_SIZE/2;
        screenY = viewHeight/2 - Tile.TILE_SIZE/2;
    }

    /**
     * Draws the NPCs on the screen using the specified graphics context and player location.
     * @param graphics2D    the Graphics2D object used for drawing
     * @param playerLocation  the current location of the player
     */
    public void draw(Graphics2D graphics2D, Point playerLocation) {
        drawNPC(graphics2D, playerLocation);
    }


    /**
     * Draws a single NPC on the screen at its specified location relative to the player.
     * @param graphics2D the Graphics2D object used for drawing
     * @param playerLocation the current location of the player
     */
    private void drawNPC(Graphics2D graphics2D, Point playerLocation) {
        BufferedImage sprite;
        for (NonPlayableCharacter npc: NPCs) {
            sprite = npc.getIdle();
            int screenXNPC = npc.getLocation().x - playerLocation.x + screenX;
            int screenYNPC = npc.getLocation().y - playerLocation.y + screenY;
            graphics2D.drawImage(sprite, screenXNPC, screenYNPC, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
        }

    }

}
