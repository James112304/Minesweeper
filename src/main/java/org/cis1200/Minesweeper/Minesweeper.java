package org.cis1200.Minesweeper;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Minesweeper {

    private Tile[][] board;

    private boolean[][] revealedBoard;
    private boolean[][] flagBoard;
    private final int numMines;
    private final int width;
    private final int height;

    private boolean initialized;

    private int protectedX;
    private int protectedY;
    private boolean gameOver;

    private int numFlags;

    // constructs minesweeper
    public Minesweeper(int x, int y, int m) {
        width = x;
        height = y;
        numMines = m;
        numFlags = 0;
        initialized = false;
        System.out.println("height is " + height + " width is " + width);
        reset();
    }

    public Minesweeper(String b) {
        System.out.println("new");
        System.out.println(b);
        String[] lines = b.split("\n");
        int numMines1 = 0;
        numFlags = 0;
        width = 16;
        height = 30;
        board = new Tile[height][width];
        revealedBoard = new boolean[height][width];
        flagBoard = new boolean[height][width];
        gameOver = true;
        boolean allEmpty = true;

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 16; j++) {
                board[i][j] = new Tile(false);
            }
        }

        for (int j = 0; j < 30; j++) {
            String[] chars = lines[j].split("");
            for (int i = 0; i < 16; i++) {
                switch (chars[i]) {
                    case "C" -> {numMines1++; placeMine(i, j); flagBoard[j][i] = true; allEmpty = false;}
                    case "X" -> {
                        numMines1++;
                        placeMine(i, j);
                        allEmpty = false;
                    }
                    case "R" -> {revealedBoard[j][i] = true; allEmpty = false;}
                    case "F" -> {flagBoard[j][i] = true; allEmpty = false;}
                    default -> gameOver = false;
                }
                System.out.println("(" + i + ", " + j + ") - " + chars[i]);
            }
        }

        if (allEmpty) {
            numMines = 99;
            reset();
        } else {
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 30; j++) {
                    if (revealedBoard[j][i]) {
                        reveal(i, j);
                    }
                }
            }
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 30; j++) {
                    if (flagBoard[j][i]) {
                        changeFlag(i, j);
                        numFlags++;;
                    }
                }
            }
            returnBoard();
            numMines = numMines1;
        }

    }

    public int getNumFlags () {
        return numFlags;
    }

    private void updateNeighbors(int xcoord, int ycoord) {
        for (int i = xcoord - 1; i < xcoord + 2; i++) {
            for (int j = ycoord - 1; j < ycoord + 2; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height) {
                    board[j][i].incrementMines();
                }
            }
        }
    }

    public void placeMine() {
        boolean placed = false;
        while (!placed) {
            int xcoord = (int) (Math.random() * width);
            int ycoord = (int) (Math.random() * height);
            boolean proceed = true;
            System.out.println("attempting mine placement at" + xcoord + ", " + ycoord);
            for (int i = protectedX - 1; i < protectedX + 2; i++) {
                for (int j = protectedY - 1; j < protectedY + 2; j++) {
                    if (xcoord == i && ycoord == j) {
                        proceed = false;
                        System.out.println("Can't place mine at " + xcoord + " " + ycoord);
                        break;
                    }
                }
            }
            if (!(board[ycoord][xcoord].checkMine()) && proceed) {
                board[ycoord][xcoord] = new Tile(true);
                updateNeighbors(xcoord, ycoord);
                placed = true;
            }
        }
    }

    // use for testing, in real game will be randomized
    public void placeMine(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            board[y][x] = new Tile(true);
            updateNeighbors(x, y);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void initialize(int x, int y) {
        protectedX = x;
        protectedY = y;
        for (int i = 0; i < numMines; i++) {
            placeMine();
        }
        reveal(x, y);
    }

    public void reset() {
        initialized = false;
        gameOver = false;
        board = new Tile[height][width];
        revealedBoard = new boolean[height][width];
        flagBoard = new boolean[height][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[j][i] = new Tile(false);
            }
        }
    }

    private void printRevealedBoard() {
        for (int j = 0; j < height; j++) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < width; i++) {
                if (board[j][i].checkMine()) {
                    s.append("X");
                } else {
                    s.append(board[j][i].getAdjacentMines());
                }
                s.append(" ");
            }
            System.out.println(s.toString());
        }
    }

    public String returnBoard() {
        System.out.println("ran");
        StringBuilder s = new StringBuilder();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (gameOver) {
                    if (board[j][i].checkMine()) {
                        s.append("X");
                    } else {
                        s.append("R");
                    }
                } else {
                    if (board[j][i].isRevealed()) {
                        s.append("R");
                    } else if (board[j][i].isFlagged() && board[j][i].checkMine()) {
                        s.append("C");
                    } else if (board[j][i].isFlagged()) {
                        s.append("F");
                    } else if (board[j][i].checkMine()) {
                        s.append("X");
                    } else {
                        s.append("0");
                    }
                }
            }
            s.append("\n");
        }
        System.out.println(s);
        return s.toString();
    }

    private void printCurrentBoard() {
        for (int j = 0; j < height; j++) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < width; i++) {
                s.append(board[j][i].currentDisplayState());
                s.append(" ");
            }
            System.out.println(s.toString());
        }
    }

    // returns -1 if mine or if game over
    public int reveal(int x, int y) {
        if (!initialized) {
            initialized = true;
            initialize(x, y);
            return -3;
        } else if (!gameOver && !board[y][x].isFlagged()) {
            int result = board[y][x].clicked();
            revealedBoard[y][x] = true;
            if (result == -1) {
                gameOver = true;
            } else if (result == 0) {
                revealAdjacent(x, y);
            }
            return result;
        } else if (gameOver) {
            return -4;
        } else if (board[y][x].isFlagged()) {
            return -2;
        }
        return 0;
    }

    private void revealAdjacent(int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (i >= 0 && i < width && j >= 0 && j < height && !revealedBoard[j][i]) {
                    int result = board[j][i].clicked();
                    if (result != -2) {
                        revealedBoard[j][i] = true;
                        if (result == -1) {
                            throw new Error("Method shouldn't have been called");
                        } else if (result == 0) {
                            revealAdjacent(i, j);
                        }
                    }
                }
            }
        }
    }

    public void changeFlag(int x, int y) {
        flagBoard[y][x] = !flagBoard[y][x];
        board[y][x].flag();
    }

    public boolean isFlagged(int x, int y) {
        return flagBoard[y][x];
    }

    public Tile getTile(int x, int y) {
        return board[y][x];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean playerWon() {
        boolean result = true;
        for (Tile[] tileR : board) {
            for (Tile tile : tileR) {
                if (!tile.checkMine() && !tile.isRevealed()) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Minesweeper m = new Minesweeper(3, 5, 2);
        m.initialize(0, 0);
        m.printRevealedBoard();
        m.returnBoard();
    }
}
