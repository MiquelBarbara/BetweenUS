package presentation.views.panels.menu;

import presentation.views.ReadTxtFilesInFolder;
import presentation.views.panels.PanelKey;
import presentation.views.panels.menu.components.ColorPickerPanel;
import presentation.views.panels.menu.components.GreenButtonComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * Class that acts as a customized JPanel, that shows the New Game Configuration, where
 * the user can choose his username, number of impostors and players, his color and the map.
 */

public class GameConfigPanel extends MenuPanel {
    /**
     * JButton variable that lets the user leave that view to go to the next
     */
    private JButton next;

    /**
     * JButton variable that lets the user leave that view to go to the back
     */
    private JButton back;
    /**
     * JTextField variable that lets the user introduces his username
     */
    private JTextField gameName;
    /**
     * Arraylist of JSpinner variable that lets the user choose a number of players / impostors
     */
    private ArrayList<JSpinner> spinners = new ArrayList<>();

    /**
     * JComboBox variable that lets the user choose the map from a drop-down list
     */
    private JComboBox jComboBox = new JComboBox<>();

    /**
     * ColorPickerPanel customised component that lets the user choose a color
     */
    ColorPickerPanel colorPanel = new ColorPickerPanel();

    /**
     * Constructs the main panel and calls other functions that adds more panels
     */

    public GameConfigPanel() {
        super("files/newgame.png", PanelKey.GAME_CONFIG);
        this.setLayout(new BorderLayout());
        configureCenterPanel();
        configureWindow();
        back.setActionCommand(PanelKey.MAIN_MENU.label);
        next.setActionCommand(PanelKey.GAME.label);
    }

    private void configureWindow() {
        setSize(1300, 900);
    }

    private void configureCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createRigidArea(new Dimension(70, 130)));
        centerPanel.add(createLabelText("Name:"));
        centerPanel.add(Box.createRigidArea(new Dimension(20, 30)));
        centerPanel.add(createJSpinner("Number of players:         ",4,10));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(createJSpinner("Number of impostors:     ",1,3));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(createText("Player color:"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(500, 50));
        panel.add(colorPanel);
        panel.setOpaque(false);
        centerPanel.add(panel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(createText("Map:"));
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.setMaximumSize(new Dimension(92, 92));
        panel3.setOpaque(false);
        panel3.add(createJComboBox());
        centerPanel.add(panel3);

        centerPanel.setVisible(true);

        this.add(createLeftPanel(),BorderLayout.WEST);
        this.add(createRightPanel(),BorderLayout.EAST);
        this.add(centerPanel);


    }

    private JPanel createLabelText(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(500,70));
        JLabel label = new JLabel(name);
        label.setFont(new Font("Belgrano", Font.PLAIN, 23));
        label.setForeground(Color.BLACK);
        panel.add(label, BorderLayout.NORTH);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        gameName = new JTextField();
        gameName.setPreferredSize(new Dimension(20,40));
        panel.add(gameName, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }

    private JPanel createText(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(500, 30));
        JLabel label = new JLabel(name);
        label.setFont(new Font("Belgrano", Font.PLAIN, 23));
        label.setForeground(Color.BLACK);
        panel.add(label, BorderLayout.NORTH);
        panel.setOpaque(false);

        return panel;
    }

    private JPanel createJSpinner(String name, int min, int max){
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(500,60));
        JLabel label = new JLabel(name);
        label.setFont(new Font("Belgrano", Font.PLAIN, 23));
        label.setForeground(Color.BLACK);
        panel.add(label, BorderLayout.WEST);
        panel.setOpaque(false);
        SpinnerModel value = new SpinnerNumberModel(min, min, max, 1);
        JSpinner spinner = new JSpinner(value);
        spinners.add(spinner);
        spinner.setPreferredSize(new Dimension(20,35));
        panel.add(spinner);
        return panel;
    }

    private JPanel createJComboBox () {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(92, 92));
        ReadTxtFilesInFolder readTxtFilesInFolder = new ReadTxtFilesInFolder();
        ArrayList<String> arrayList = readTxtFilesInFolder.getNameFiles();
        String[] names = arrayList.toArray(new String[0]);
        jComboBox = new JComboBox<>(names);
        jComboBox.setSize(80, 50);
        panel.setOpaque(false);
        panel.add(jComboBox);
        return panel;
    }
    private JPanel createLeftPanel(){
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(300,100));
        left.setOpaque(false);

        back = createImageButton("files/back_button.png", 75,75);
        left.add(back, BorderLayout.WEST);
        left.add(Box.createRigidArea(new Dimension(130,0)));

        return left;
    }

    private JPanel createRightPanel(){
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(300,100));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setOpaque(false);
        right.add(Box.createRigidArea(new Dimension(50,80)));
        next = new GreenButtonComponent("Next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to be executed when the button is clicked
                System.out.println(gameName.getText());
                System.out.println(spinners.get(0).getValue().toString());
                System.out.println(spinners.get(1).getValue().toString());
                System.out.println(colorPanel.getSelectedColor().toString());
            }
        });
        right.add(next, BorderLayout.NORTH);


        return right;
    }

    /**
     * Method that gets the data that the user has chosen for the game
     * @return an array of this data (name, players, impostors, color and map)
     */

    public String[] getData () {
        String[] data = new String[5];
        data[0] = gameName.getText();
        data[1] = spinners.get(0).getValue().toString();
        data[2] = spinners.get(1).getValue().toString();
        data[3] = colorPanel.getSelectedColor().toString();
        data[4] = jComboBox.getSelectedItem().toString();

        return data;
    }
    /**
     * A thread that is started when the first attach request occurs.
     * @param listener: event of the user's mouse click
     */

    @Override
    public void attachListener(EventListener listener) {
        back.addActionListener((ActionListener) listener);
        next.addActionListener((ActionListener) listener);
    }

    /**
     * It removes the event that previously has occurred.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void detachListener(EventListener listener) {
        //back.removeActionListener((ActionListener) listener);
        //next.removeActionListener((ActionListener) listener);
    }
}