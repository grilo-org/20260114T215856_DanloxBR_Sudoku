    package com.bringto.sudoku.ui.custom.screen;

    import com.bringto.sudoku.model.Space;
    import com.bringto.sudoku.service.*;
    import com.bringto.sudoku.service.EventListener;
    import com.bringto.sudoku.ui.custom.frame.MainFrame;
    import com.bringto.sudoku.ui.custom.input.NumberText;
    import com.bringto.sudoku.ui.custom.panel.*;

    import javax.swing.*;
    import java.awt.*;
    import java.util.*;
    import java.util.List;
    import java.util.stream.Collectors;

    public class MainScreen implements EventListener {

        private static final Dimension dimension = new Dimension(600, 600);

        private final BoardService boardService = new BoardService();
        private final SudokuLogPanel logPanel = new SudokuLogPanel();
        private final List<NumberText> allFields = new ArrayList<>();
        private final Map<String, String> gameConfig;

        public MainScreen(final Map<String, String> gameConfig) {
            this.gameConfig = gameConfig;

            NotifierService notifierService = NotifierService.getInstance();
            notifierService.subscribe(EventEnum.CHANGE_VALUE, this);
        }

        public void buildMainScreen() {
            JFrame mainFrame = new MainFrame(gameConfig);

            JPanel boardPanel = new MainPanel(dimension);
            boardPanel.setLayout(new GridLayout(3, 3, 4, 4));

            for (int r = 0; r < 9; r += 3) {
                int endRow = r + 2;
                for (int c = 0; c < 9; c += 3) {
                    int endCol = c + 2;
                    List<Space> sectorSpaces = getSpacesFromSector(boardService.getBoard().getSpaces(), c, endCol, r, endRow);
                    JPanel sector = generateSection(sectorSpaces);
                    boardPanel.add(sector);
                }
            }

            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.add(boardPanel, BorderLayout.CENTER);

            JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, logPanel);
            split.setDividerLocation(450);
            split.setResizeWeight(0.8);

            mainFrame.setContentPane(split);
            mainFrame.revalidate();
            mainFrame.repaint();
            mainFrame.setVisible(true);
        }

        private List<Space> getSpacesFromSector(List<List<Space>> spaces,
                                                int initCol, int endCol,
                                                int initRow, int endRow) {
            List<Space> sector = new ArrayList<>();
            for (int r = initRow; r <= endRow; r++) {
                for (int c = initCol; c <= endCol; c++) {
                    sector.add(spaces.get(r).get(c));
                }
            }
            return sector;
        }

        private JPanel generateSection(List<Space> spaces) {
            List<NumberText> fields = spaces.stream()
                    .map(space -> new NumberText(space, boardService))
                    .collect(Collectors.toList());

            allFields.addAll(fields);
            return new SudokuSector(fields);
        }

        @Override
        public void update(EventEnum eventType, EventData data) {
            if (eventType == EventEnum.CHANGE_VALUE) {

                boolean allCorrect = true;

                for (NumberText field : allFields) {
                    if (!field.getSpace().isFixed() && field.getSpace().getActual() != null) {
                        if (field.getSpace().getActual().equals(field.getSpace().getExpected())) {
                        } else {
                            allCorrect = false;
                        }
                    } else if (!field.getSpace().isFixed() && field.getSpace().getActual() == null) {
                        allCorrect = false;
                    }
                }

                if (allCorrect) {
                    SwingUtilities.invokeLater(() -> {
                        NotifierService.getInstance().notify(EventEnum.GAME_COMPLETE, null);
                        JOptionPane.showMessageDialog(null, "Parabéns! Você completou o jogo.");
                    });
                }
            }
        }
    }


