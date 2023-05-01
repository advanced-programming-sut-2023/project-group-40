package view;

import controller.GameMenuController;
import controller.MainController;
import model.Game;
import org.apache.commons.lang3.StringUtils;
import view.enums.Commands;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in game menu!");
        System.out.println("choose your size for map: ");
        GameMenuController.setMapSize(Integer.parseInt(MainController.scanner.nextLine()));
        while (true) {
            String command = MainController.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, GameMenu.class));
        }
    }

    public static String showMap(Matcher matcher) throws IOException {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.showMap(x,y);
    }

    public static String changeSightArea(Matcher matcher) {
        //left in another first
        while (matcher.find()) {
            int leftNumber = StringUtils.isNotBlank(matcher.group("leftNumber")) ? Integer.parseInt(matcher.group("leftNumber")) : 1;
            int topNumber = StringUtils.isNotBlank(matcher.group("topNumber")) ? Integer.parseInt(matcher.group("topNumber")) : 1;
            int rightNumber = StringUtils.isNotBlank(matcher.group("rightNumber")) ? Integer.parseInt(matcher.group("rightNumber")) : 1;
            int downNumber = StringUtils.isNotBlank(matcher.group("downNumber")) ? Integer.parseInt(matcher.group("downNumber")) : 1;
            if (matcher.group("left") != null)
                GameMenuController.increaseX(-1 * leftNumber);
            if (matcher.group("top") != null)
                GameMenuController.increaseY(topNumber);
            if (matcher.group("right") != null)
                GameMenuController.increaseX(rightNumber);
            if (matcher.group("down") != null)
                GameMenuController.increaseY(-1 * downNumber);
        }
        return "sight area changed!";
    }

    public static String showDetails(Matcher matcher) {
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
        return GameMenuController.dropBuilding(x,y,type);
    }

    public static String selectBuilding(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return GameMenuController.selectBuilding(x,y);
    }

    public static String createUnit(Matcher matcher) {
        return null;
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

    public static String setTexture(Matcher matcher) {
        return null;
    }

    public static String clearBlock(Matcher matcher){
        return null;
    }

    public static String dropBlock(Matcher matcher){
        return null;
    }

    public static String dropTree(Matcher matcher){
        return null;
    }

    public static String dropUnit(Matcher matcher){
        return null;
    }

    public static String enterTrade(Matcher matcher){
        return null;
    }

    public static String nextTurn(Matcher matcher){
        return null;
    }
}
