package view;

import controller.GameMenuController;
import controller.ShopMenuController;
import controller.TradeMenuController;
import view.enums.Commands;

import java.io.IOException;

import java.util.regex.Matcher;

public class GameMenu {
    private static boolean gameStarted = false;

    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in game menu!");
        if (!gameStarted) {
            System.out.println(EnvironmentMenu.run());
            return;
        }
        if (GameMenuController.getCurrentGovernment() != GameMenuController.getOnGovernment()) {
            System.out.println("It is not your turn");
            return;
        }
        while (true) {
            String command = Commands.scanner.nextLine();
            String result = Commands.regexFinder(command, GameMenu.class);
            if (result != null) System.out.println(result);
        }
    }

    public static String enterMapMenu(Matcher matcher) throws ReflectiveOperationException {
        MapMenu.run();
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

    public static String selectBuilding(Matcher matcher) throws ReflectiveOperationException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.selectBuilding(x, y);
    }

    public static String createUnit(Matcher matcher) {
        String type = Commands.eraseQuot(matcher.group("type"));
        int count = Integer.parseInt(matcher.group("count"));
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.createUnit(x,y,type, count);
    }
    public static String trade(Matcher matcher) throws ReflectiveOperationException {
        TradeMenuController.setCurrentGovernment(GameMenuController.getCurrentGovernment());
        TradeMenu.run();
        return null;
    }

    public static String shop(Matcher matcher) throws ReflectiveOperationException {
        ShopMenuController.setCurrentGovernment(GameMenuController.getCurrentGovernment());
        ShopMenu.run();
        return null;
    }

    public static String repair(Matcher matcher) {
        return null;
    }

    public static String selectUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.selectUnit(x,y);
    }

    public static String moveUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.moveUnit(x,y);
    }

    public static String patrolUnit(Matcher matcher) {
        int x1 = Integer.parseInt(matcher.group("x"));
        int y1 = Integer.parseInt(matcher.group("y"));
        int x2 = Integer.parseInt(matcher.group("x"));
        int y2 = Integer.parseInt(matcher.group("y"));
        return GameMenuController.patrolUnit(x1,y1,x2,y2);
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
        String equipmentName = Commands.eraseQuot(matcher.group("equipmentName"));
        return GameMenuController.buildEquipments(equipmentName);
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

    public static String dropWall(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        int thickness = Integer.parseInt(matcher.group("thickness"));
        int height = Integer.parseInt(matcher.group("height"));
        String direction = matcher.group("direction");
        return GameMenuController.dropWall(x,y,thickness,height,direction);
    }

    public static String dropTower(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.dropTower(x,y);
    }

    public static String dropTurret(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.dropTurret(x,y);
    }

    public static String startDiggingDitch(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.startDiggingDitch(x,y);
    }
    public static String stopDiggingDitch(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.stopDiggingDitch(x,y);
    }

    public static String deleteDitch(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.deleteDitch(x,y);
    };
    public static String captureTheGate(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.captureTheGate(x,y);
    };
    public static String nextTurn(Matcher matcher) {
        if (GameMenuController.isLastGovernment()){
            GameMenuController.foodVarietyAction();
            GameMenuController.runBuildings();
            GameMenuController.oxTetherAction();
            GameMenuController.checkPopulation();
            GameMenuController.StableAction();
        }
        GameMenuController.diggingDitch();
        GameMenuController.handleAttacks();
        GameMenuController.setOnGovernment();
        GameMenuController.setDefaults();
        return null;
    }

    public static void setGameStarted(boolean b) {
        GameMenu.gameStarted = b;
    }
}
