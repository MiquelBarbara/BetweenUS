package presentation.views.panels.menu.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class used to pick a colour to create a new configuration
 */
public class ColorPickerPanel extends JPanel implements ChangeListener {
    /**
     * array of possible colours to be picked
     */
    private final Color[] colors;
    /**
     * Panels of the possible colour squares
     */
    private final JPanel[] colorSquares;
    /**
     * Color choooser
     */
    private final JColorChooser colorChooser;
    /**
     * Border that indicates the selected border
     */
    private final Border selectedBorder;
    /**
     * Border set to the unselected colours
     */
    private final Border unselectedBorder;
    /**
     * Index that indicates the selected colour
     */
    private int selectedColorIndex;

    /**
     * Constructor of colour picker panel
     */
    public ColorPickerPanel() {
        super(new GridLayout(1, 0));
        setPreferredSize(new Dimension(300, 60));


        //array de colors
        colors = new Color[] {
                Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE,
                new Color(128, 64, 0)/*marron*/, new Color(177,77,186)/*lila*/, new Color(255,0,128)/*rosa*/,
                new Color(191, 255, 0)/*lima*/, Color.BLACK, Color.WHITE,
        };

        colorSquares = new JPanel[colors.length];
        selectedBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.GRAY);
        unselectedBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        selectedColorIndex = 0;

        for (int i = 0; i < colorSquares.length; i++) {
            colorSquares[i] = new JPanel();
            colorSquares[i].setBackground(colors[i]);
            colorSquares[i].setBorder(unselectedBorder);
            colorSquares[i].addMouseListener(new ColorSquareListener(i));
            add(colorSquares[i]);
        }

        colorChooser = new JColorChooser(Color.WHITE);
        colorChooser.getSelectionModel().addChangeListener(this);
    }

    /**
     * Function used to get the selected colour
     * @return selcted colour
     */
    public Color getSelectedColor() {
        return colors[selectedColorIndex];
    }

    /**
     * Function used to check when an event has been changed
     * @param e  a ChangeEvent object
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        Color color = colorChooser.getColor();
        for (int i = 0; i < colorSquares.length; i++) {
            if (colorSquares[i].getBorder() == selectedBorder) {
                colorSquares[i].setBackground(color);
                colors[i] = color;
                selectedColorIndex = i;
            }
        }
    }

    /**
     * class used to get the colour square
     */
    private class ColorSquareListener extends java.awt.event.MouseAdapter {
        /**
         * Index of the square
         */
        private final int index;

        /**
         * Constructor of the colour square
         * @param index index to be set
         */
        public ColorSquareListener(int index) {
            this.index = index;
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            for (int i = 0; i < colorSquares.length; i++) {
                if (i == index) {
                    colorSquares[i].setBorder(selectedBorder);
                    selectedColorIndex = i;
                } else {
                    colorSquares[i].setBorder(unselectedBorder);
                }
            }
        }

    }

}
