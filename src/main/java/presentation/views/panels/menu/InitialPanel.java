package presentation.views.panels.menu;

import presentation.views.panels.PanelKey;
import presentation.views.panels.menu.components.JImagePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EventListener;

/**
 * Class that acts as a customized JPanel, that shows the first view once the user opens the videogame
 * with options of login or register
 */
public class InitialPanel extends MenuPanel {

    /**
     * JButton variable that allows the user to log in with his account, whenever he clicks
     */
    private JButton jbLogIn;

    /**
     * JButton variable that allows the user to register creating an account, whenever he clicks
     */
    private JButton jbRegister;

    /**
     * Constructs the main panel and calls other functions that include other panels
     */
    public InitialPanel() {
        super("files/home_background.png", PanelKey.INITIAL);
        this.setLayout(new BorderLayout());
        configureWindow();
        configureLeftPanel();
        configureRightPanel();
        configureCenterPanel();
        jbLogIn.setActionCommand(PanelKey.LOGIN.label);
        jbRegister.setActionCommand(PanelKey.REGISTER.label);
    }

    private void configureWindow () {
        this.setSize(1300, 900);
    }

    private void configureLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(180, 180));
        JImagePanel image = new JImagePanel("files/Red.png");
        image.setOpaque(false);
        leftPanel.add(image, BorderLayout.SOUTH);
        leftPanel.setOpaque(false);
        this.add(leftPanel, BorderLayout.WEST);
    }

    private void configureRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(230, 230));
        JImagePanel image = new JImagePanel("files/party.png");
        image.setOpaque(false);
        rightPanel.add(image, BorderLayout.SOUTH);
        rightPanel.setOpaque(false);
        this.add(rightPanel, BorderLayout.EAST);
    }

    private void configureCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("BetweenUs");
        label.setFont(new Font("Chewy Pro", Font.BOLD, 100));
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(140, 0, 50, 0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(label);

        jbLogIn = createButton("LogIn");

        jbLogIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(jbLogIn);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        jbRegister = createButton("Register");

        jbRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(jbRegister);

        centerPanel.setOpaque(false);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createButton (String name) {
        Dimension buttonSize = new Dimension(380, 75);
        JButton button = new JButton(name);
        button.setMaximumSize(buttonSize);
        button.setFont(new Font("Belgrano", Font.PLAIN, 50));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setContentAreaFilled(false);
        return button;
    }

    /**
     * A thread that is started when the first attach request occurs.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void attachListener(EventListener listener) {
        jbLogIn.addActionListener((ActionListener) listener);
        jbRegister.addActionListener((ActionListener) listener);
    }
    /**
     * It removes the event that previously has occurred.
     * @param actionListener: event of the user's mouse click
     */
    @Override
    public void detachListener(EventListener actionListener) {
        jbLogIn.removeActionListener((ActionListener) actionListener);
        jbRegister.removeActionListener((ActionListener) actionListener);
    }

}
