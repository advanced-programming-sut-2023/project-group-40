package controller;

import model.*;
import model.buildings.Building;
import model.buildings.Buildings;
import model.buildings.EngineerGuild;
import model.buildings.Storage;
import model.troops.Troop;
import model.troops.Troops;
import org.apache.commons.text.RandomStringGenerator;
import view.*;
import model.buildings.Buildings;
import model.buildings.Storage;
import org.apache.commons.text.RandomStringGenerator;
import model.buildings.Buildings;
import model.buildings.Storage;
import org.apache.commons.text.RandomStringGenerator;
import view.GameMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.Scanner;
import java.io.IOException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.Scanner;

enum Direction {

}

public class GameMenuController {
    private static Government currentGovernment;
    private static Government onGovernment;
    private static Building selectedBuilding;
    private static Unit selectedUnit;


    public static void setMapSize(int size) {
        Map.initMap(size);
    }

    public static String showMap(int x, int y) throws ReflectiveOperationException {
        MapMenuController.setX(x);
        MapMenuController.setY(y);
        MapMenu.run();
        return null;
    }

    public static String showDetails(int x, int y) {
        String result = "";
        result += "Texture: " + Map.getMap()[x][y].getTexture().name().toLowerCase();
        result += "نوع منبع و مقدارش؟؟؟؟";
        if (Map.getMap()[x][y].getUnit() != null) {
            result += "troops type: " + Map.getMap()[x][y].getUnit().getTroops().get(0).getName();
            result += "troop count: "+ Map.getMap()[x][y].getUnit().getTroops().size();
        }
        if (Map.getMap()[x][y].getBuilding() != null) {
            result += "building: " + Map.getMap()[x][y].getBuilding().getName();
        }
        return result;
    }

    public static String showPopularityFactors() {
        return "Popularity Factors: \n 1.food \n 2.tax \n 3.religion \n 4.fear";
    }

    public static String showPopularity() {
        return "rate of your government popularity is " + currentGovernment.getPopularity();
    }

    public static String showFoodList() {
        return null;
    }

    public static String setFoodRate(int rate) {
        if (rate > 2 || rate < -2) return "rate-number is out of bound";
        currentGovernment.setFoodRate(rate);
        int numberOfFoods = currentGovernment.getAmountOfGood(Good.FOOD1);
        numberOfFoods += currentGovernment.getAmountOfGood(Good.FOOD2);
        numberOfFoods += currentGovernment.getAmountOfGood(Good.FOOD3);
        numberOfFoods += currentGovernment.getAmountOfGood(Good.FOOD4);
        switch (rate) {
            case -2 : currentGovernment.changePopularity(-8);
            case -1 : {
                if (currentGovernment.getPopulation() > 2 * numberOfFoods)
                    return "you haven't enough food!";
                currentGovernment.decreaseAmountOfFood(currentGovernment.getPopulation()/2);
                currentGovernment.changePopularity(-4);
            }
            case 0 : {
                if (currentGovernment.getPopulation() > numberOfFoods)
                    return "you haven't enough food!";
                currentGovernment.decreaseAmountOfFood(currentGovernment.getPopulation());
                currentGovernment.changePopularity(0);
            }
            case 1 : {
                if (1.5 * currentGovernment.getPopulation() > numberOfFoods)
                    return "you haven't enough food!";
                currentGovernment.decreaseAmountOfFood((int) (1.5 * currentGovernment.getPopulation()));
                currentGovernment.changePopularity(4);
            }
            case 2 : {
                if (2 * currentGovernment.getPopulation() > numberOfFoods)
                    return "you haven't enough food!";
                currentGovernment.decreaseAmountOfFood(2 * currentGovernment.getPopulation());
                currentGovernment.changePopularity(8);
            }
        };
        return "set rate-number is successful";
    }

    public static String showFoodRate() {
        return "your government food rate is: " + currentGovernment.getFoodRate();
    }

    public static String setTaxRate(int rate) {
        if (!selectedBuilding.getName().equals("Small stone gatehouse"))
            return "you don't select Small stone gatehouse";
        if (rate > 8 || rate < -3) return "rate-number is out of bound";
        if (currentGovernment.getAmountOfGood(Good.GOLD) == 0)
            return "you haven't any gold in your treasury";
        currentGovernment.setTaxRate(rate);
        int gold = currentGovernment.getAmountOfGood(Good.GOLD);
        int population = currentGovernment.getPopulation();
        switch (rate) {
            case -3 : {
                if (population > gold)
                    return "you haven't enough gold in your treasury";
                currentGovernment.decreaseAmountOfGood(Good.GOLD,population);
                currentGovernment.changePopularity(7);
            }
            case -2 : {
                if (0.8 * population > gold)
                    return "you haven't enough gold in your treasury";
                currentGovernment.decreaseAmountOfGood(Good.GOLD, (int) (0.8 * population));
                currentGovernment.changePopularity(5);
            }
            case -1 : {
                if (0.6 * population > gold)
                    return "you haven't enough gold in your treasury";
                currentGovernment.decreaseAmountOfGood(Good.GOLD, (int) (0.6 * population));
                currentGovernment.changePopularity(3);
            }
            case 0 : {
                currentGovernment.changePopularity(1);
            }
            case 1 : {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (0.6 * population));
                currentGovernment.changePopularity(-2);
            }
            case 2 : {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (0.8 * population));
                currentGovernment.changePopularity(-4);
            }
            case 3 : {
                currentGovernment.increaseAmountOfGood(Good.GOLD, population);
                currentGovernment.changePopularity(-6);
            }
            case 4 : {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (1.2 * population));
                currentGovernment.changePopularity(-8);
            }
            case 5 : {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (1.4 * population));
                currentGovernment.changePopularity(-12);
            }
            case 6 : {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (1.6 * population));
                currentGovernment.changePopularity(-16);
            }
            case 7 : {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (1.8 * population));
                currentGovernment.changePopularity(-20);
            }
            case 8 : {
                currentGovernment.increaseAmountOfGood(Good.GOLD, 2 * population);
                currentGovernment.changePopularity(-24);
            }
        };
        return "set rate-number is successful";
    }

    public static String showTaxRate() {
        return "your government tax rate is: " + currentGovernment.getTaxRate();
    }

    public static String setFearRate(int rate) {
        //increase randeman
        if (rate > 5 || rate < -5) return "rate-number is out of bound";
        currentGovernment.setFearRate(rate);
        for (int i = 0; i < Map.getSize(); i++)
            for (int j = 0; j < Map.getSize(); j++) {
                Unit unit = Map.getMap()[i][j].getUnit();
                if (unit != null && unit.getGovernment() == currentGovernment) for (Troop troop : unit.getTroops())
                    troop.changePower(rate);
            }
        return "set rate-number is successful";
    }

    public static String showFearRate() {
        return "your government feat rate is: " + currentGovernment.getFearRate();
    }

    public static boolean isCoordinateValid(int coordinate) {
        return coordinate > 0 && coordinate <= Map.getSize();
    }

    public static String dropBuilding(int x, int y, String type) {
        //جنس زمین
        if (Map.getMap()[x][y].getRock() != null)
            return "you can not drop building because there is a rock in this cell!";
        if (!isCoordinateValid(x) || !isCoordinateValid(y)) return "your coordinates is incorrect!";
        if (Map.getMap()[x][y].getBuilding() != null) return "There is already a building in your coordinates!";
        Building targetBuilding = Buildings.getBuildingObjectByType(type);
        if (targetBuilding == null) return "your building type is incorrect!";
        if (targetBuilding.checkTexture(Map.getMap()[x][y].getTexture()))
            return "you can not drop building to target cell!";
        currentGovernment.decreaseAmountOfGood(Good.GOLD, targetBuilding.getCost()[0]);
        currentGovernment.decreaseAmountOfGood(Good.WOOD, targetBuilding.getCost()[1]);
        currentGovernment.decreaseAmountOfGood(Good.STONE, targetBuilding.getCost()[2]);
        Map.getMap()[x][y].setBuilding(targetBuilding);
        Map.getMap()[x][y].setAvailable(false);
        currentGovernment.addBuilding(targetBuilding);
        return "building dropped to the target cell!";
    }

    public static String selectBuilding(int x, int y) {
        //do work after select
        if (!isCoordinateValid(x) || !isCoordinateValid(y))
            return "your coordinates is incorrect!";
        if (Map.getMap()[x][y].getBuilding() == null)
            return "There is no existing building in your coordinates!";
        selectedBuilding = Map.getMap()[x][y].getBuilding();
        if (selectedBuilding.getName().equals("shop"))
            ShopMenu.run();
        return "target building selected";
    }

    public static String createUnit(String type, int count) {
        //میتونن دو تا اسلحه داشته باشن
        //یگان مهندس؟؟؟
        if (count < 0) return "count is invalid";
        if (type.equals("engineer")) {
            if (selectedBuilding.getName().equals("engineer guild")) {
                EngineerGuild engineerGuild = (EngineerGuild) selectedBuilding;
                engineerGuild.increaseNumberOfEngineer(count);
            } else return "you can't create engineer in this building";
        }
        if (type.equals("LadderMan")) {
            if (selectedBuilding.getName().equals("engineer guild")) {
                EngineerGuild engineerGuild = (EngineerGuild) selectedBuilding;
                engineerGuild.increaseNumberOfLadderMan(count);
            } else return "you can't create LadderMan in this building";
        }
        Troop troop = Troops.getTroopObjectByType(type);
        if (troop == null) return "unit type is invalid";
        int goldForUnit = troop.getValue() * count;
        if (currentGovernment.getAmountOfGood(Good.GOLD) != goldForUnit)
            return "you don't have enough gold for create this unit";
        if (currentGovernment.getAmountOfGood(troop.getWeapon()) != count)
            return "you don't have enough weapon for create this unit";
        if (currentGovernment.getCastle().getPopulation() < count)
            return "you don't have enough population for create this unit";
        if (type.equals("european") && selectedBuilding.getName().equals("Mercenary Post"))
            return "you can't create european in Mercenary Post";
        if (type.equals("arabian") && selectedBuilding.getName().equals("barrack"))
            return "you can't create arabian in barrack";
        currentGovernment.getCastle().changePopulation(-1 * count);
        currentGovernment.decreaseAmountOfGood(Good.GOLD, count);
        currentGovernment.decreaseAmountOfGood(troop.getWeapon(), count);
        int x = selectedBuilding.getX();
        int y = selectedBuilding.getY();
        Unit unit = new Unit(currentGovernment, "standing", troop.getHp() * count);
        unit.addTroop(troop, count);
        Map.getMap()[x][y].setUnit(unit);
        return "you successfully create unit";
    }

    public static String repair() {
        return null;
    }

    public static String selectUnit(int x, int y) {
        return null;
    }

    public static String moveUnit(int x, int y) {
        return null;
    }

    public static String setUnitState(String state) {
        return null;
    }

    public static String attackEnemy(int x, int y) {
        return null;
    }

    public static String airAttack(int x, int y) {
        return null;
    }

    public static String pourOil(String direction) {
        return null;
    }

    public static String digTunnel(int x, int y) {
        return null;
    }

    public static String buildEquipments(String equipmentName) {
        return null;
    }

    public static String disbandUnit() {
        return null;
    }

    public static String dropUnit(int x, int y, String type, int count) {
        return null;
    }

    public static String enterTrade() {
        return null;
    }

    public static String nextTurn() {
        currentGovernment.changePopularity(currentGovernment.getNumberOfFoodVariety() - 1);
        return null;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(User user) {
        currentGovernment = new Government(user);
    }

    public static void chooseColor() {
        System.out.println("type your color :");
        while (true) {
            for (Color color : Color.values())
                if (color.getGovernment() == null) System.out.println(color.getColorName());
            String selectedColorName = MainController.scanner.nextLine();
            String message = Color.setOwnerOfColor(selectedColorName, currentGovernment);
            System.out.println(message);
            if (message.startsWith("you successfully")) return;
        }
    }

    public static void setOnGovernment() {
        if (onGovernment == null) {
            onGovernment = currentGovernment;
            return;
        }
        int index = Government.getGovernments().indexOf(currentGovernment) + 1;
        if (index >= Government.getGovernments().size())
            index %=  Government.getGovernments().size();
        onGovernment = Government.getGovernments().get(index);
    }

    public static Government getOnGovernment() {
        return onGovernment;
    }

    public static void checkPopulation() {
        int amountOfFoods = currentGovernment.getAmountOfGood(Good.FOOD1) + currentGovernment.getAmountOfGood(Good.FOOD2)
                + currentGovernment.getAmountOfGood(Good.FOOD3) + currentGovernment.getAmountOfGood(Good.FOOD4);
        int additionalFood = amountOfFoods - currentGovernment.getPopulation();
        int emptySpaces = currentGovernment.getEmptySpaces();
        currentGovernment.increasePopulation(Math.min(additionalFood,emptySpaces));
    }
}
