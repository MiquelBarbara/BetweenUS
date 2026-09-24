package presentation.views.panels.game;

import business.game.Log;
import presentation.views.panels.PanelKey;
import presentation.views.panels.menu.MenuPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * Class used to show a JTable with the info of the players, their perception and where they have been with the respective timestamp
 */

public class LogsPanel extends MenuPanel {

    /**
     * ArrayList with Log variable, that will be used to get the data of the players
     */
    private ArrayList<Log> logs;

    /**
     * JTable that is going to be shown one the user enter to the CameraRoom (3 columns, X rows)
     */
    private JTable table;

    /**
     * Constructs the panel where the table is going to be
     */

    public LogsPanel() {
        super("files/home_background.png", PanelKey.LOGS);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.requestFocusInWindow(true);

    }

    private JPanel configureTable(ArrayList<Log> logs) {

        JPanel paneltable = new JPanel(new BorderLayout());
        paneltable.setMaximumSize(new Dimension(300,300));

        String[] columnNames = {"Crewmate", "Room", "Instant"};
        Object[][] rowData = new Object[logs.size()][columnNames.length];

        for (int i = 0; i < logs.size(); i++) {
            Log log = logs.get(i);
            String playerColorWithPerception = log.getPlayerColour() + " (" + log.getPerception() + ")";
            rowData[i] = new Object[]{playerColorWithPerception, log.getRoomName(), log.getInstant()};
        }

        table = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.PINK);


        tableHeader.setFont(new Font("Belgrano", Font.PLAIN, 25));
        table.setFont(new Font("Belgrano", Font.PLAIN, 16));
        table.setRowHeight(30);

        TableCellRenderer colorRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String playerColor = logs.get(row).getPlayerColour();
                Color color = getColorForPlayer(playerColor);
                component.setBackground(color);

                return component;
            }
        };

        table.getColumnModel().getColumn(0).setCellRenderer(colorRenderer);


        //centrar textos celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        paneltable.add(scrollPane, BorderLayout.CENTER);

        return paneltable;
    }

    /**
     * Method that replaces the previous panel with the new one, in order to update the logs info
     * @param logs is the data of the players
     */

    public void updateLogs(ArrayList<Log> logs) {
        this.logs = logs;
        if(logs.size() == 0){
            this.add(configureTable(logs));
        }else{
            this.removeAll();
            this.add(configureTable(logs));
            this.revalidate();
            this.repaint();
        }

    }

    private Color getColorForPlayer(String playerColor) {
        switch (playerColor.toUpperCase()) {
            case "RED":
                return Color.RED;
            case "BLUE":
                return Color.BLUE;
            case "GREEN":
                return Color.GREEN;
            case "CYAN":
                return Color.CYAN;
            case "YELLOW":
                return Color.YELLOW;
            case "ORANGE":
                return Color.ORANGE;
            case "WHITE":
                return Color.WHITE;
            case "BLACK":
                return new Color(71, 75, 78);
            case "PURPLE":
                return new Color(177, 77, 186);
            case "PINK":
                return new Color(255, 0, 128);
            case "BROWN":
                return new Color(128, 64, 0);
            case "LIME":
                return new Color(191, 255, 0);
            default:
                return Color.WHITE;
        }

    }

    /**
     * Method called in the changing panel system, in order to refresh the info
     */
    public void refresh() {
        table.repaint();
    }

    /**
     * A thread that is started when the attach request occurs.
     * @param listener: event of the user's enter to the room
     */
    @Override
    public void attachListener(EventListener listener) {
        updateLogs(logs);
        this.addKeyListener((KeyListener) listener);

    }
    /**
     * It removes the event that previously has occurred.
     * @param listener: event of the user's mouse click
     */

    @Override
    public void detachListener(EventListener listener) {
        this.removeKeyListener((KeyListener) listener);
    }

}


