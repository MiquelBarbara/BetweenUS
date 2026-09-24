package presentation.views.panels.menu.components;

import business.game.config.GameConfig;
import business.game.config.GameConfigManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class used to manage user existing configurations
 */
public class ManageExistingConfigsTable extends JPanel {
    /**
     * String values to be set o columns
     */
    private String[] columns = new String[] {"Name of the configuration", "Color player", "Impostor count", "Player count", "Create game with this config."};
    /**
     * Data matrix to be set on the grid
     */
    private Object[][] data;
    /**
     * Game configuraiton manager to get the gameConfig Information
     */
    private GameConfigManager gameConfigManager = new GameConfigManager();
    /**
     * Table to be used
     */
    private TableModel model;
    /**
     * Table to be displayed
     */
    private JTable table;

    /**
     * Function used to manage the configurations table
     */
    public ManageExistingConfigsTable () {
        ArrayList<GameConfig> gameConfigManagerArrayList = new ArrayList<>();
        ArrayList<GameConfigDataTable> gameConfigDataTableArraylist = new ArrayList<>();

            gameConfigManagerArrayList = gameConfigManager.getUserGameConfigs(1);
            for (GameConfig gameConfig : gameConfigManagerArrayList) {
                gameConfigDataTableArraylist.add(new GameConfigDataTable(gameConfig.getName(), gameConfig.getPlayerColour(), gameConfig.getImpostorCount(), gameConfig.getCharacterCount()));
            }
            data = new Object[gameConfigDataTableArraylist.size()][5];
            for (int i = 0; i < gameConfigDataTableArraylist.size(); i++) {
                data[i][0] = gameConfigDataTableArraylist.get(i).getName();
                data[i][1] = gameConfigDataTableArraylist.get(i).getColour();
                data[i][2] = gameConfigDataTableArraylist.get(i).getImpostorCount();
                data[i][3] = gameConfigDataTableArraylist.get(i).getPlayerCount();
                data[i][4] = "USE CONFIGURATION";
            }

        model = new DefaultTableModel(data, columns) {};
        table = new JTable(model);
        table.getColumnModel().getColumn(4).setCellRenderer(new ManageExistingConfigsTable.ClientsTableButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ManageExistingConfigsTable.ClientsTableRenderer(new JCheckBox()));
        table.setPreferredSize(new Dimension(900, 500));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        //table.setPreferredSize(new Dimension(900, 500));
        JScrollPane scroll = new JScrollPane(table);
        add(scroll);
    }

    /**
     * Class used to render clients table buttons
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
     * class to render table
     */
    public class ClientsTableRenderer extends DefaultCellEditor
    {
        /**
         * Button to be displayed
         */
        private JButton button;
        /**
         * label to be used
         */
        private String label;
        /**
         * Boolean that indicates if its clicked
         */
        private boolean clicked;
        /**
         * integers that indicates row and column
         */
        private int row, col;
        /**
         * Table to be used
         */
        private JTable table;

        /**
         * Constructor of the table rendered
         * @param checkBox CheckBox to be used
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
