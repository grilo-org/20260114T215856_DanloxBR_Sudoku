package com.bringto.sudoku.ui.custom.frame;

import com.bringto.sudoku.service.BoardService;
import com.bringto.sudoku.service.EventData;
import com.bringto.sudoku.service.EventEnum;
import com.bringto.sudoku.service.NotifierService;
import com.bringto.sudoku.ui.custom.panel.SudokuBoardPanel;
import com.bringto.sudoku.ui.custom.panel.SudokuLogPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainFrame extends JFrame {

    private Timer autoSolveTimer;

    /* Layout do jogo */
    public MainFrame(Map<String, String> gameConfig) {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        BoardService boardService = new BoardService();

        SudokuBoardPanel boardPanel = new SudokuBoardPanel(boardService);
        SudokuLogPanel logPanel = new SudokuLogPanel();

        boardPanel.setPreferredSize(new Dimension(600, 600));
        logPanel.setPreferredSize(new Dimension(300, 600));

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                boardPanel,
                logPanel
        );

        split.setDividerLocation(600);
        split.setResizeWeight(1.0);
        split.setDividerSize(4);
        split.setContinuousLayout(true);
        split.setBorder(null);

        add(split, BorderLayout.CENTER);

        NotifierService.getInstance()
                .subscribe(EventEnum.GAME_FINISHED, this::onGameFinished);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        SwingUtilities.invokeLater(this::iniciarAutomatico);
    }

    private void iniciarAutomatico() {
        NotifierService.getInstance().notify(EventEnum.NEW_GAME, null);

        int velocidade = 150;

        autoSolveTimer = new Timer(velocidade, e ->
                NotifierService.getInstance().notify(EventEnum.SOLVE_STEP, null)
        );

        autoSolveTimer.start();
    }

    private void onGameFinished(EventEnum event, EventData data) {
        if (event != EventEnum.GAME_FINISHED) {
            return;
        }

        if (autoSolveTimer != null) {
            autoSolveTimer.stop();
        }

        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                        this,
                        "Jogo finalizado com sucesso!",
                        "Sudoku",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        dispose();
        System.exit(0);
    }
}
