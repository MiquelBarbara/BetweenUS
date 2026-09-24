package presentation.views.panels.game;

import business.game.map.GameMap;
import business.game.map.Room;
import business.game.map.Tile;

import java.awt.*;

/**
 * The MapPrinter class is responsible for managing and drawing the tiles of the game map on the game panel.
 */
public class MapPrinter {
    /**
     * the value of the width
     */
    private final int screenX;
    /**
     * the value of the height
     */
    private final int screenY;
    /**
     * the X-position of the tile being drawn
     */
    private int worldPrinterX;
    /**
     * the Y-position of the tile being drawn
     */
    private int worldPrinterY;

    /**
     * Constructs a MapPrinter object with the specified view width and height.
     * @param viewWidth the width of the view
     * @param viewHeight the height of the view
     */
    public MapPrinter(int viewWidth, int viewHeight){
        screenX = viewWidth/2 - Tile.TILE_SIZE/2;
        screenY = viewHeight/2 - Tile.TILE_SIZE/2;
    }

    /**
     * Draws the game map tiles on the screen using the specified graphics context, game map, and player location.
     * @param graphics2D the Graphics2D object used for drawing
     * @param map the GameMap object representing the game map
     * @param playerLocation the current location of the player
     */
    public void draw(Graphics2D graphics2D, GameMap map, Point playerLocation) {

        int maxWorldCol = map.getMapWordColumns();
        int maxWordRow = map.getMapWordRows();

        //Room of the map being drawn
        int worldCol = 0;
        int worldRow = 0;

        //Position of the tile being drawn
        worldPrinterX = 0;
        worldPrinterY = 0;

        //Loop that stops when all the maps are already drawn
        while (worldCol <= maxWorldCol && worldRow <= maxWordRow) {
            //Extract all the information from the room
            Room room = map.getRooms().get(new Point(worldCol,worldRow));
            int[][] mapTileNum = room.getMapTileNum();
            Tile[] tiles = room.getTiles();

            drawRoom(mapTileNum, tiles, graphics2D, playerLocation);
            worldCol++;

            //This lines make sure that the worldPrinter is in the correct position
            if (worldCol == maxWorldCol + 1) {
                worldCol = 0;
                worldRow++;
                worldPrinterX = worldPrinterX - (Room.ROOM_SIZE * maxWorldCol);
            }

            else {
                worldPrinterX = worldPrinterX + Room.ROOM_SIZE;
                worldPrinterY = worldPrinterY - Room.ROOM_SIZE;
            }
        }

    }

    /**
     * Draws a single room of tiles on the screen using the specified tile map, tiles, graphics context, and player location.
     * @param mapTileNum the 2D array representing the tile numbers of the room
     * @param tiles the array of Tile objects representing the room tiles
     * @param graphics2D  the Graphics2D object used for drawing
     * @param playerLocation the current location of the player
     */
    public void drawRoom(int[][] mapTileNum, Tile[] tiles, Graphics2D graphics2D, Point playerLocation) {
        //Tile of the room being drawn
        int roomCol = 0;
        int roomRow = 0;
        //Loop to print the room
        while(roomCol < Room.ROOM_SIZE && roomRow < Room.ROOM_SIZE) {
            int tileNum = mapTileNum[roomCol][roomRow];

            int screenX = getScreenX(playerLocation);
            int screenY = getScreenY(playerLocation);

            if (drawTile(playerLocation)) {
                graphics2D.drawImage(tiles[tileNum].getImage(), screenX, screenY, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
            }

            roomCol++;
            worldPrinterX++;
            if (roomCol == Room.ROOM_SIZE) {
                roomCol = 0;
                worldPrinterX = worldPrinterX - Room.ROOM_SIZE;
                roomRow++;
                worldPrinterY++;
            }
        }
    }

    /**
     * Calculates and returns the screen X-coordinate of a tile based on the printer's location and player location.
     * @param playerLocation  the current location of the player
     * @return the screen X-coordinate of the tile
     */
    public int getScreenX(Point playerLocation) {
        int worldX = worldPrinterX * Tile.TILE_SIZE;
        return worldX - playerLocation.x + screenX;
    }
    /**
     * Calculates and returns the screen Y-coordinate of a tile based on the printer's location and player location.
     * @param playerLocation  the current location of the player
     * @return the screen Y-coordinate of the tile
     */
    public int getScreenY(Point playerLocation) {
        int worldY = worldPrinterY * Tile.TILE_SIZE;
        return worldY - playerLocation.y + screenY;
    }

    /**
     * Checks if a tile should be drawn on the screen based on the printer's location and player location.
     * @param playerLocation the current location of the player
     * @return true if the tile should be drawn, false otherwise
     */
    public boolean drawTile(Point playerLocation) {
        int worldX = worldPrinterX * Tile.TILE_SIZE;
        int worldY = worldPrinterY * Tile.TILE_SIZE;

        if(worldX + Tile.TILE_SIZE > playerLocation.x - screenX && worldX - Tile.TILE_SIZE < playerLocation.x + screenX && worldY + Tile.TILE_SIZE > playerLocation.y - screenY && worldY - Tile.TILE_SIZE < playerLocation.y + screenY){
            return true;
        }
        else {
            return false;
        }
    }
}
