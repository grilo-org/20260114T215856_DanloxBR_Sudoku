package com.bringto.sudoku.service;

import com.bringto.sudoku.model.Board;
import com.bringto.sudoku.model.Space;

import java.util.ArrayList;
import java.util.List;

public class BoardFactory {

    public static Board createRandomBoard() {

        int[][] solution = SudokuGenerator.generateSolvedBoard();
        boolean[][] fixedMask = DifficultyMask.generate(40);

        List<List<Space>> spaces = new ArrayList<>();

        for (int row = 0; row < 9; row++) {
            List<Space> line = new ArrayList<>();
            for (int col = 0; col < 9; col++) {

                boolean fixed = fixedMask[row][col];
                int expected = solution[row][col];

                Space space = new Space(
                        expected,
                        fixed,
                        row,
                        col
                );

                if (!fixed) {
                    space.clearSpace();
                }

                line.add(space);
            }
            spaces.add(line);
        }

        return new Board(spaces);
    }
}
