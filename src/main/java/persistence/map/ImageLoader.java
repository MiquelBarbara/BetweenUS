package persistence.map;

import business.characters.attributes.Colour;
import business.game.map.Tile;
import persistence.exceptions.PersistenceException;
import persistence.exceptions.mapLoadingException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class ImageLoader {
    /**
     * Scales a given BufferedImage object to the specified width and height dimensions.
     * @param original the original image to be scaled
     * @return a new image object with the scaled image
     * @throws IllegalArgumentException if the original image is null
     */
    public BufferedImage scaleImage(BufferedImage original){
        if (original == null) {
            throw new IllegalArgumentException("Original image cannot be null");
        }

        BufferedImage scaledImage = new BufferedImage(Tile.TILE_SIZE, Tile.TILE_SIZE, original.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(original, 0, 0, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
        graphics2D.dispose();
        return scaledImage;
    }

    /**
     * Function that loads an image from resources given the player colour and the image name
     * @param color colour of the player selected
     * @param name name of the image to display
     * @return selected image
     */
    private BufferedImage loadImage(String color, String name) throws PersistenceException {
        BufferedImage image = new BufferedImage(76, 97, BufferedImage.TYPE_INT_ARGB);

        try{
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("susBoy/" + color + "/" + name + ".PNG"));
            image = scaleImage(image);
        } catch (IOException e){
            throw new mapLoadingException();
        }

        return image;
    }

    /**
     * Function that loads the Sprite of the character to be displayed
     * @param colour colour of the character
     * @param idle actual image of the character
     * @param walk images disposable of the character
     * @param invertedWalk inverted images of the character
     * @return Image selected by the logic to be displayed
     */
    public BufferedImage loadCharacterSprites(String colour, BufferedImage idle, ArrayList<BufferedImage> walk,
                                              ArrayList<BufferedImage> invertedWalk) throws PersistenceException {

        idle = loadImage(colour, colour + "_idle_01");

        for (int i = 1; i < 5; i++) {
            walk.add(loadImage(colour,colour + "_walk_0" + i));
        }

        for (int i = 1; i < 5; i++) {
            invertedWalk.add(loadImage(colour,colour + "_invertedwalk_0" + i));
        }
        return idle;
    }
}
