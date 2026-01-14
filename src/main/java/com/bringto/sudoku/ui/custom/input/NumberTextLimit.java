package com.bringto.sudoku.ui.custom.input;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class NumberTextLimit extends PlainDocument {

    private static final List<String> NUMBERS;
    static {
        NUMBERS = new ArrayList<>();
        NUMBERS.add("1");
        NUMBERS.add("2");
        NUMBERS.add("3");
        NUMBERS.add("4");
        NUMBERS.add("5");
        NUMBERS.add("6");
        NUMBERS.add("7");
        NUMBERS.add("8");
        NUMBERS.add("9");
    }


    @Override
    public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
        if (isNull(str) || (!NUMBERS.contains(str))) return;

        if (getLength() + str.length() <= 1){
            super.insertString(offs, str, a);
        }
    }
}
