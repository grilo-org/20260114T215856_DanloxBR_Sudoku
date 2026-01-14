package com.bringto.sudoku.model;


import java.util.List;

public class Board {

    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public Space getSpace(int row, int col) {
        return spaces.get(row).get(col);
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }
}
