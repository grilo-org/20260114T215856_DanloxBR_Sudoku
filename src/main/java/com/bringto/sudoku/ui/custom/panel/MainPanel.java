package com.bringto.sudoku.ui.custom.panel;


import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel(final Dimension dimension) {
        setPreferredSize(dimension);
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
    }
}
