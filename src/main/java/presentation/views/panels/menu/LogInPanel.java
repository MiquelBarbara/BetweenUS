package presentation.views.panels.menu;

import presentation.views.panels.PanelKey;
import presentation.views.panels.menu.MenuPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * Class that extendrs from a customized panel, that shows the LogIn view, so the user can access to his account
 */

public class LogInPanel extends MenuPanel {

    /**
     * ArrayList of JTextField variable that lets the user introduce his username / password
     */
    private ArrayList<JTextField> textFields = new ArrayList<>();

    /**
     * JButton variable that lets the user leave that view to go to the back
     */
    private JButton goBack;
    /**
     * JButton variable that lets the user finish his login and leave the view
     */
    private JButton jbLogIn;

    /**
     * Constructs the background and includes 2 panels
     */
    public LogInPanel() {
        super("files/home_background.png", PanelKey.LOGIN);
        this.setLayout(new BorderLayout());
        configureWindow();
        configureCenterPanel();
        configureLeftPanel();
        goBack.setActionCommand(PanelKey.INITIAL.label);
        jbLogIn.setActionCommand(PanelKey.MAIN_MENU.label);
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

    private void configureCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("LogIn");
        label.setFont(new Font("Chewy Pro", Font.BOLD, 80));
        label.setForeground(Color.GREEN);
        label.setBorder(new EmptyBorder(120, 0, 50, 80));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(label);

        centerPanel.add(Box.createRigidArea(new Dimension(0,20)));
        centerPanel.add(createLabelText("Username / Email"));
        centerPanel.add(Box.createRigidArea(new Dimension(0,50)));
        centerPanel.add(createPasswordText("Enter password"));
        centerPanel.add(Box.createRigidArea(new Dimension(0,50)));

        jbLogIn = createButton("LOG IN");
        centerPanel.add(jbLogIn);

        centerPanel.setOpaque(false);
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
     * Method that is used to get the info of the user so then check if it's wrong or not
     * @return an array with the info that the user has introduced
     */
    public String[] getCredentials() {
        String[] credentials = new String[2];

        credentials[0] = textFields.get(0).getText();
        credentials[1] = textFields.get(1).getText();

        return credentials;
    }

    /**
     * Method that shows a little panel with an advising message error
     * @param message string it will be shown
     */

    public void showCredentialsError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * A thread that is started when the first attach request occurs.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void attachListener(EventListener listener) {
        goBack.addActionListener((ActionListener) listener);
        jbLogIn.addActionListener((ActionListener) listener);
    }

    /**
     * It removes the event that previously has occurred.
     * @param listener: event of the user's mouse click
     */
    public void detachListener(EventListener listener) {
        goBack.removeActionListener((ActionListener) listener);
        jbLogIn.removeActionListener((ActionListener) listener);
    }
}
