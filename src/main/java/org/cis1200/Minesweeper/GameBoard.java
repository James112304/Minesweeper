package org.cis1200.Minesweeper;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * This class instantiates a TicTacToe object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Minesweeper ms; // model for the game
    private JLabel status; // current status text

    private JLabel flags;

    // Game constants
    public static final int BOARD_WIDTH = 900;
    public static final int BOARD_HEIGHT = 480;
    private boolean over;
    private boolean won;
    private int flagsLeft;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, JLabel flags1) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridLayout(16, 30));
        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        flags = flags1;

        // ms = new Minesweeper(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        int numMines = 99;
        ms = new Minesweeper(16, 30, numMines);
        flagsLeft = numMines;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getButton());
                if (e.getButton() == MouseEvent.BUTTON1) {
                    ms.reveal(e.getY() / 30, e.getX() / 30);
                    updateStatus();
                }
                if (e.getButton() == MouseEvent.BUTTON2
                        || e.getButton() == MouseEvent.BUTTON3 && flagsLeft > 0) {
                    System.out.println("fired");
                    if (ms.isFlagged(e.getY() / 30, e.getX() / 30)) {
                        flagsLeft += 1;
                    } else {
                        flagsLeft -= 1;
                    }
                    ms.changeFlag(e.getY() / 30, e.getX() / 30);
                    updateStatus();
                }
                repaint();
            }
        });
        repaint();
    }

    public void save() {
        String s = ms.returnBoard();
        try {
            File file = new File("minesweeper");
            BufferedWriter w = new BufferedWriter(new FileWriter(file));
            w.write(s);
            w.flush();
        } catch (IOException e) {
            throw new Error("ha ha");
        }
    }

    public void load() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(new File("minesweeper")));
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < 30; i++) {
                s.append(r.readLine());
                s.append("\n");
            }
            ms = new Minesweeper(s.toString());
            flagsLeft = flagsLeft - ms.getNumFlags();
            System.out.println(ms.getNumFlags());
            updateStatus();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        ms.reset();
        over = false;
        won = false;
        flagsLeft = 99;
        flags.setText("FLAGS: " + flagsLeft);
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        status.setText("Make a move . . . ");
        flags.setText("FLAGS: " + flagsLeft);
        if (ms.isGameOver()) {
            over = true;
            flags.setText("");
            status.setText("Game Over! You lost!");
        }
        if (ms.playerWon()) {
            won = true;
            flags.setText("");
            status.setText("You win!");
            repaint();
        }
        if (!ms.playerWon() && flagsLeft == 0) {
            status.setText("All flags used! Made a mistake?");
        }
    }

    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    /**
     * public JPanel gridLayoutPanel() {
     * JPanel panel = new JPanel();
     * panel.setLayout(new GridLayout(16, 30));
     * Tile[][] board = ms.getBoard();
     * for (Tile[] row : board) {
     * for (Tile tile : row) {
     * panel.add(new JButton("1"));
     * }
     * }
     * return panel;
     * }
     **/

    /**
     * Returns the size of the game board.
     */

    @Override
    public void paintComponent(Graphics g) {
        g.setFont(Images.getmFont());
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 16; j++) {
                if (won) {
                    int state = ms.getTile(j, i).trueStatus();
                    if (state == -1) {
                        g.drawImage(Images.getGoodbomb(), i * 30, j * 30, 30, 30, null);
                    } else {
                        g.drawImage(Images.getEmpty(), i * 30, j * 30, 30, 30, null);
                        if (state != 0) {
                            g.drawString(Integer.toString(state), i * 30 + 10, j * 30 + 22);
                        }
                    }
                } else if (over) {
                    int state = ms.getTile(j, i).trueStatus();
                    if (state == -1) {
                        g.drawImage(Images.getBomb(), i * 30, j * 30, 30, 30, null);
                    } else {
                        g.drawImage(Images.getEmpty(), i * 30, j * 30, 30, 30, null);
                        if (state != 0) {
                            g.drawString(Integer.toString(state), i * 30 + 10, j * 30 + 22);
                        }
                    }

                } else {
                    int state = ms.getTile(j, i).currentDisplayState();
                    if (state == -3) {
                        g.drawImage(Images.getBlank(), i * 30, j * 30, 30, 30, null);
                    } else if (state == -2) {
                        g.drawImage(Images.getFlag(), i * 30, j * 30, 30, 30, null);
                    } else if (state == -1) {
                        g.setColor(Color.RED);
                        g.fillRect(i * 30, j * 30, 30, 30);
                        g.drawImage(Images.getBomb(), i * 30, j * 30, 30, 30, null);
                        g.setColor(Color.BLACK);
                    } else if (state == 0) {
                        g.drawImage(Images.getEmpty(), i * 30, j * 30, 30, 30, null);
                    } else {
                        int differential = -2;
                        if (state == 1) {
                            g.setColor(Color.BLUE);
                            differential = 2;
                        } else if (state == 2) {
                            g.setColor(Color.GREEN);
                        } else if (state == 3) {
                            g.setColor(Color.RED);
                        } else if (state == 4) {
                            g.setColor(Color.ORANGE);
                        } else if (state == 5) {
                            g.setColor(Color.MAGENTA);
                        } else if (state == 6) {
                            g.setColor(Color.cyan);
                        } else if (state == 7) {
                            g.setColor(Color.pink);
                        } else if (state == 8) {
                            g.setColor(Color.yellow);
                        }
                        g.drawImage(Images.getEmpty(), i * 30, j * 30, 30, 30, null);
                        g.drawString(Integer.toString(state), i * 30 + 9 + differential, j * 30 + 23);
                        g.setColor(Color.black);
                    }
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
