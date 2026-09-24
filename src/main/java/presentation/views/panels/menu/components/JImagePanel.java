package presentation.views.panels.menu.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


/**
 * Custom JPanel that renders an image in the background
 */
public class JImagePanel extends JPanel {

    /**
     * Image to render
     */
    private BufferedImage image;

    /**
     * Constructor of JImage Panel
     * @param path path of the image to render
     */
    public JImagePanel(String path) {
        String resourcePath = path.startsWith("/") ? path : "/" + path;
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Image resource not found: " + resourcePath);
            }
            image = ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read image resource: " + resourcePath, e);
        }
    }

    // IMPORTANT: WE override this to scale the image in layouts that stretch it horizontally while respecting its preferred vertical size
    // THIS WILL NOT WORK IF YOU HAVE OTHER GOALS, DON'T REUSE WITHOUT THINKING
    @Override
    public Dimension getPreferredSize() {
        Dimension preferred = super.getPreferredSize();

        if (image == null) {
            return preferred;
        }

        float width = image.getWidth();
        float height = image.getHeight();

        // Calculate the height needed to mantain aspect ratio
        preferred.height = Math.round(getWidth()*height/width);

        return preferred;
    }

    // Paint the image in the background, with the size the layout assigns to the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
