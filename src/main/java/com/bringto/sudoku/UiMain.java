package com.bringto.sudoku;

import com.bringto.sudoku.ui.custom.screen.MainScreen;

import java.util.HashMap;
import java.util.Map;

public class UiMain {

    public static void main(String[] args) {

        Map<String, String> gameConfig = new HashMap<>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String key = row + "," + col;
                String value = getValueFromArgs(args, key);
                gameConfig.put(key, value);
            }
        }

        MainScreen mainScreen = new MainScreen(gameConfig);
        mainScreen.buildMainScreen();
    }

    private static String getValueFromArgs(String[] args, String key) {
        for (String arg : args) {
            String[] parts = arg.split(";");
            if (parts.length == 2 && parts[0].equals(key)) {
                return parts[1];
            }
        }
        return "0,false";
    }
}