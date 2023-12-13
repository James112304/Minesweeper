package org.cis1200.Minesweeper;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class RunMineSweeper implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel remainingFlags = new JLabel("Flags: 99");
        status_panel.add(remainingFlags);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status, remainingFlags);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> {
            JFrame iFrame = new JFrame("Instructions");
            iFrame.setPreferredSize(new Dimension(400, 300));
            JTextArea text = new JTextArea();
            text.setLineWrap(true);
            text.setWrapStyleWord(true);
            text.setEditable(false);
            text.setText(
                    "Hello all! Welcome to minesweeper! " +
                            "\n\nIn this game, your goal is to " +
                            "reveal all tiles that aren't mines. " +
                            "Start by right-clicking any tile. " +
                            "The number of each tile revealed on the screen " +
                            "represents the number of mines " +
                            "adjacent to it. Use this information" +
                            " to deduce where" +
                            " the " +
                            "mines are. If you believe a tile " +
                            "to be a mine, right click to mark it with a flag, " +
                            "it will " +
                            "prevent you from accidentally clicking " +
                            "the tile. There are 99 mines. " +
                            "If you place all 99 flags and the game " +
                            "doesn't end, you've made a mistake!"
            );
            iFrame.add(text);
            iFrame.setLocation(550, 300);
            iFrame.pack();
            iFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            iFrame.setVisible(true);
        });
        control_panel.add(instructions);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.save());
        control_panel.add(save);

        final JButton load = new JButton("Load");
        load.addActionListener(e -> board.load());
        control_panel.add(load);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                frame.repaint();
            }
        }, 0, 8);

        // Start the game
        board.reset();
    }
}