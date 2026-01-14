package com.bringto.sudoku.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SudokuGenerator {

    private static final int SIZE = 9;
    private static final int[][] board = new int[SIZE][SIZE];

    public static int[][] generateSolvedBoard() {
        fillBoard(0, 0);
        return board;
    }

    private static boolean fillBoard(int row, int col) {
        if (row == SIZE) return true;

        int nextRow = (col == 8) ? row + 1 : row;
        int nextCol = (col + 1) % 9;

        List<Integer> numbers = IntStream.rangeClosed(1, 9)
                .boxed()
                .collect(Collectors.toList());

        Collections.shuffle(numbers);

        for (int number : numbers) {
            if (canPlace(row, col, number)) {
                board[row][col] = number;
                if (fillBoard(nextRow, nextCol)) return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    private static boolean canPlace(int row, int col, int num) {
        for (int i = 0; i < 9; i++)
            if (board[row][i] == num || board[i][col] == num)
                return false;

        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;

        for (int r = boxRow; r < boxRow + 3; r++)
            for (int c = boxCol; c < boxCol + 3; c++)
                if (board[r][c] == num)
                    return false;

        return true;
    }
}
