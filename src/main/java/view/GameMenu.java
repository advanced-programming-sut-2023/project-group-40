package view;

import controller.GameMenuController;
import controller.MainController;
import org.apache.commons.lang3.StringUtils;
import view.enums.Commands;

import java.io.IOException;

import java.util.regex.Matcher;

public class GameMenu {
    private static boolean gameStarted = false;

    public static void run() throws ReflectiveOperationException {
        if (!gameStarted) System.out.println(EnvironmentMenu.run());
        System.out.println("you are in game menu!");
        if (GameMenuController.getCurrentGovernment() != GameMenuController.getOnGovernment()) {
            System.out.println("It is not your turn");
            return;
        }
        while (true) {
            String command = MainController.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, GameMenu.class));
        }
    }

    public static String showMap(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return null;
    }

    public static String showPopularityFactors(Matcher matcher) {
        return GameMenuController.showPopularityFactors();
    }

    public static String showPopularity(Matcher matcher) {
        return GameMenuController.showPopularity();
    }

    public static String showFoodList(Matcher matcher) {
        return GameMenuController.showFoodList();
    }

    public static String setFoodRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));
        return GameMenuController.setFoodRate(rateNumber);
    }

    public static String showFoodRate(Matcher matcher) {
        return GameMenuController.showFoodRate();
    }

    public static String setTaxRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));
        return GameMenuController.setTaxRate(rateNumber);
    }

    public static String showTaxRate(Matcher matcher) {
        return GameMenuController.showTaxRate();
    }

    public static String setFearRate(Matcher matcher) {
        int rateNumber = Integer.parseInt(matcher.group("rateNumber"));
        return GameMenuController.setFearRate(rateNumber);
    }

    public static String showFearRate(Matcher matcher) {
        return GameMenuController.showFearRate();
    }

    public static String dropBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        return GameMenuController.dropBuilding(x, y, type);
    }

    public static String selectBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.selectBuilding(x, y);
    }

    public static String createUnit(Matcher matcher) {
        String type = matcher.group("type");
        int count = Integer.parseInt(matcher.group("count"));
        return GameMenuController.createUnit(type, count);
    }

    public static String repair(Matcher matcher) {
        return null;
    }

    public static String selectUnit(Matcher matcher) {
        return null;
    }

    public static String moveUnit(Matcher matcher) {
        return null;
    }

    public static String setUnitState(Matcher matcher) {
        return null;
    }

    public static String attackEnemy(Matcher matcher) {
        return null;
    }

    public static String airAttack(Matcher matcher) {
        return null;
    }

    public static String pourOil(Matcher matcher) {
        return null;
    }

    public static String digTunnel(Matcher matcher) {
        return null;
    }

    public static String buildEquipments(Matcher matcher) {
        return null;
    }

    public static String disbandUnit(Matcher matcher) {
        return null;
    }

    public static String dropUnit(Matcher matcher) {
        return null;
    }

    public static String enterTrade(Matcher matcher) {
        return null;
    }

    public static String nextTurn(Matcher matcher) {
        GameMenuController.setOnGovernment();
        GameMenuController.checkPopulation();
        return null;
    }

    public static void setGameStarted(boolean b) {
        GameMenu.gameStarted = b;
    }
}
