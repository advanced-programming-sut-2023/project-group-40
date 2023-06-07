package view;

import controller.GameMenuController;
import controller.ShopMenuController;
import controller.TradeMenuController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class GameMenu {
    private static boolean gameStarted = false;

    public static void setGameStarted(boolean b) {
        GameMenu.gameStarted = b;
    }

    public static int[] getCoordinate() {
        System.out.println("type your coordinate :");
        int[] coordinates = new int[2];
        coordinates[0] = Commands.scanner.nextInt();
        coordinates[1] = Commands.scanner.nextInt();
        return coordinates;
    }

}
