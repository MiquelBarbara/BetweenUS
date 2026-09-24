package business.game;

import business.characters.attributes.Colour;
import business.exceptions.BusinessException;
import business.exceptions.ResourcesNotAccessedException;
import persistence.exceptions.PersistenceException;
import persistence.map.ImageLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * UtilityTool is a class that provides functions for manipulating images.
 */
public class ImageManager {
    /**
     * Image loader used to load images on a map
     */
    private final ImageLoader imageLoader;

    /**
     * Constructor of ImageManager
     */
    public ImageManager() {
        this.imageLoader = new ImageLoader();
    }

    /**
     * Function used to get character sprite
     * @param colour Colour of the character
     * @param idle Default image of the character to get
     * @param walk Arraylist of Walking animation images
     * @param invertedWalk Arraylist of inverted Walking animation images
     * @return Image loaded according to the specified image to get
     * @throws BusinessException Occurs when there's an error on image loading
     */
    public BufferedImage getCharacterSprite(Colour colour, BufferedImage idle, ArrayList<BufferedImage> walk, ArrayList<BufferedImage> invertedWalk) throws BusinessException {
        try {
            return imageLoader.loadCharacterSprites(colour.label,idle,walk, invertedWalk);
        } catch (PersistenceException e) {
            throw new ResourcesNotAccessedException();
        }

    }


}
