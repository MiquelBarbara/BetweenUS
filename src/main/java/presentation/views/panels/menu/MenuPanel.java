package presentation.views.panels.menu;

import presentation.views.panels.PanelKey;
import presentation.views.panels.menu.components.JImagePanel;

import javax.swing.*;
import java.awt.*;
import java.util.EventListener;

/**
 * Class that will be extended from another classes, in order to get his methods
 * Every class that extends from MenuPanel, will have a panel key and a background set with a path
 */

public abstract class MenuPanel extends JImagePanel implements BetweenUsPanel {

    /**
     * the key of every panel
     */
    protected PanelKey panelKey;

    /**
     * creates the class
     * @param path the path of the background
     * @param panelKey the key of all the panels
     */
    public MenuPanel(String path, PanelKey panelKey) {
        super(path);
        this.panelKey = panelKey;
    }

    /**
     * A thread that is started when the first attach request occurs.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void attachListener(EventListener listener) {

    }

    /**
     * It removes the event that previously has occurred.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void detachListener(EventListener listener) {

    }

    public PanelKey getPanelKey() {
        return panelKey;
    }

    protected JButton createImageButton(String imagePath, int width, int height) {
        String resourcePath = imagePath.startsWith("/") ? imagePath : "/" + imagePath;
        java.net.URL imageUrl = getClass().getResource(resourcePath);
        if (imageUrl == null) {
            throw new IllegalArgumentException("Image resource not found: " + resourcePath);
        }
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        JButton button = new JButton(scaledIcon);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
}
