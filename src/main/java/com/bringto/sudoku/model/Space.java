package com.bringto.sudoku.model;

public class Space {

    private Integer actual;
    private final int expected;
    private final boolean fixed;
    private final int row;
    private final int col;

    private CellStatus status;

    public Space(int expected, boolean fixed, int row, int col) {
        this.expected = expected;
        this.fixed = fixed;
        this.row = row;
        this.col = col;

        if (fixed) {
            lock();
        }
    }

    private void lock() {
        this.actual = expected;
        this.status = CellStatus.CORRECT;
    }

    public void clearSpace() {
        if (fixed) return;
        this.actual = null;
        this.status = null;
    }

    public void setActual(Integer actual) {
        if (fixed) return;
        this.actual = actual;
    }

    public boolean isFixed() {
        return fixed;
    }

    public Integer getActual() {
        return actual;
    }

    public int getExpected() {
        return expected;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }
}
