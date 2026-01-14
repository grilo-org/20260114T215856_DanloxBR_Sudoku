package com.bringto.sudoku.service;

import com.bringto.sudoku.model.Board;
import com.bringto.sudoku.model.Space;
import com.bringto.sudoku.model.CellStatus;

/* Controla os valores do tabuleiro */
public class BoardService {

    private final Board board;

    public BoardService() {
        this.board = BoardFactory.createRandomBoard();
    }

    public Board getBoard() {
        return board;
    }

    public CellStatus validate(int row, int col, int value) {

        Space space = board.getSpace(row, col);

        if (value == space.getExpected()) {
            return CellStatus.CORRECT;
        }

        return isValidMove(row, col, value)
                ? CellStatus.POSSIBLE
                : CellStatus.WRONG;
    }

    private boolean isValidMove(int row, int col, int value) {

        for (int c = 0; c < 9; c++) {
            if (c == col) continue;

            Integer actual = board.getSpace(row, c).getActual();
            if (actual != null && actual.equals(value)) {
                return false;
            }
        }

        for (int r = 0; r < 9; r++) {
            if (r == row) continue;

            Integer actual = board.getSpace(r, col).getActual();
            if (actual != null && actual.equals(value)) {
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (r == row && c == col) continue;

                Integer actual = board.getSpace(r, c).getActual();
                if (actual != null && actual.equals(value)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isGameFinished() {

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {

                Space space = board.getSpace(r, c);

                if (space.getActual() == null) {
                    return false;
                }

                if (!space.getActual().equals(space.getExpected())) {
                    return false;
                }
            }
        }

        return true;
    }
}
