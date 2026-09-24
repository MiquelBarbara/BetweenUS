package presentation.views.panels.menu;

import presentation.views.panels.PanelKey;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
/**
 * Class used to show the register panel of the users account, introducing his username, email and password
 */

public class RegisterPanel extends MenuPanel {
    /**
     * ArrayList with JTexField variables where the user will introduce his data
     */
    private ArrayList<JTextField> textFields = new ArrayList<>();
    /**
     * JButton variable that lets the user leave that view to go to the back
     */
    private JButton goBack;

    /**
     * JButton variable that once the user click's it will save his data from his account
     */
    private JButton jbRegister;

    /**
     * creates the panel
     */
    public RegisterPanel() {
        super("files/home_background.png", PanelKey.REGISTER);
        this.setLayout(new BorderLayout());
        configureLeftPanel();
        configureCenterPanel();
        configureWindow();
        goBack.setActionCommand(PanelKey.INITIAL.label);
        jbRegister.setActionCommand(PanelKey.MAIN_MENU.label);
    }

    private void configureWindow () {
        setSize(1300, 900);
    }

    private void configureLeftPanel () {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(10,10,0,0));
        leftPanel.setPreferredSize(new Dimension(75, 75));

        goBack = createImageButton("files/back_button.png", 62, 62);
        goBack.setOpaque(false);

        leftPanel.add(goBack, BorderLayout.NORTH);
        leftPanel.setOpaque(false);
        this.add(leftPanel, BorderLayout.WEST);
    }

    private void configureCenterPanel () {
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Create account");
        label.setFont(new Font("Chewy Pro", Font.BOLD, 80));
        label.setForeground(Color.GREEN);
        label.setBorder(new EmptyBorder(50, 0, 0, 0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(label);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        centerPanel.add(createLabelText("Username"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(createLabelText("Email"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(createPasswordText("Enter password"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(createPasswordText("Re-enter password"));
        centerPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        jbRegister = createButton("REGISTER");
        centerPanel.add(jbRegister);

        this.add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createLabelText(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(700,70));
        JLabel label = new JLabel(name);
        label.setFont(new Font("Belgrano", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.NORTH);

        JTextField textField = new JTextField();
        textFields.add(textField);
        panel.add(textField, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }

    private String getText (JTextField textField) {
        return textField.getText();
    }

    private JPanel createPasswordText(String name) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(700,70));
        JLabel label = new JLabel(name);
        label.setFont(new Font("Belgrano", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.NORTH);

        JPasswordField textField = new JPasswordField();
        textFields.add(textField);
        panel.add(textField, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }

    private JButton createButton (String name) {
        JButton button = new JButton(name);
        button.setFont(new Font("Chewy Pro", Font.BOLD, 30));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(0xB7FFAB));

        return button;
    }

    /**
     * Method that is used to get the data that the user has introduced
     * @return these data of the user account
     */
    public String[] getCredentials() {
        String[] credentials = new String[4];

        credentials[0] = textFields.get(0).getText();
        credentials[1] = textFields.get(1).getText();
        credentials[2] = textFields.get(2).getText();
        credentials[3] = textFields.get(3).getText();

        return credentials;
    }

    /**
     * shows an error if there's an error doing the register
     * @param message the message needs to be shown
     */
    public void showFormatError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    /**
     * A thread that is started when attach request occurs.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void attachListener(EventListener listener) {
        goBack.addActionListener((ActionListener) listener);
        jbRegister.addActionListener((ActionListener) listener);
    }
    /**
     * It removes the event that previously has occurred.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void detachListener(EventListener listener) {
        goBack.removeActionListener((ActionListener) listener);
        jbRegister.removeActionListener((ActionListener) listener);
    }
}
