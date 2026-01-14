package com.bringto.sudoku.ui.custom.input;

import com.bringto.sudoku.model.CellStatus;
import com.bringto.sudoku.model.Space;
import com.bringto.sudoku.service.BoardService;
import com.bringto.sudoku.service.EventData;
import com.bringto.sudoku.service.EventEnum;
import com.bringto.sudoku.service.NotifierService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class NumberText extends JTextField {

    private final Space space;
    private final BoardService boardService;

    public NumberText(Space space, BoardService boardService) {
        this.space = space;
        this.boardService = boardService;

        setHorizontalAlignment(JTextField.CENTER);
        setFont(new Font("Monospaced", Font.BOLD, 18));

        if (space.isFixed()) {
            setText(String.valueOf(space.getExpected()));
            setEditable(false);
            setForeground(Color.BLACK);
            setBackground(Color.LIGHT_GRAY);
        } else {
            setText(space.getActual() != null ? String.valueOf(space.getActual()) : "");
            setEditable(true);
            setForeground(Color.BLUE);
            setBackground(Color.WHITE);
        }

        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                notifyChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                notifyChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                notifyChange();
            }
        });

        NotifierService.getInstance()
                .subscribe(EventEnum.CELL_UPDATED, this::onCellUpdated);
    }

    public Space getSpace() {
        return space;
    }

    private void notifyChange() {
        String text = getText();

        if (text.isEmpty()) {
            space.setActual(null);

            NotifierService.getInstance().notify(
                    EventEnum.LOG,
                    new EventData(
                            EventEnum.LOG,
                            String.format(
                                    "Linha %d Coluna %d → apagado",
                                    space.getRow() + 1,
                                    space.getCol() + 1
                            )
                    )
            );

            NotifierService.getInstance().notify(
                    EventEnum.CELL_UPDATED,
                    new EventData(
                            EventEnum.CELL_UPDATED,
                            null,
                            null,
                            null,
                            space.getRow(),
                            space.getCol()
                    )
            );
            return;
        }

        try {
            int value = Integer.parseInt(text);

            if (value < 1 || value > 9) {
                SwingUtilities.invokeLater(() -> setText(""));
                return;
            }

            Integer oldValue = space.getActual();
            space.setActual(value);

            NotifierService.getInstance().notify(
                    EventEnum.CELL_UPDATED,
                    new EventData(
                            EventEnum.CELL_UPDATED,
                            null,
                            oldValue,
                            value,
                            space.getRow(),
                            space.getCol()
                    )
            );

            CellStatus status = boardService.validate(
                    space.getRow(),
                    space.getCol(),
                    value
            );

            NotifierService.getInstance().notify(
                    EventEnum.LOG,
                    new EventData(
                            EventEnum.LOG,
                            String.format(
                                    "Linha %d Coluna %d → %d (%s)",
                                    space.getRow() + 1,
                                    space.getCol() + 1,
                                    value,
                                    status.name()
                            )
                    )
            );

        } catch (NumberFormatException e) {
            SwingUtilities.invokeLater(() -> setText(""));
        }
    }

    private void onCellUpdated(EventEnum event, EventData data) {

        if (event != EventEnum.CELL_UPDATED) return;
        if (data.getRow() != space.getRow() || data.getCol() != space.getCol()) return;

        if (space.getActual() == null) {
            setBackground(Color.WHITE);
            return;
        }

        CellStatus status = boardService.validate(
                space.getRow(),
                space.getCol(),
                space.getActual()
        );

        switch (status) {
            case CORRECT:
                setBackground(new Color(0, 251, 0));
                break;
            case WRONG:
                setBackground(new Color(255, 0, 0));
                break;
            case POSSIBLE:
                setBackground(new Color(255, 255, 0));
                break;
        }

        if (boardService.isGameFinished()) {
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(
                            this,
                            " ----- Parabéns ----- ",
                            "Jogo Concluído com Sucesso!",
                            JOptionPane.INFORMATION_MESSAGE
                    )
            );

            NotifierService.getInstance().notify(
                    EventEnum.LOG,
                    new EventData(
                            EventEnum.LOG,
                            "🎉 Jogo concluído com sucesso!"
                    )
            );
        }
    }
}
