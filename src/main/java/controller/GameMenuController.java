package controller;

import model.*;
import model.Color;
import model.Turret;
import model.buildings.*;
import model.troops.Troop;
import model.troops.Troops;
import view.*;
import model.buildings.Buildings;
import view.enums.Commands;

import java.lang.reflect.Field;

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
        int numberOfFoods = currentGovernment.getAmountOfGood(Good.MEAT);
        numberOfFoods += currentGovernment.getAmountOfGood(Good.APPLE);
        numberOfFoods += currentGovernment.getAmountOfGood(Good.CHEESE);
        numberOfFoods += currentGovernment.getAmountOfGood(Good.BREAD);
        switch (rate) {
            case -2:
                currentGovernment.changePopularity(-8);
            case -1: {
                if (currentGovernment.getPopulation() > 2 * numberOfFoods)
                    return "you haven't enough food!";
                currentGovernment.decreaseAmountOfFood(currentGovernment.getPopulation() / 2);
                currentGovernment.changePopularity(-4);
            }
            case 0: {
                if (currentGovernment.getPopulation() > numberOfFoods)
                    return "you haven't enough food!";
                currentGovernment.decreaseAmountOfFood(currentGovernment.getPopulation());
                currentGovernment.changePopularity(0);
            }
            case 1: {
                if (1.5 * currentGovernment.getPopulation() > numberOfFoods)
                    return "you haven't enough food!";
                currentGovernment.decreaseAmountOfFood((int) (1.5 * currentGovernment.getPopulation()));
                currentGovernment.changePopularity(4);
            }
            case 2: {
                if (2 * currentGovernment.getPopulation() > numberOfFoods)
                    return "you haven't enough food!";
                currentGovernment.decreaseAmountOfFood(2 * currentGovernment.getPopulation());
                currentGovernment.changePopularity(8);
            }
        }
        ;
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
            case -3: {
                if (population > gold)
                    return "you haven't enough gold in your treasury";
                currentGovernment.decreaseAmountOfGood(Good.GOLD, population);
                currentGovernment.changePopularity(7);
            }
            case -2: {
                if (0.8 * population > gold)
                    return "you haven't enough gold in your treasury";
                currentGovernment.decreaseAmountOfGood(Good.GOLD, (int) (0.8 * population));
                currentGovernment.changePopularity(5);
            }
            case -1: {
                if (0.6 * population > gold)
                    return "you haven't enough gold in your treasury";
                currentGovernment.decreaseAmountOfGood(Good.GOLD, (int) (0.6 * population));
                currentGovernment.changePopularity(3);
            }
            case 0: {
                currentGovernment.changePopularity(1);
            }
            case 1: {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (0.6 * population));
                currentGovernment.changePopularity(-2);
            }
            case 2: {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (0.8 * population));
                currentGovernment.changePopularity(-4);
            }
            case 3: {
                currentGovernment.increaseAmountOfGood(Good.GOLD, population);
                currentGovernment.changePopularity(-6);
            }
            case 4: {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (1.2 * population));
                currentGovernment.changePopularity(-8);
            }
            case 5: {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (1.4 * population));
                currentGovernment.changePopularity(-12);
            }
            case 6: {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (1.6 * population));
                currentGovernment.changePopularity(-16);
            }
            case 7: {
                currentGovernment.increaseAmountOfGood(Good.GOLD, (int) (1.8 * population));
                currentGovernment.changePopularity(-20);
            }
            case 8: {
                currentGovernment.increaseAmountOfGood(Good.GOLD, 2 * population);
                currentGovernment.changePopularity(-24);
            }
        }
        ;
        return "set rate-number is successful";
    }

    public static String showTaxRate() {
        return "your government tax rate is: " + currentGovernment.getTaxRate();
    }

    public static String setFearRate(int rate) {
        for (Building building : currentGovernment.getBuildings()) {
            try {
                Field rateField = building.getClass().getField("rate");
                rateField.setAccessible(true);
                rateField.set(building, rateField.getInt(building) + 50 * rate);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                continue;
            }
        }

        if (rate > 5 || rate < -5) return "rate-number is out of bound";
        currentGovernment.setFearRate(rate);
        for (int i = 0; i < Map.getSize(); i++)
            for (int j = 0; j < Map.getSize(); j++) {
                Unit unit = Map.getMap()[i][j].getUnit();
                if (unit != null && unit.getGovernment() == currentGovernment)
                    for (Troop troop : unit.getTroops())
                        unit.changePower(rate);
            }
        return "set rate-number is successful";
    }

    public static String showFearRate() {
        return "your government feat rate is: " + currentGovernment.getFearRate();
    }

    public static boolean isCoordinateValid(int coordinate) {
        return coordinate > 0 && coordinate <= Map.getSize();
    }

    public static String dropBuilding(int x1, int y1, int x2, int y2, String type) {
        //جنس زمین
        //دروازه بسازیم دیوار خراب شه
        if (x1 > x2 || y1 > y2) return "your coordinates is incorrect!";
        for (int i = x1; i < x2; i++)
            for (int j = y1; j < y2; j++) {
                if (!Map.getMap()[i][j].isAvailable())
                    return "you can't drop building because this cell isn't available";
                if (!isCoordinateValid(i) || !isCoordinateValid(j))
                    return "your coordinates is incorrect!";
                if (!Map.getMap()[i][j].getTexture().getType().equals("water"))
                    return "You can't drop  building because texture of this cell is water";
            }
        Building targetBuilding = Buildings.getBuildingObjectByType(type);
        if (targetBuilding == null) return "your building type is incorrect!";
        if ((x2 - x1) != (targetBuilding.getX2() - targetBuilding.getX1()) || (y2 - y1) != (targetBuilding.getY2() - targetBuilding.getY1()))
            return "your coordinates is incorrect!";
        for (int i = x1; i < x2; i++)
            for (int j = y1; j < y2; j++)
                if (targetBuilding.checkTexture(Map.getMap()[i][j].getTexture()))
                    return "you can't drop " + targetBuilding.getName() + " in " + Map.getMap()[i][j].getTexture().getType();
        currentGovernment.decreaseAmountOfGood(Good.GOLD, targetBuilding.getCost()[0]);
        currentGovernment.decreaseAmountOfGood(Good.WOOD, targetBuilding.getCost()[1]);
        currentGovernment.decreaseAmountOfGood(Good.STONE, targetBuilding.getCost()[2]);
        for (int i = x1; i < x2; i++)
            for (int j = y1; j < y2; j++) {
                Map.getMap()[i][j].setBuilding(targetBuilding);
                Map.getMap()[i][j].setAvailable(false);
                Map.getMap()[i][j].setPassable(false);
            }
        currentGovernment.addBuilding(targetBuilding);
        if (targetBuilding.getName().equals("Woodcutter")) {
            Mine mine = (Mine) targetBuilding;
            mine.setProductRate(2 ^ currentGovernment.getCountOfBuilding("Woodcutter"));
        }
        return "building dropped to the target cell!";
    }

    public static String selectBuilding(int x, int y) throws ReflectiveOperationException {
        //do work after select
        if (!isCoordinateValid(x) || !isCoordinateValid(y))
            return "your coordinates is incorrect!";
        if (Map.getMap()[x][y].getBuilding() == null)
            return "There is no existing building in your coordinates!";
        selectedBuilding = Map.getMap()[x][y].getBuilding();
        if (selectedBuilding.getName().equals("shop")) ShopMenu.run();
        if (selectedBuilding.getName().equals("Mercenary Post")) {
            System.out.print("enter number of troops you want buy: ");
            int count = Commands.scanner.nextInt();
            System.out.print("enter type of troops you want buy: ");
            String type = Commands.scanner.nextLine();
            Troop troop = Troops.getTroopObjectByType(type);
            if (troop == null)
                return "invalid troop name";
            if (troop.getValue() * count > currentGovernment.getAmountOfGood(Good.GOLD))
                return "you haven't enough gold";
            Barrack barrack = (Barrack) selectedBuilding;
            barrack.addTroop(troop, count);
        }
        if (selectedBuilding instanceof CagedWarDogs cagedWarDogs)
            cagedWarDogs.setOpen(true);
        String output = "target building selected";
        if (selectedBuilding.getGroup().equals(BuildingGroups.CASTLE))
            output += " (max hp = " + selectedBuilding.getMaxHp() + ", hp = " + selectedBuilding.getHp() + ")";
        if (selectedBuilding.getGroup().equals(BuildingGroups.CASTLE) && selectedBuilding.getHp() < selectedBuilding.getMaxHp())
            return GameMenu.repair(output);
        else
            return output;
    }

    public static String createUnit(int x, int y, String type, int count) {
        if (!Map.getMap()[x][y].isAvailable())
            return "you can't drop unit because this cell isn't available!";
        if (!isCoordinateValid(x) || !isCoordinateValid(y))
            return "your coordinates is incorrect!";
        if (count < 0) return "count is invalid";
        if (currentGovernment.getCastle().getPopulation() < count)
            return "you don't have enough population for create this unit";
        if (type.equals("worker")) {
            if (currentGovernment.getAmountOfGood(Good.GOLD) < 10 * count)
                return "you don't have enough gold for create worker!";
            if (selectedBuilding.getName().equals("engineer guild"))
                return "you can't create " + type + " in " + selectedBuilding.getName();
            currentGovernment.getCastle().changeNumberOfWorkers(count);
            currentGovernment.decreaseAmountOfGood(Good.GOLD, 10 * count);
        } else {
            Troop troop = Troops.getTroopObjectByType(type);
            if (Map.getMap()[x][y].getUnit() != null)
                return "you can't drop unit on this cell!";
            if (troop == null) return "unit type is invalid";
            if (currentGovernment.getAmountOfGood(Good.GOLD) < troop.getValue() * count)
                return "you don't have enough gold for create this unit";
            if (troop.getWeapon() != null && currentGovernment.getAmountOfGood(troop.getWeapon()) < count)
                return "you don't have enough weapon for create this unit";
            if (troop.isHasArmor() && currentGovernment.getAmountOfGood(Good.ARMOR) < count)
                return "you don't have enough armor for create this unit";
            if (troop.getName().equals("Black Monk") && !selectedBuilding.getName().equals("Cathedral"))
                return "you can make Black Monk only in Cathedral";
            if (type.equals("european") && selectedBuilding.getName().equals("Mercenary Post"))
                return "you can't create european in Mercenary Post";
            if (type.equals("arabian") && selectedBuilding.getName().equals("barrack"))
                return "you can't create arabian in barrack";
            if (type.equals("engineer") && selectedBuilding.getName().equals("engineer guild"))
                return "you can create engineer unit only in engineer guild";
            if (troop.getName().equals("Knight") || troop.getName().equals("Horse Archers")) {
                if (count > currentGovernment.getCountOfHorses())
                    return "you don't have enough horses for your Knight unit";
                else
                    currentGovernment.changeCountOfHorses(count);
            }
            currentGovernment.getCastle().changePopulation(-1 * count);
            Unit unit = new Unit(x, y, currentGovernment, "standing", troop.getHp() * count);
            if (troop.getName().startsWith("Archer"))
                unit.setCanDamage(false);
            unit.addTroop(troop, count);
            Map.getMap()[x][y].addUnit(unit);
            currentGovernment.decreaseAmountOfGood(Good.GOLD, count * troop.getValue());
            currentGovernment.decreaseAmountOfGood(troop.getWeapon(), count);
        }
        return "you successfully create unit";
    }

    public static String repair() {
        for (int i = selectedBuilding.getX1() - 1; i < selectedBuilding.getX2() + 1; i++)
            for (int j = selectedBuilding.getY1() - 1; j < selectedBuilding.getY2() + 1; j++) {
                Unit unit = Map.getMap()[i][j].getUnit();
                if (unit != null && !unit.getGovernment().equals(currentGovernment))
                    return "enemy is near your building!";
            }
        int consumableStone = (int) Math.ceil((double) (selectedBuilding.getMaxHp() - selectedBuilding.getHp()) / 40);
        selectedBuilding.getOwner().decreaseAmountOfGood(Good.STONE, consumableStone);
        selectedBuilding.setHp(selectedBuilding.getMaxHp());
        return "repair successful";
    }

    public static String selectUnit(int x, int y) {
        if (Map.getMap()[x][y].getUnit() == null)
            return "there is no unit in this cell!";
        selectedUnit = Map.getMap()[x][y].getUnit();
        return "unit successfully selected";
    }

    public static String moveUnit(int x, int y) {
        //add to dfs
        //بعد خروج اب سرعتشون زیاد شه
        //توی move unit بذاریم سربازا برن تو برج و برجک ؟؟
        Cell cell = Map.getMap()[x][y];
        if (!cell.isPassable() && (!(cell.getBuilding() instanceof GateHouse) || selectedUnit.getGovernment() != currentGovernment))
            return "you can't pass this cell;";
        if (!cell.isAvailable() && cell.getWall() == null)
            return "you can't pass this cell;";
        if (!cell.isAvailable() && cell.getWall() != null && !selectedUnit.isCanClimb())
            return "you can't pass this cell;";
        if (cell.getTexture().getType().equals("water"))
            return "you can't go to water regions!";
        if (cell.getTexture() == Texture.SHALLOW_WATER)
            selectedUnit.decreaseVelocity(1);
        move(selectedUnit.getX(), selectedUnit.getY(), x, y, selectedUnit);
        return "unit move to x: " + selectedUnit.getX() + "and y: " + selectedUnit.getY() + "successfully";
    }

    private static void move(int x1, int y1, int x2, int y2, Unit unit) {
    }

    public static String setUnitState(String state) {
        if (selectedUnit == null)
            return "you don't select any unit!";
        selectedUnit.setState(state);
        return null;
    }

    public static String patrolUnit(int x1, int y1, int x2, int y2) {
        if (Map.getMap()[x1][y1].getTexture().getType().equals("water") || Map.getMap()[x2][y2].getTexture().getType().equals("water"))
            return "you can't go to water regions!";
        return "your unit stayed in x:" + selectedUnit.getX() + "y: " + selectedUnit.getY();
    }

    public static boolean checkRange(int x1, int y1, int x2, int y2, int range) {
        return x1 <= x2 + range && x1 >= x2 - range
                && y1 <= y2 + range && y1 >= y2 - range;
    }

    public static Unit findNearestUnit(int range, int selectedUnitX, int selectedUnitY) {
        for (int k = 1; k <= range; k++)
            for (int i = selectedUnitX - k; i <= selectedUnitX + k; i++)
                for (int j = selectedUnitY - k; j <= selectedUnitY + k; j++)
                    if (Map.getMap()[i][j].getUnit() != null && Map.getMap()[i][j].getUnit().getGovernment() != currentGovernment)
                        return Map.getMap()[i][j].getUnit();
        return null;
    }

    public static String attackEnemy(int x, int y) {
        int sightRange = selectedUnit.getSightRange();
        int selectedUnitX = selectedUnit.getX();
        int selectedUnitY = selectedUnit.getY();
        if (Map.getMap()[x][y].getUnit() == null)
            return "there is no enemy in this cell!";
        if (!checkRange(x, y, selectedUnitX, selectedUnitY, sightRange)
                || x - selectedUnitX > selectedUnit.getVelocity()
                || y - selectedUnitY > selectedUnit.getVelocity())
            return "you can't attack this enemy!";
        if (selectedUnit.getShootingRange() != 0)
            return "your unit not appropriate for this attack";
        move(selectedUnit.getX(), selectedUnit.getY(), x, y, selectedUnit);
        Unit enemy = Map.getMap()[x][y].getUnit();
        while (enemy.getHp() <= 0 || selectedUnit.getHp() <= 0) {
            enemy.decreaseHpOfUnit(selectedUnit.getPower());
            selectedUnit.decreaseHpOfUnit(enemy.getPower());
        }
        if (selectedUnit.getState().equals("offensive")) {
            if (enemy.getHp() == 0) {
                Map.getMap()[x][y].removeUnit(enemy);
                for (int k = 1; k <= sightRange; k++)
                    for (int i = selectedUnitX - k; i <= selectedUnitX + k; i++)
                        for (int j = selectedUnitY - k; j <= selectedUnitY + k; j++)
                            if (Map.getMap()[i][j].getUnit() != null && Map.getMap()[i][j].getUnit().getGovernment() != currentGovernment)
                                attackEnemy(i, j);
            } else {
                Map.getMap()[selectedUnit.getX()][selectedUnit.getY()].removeUnit(selectedUnit);
                return "attack finished!";
            }
        } else {
            if (enemy.getHp() == 0) Map.getMap()[x][y].removeUnit(enemy);
            else Map.getMap()[selectedUnit.getX()][selectedUnit.getY()].removeUnit(selectedUnit);
        }
        return "attack finished";
    }

    public static String airAttack(int x, int y) {
        int shootingRange = selectedUnit.getShootingRange();
        int selectedUnitX = selectedUnit.getX();
        int selectedUnitY = selectedUnit.getY();
        if (!checkRange(x, y, selectedUnitX, selectedUnitY, shootingRange))
            return "you can't attack this enemy!";
        if (selectedUnit.getShootingRange() == 0)
            return "your unit not appropriate for this attack";
        if (Map.getMap()[x][y].getUnit() == null) {
            Building building = Map.getMap()[x][y].getBuilding();
            if (building == null)
                return "there is no enemy in this cell!";
            else {
                while (building.getHp() == 0 || selectedUnit.getHp() == 0) {
                    building.setHp(building.getHp() - selectedUnit.getPower());
                    if (building instanceof Tower) {
                        Tower tower = (Tower) building;
                        if (selectedUnitX < tower.getX2() + tower.getDefenceRange() &&
                                selectedUnitX > tower.getX1() - tower.getDefenceRange() &&
                                selectedUnitY > tower.getY2() + tower.getDefenceRange() &&
                                selectedUnitY < tower.getY1() - tower.getDefenceRange())
                            selectedUnit.decreaseHpOfUnit(tower.getAttackRange());
                    }
                }
            }
        }
        Unit enemy = Map.getMap()[x][y].getUnit();
        while (enemy.getHp() <= 0 || selectedUnit.getHp() <= 0) {
            enemy.decreaseHpOfUnit(selectedUnit.getPower());
            if (!checkRange(selectedUnitX, selectedUnitY, x, y, enemy.getShootingRange()) && enemy.getShootingRange() != 0)
                selectedUnit.decreaseHpOfUnit(enemy.getPower());
        }
        if (selectedUnit.getState().equals("offensive")) {
            if (enemy.getHp() == 0) {
                Map.getMap()[x][y].removeUnit(enemy);
                for (int k = 1; k <= shootingRange; k++)
                    for (int i = selectedUnitX - k; i <= selectedUnitX + k; i++)
                        for (int j = selectedUnitY - k; j <= selectedUnitY + k; j++)
                            if (Map.getMap()[i][j].getUnit() != null && Map.getMap()[i][j].getUnit().getGovernment() != currentGovernment)
                                airAttack(i, j);
            } else {
                Map.getMap()[selectedUnit.getX()][selectedUnit.getY()].removeUnit(selectedUnit);
                return "attack finished!";
            }
        } else {
            if (enemy.getHp() == 0) Map.getMap()[x][y].removeUnit(enemy);
            else Map.getMap()[selectedUnit.getX()][selectedUnit.getY()].removeUnit(selectedUnit);
        }
        return "air attack finished";
    }

    public static String pourOil(String direction) {
        return null;
    }

    public static String digTunnel(int x, int y) {
        Building targetBuilding = Map.getMap()[x][y].getBuilding();
        if (targetBuilding instanceof Tower && !targetBuilding.getName().equals("lookout tower") ||
                targetBuilding instanceof Turret && !targetBuilding.getName().equals("turret"))
            return "you can't dig tunnel under this building";
        if (Map.getMap()[x][y].getTexture().getType().equals("water"))
            return "you can't dig tunnel on water regions";
        if (Map.getMap()[x][y].isStartDigging() || Map.getMap()[x][y].isHaveDitch())
            return "you can't dig tunnel on ditch";
        for (int i = x; i < x + 3; i++) {
            targetBuilding = Map.getMap()[i][y].getBuilding();
            currentGovernment.getBuildings().remove(targetBuilding);
            for (int j = targetBuilding.getX1(); j <= targetBuilding.getX2(); j++) {
                for (int k = targetBuilding.getY1(); k <= targetBuilding.getY2(); k++) {
                    Map.getMap()[j][k].setBuilding(null);
                    Map.getMap()[j][k].setAvailable(true);
                    Map.getMap()[j][k].setPassable(true);
                }
            }
        }
        return "tunnel digged successfully";
    }

    public static String buildEquipments(String equipmentName) {
        if (!selectedUnit.getType().equals("Engineer"))
            return "you don't select Engineer Unit";
        Tool tool = Tool.getToolByName(equipmentName);
        if (tool == null)
            return "your equipment name is invalid!";
        if (currentGovernment.getNumberOfEngineer() < tool.getNumberOfEngineer())
            return "you haven't enough engineer";
        if (!(selectedBuilding.getName().equals("normal tower")) && !(selectedBuilding instanceof Tower))
            return "you can't build equipment in this building";
        Tower tower = (Tower) selectedBuilding;
        tower.addTool(tool);
        return "equipment successfully dropped";
    }

    public static String disbandUnit() {
        int x1 = currentGovernment.getCastle().getX1();
        int x2 = currentGovernment.getCastle().getX2();
        int y1 = currentGovernment.getCastle().getY1();
        int y2 = currentGovernment.getCastle().getY2();
        for (int i = x1 - 1; i <= x2 + 1; i++)
            for (int j = y1 - 1; j <= y2 + 1; j++)
                if (Map.getMap()[i][j].getUnit() == null)
                    Map.getMap()[i][j].addUnit(selectedUnit);
        selectedUnit = null;
        return null;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government government) {
        currentGovernment = government;
    }

    public static String chooseColor(String color) {
        return Color.setOwnerOfColor(color, currentGovernment);
    }

    public static String getColorList() {
        StringBuilder result = new StringBuilder();
        for (Color c : Color.values())
            if (c.getGovernment() == null) result.append(c.getColorName()).append("\n");
        return result.toString();
    }

    public static void setOnGovernment() {
        if (onGovernment == null) {
            onGovernment = currentGovernment;
            return;
        }
        int index = Government.getGovernments().indexOf(currentGovernment) + 1;
        if (index >= Government.getGovernments().size())
            index %= Government.getGovernments().size();
        onGovernment = Government.getGovernments().get(index);
    }

    public static Government getOnGovernment() {
        return onGovernment;
    }

    public static void checkPopulation() {
        for (Government government : Government.getGovernments()) {
            int amountOfFoods = government.getAmountOfGood(Good.MEAT) + government.getAmountOfGood(Good.APPLE)
                    + government.getAmountOfGood(Good.CHEESE) + government.getAmountOfGood(Good.BREAD);
            int additionalFood = amountOfFoods - government.getPopulation();
            int emptySpaces = government.getEmptySpaces();
            government.increasePopulation(Math.min(additionalFood, emptySpaces));
        }
    }

    public static void runBuildings() {
        for (Government government : Government.getGovernments())
            for (Building building : government.getBuildings())
                building.action();
    }

    public static void oxTetherAction() {
        for (Government government : Government.getGovernments()) {
            int countOFOxTetherBuildings = government.getCountOfBuilding("Ox tether");
            int totalTransformation = 12 * countOFOxTetherBuildings;
            for (Building building : government.getBuildings()) {
                if (totalTransformation == 0) return;
                if (building.getName().equals("Quarry")) {
                    Mine mine = (Mine) building;
                    if (mine.getStorage() >= totalTransformation) {
                        mine.decreaseStorage(totalTransformation);
                        government.increaseAmountOfGood(Good.STONE_BLOCK, totalTransformation);
                    } else {
                        mine.decreaseStorage(mine.getStorage());
                        government.increaseAmountOfGood(Good.STONE_BLOCK, mine.getStorage());
                        totalTransformation -= mine.getStorage();
                    }
                }
            }
        }
    }

    public static void StableAction() {
        for (Government government : Government.getGovernments())
            government.updateCountOfHorses();
    }

    public static String dropWall(int x, int y, int thickness, int height, String direction) {
        //left or right??? top or bottom
        if (direction.equals("horizontal")) {
            for (int i = x; i <= x + thickness; i++)
                if (Map.getMap()[i][y].isAvailable() || (!Map.getMap()[i][y].getTexture().equals(Texture.LAND) && !Map.getMap()[i][y].getTexture().equals(Texture.LAND_WITH_PEBBLES)))
                    return "you can't drop wall on this cell!";
        }
        if (direction.equals("vertical")) {
            for (int i = y; i <= y + thickness; i++)
                if (Map.getMap()[x][i].isAvailable() || (!Map.getMap()[x][i].getTexture().equals(Texture.LAND) && !Map.getMap()[x][i].getTexture().equals(Texture.LAND_WITH_PEBBLES)))
                    return "you can't drop wall on this cell!";
        }
        int count = (height / 2) * thickness;
        if (currentGovernment.getAmountOfGood(Good.STONE_BLOCK) < count)
            return "you don't have enough stone block!";
        if (currentGovernment.getAmountOfGood(Good.GOLD) < count * 5)
            return "you don't have enough gold!";
        Wall wall = new Wall(height, 5 * count);
        if (direction.equals("horizontal"))
            for (int i = x; i <= x + thickness; i++) {
                Map.getMap()[i][y].setWall(wall);
                Map.getMap()[i][y].setAvailable(false);
                Map.getMap()[i][y].setPassable(false);
            }
        if (direction.equals("vertical"))
            for (int i = y; i <= y + thickness; i++) {
                Map.getMap()[x][i].setWall(wall);
                Map.getMap()[x][i].setAvailable(false);
                Map.getMap()[x][i].setPassable(false);
            }
        return "wall dropped successfully";
    }

    public static String dropTower(int x, int y) {
        Cell cell = Map.getMap()[x][y];
        if (!cell.isAvailable())
            return "this cell not available!";
        cell.setAvailable(false);
        cell.setPassable(false);
        currentGovernment.addBuilding(new Turret("normal tower", 5, 10, 1000, new int[]{0, 0, 20, 0, 0}, 20));
        return "normal tower dropped successfully";
    }

    public static String dropTurret(int x, int y) {
        Cell cell = Map.getMap()[x][y];
        if (!cell.isAvailable())
            return "this cell not available!";
        currentGovernment.addBuilding(new Turret("normal tower", 2, 5, 500, new int[]{0, 0, 10, 0, 0}, 10));
        cell.setAvailable(false);
        cell.setPassable(false);
        return "turret dropped successfully";
    }

    public static String startDiggingDitch(int x, int y) {
        if (!selectedUnit.getType().equals("Spearmen"))
            return "you don't select appropriate unit for digging ditch!";
        if (!Map.getMap()[x][y].isAvailable())
            return "you can't digging ditch on this cell!";
        Map.getMap()[x][y].setStartDigging(true);
        Map.getMap()[x][y].setDitchOwner(currentGovernment);
        return "digging ditch started";
    }

    public static String stopDiggingDitch(int x, int y) {
        if (Map.getMap()[x][y].isStartDigging()) {
            Map.getMap()[x][y].setStartDigging(false);
            Map.getMap()[x][y].setDitchOwner(null);
        }
        return "digging ditch stopped";
    }

    public static String deleteDitch(int x, int y) {
        if (Map.getMap()[x][y].isHaveDitch()) {
            Map.getMap()[x][y].setHaveDitch(false);
            Map.getMap()[x][y].setDitchOwner(null);
        }
        return "ditch deleted successfully";
    }

    ;

    public static String captureTheGate(int x, int y) {
        Cell cell = Map.getMap()[x][y];
        Building targetBuilding = cell.getBuilding();
        if (!(targetBuilding instanceof GateHouse))
            return "invalid target";
        if (!selectedUnit.isCanClimb())
            return "your unit can't capture the gate";
        if (!targetBuilding.isNearGate(selectedUnit))
            return "your unit can't capture the gate";
        targetBuilding.getOwner().getBuildings().remove(targetBuilding);
        currentGovernment.addBuilding(targetBuilding);
        targetBuilding.setOwner(currentGovernment);
        return "gate successfully captured";
    }

    public static boolean isLastGovernment() {
        return Government.getGovernments().size() - 1 == Government.getGovernments().indexOf(currentGovernment);
    }

    public static void foodVarietyAction() {
        for (Government government : Government.getGovernments())
            government.changePopularity(government.getNumberOfFoodVariety() - 1);
    }

    public static void diggingDitch() {
        for (int i = 0; i < Map.getSize(); i++)
            for (int j = 0; j < Map.getSize(); j++)
                if (Map.getMap()[i][i].isStartDigging() && Map.getMap()[i][j].getDitchOwner() == currentGovernment) {
                    Map.getMap()[i][i].setStartDigging(false);
                    Map.getMap()[i][i].setHaveDitch(true);
                }
    }

    public static void handleAttacks() {
        for (int x = 0; x < Map.getSize(); x++)
            for (int y = 0; y < Map.getSize(); y++) {
                Unit unit = Map.getMap()[x][y].getUnit();
                if (unit == null || unit.getState().equals("standing") || unit.getGovernment() != currentGovernment)
                    continue;
                handleAttackUnit(unit);
            }
    }

    private static void handleAttackUnit(Unit unit) {
        int unitX = unit.getX();
        int unitY = unit.getY();
        int shootingRange = unit.getShootingRange();
        int sightRange = unit.getSightRange();
        if (shootingRange == 0) {
            Unit enemy = findNearestUnit(sightRange, unitX, unitY);
            if (enemy != null) attackEnemy(enemy.getX(), enemy.getY());
        } else {
            Unit enemy = findNearestUnit(shootingRange, unitX, unitY);
            if (enemy != null) airAttack(enemy.getX(), enemy.getY());
        }
    }

    public static void setDefaults() {
        selectedBuilding = null;
        selectedUnit = null;
    }
}
