package presentation.views.panels.menu.components;

import business.exceptions.BusinessException;
import business.game.config.GameConfig;
import business.game.config.GameConfigManager;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class used to manage created games by a user
 */
public class ManageExistingGamesTable extends JPanel {
    /**
     * Strings to be displayed on table's column
     */
    private String[] columns = new String[] {"Name of the game", "Resume game"};
    /**
     * Game configuration manager used to get the configurations game
     */
    private GameConfigManager gameConfigManager = new GameConfigManager();
    /**
     * Array of data to be displayed
     */
    private Object[][] data;
    /**
     * table to be used
     */
    private TableModel model;
    /**
     * table to be displayed
     */
    private JTable table;

    /**
     * Function used to manage the games got
     */
    public ManageExistingGamesTable() {
        ArrayList<GameConfig> gameConfigManagerArrayList = new ArrayList<>();
        ArrayList<GameConfigDataTable> gameConfigDataTableArraylist = new ArrayList<>();
        gameConfigManagerArrayList = gameConfigManager.getUserGameConfigs(1);
        for (GameConfig gameConfig : gameConfigManagerArrayList) {
            gameConfigDataTableArraylist.add(new GameConfigDataTable(gameConfig.getName(), gameConfig.getPlayerColour(), gameConfig.getImpostorCount(), gameConfig.getCharacterCount()));
        }
        data = new Object[gameConfigDataTableArraylist.size()][5];
        for (int i = 0; i < gameConfigDataTableArraylist.size(); i++) {
            data[i][0] = gameConfigDataTableArraylist.get(i).getName();
            data[i][1] = "RESUME";
        }
        model = new DefaultTableModel(data, columns) {
        };
        table = new JTable(model);
        table.getColumnModel().getColumn(1).setCellRenderer(new ManageExistingGamesTable.ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(1).setCellEditor(new ManageExistingGamesTable.ClientsTableRenderer(new JCheckBox()));
        table.setPreferredSize(new Dimension(900, 500));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        //table.setPreferredSize(new Dimension(900, 500));
        JScrollPane scroll = new JScrollPane(table);
        add(scroll);
    }

    /**
     * Class used to render tables buttons
     */
    class ClientsTableButtonRenderer extends JButton implements TableCellRenderer
    {
        /**
         * Constructor of the class
         */
        public ClientsTableButtonRenderer()
        {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            setForeground(Color.black);
            setBackground(UIManager.getColor("Button.background"));
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    /**
     * Class used to render table
     */
    public class ClientsTableRenderer extends DefaultCellEditor
    {
        /**
         * Button ot be used
         */
        private JButton button;
        /**
         * Label of the table
         */
        private String label;
        /**
         * Boolean that indicates if the table is clicked
         */
        private boolean clicked;
        /**
         * integer that indicates row and column
         */
        private int row, col;
        /**
         * Table to be displayed
         */
        private JTable table;

        /**
         * Constructor of the class
         * @param checkBox checkbox to be used
         */
        public ClientsTableRenderer(JCheckBox checkBox)
        {
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
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
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
        public Object getCellEditorValue()
        {
            if (clicked) {
                // ANAR A LA NOVA PANTALLA
            }
            clicked = false;
            return new String(label);
        }

        public boolean stopCellEditing()
        {
            clicked = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped()
        {
            super.fireEditingStopped();
        }
    }
}