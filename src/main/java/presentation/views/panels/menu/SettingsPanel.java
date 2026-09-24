package presentation.views.panels.menu;

import presentation.views.panels.PanelKey;
import presentation.views.panels.menu.MenuPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EventListener;

/**
 * Class used to let the user log out, delete the account or see the stats
 */
public class SettingsPanel extends MenuPanel {
    /**
     * JButton variable that lets the user leave that view to go to the back
     */
    private JButton goBack;
    /**
     * JButton variable that lets the user close his account
     */
    private JButton jblogout;
    /**
     * JButton variable that lets the user delete his account
     */
    private JButton jbDelete;
    /**
     * JButton variable that lets the user see the statistics of the games
     */
    private JButton jbStats;

    /**
     * Constructs the main settings panel including another panels
     */

    public SettingsPanel() {
        super("files/home_background.png", PanelKey.SETTINGS);
        this.setLayout(new BorderLayout());
        configureLeftPanel();
        configureCenterPanel();
        configureWindow();
        goBack.setActionCommand(PanelKey.MAIN_MENU.label);
        jbDelete.setActionCommand(PanelKey.INITIAL.label);
        jblogout.setActionCommand(PanelKey.INITIAL.label);
        jbStats.setActionCommand(PanelKey.STATISTICS.label);
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
        JLabel label = new JLabel("Settings");
        label.setFont(new Font("Chewy Pro", Font.BOLD, 100));
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(140, 0, 50, 0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(label);

        jblogout = createButton("LogOut");

        jblogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(jblogout);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        jbDelete = createButton("Delete account");

        jbDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(jbDelete);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        jbStats = createButton("Statistics");
        jbStats.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(jbStats);

        centerPanel.setOpaque(false);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    private JButton createButton (String name) {
        Dimension buttonSize = new Dimension(380, 75);
        JButton button = new JButton(name);
        button.setMaximumSize(buttonSize);
        button.setFont(new Font("Belgrano", Font.PLAIN, 50));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setContentAreaFilled(false);
        return button;
    }

    /**
     * asks the user if really wants to logout
     * @return if the user want to logout
     */
    public boolean askLogOut () {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "LogOut", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * asks the user if really wants to delete his/her account
     * @return if the user want to delete the account
     */
    public boolean askDelete () {
        JOptionPane askOption = new JOptionPane();
        int result = askOption.showConfirmDialog(this, "Are you sure you want to delete your account?", "Delete account", JOptionPane.YES_NO_OPTION);

        if (result == askOption.YES_OPTION) {
            askOption.setVisible(false);
            JDialog dialog = new JDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(200, 200);
            dialog.setLayout(new BorderLayout());
            JLabel gifLabel = new JLabel(new ImageIcon(getClass().getResource("/files/loading.gif")));
            gifLabel.setOpaque(false);
            dialog.add(gifLabel, BorderLayout.CENTER);
            dialog.setLocationRelativeTo(null);
            dialog.setModal(true);

            // Set a timer to close the dialog after a certain time (optional)
            Timer timer = new Timer(2500, (e) -> dialog.dispose());
            timer.setRepeats(false);
            timer.start();

            dialog.setVisible(true);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * A thread that is started when attach request occurs.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void attachListener(EventListener listener) {
        goBack.addActionListener((ActionListener) listener);
        jblogout.addActionListener((ActionListener) listener);
        jbDelete.addActionListener((ActionListener) listener);
        jbStats.addActionListener((ActionListener) listener);
    }
    /**
     * It removes the event that previously has occurred.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void detachListener(EventListener listener) {
        goBack.removeActionListener((ActionListener) listener);
        jbDelete.removeActionListener((ActionListener) listener);
        jblogout.removeActionListener((ActionListener) listener);
        jbStats.removeActionListener((ActionListener) listener);
    }
}
