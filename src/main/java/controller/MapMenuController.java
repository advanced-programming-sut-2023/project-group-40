package controller;

import model.Good;
import model.Map;
import model.buildings.Storage;

import javax.print.DocFlavor;

public class MapMenuController {
    private static int x;
    private static int y;

    public static void increaseX(int amount) {
        x += amount;
    }

    public static void increaseY(int amount) {
        y += amount;
    }
    public static int getY() {
        return y;
    }

    public static int getX() {
        return x;
    }

    public static void setX(int x) {
        MapMenuController.x = x;
    }

    public static void setY(int y) {
        MapMenuController.y = y;
    }

    public static String showDetails(int x, int y) {
        String result = "";
        result += "Texture: " + Map.getMap()[x][y].getTexture().name().toLowerCase();
        if (Map.getMap()[x][y].getBuilding().getName().equals("Stockpile")) {
            Storage storage = (Storage) Map.getMap()[x][y].getBuilding();
            result += "gold :";
            result += storage.getProducts().get(Good.GOLD) + "\n";
            result += "wood :";
            result += storage.getProducts().get(Good.WOOD) + "\n";
            result += "iron :";
            result += storage.getProducts().get(Good.IRON)+ "\n";
            result += "stone :";
            result += storage.getProducts().get(Good.STONE)+ "\n";
        }
        if (Map.getMap()[x][y].getUnit() != null) {
            result += "troops type: " + Map.getMap()[x][y].getUnit().getType();
            result += "troop count: "+ Map.getMap()[x][y].getUnit().getTroops().size();
        }
        if (Map.getMap()[x][y].getBuilding() != null) {
            result += "building: " + Map.getMap()[x][y].getBuilding().getName();
        }
        return result;
    }

}
