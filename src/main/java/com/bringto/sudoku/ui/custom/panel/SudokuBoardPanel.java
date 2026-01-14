package com.bringto.sudoku.ui.custom.panel;

import com.bringto.sudoku.model.Board;
import com.bringto.sudoku.model.Space;
import com.bringto.sudoku.service.BoardService;
import com.bringto.sudoku.ui.custom.input.NumberText;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/* jogo */
public class SudokuBoardPanel extends JPanel {

    private final BoardService boardService;

    public SudokuBoardPanel(BoardService boardService) {
        this.boardService = boardService;
        Board board = boardService.getBoard();

        setLayout(new GridLayout(3, 3, 3, 3));
        buildBoard(board);
    }

    private void buildBoard(Board board) {
        List<List<Space>> grid = board.getSpaces();

        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {

                JPanel block = new JPanel(new GridLayout(3, 3));
                block.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                for (int row = blockRow * 3; row < blockRow * 3 + 3; row++) {
                    for (int col = blockCol * 3; col < blockCol * 3 + 3; col++) {

                        Space space = grid.get(row).get(col);

                        NumberText txt = new NumberText(space, boardService);

                        block.add(txt);
                    }
                }

                add(block);
            }
        }
    }
}
