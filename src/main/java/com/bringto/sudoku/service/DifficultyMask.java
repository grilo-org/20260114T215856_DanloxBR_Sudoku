package com.bringto.sudoku.service;

import java.util.Random;

public class DifficultyMask {

    private static final int SIZE = 9;

    public static boolean[][] generate(int visible) {

        boolean[][] mask = new boolean[SIZE][SIZE];
        Random random = new Random();

        int count = 0;

        while (count < visible) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            if (!mask[row][col]) {
                mask[row][col] = true;
                count++;
            }
        }

        return mask;
    }
}
