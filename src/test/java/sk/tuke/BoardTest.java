package sk.tuke;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.game.core.Board;
import sk.tuke.gamestudio.game.core.Symbol;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static sk.tuke.gamestudio.game.core.Board.COLUMNS;
import static sk.tuke.gamestudio.game.core.Board.ROWS;

public class BoardTest {

    @Test
    public void testCheckBoardWhenNeedToMerge() {
        Board board = new Board();
        board.initializeBoard();
        board.putSymbol(Symbol.FOUR,0,0);
        board.putSymbol(Symbol.FOUR,1,1);
        board.putSymbol(Symbol.FOUR,1,2);
        board.putSymbol(Symbol.FOUR,1,0);
        assertEquals(16,board.checkBoard());
    }

    @Test
    public void testCheckTwoFreeCellsExists() {
        Board board = new Board();
        board.initializeBoard();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board.putSymbol(Symbol.FOUR, row, col);
            }
        }
        board.putSymbol(Symbol.EMPTY, 0, 2);
        board.putSymbol(Symbol.EMPTY, 0, 3);

        assertEquals(true,board.checkTwoFreeCellsExists());
    }

    @Test
    public void testCheckBoardWhenNoNeedToMerge() {
        Board board = new Board();
        board.initializeBoard();
        board.putSymbol(Symbol.FOUR,0,0);
        board.putSymbol(Symbol.FOUR,1,1);
        board.putSymbol(Symbol.FOUR,1,2);
        assertEquals(0,board.checkBoard());
    }

    @Test
    public void testHasAtLeastOneFreeCell() {
        Board board = new Board();
        board.initializeBoard();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                board.putSymbol(Symbol.FOUR, row, col);
            }
        }
        board.putSymbol(Symbol.EMPTY, 0, 2);

        assertEquals(true,board.hasAtLeastOneFreeCell());
    }

}