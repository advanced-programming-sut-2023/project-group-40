package controller;

import model.Food;
import model.Government;
import model.Map;
import model.Texture;
import model.Unit;
import model.buildings.Building;
import model.buildings.Buildings;
import model.buildings.Storage;

import java.io.IOException;

enum Direction {

}

public class GameMenuController {
    private static Government currentGovernment;
    private static Building selectedBuilding;
    private static Unit selectedUnit;
    private static Map map;
    private static int x;
    private static int y;

    public static void increaseX(int amount) {
        x += amount;
    }

    public static void increaseY(int amount) {
        y += amount;
    }

    public static void setMapSize(int size) {
        map = new Map(size);
    }

    public static String showMap(int x, int y) throws IOException {
        GameMenuController.x = x;
        GameMenuController.y = y;
        return null;
    }

    public static String showDetails(int x, int y) {
        return null;
    }

    public static String showPopularityFactors() {
        return "Popularity Factors: \n 1.food \n 2.tax \n 3.religion \n 4.fear";
    }

    public static String showPopularity() {
        return "rate of your government popularity is " + currentGovernment.getPopularity();
    }

    public static String showFoodList() {
        int numOfFood1 = 0, numOfFood2 = 0, numOfFood3 = 0, numOfFood4 = 0, index = 0;
        for (Storage<Food> foodStorage : currentGovernment.getFoodStorages()) {
            for (Food value : Food.values()) {
                if(index == 4) break;
                if(foodStorage.getProducts().get(value) != null)
                    numOfFood1 += foodStorage.getProducts().get((value));
            }
            index++;
        }
        return "Food List: \n food1: " + numOfFood1 + "\n food2: " + numOfFood2 +
                "\n food3: " + numOfFood3 + "\n food4: " + numOfFood4;
    }

    public static String setFoodRate(int rate) {
        if(rate > 2 || rate < -2)
            return "rate-number is out of bound";
        currentGovernment.setFoodRate(rate);
        return "set rate-number is successful";
    }

    public static String showFoodRate() {
        return "your government food rate is: " + currentGovernment.getFoodRate();
    }

    public static String setTaxRate(int rate) {
        if(rate > 8 || rate < -3)
            return "rate-number is out of bound";
        currentGovernment.setTaxRate(rate);
        return "set rate-number is successful";
    }

    public static String showTaxRate() {
        return "your government tax rate is: " + currentGovernment.getTaxRate();
    }

    public static String setFearRate(int rate) {
        if(rate > 5 || rate < -5)
            return "rate-number is out of bound";
        currentGovernment.setFearRate(rate);
        return "set rate-number is successful";
    }

    public static String showFearRate() {
        return "your government feat rate is: " + currentGovernment.getFearRate();
    }

    private static boolean isCoordinateValid(int coordinate){
        return coordinate > 0 && coordinate <= map.getSize();
    }

    public static String dropBuilding(int x, int y, String type) {
        if (!isCoordinateValid(x) || !isCoordinateValid(y))
            return "your coordinates is incorrect!";
        if (map.getMap()[x][y].getBuilding() != null)
            return "There is already a building in your coordinates!";
        Building targetBuilding = Buildings.getBuildingObjectByType(type);
        if (targetBuilding == null)
            return "your building type is incorrect!";
        if(targetBuilding.checkTexture(map.getMap()[x][y].getTexture()))
            return "you can not drop building to target cell!";
        map.getMap()[x][y].setBuilding(targetBuilding);
        return "building dropped to the target cell!";
    }

    public static String selectBuilding(int x, int y) {
        //do work after select
        if (!isCoordinateValid(x) || !isCoordinateValid(y))
            return "your coordinates is incorrect!";
        if (map.getMap()[x][y].getBuilding() == null)
            return "There is no existing building in your coordinates!";
        selectedBuilding = map.getMap()[x][y].getBuilding();
        return "target building selected";
    }

    public static String createUnit(String type, int count) {
        return null;
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

    public static String setTexture(int x, int y, Texture type) {
        if (!isCoordinateValid(x) || !isCoordinateValid(y))
            return "your coordinates is incorrect!";
        if (map.getMap()[x][y].getBuilding() != null)
            return "There is already a building in your coordinates!";
        map.getMap()[x][y].setTexture(type);
        return "texture successfully changed";
    }

    public static String setTexture(int x1, int y1,int x2,int y2,Texture type) {
        if (!isCoordinateValid(x1) || !isCoordinateValid(x2) || !isCoordinateValid(y1) || !isCoordinateValid(y2))
            return "your coordinates is incorrect!";
        for (int i = x1; i < x2 ; i++)
            for (int j = y1; j < y2; j++){
                if (map.getMap()[i][j].getBuilding() != null)
                    return "There is already a building in your coordinates!";
                map.getMap()[i][j].setTexture(type);
            }
        return "texture successfully changed";
    }

    public static String clearBlock(int x,int y){
        return null;
    }

    public static String dropBlock(int x,int y,String direction){
        return null;
    }

    public static String dropTree(int x,int y,String type){
        return null;
    }

    public static String dropUnit(int x,int y,String type,int count){
        return null;
    }

    public static String enterTrade(){
        return null;
    }

    public static String nextTurn(){
        return null;
    }
    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        GameMenuController.currentGovernment = currentGovernment;
    }
}
