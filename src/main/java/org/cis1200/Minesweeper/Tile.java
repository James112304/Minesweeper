package org.cis1200.Minesweeper;

public class Tile {
    private boolean isMine = false;
    private int adjacentMines = 0;
    private boolean flagged = false;
    private boolean revealed = false;

    public Tile(boolean mine) {
        adjacentMines = 0;
        isMine = mine;
    }

    public boolean checkMine() {
        return isMine;
    };

    public void flag() {
        flagged = !flagged;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public int clicked() {
        if (!flagged && !revealed) {
            revealed = true;
            if (isMine) {
                return -1;
            } else {
                return adjacentMines;
            }
        }
        return -2;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public int trueStatus() {
        if (isMine) {
            return -1;
        } else {
            return adjacentMines;
        }
    }

    public int currentDisplayState() {
        if (!revealed) {
            if (flagged) {
                return -2;
            } else {
                return -3;
            }
        } else {
            if (isMine) {
                return -1;
            } else {
                return adjacentMines;
            }
        }
    }

    public void incrementMines() {
        adjacentMines++;
    }

}
