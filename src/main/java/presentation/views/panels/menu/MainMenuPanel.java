package presentation.views.panels.menu;

import presentation.views.panels.PanelKey;
import presentation.views.panels.menu.components.GreenButtonComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EventListener;

/**
 * Class used to show the main 4 options that the videogame has, you can also go to the settings panel
 */

public class MainMenuPanel extends MenuPanel {
    /**
     * JButton variable to enter to a new creating game configuration
     */
    private JButton newGame;

    /**
     * JButton variable to resume another game
     */
    private JButton continueLast;

    /**
     * JButton variable to choose a configuration of a previous game
     */
    private JButton lastConfig;

    /**
     * JButton variable to delete a previous game
     */
    private JButton deleteGame;

    /**
     * JButton variable to enter to the settings panel
     */
    private JButton config;
    /**
     * Constructs the main panel with the different subpanels
     */

    public MainMenuPanel() {
        super("files/fondoninots.png", PanelKey.MAIN_MENU);
        this.setLayout(new BorderLayout());
        configureCenterPanel();
        configureConfigButton();
        configureWindow();
        newGame.setActionCommand(PanelKey.GAME_CONFIG.label);
        config.setActionCommand(PanelKey.SETTINGS.label);
        lastConfig.setActionCommand(PanelKey.COPY_CONFIGS.label);
        continueLast.setActionCommand(PanelKey.CONTINUE_GAME.label);
        deleteGame.setActionCommand(PanelKey.DELETE_GAME.label);
    }

    private void configureWindow () {
        setSize(1300, 900);
    }

    private void configureCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        newGame = new GreenButtonComponent("Create game");
        continueLast = new GreenButtonComponent("Continue Last Game");
        lastConfig = new GreenButtonComponent("Choose configuration");
        deleteGame = new GreenButtonComponent("Delete Game");

        newGame.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(Box.createRigidArea(new Dimension(450,100)));
        centerPanel.add(newGame);
        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));
        centerPanel.add(lastConfig);
        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));
        centerPanel.add(continueLast);
        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));
        centerPanel.add(deleteGame);

        centerPanel.setOpaque(false);
        this.add(centerPanel, BorderLayout.CENTER);

    }

    private void configureConfigButton() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10,10,0,0));
        panel.setPreferredSize(new Dimension(150, 150));
        //JImagePanel image = new JImagePanel("files/config.png");
        config = createImageButton("files/config.png", 80, 80);
        config.setOpaque(false);
        panel.add(config, BorderLayout.NORTH);
        panel.setOpaque(false);
        this.add(panel, BorderLayout.EAST);

    }
    /**
     * A thread that is started when attach request occurs.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void attachListener(EventListener listener) {
        newGame.addActionListener((ActionListener) listener);
        lastConfig.addActionListener((ActionListener) listener);
        continueLast.addActionListener((ActionListener) listener);
        deleteGame.addActionListener((ActionListener) listener);
        config.addActionListener((ActionListener) listener);
    }
    /**
     * It removes the event that previously has occurred.
     * @param listener: event of the user's mouse click
     */
    public void detachListener(EventListener listener) {
        newGame.removeActionListener((ActionListener) listener);
        lastConfig.removeActionListener((ActionListener) listener);
        continueLast.removeActionListener((ActionListener) listener);
        deleteGame.removeActionListener((ActionListener) listener);
        config.removeActionListener((ActionListener) listener);
    }
}
