package presentation.views;

import presentation.views.panels.PanelKey;
import presentation.views.panels.game.LogsPanel;
import presentation.views.panels.menu.BetweenUsPanel;
import presentation.views.panels.menu.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.util.EventListener;
import java.util.HashMap;

/**
 * This is the mainView of the program and is the one that manage the JFrame
 */
public class MainView extends JFrame {
    /**
     * private variable that creates the layout of the frame
     */
    private CardLayout cardLayout;
    /**
     * private variable that stores all the panels
     */
    private JPanel containerPanel;

    /**
     * Creates the class
     * @param panels stores all the panels of the game that need to be shown
     */
    public MainView (HashMap<PanelKey, JPanel> panels) {
        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);
        initializeCardPanel(panels);
        initializeFrame();
        cardLayout.show(containerPanel, PanelKey.INITIAL.label);
    }

    private void initializeCardPanel(HashMap<PanelKey, JPanel> panels) {
        containerPanel.add(panels.get(PanelKey.INITIAL), PanelKey.INITIAL.label);
        containerPanel.add(panels.get(PanelKey.LOGIN), PanelKey.LOGIN.label);
        containerPanel.add(panels.get(PanelKey.REGISTER), PanelKey.REGISTER.label);
        containerPanel.add(panels.get(PanelKey.MAIN_MENU), PanelKey.MAIN_MENU.label);
        containerPanel.add(panels.get(PanelKey.GAME_CONFIG), PanelKey.GAME_CONFIG.label);
        containerPanel.add(panels.get(PanelKey.GAME), PanelKey.GAME.label);
        containerPanel.add(panels.get(PanelKey.LOGS), PanelKey.LOGS.label);
        containerPanel.add(panels.get(PanelKey.DEFEAT), PanelKey.DEFEAT.label);
        containerPanel.add(panels.get(PanelKey.SETTINGS), PanelKey.SETTINGS.label);
        containerPanel.add(panels.get(PanelKey.CONTINUE_GAME), PanelKey.CONTINUE_GAME.label);
        containerPanel.add(panels.get(PanelKey.COPY_CONFIGS), PanelKey.COPY_CONFIGS.label);
        containerPanel.add(panels.get(PanelKey.DELETE_GAME), PanelKey.DELETE_GAME.label);
        containerPanel.add(panels.get(PanelKey.STATISTICS), PanelKey.STATISTICS.label);
    }

    private void initializeFrame() {
        setTitle("Between US");
        setSize(1300, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(containerPanel);
    }

    /**
     * sets the visibility of the frame into true
     */
    public void start() {
        this.setVisible(true);
    }

    /**
     * changes the panel is being shown in the frame
     * @param panelKey the key of the panel needs to be shown
     * @param listener the listener that the panels need
     */
    public void changePanel(String panelKey, EventListener listener) {
        BetweenUsPanel currentPanel = getCurrentPanel();
        currentPanel.detachListener(listener);

        cardLayout.show(containerPanel, panelKey);

        currentPanel = getCurrentPanel();
        currentPanel.attachListener(listener);

        cardLayout.show(containerPanel, panelKey);

        switch (panelKey) {
            case "logs":
                LogsPanel logsPanel = (LogsPanel) getCurrentPanel();
                logsPanel.refresh();
        }
    }

    private BetweenUsPanel getCurrentPanel() {
        BetweenUsPanel panel = null;

        for (Component component: containerPanel.getComponents()) {
            if (component.isVisible()) {
                panel = (BetweenUsPanel) component;
            }
        }

        return panel;
    }
}
