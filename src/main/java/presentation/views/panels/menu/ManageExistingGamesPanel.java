package presentation.views.panels.menu;

import presentation.views.panels.menu.components.ManageExistingConfigsTable;
import presentation.views.panels.menu.components.ManageExistingGamesTable;
import presentation.views.panels.PanelKey;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EventListener;
/**
 * Class used to show a JTable with the previous games, in order to choose and resume one of them
 */
public class ManageExistingGamesPanel extends MenuPanel {
    /**
     * JButton variable that lets the user leave that view to go to the back
     */
    private JButton goBack;

    /**
     * Constructs the main panel with the table and the goback button
     */
    public ManageExistingGamesPanel() {
        super("files/home_background.png", PanelKey.CONTINUE_GAME);
        this.setLayout(new BorderLayout());
        configureTable();
        configureLeftPanel();
        configureWindow();
        goBack.setActionCommand(PanelKey.MAIN_MENU.label);
    }

    private void configureWindow () {
        setSize(1300, 900);
    }

    private void configureTable () {
        this.setBorder(new EmptyBorder(0, 0, 300, 100));
        ManageExistingGamesTable manageExistingGamesTable = new ManageExistingGamesTable();
        manageExistingGamesTable.setOpaque(false);
        manageExistingGamesTable.setBorder(new EmptyBorder(100,0,0,0));
        this.add(manageExistingGamesTable, "Center");
    }

    private void configureLeftPanel () {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10,10,0,0));
        leftPanel.setPreferredSize(new Dimension(75, 75));

        goBack = createImageButton("files/back_button.png", 62, 62);
        goBack.setOpaque(false);
        goBack.setActionCommand(PanelKey.MAIN_MENU.label);

        leftPanel.add(goBack, BorderLayout.NORTH);
        leftPanel.setOpaque(false);
        this.add(leftPanel, BorderLayout.WEST);
    }
    private void updateTable(){
        this.removeAll();
        configureLeftPanel();
        configureTable();
        this.revalidate();
        this.repaint();
    }
    /**
     * A thread that is started when attach request occurs.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void attachListener(EventListener listener) {
        updateTable();
        goBack.addActionListener((ActionListener) listener);
    }
    /**
     * It removes the event that previously has occurred.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void detachListener(EventListener listener) {
        goBack.removeActionListener((ActionListener) listener);
    }
}
