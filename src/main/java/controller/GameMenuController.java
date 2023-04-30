package controller;

import model.Government;
import model.Map;
import model.Unit;
import model.buildings.Building;
import model.buildings.Buildings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

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
        return null;
    }

    public static String showPopulation() {
        return null;
    }

    public static String showFoodList() {
        return null;
    }

    public static String setFoodRate(int rate) {
        return null;
    }

    public static String showFoodRate() {
        return null;
    }

    public static String setTaxRate(int rate) {
        return null;
    }

    public static String showTaxRate() {
        return null;
    }

    public static String setFearRate(int rate) {
        return null;
    }

    public static String showFearRate() {
        return null;
    }

    public static String dropBuilding(int x, int y, String type) {
        map.getMap()[x][y].setBuilding(Buildings.getBuildingObjectByType(type));
        return "building dropped to the target cell";
    }

    public static String selectBuilding(int x, int y) {
        return null;
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

    public static String setTexture(int x, int y) {
        return null;
    }

    public static String setTexture(int x1, int y1,int x2,int y2) {
        return null;
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

    public static void setCurrentGovernment(Government currentGovernment) {
        GameMenuController.currentGovernment = currentGovernment;
    }
}
