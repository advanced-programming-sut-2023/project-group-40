package controller;

import model.Cell;
import model.Good;
import model.Map;
import model.Texture;
import model.buildings.Storage;

public class MapMenuController {
    private static int centerX;
    private static int centerY;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static String showMap(int x, int y) {
        setCenters(x, y);
        StringBuilder result = new StringBuilder();
        int size = Map.getSize();
        result.append("    ");
        for (int j = Math.max(0, y - 20); j < Math.min(size - 1, y + 20); j++)
            result.append(String.format("%03d ", j));
        result.append("\n");
        for (int i = Math.max(0, x - 6); i < Math.min(size - 1, x + 6); i++) {
            result.append(String.format("%03d ", i));
            for (int j = Math.max(0, y - 20); j < Math.min(size - 1, y + 20); j++) {
                Cell cell = Map.getMap()[i][j];
                String text = "   ";
                Texture texture = cell.getTexture();
                if (cell.getTree() != null) text = " T ";
                if (cell.getBuilding() != null) text = " B ";
                if (cell.getUnit() != null) text = " S ";
                if (cell.getCastle() != null) text = " C ";
                result.append(texture.getBackGroundColor())
                        .append(texture.getTextColor()).append(text).append(ANSI_RESET).append("|");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static int getCenterX() {
        return centerX;
    }

    public static int getCenterY() {
        return centerY;
    }

    public static void setCenters(int centerX, int centerY) {
        MapMenuController.centerX = centerX;
        MapMenuController.centerY = centerY;
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
            result += storage.getProducts().get(Good.IRON) + "\n";
            result += "stone :";
            result += storage.getProducts().get(Good.STONE) + "\n";
        }
        if (Map.getMap()[x][y].getUnit() != null) {
            result += "troops type: " + Map.getMap()[x][y].getUnit().getType();
            result += "troop count: " + Map.getMap()[x][y].getUnit().getTroops().size();
        }
        if (Map.getMap()[x][y].getBuilding() != null) {
            result += "building: " + Map.getMap()[x][y].getBuilding().getName();
        }
        return result;
    }


}
