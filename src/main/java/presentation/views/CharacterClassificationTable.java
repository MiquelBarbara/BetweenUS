package presentation.views;

import business.game.Game;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class that creates the table to classificate the NPC
 */
public class CharacterClassificationTable extends JPanel {
    /**
     * private variable that stores the values of the column
     */
    private String[] columns = new String[] {"Unknown", "Not Sus", "Sus", "MOVE"};
    /**
     * private variable that stores the data that needs to be shown in the table
     */
    private Object[][] data;
    /**
     * private variable that stores the model of the table
     */
    private TableModel model;
    /**
     * private variable that stores the renderer of the table
     */
    private ClientsTableRenderer clientsTableRenderer;
    /**
     * private variable that stores the table
     */
    private JTable table;

    /**
     * function that creates the tables
     * @param game the game is being played
     */
    public CharacterClassificationTable(Game game) {
        ArrayList<String> colors = new ArrayList<>();
        colors = game.getCharactersColourLabel();
        data = new Object[colors.size()][4];
        for (int i = 0; i < colors.size(); i++) {
            data[i][0] = colors.get(i);
            data[i][1] = "";
            data[i][2] = "";
            data[i][3] = ">";
        }
        model = new DefaultTableModel(data, columns) {
            private static final long serialVersionUID = 1L;
            public boolean isCellEditable(int row, int column)
            {
                return column == 3;
            }
        };
        table = new JTable(model);
        clientsTableRenderer = new ClientsTableRenderer(new JCheckBox());
        table.getColumnModel().getColumn(3).setCellRenderer(new CharacterClassificationTable.ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(clientsTableRenderer);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll);
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
     * class that introduces the button to the JTable
     */
    class ClientsTableButtonRenderer extends JButton implements TableCellRenderer {
        /**
         * creates the class
         */
        public ClientsTableButtonRenderer()
        {
            setOpaque(true);
        }

        /**
         * makes the cell of the button a button
         * @param table the JTable is being shown
         * @param value the value we want to update
         * @param isSelected to know if the cell is being selected or not
         * @param hasFocus to know if we are clicking on the button
         * @param row the row we are clicking
         * @param column the column we are clicking
         * @return the table
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setForeground(Color.black);

            if (column == 0) {
                // Set the desired background color for the cell
                setBackground(getColorForPlayer(value.toString()));
            } else {
                // Set the default background color for other cells
                setBackground(UIManager.getColor("Button.background"));
            }

            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    /**
     * Creates the class to render the values of the table
     */
    public class ClientsTableRenderer extends DefaultCellEditor {
        /**
         * private variable that stores the button
         */
        private JButton button;
        /**
         * private variable that stores the label of the button
         */
        private String label;
        /**
         * private variable that stores if the button is being clicked or not
         */
        private boolean clicked;
        /**
         * private variable that stores the row and the column
         */
        private int row, col;
        /**
         * private variable that stores the JTable
         */
        private JTable table;

        /**
         * this method creates the class
         * @param checkBox to make the cell clickable
         */
        public ClientsTableRenderer(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    fireEditingStopped();
                }
            });
        }
        /**
         * makes the cell of the button a button
         * @param table the JTable is being shown
         * @param value the value we want to update
         * @param isSelected to know if the cell is being selected or not
         * @param row the row we are clicking
         * @param column the column we are clicking
         * @return the button
         */
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            this.col = column;

            button.setForeground(Color.black);
            button.setBackground(UIManager.getColor("Button.background"));
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }
        /**
         * gets the value of the button when pressed
         * @return the value
         */
        public Object getCellEditorValue()
        {
            if (clicked)
            {
                Object one = table.getValueAt(row, 0);
                Object two = table.getValueAt(row, 1);
                Object three = table.getValueAt(row, 2);
                table.setValueAt(one, row, 1);
                table.setValueAt(two, row, 2);
                table.setValueAt(three, row, 0);

            }
            clicked = false;
            return new String(label);
        }

        /**
         * makes it impossible to edit
         * @return if it is possible to edit or not
         */
        public boolean stopCellEditing()
        {
            clicked = false;
            return super.stopCellEditing();
        }

        /**
         * controls if the user is editing or not
         */
        protected void fireEditingStopped()
        {
            super.fireEditingStopped();
        }

        /**
         * gets the button
         * @return the button
         */
        public JButton getButton() {
            return button;
        }
    }

    /**
     * A thread that is started when the first attach request occurs.
     * @param actionListener event of the user's mouse click
     */
    public void attachListener(ActionListener actionListener) {
        JButton jButton = clientsTableRenderer.getButton();
        jButton.addActionListener(actionListener);
    }

    /**
     * It removes the event that previously has occurred.
     * @param actionListener: event of the user's mouse click
     */
    public void detachListener(ActionListener actionListener) {
        JButton jButton = clientsTableRenderer.getButton();
        jButton.removeActionListener(actionListener);
    }

}
