package org.cis1200.minesweeper;

import org.cis1200.Minesweeper.Minesweeper;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    public void testPlaceMineCenter3x3() {
        Minesweeper ms = new Minesweeper(3, 3, 0);
        ms.placeMine(1, 1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 1 && j == 1) {
                    assertTrue(ms.getTile(i, j).checkMine());
                } else {
                    assertEquals(1, ms.getTile(i, j).getAdjacentMines());
                }
            }
        }
    }

    @Test
    public void testPlaceTwoMines3x3() {
        Minesweeper ms = new Minesweeper(3, 3, 0);
        ms.placeMine(0, 0);
        ms.placeMine(1, 0);
        assertEquals(2, ms.getTile(0, 1).getAdjacentMines());
        assertEquals(2, ms.getTile(1, 1).getAdjacentMines());
        assertEquals(1, ms.getTile(2, 1).getAdjacentMines());
        assertEquals(1, ms.getTile(2, 0).getAdjacentMines());
        assertEquals(0, ms.getTile(0, 2).getAdjacentMines());
        assertEquals(0, ms.getTile(1, 2).getAdjacentMines());
        assertEquals(0, ms.getTile(2, 2).getAdjacentMines());
    }

    @Test
    public void initializeTest() {
        Minesweeper ms = new Minesweeper(3, 3, 5);
        ms.reveal(0,0);
        assertTrue(ms.getTile(2, 0).checkMine());
        assertTrue(ms.getTile(2, 1).checkMine());
        assertTrue(ms.getTile(2, 2).checkMine());
        assertTrue(ms.getTile(1, 2).checkMine());
        assertTrue(ms.getTile(0, 2).checkMine());
    }

    //edge case!!!!
    @Test
    public void noRevealAfterOver() {
        Minesweeper ms = new Minesweeper(3, 3, 5);
        ms.reveal(0,0);
        assertEquals(-1, ms.reveal(0, 2));
        assertEquals(-4, ms.reveal(1, 1));
    }

    //edge case!!
    @Test
    public void placeMineOutside() {
        Minesweeper ms = new Minesweeper(3, 3, 0);
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> ms.placeMine(3, 0));
    }

}
