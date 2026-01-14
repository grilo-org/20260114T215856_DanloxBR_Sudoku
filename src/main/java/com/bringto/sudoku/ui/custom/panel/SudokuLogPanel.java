package com.bringto.sudoku.ui.custom.panel;

import com.bringto.sudoku.service.EventData;
import com.bringto.sudoku.service.EventEnum;
import com.bringto.sudoku.service.EventListener;
import com.bringto.sudoku.service.NotifierService;

import javax.swing.*;
import java.awt.*;

public class SudokuLogPanel extends JPanel implements EventListener {

    private final JTextArea logArea = new JTextArea();

    public SudokuLogPanel() {
        setLayout(new BorderLayout());

        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);

        add(new JScrollPane(logArea), BorderLayout.CENTER);

        NotifierService.getInstance()
                .subscribe(EventEnum.LOG, this);
    }

    @Override
    public void update(EventEnum eventEnum, EventData eventData) {
        if (eventEnum != EventEnum.LOG) return;

        SwingUtilities.invokeLater(() -> {
            logArea.append(String.valueOf(eventData.getPayload()));
            logArea.append("\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
}
