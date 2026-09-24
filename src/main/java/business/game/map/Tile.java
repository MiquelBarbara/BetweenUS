package business.game.map;

import java.awt.image.BufferedImage;

/**
 * The Tile class defines what is a tile, basically is a object that stores an image and a boolean that represents
 * if it has collision or not
 */
public class Tile {
    /**
     * Image of the Tile
     */
    private BufferedImage image;
    /**
     * Boolean that indicates if the tile has any type of collision
     */
    private boolean collision = false;
    /**
     * integer that indicades the tile size (set to 48)
     */
    public static final int TILE_SIZE = 48;

    /**
     * Constructor of Tile class
     * @param image Image of the tile
     * @param collision collision value
     */
    public Tile(BufferedImage image, boolean collision){
        this.image = image;
        this.collision = collision;
    }

    /**
     * Function that indicates the image of the tile
     * @return Image of the tile
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Function that returns if the tile has any collision
     * @return true if the tile is a tile with collision
     */
    public boolean isCollision() {
        return collision;
    }
}
