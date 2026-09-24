package presentation.views.panels.menu.components;

import javax.swing.*;
import java.awt.*;

/**
 * Class used to create a custormized green button component
 */
public class GreenButtonComponent extends JButton {
    /**
     * Constructor of the button component
     * @param name text to be set on the button
     */
    public GreenButtonComponent(String name){
        Dimension buttonSize = new Dimension(500, 75);
        setText(name);
        setMaximumSize(buttonSize);
        setFont(new Font("Chewy Pro", Font.PLAIN, 45));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        setContentAreaFilled(false);
    }
}
