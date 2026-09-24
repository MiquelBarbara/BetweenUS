package presentation.views.panels.menu;

import presentation.views.panels.PanelKey;
import presentation.views.panels.menu.components.GreenButtonComponent;
import presentation.views.panels.menu.components.JImagePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EventListener;

/**
 * Class used to show that the game has finished and the user has lost
 */

public class DefeatPanel extends MenuPanel {

    /**
     * JButton variable that lets the user leave that view
     */

    private JButton continuar = new JButton();

    /**
     * Constructs a main Panel which calls functions that return other panels
     */

    public DefeatPanel() {
        super("files/home_background.png", PanelKey.GAME);
        this.setLayout(new BorderLayout());
        configureWindow();
        configurerightPanel();
        configureCenterPanel();

    }

    private void configureWindow() {
        this.setSize(1300, 900);
    }

    private void configureCenterPanel(){
        JPanel centerPanel = new JPanel();
        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(230,100));
        empty.setOpaque(false);
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("DEFEAT...");
        label.setFont(new Font("Chewy Pro", Font.BOLD, 120));
        label.setForeground(Color.RED);
        label.setBorder(new EmptyBorder(230, 0, 0, 0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(label);
        JImagePanel image = new JImagePanel("files/logo.png");
        image.setOpaque(false);
        centerPanel.add(image);

        this.add(empty, BorderLayout.WEST);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    private void configurerightPanel(){
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(230,100));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);
        //continuar = new GreenButtonComponent("Continue");
        right.add(Box.createRigidArea(new Dimension(0, 50)));
        //right.add(continuar, BorderLayout.NORTH);

        this.add(right, BorderLayout.EAST);

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

}
