package presentation.views.panels.menu;

import presentation.views.panels.PanelKey;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.EventListener;

/**
 * This class shows the statistics of the user
 */
public class StatisticsPanel extends MenuPanel {
    /**
     * button to go back to the previous view
     */
    private JButton goBack;
    /**
     * array that stores the points of the user victory rate
     */
    private int[] dataPoints;
    /**
     * array that stores the yValues to print in the chart
     */
    private int[] yLabels;

    /**
     * creates the panel
     */
    public StatisticsPanel() {
        super("files/home_background.png", PanelKey.STATISTICS);
        this.setLayout(new BorderLayout());
        configureWindow();
        configureLeftPanel();
        configureChart();
        goBack.setActionCommand(PanelKey.SETTINGS.label);
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

    private void configureChart () {
        dataPoints = new int[] {0, 1, 1, 2, 3, 3, 4, 4, 4, 5, 4, 5, 6, 10};
        yLabels = new int[] {0,1,2,3,4,5,6,7,8,9,10};
    }
    /**
     * function that paints the chart
     * @param g the graphics needed by the class
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int padding = 25;
        int maxValue = getMaxValue(dataPoints);

        // Draw the background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(padding, padding, width - 2 * padding, height - 2 * padding);

        // Set the font for axis labels
        Font labelFont = g2d.getFont().deriveFont(Font.PLAIN, 10);
        g2d.setFont(labelFont);

        // Draw the x-axis
        g2d.setColor(Color.BLACK);
        g2d.drawLine(padding, height - padding, width - padding, height - padding);

        // Draw the y-axis
        g2d.drawLine(padding, padding, padding, height - padding);

        // Calculate the scaling factor for the data points
        double xScale = (double) (width - 2 * padding) / (dataPoints.length - 1);
        double yScale = (double) (height - 2 * padding) / (yLabels[yLabels.length - 1] - yLabels[0]);

        // Draw the x-axis labels
        g2d.setColor(Color.BLACK);
        int labelPadding = 5;
        for (int i = 0; i < dataPoints.length; i++) {
            int x = (int) (i * xScale + padding);
            int y = height - padding + labelPadding + g2d.getFontMetrics().getHeight();
            String label = Integer.toString(dataPoints[i]);
            g2d.drawString(label, x, y);
        }

        // Draw the data points as a line chart
        g2d.setColor(Color.BLUE);
        for (int i = 0; i < dataPoints.length - 1; i++) {
            int x1 = (int) (i * xScale + padding);
            int y1 = height - padding - (int) ((dataPoints[i] - yLabels[0]) * yScale);
            int x2 = (int) ((i + 1) * xScale + padding);
            int y2 = height - padding - (int) ((dataPoints[i + 1] - yLabels[0]) * yScale);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    private int getMaxValue(int[] dataPoints) {
        int maxValue = Integer.MIN_VALUE;
        for (int value : dataPoints) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    /**
     * A thread that is started when the first attach request occurs.
     * @param listener: event of the user's mouse click
     */
    @Override
    public void attachListener(EventListener listener) {
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