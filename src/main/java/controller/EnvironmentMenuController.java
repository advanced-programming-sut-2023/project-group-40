package controller;

import model.*;
import model.buildings.Building;
import model.buildings.Buildings;
import org.apache.commons.text.RandomStringGenerator;
import view.GameMenu;

public class EnvironmentMenuController {

    public static boolean isFirstPlayer() {
        return Government.getGovernmentsSize() == 1;
    }

    public static boolean isCurrentGovernmentChoseColor(User user) {
        return Government.getGovernmentByUser(user).getColor() != null;
    }

    public static void checkGameStarted() {
        if (Government.checkAllGovernmentsChoseColor()) GameMenu.setGameStarted(true);
    }

    public static void addPlayer(String username) {
        Government.addGovernment(username);
    }

    public static boolean isPlayerAdded(String username) {
        return Government.getGovernmentByUser(UserController.getUserByUsername(username)) != null;
    }


    public static String setTexture(int x, int y, String type) {
        if (!GameMenuController.isCoordinateValid(x) || !GameMenuController.isCoordinateValid(y))
            return "your coordinates is incorrect!";
        if (Map.getMap()[x][y].getBuilding() != null) return "There is already a building in your coordinates!";
        changeTextureOfCell(Map.getMap()[x][y], Texture.getTextureByName(type));
        return "texture successfully changed";
    }

    public static String setTexture(int x1, int y1, int x2, int y2, String type) {
        if (!GameMenuController.isCoordinateValid(x1) || !GameMenuController.isCoordinateValid(x2) || !GameMenuController.isCoordinateValid(y1) || !GameMenuController.isCoordinateValid(y2))
            return "your coordinates is incorrect!";
        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++) {
                if (!Map.getMap()[i][j].isAvailable())
                    return "this cell not available for changing texture!";
                changeTextureOfCell(Map.getMap()[i][j], Texture.getTextureByName(type));
            }
        return "texture successfully changed";
    }

    public static void changeTextureOfCell(Cell cell, Texture texture) {
        if (texture == Texture.STONE || texture == Texture.KOCH_POND ||
                texture == Texture.BIG_POND || texture == Texture.SEA)
            cell.setPassable(false);
        if (texture == Texture.STONE) cell.setAvailable(false);
        cell.setTexture(texture);
    }

    public static String clearBlock(int x, int y) {
        Map.getMap()[x][y].setRock(null);
        Map.getMap()[x][y].setTree(null);
        Map.getMap()[x][y].setTexture(Texture.LAND);
        return "clear block successfully applied";
    }

    public static String dropRock(int x, int y, String direction) {
        if (!direction.equals("n") && !direction.equals("e") && !direction.equals("s") && !direction.equals("w") && !direction.equals("r"))
            return "your direction is incorrect!";
        if (direction.equals("r")) {
            RandomStringGenerator generator = new RandomStringGenerator.Builder().selectFrom("swen".toCharArray()).build();
            direction = generator.generate(1);
        }
        Map.getMap()[x][y].setRock(new Rock(direction));
        Map.getMap()[x][y].setAvailable(false);
        Map.getMap()[x][y].setPassable(false);
        return "rock successfully dropped";
    }

    public static String dropTree(int x, int y, String type) {
        if (!GameMenuController.isCoordinateValid(x) || !GameMenuController.isCoordinateValid(y))
            return "your coordinates is incorrect!";
        if (Tree.getTree(type) == null)
            return "your tree type is incorrect";
        if (Map.getMap()[x][y].getTexture() != Texture.LAND && Map.getMap()[x][y].getTexture() != Texture.GRASS
                && Map.getMap()[x][y].getTexture() != Texture.GRASS_LAND && Map.getMap()[x][y].getTexture() != Texture.DENSE_GRASS_LAND)
            return "you can't drop tree on this cell because of texture!";

        Map.getMap()[x][y].setTree(Tree.getTree(type));
        Map.getMap()[x][y].setAvailable(false);
        return "tree successfully dropped";
    }

    private static void dropStockpile(int x, int y, Government government) {
        Building targetBuilding = Buildings.getBuildingObjectByType("stockpile");
        government.addBuilding(targetBuilding);
        for (int i = x; i < x + targetBuilding.getHeight(); i++)
            for (int j = y; j < y + targetBuilding.getWidth(); j++) {
                Map.getMap()[i][j].setBuilding(targetBuilding);
                Map.getMap()[i][j].setAvailable(false);
                Map.getMap()[i][j].setPassable(false);
            }
    }

    public static void organizeCastles(int countOfPlayers) {
        int size = Map.getSize();
        Cell[][] map = Map.getMap();
        if (countOfPlayers == 2) {
            placeCastle(Government.getGovernments().get(0), 40, 40);
            placeCastle(Government.getGovernments().get(1), size - 40, size - 40);
            dropStockpile(30, 40, Government.getGovernments().get(0));
            dropStockpile(size - 50, size - 40, Government.getGovernments().get(1));
        }

        if (countOfPlayers == 4) {
            placeCastle(Government.getGovernments().get(0), 40, 40);
            placeCastle(Government.getGovernments().get(2), 40, size - 40);
            placeCastle(Government.getGovernments().get(2), size - 40, 40);
            placeCastle(Government.getGovernments().get(3), size - 40, size - 40);
            dropStockpile(30, 40, Government.getGovernments().get(0));
            dropStockpile(30, size - 40, Government.getGovernments().get(1));
            dropStockpile(size - 50, 40, Government.getGovernments().get(2));
            dropStockpile(size - 50, size - 40, Government.getGovernments().get(3));
        }

        if (countOfPlayers == 4) {
            placeCastle(Government.getGovernments().get(0), 40, 40);
            placeCastle(Government.getGovernments().get(1), 40, size / 2);
            placeCastle(Government.getGovernments().get(2), 40, size - 40);
            placeCastle(Government.getGovernments().get(3), size / 2, 40);
            placeCastle(Government.getGovernments().get(4), size / 2, size - 40);
            placeCastle(Government.getGovernments().get(5), size - 40, 40);
            placeCastle(Government.getGovernments().get(6), size - 40, size / 2);
            placeCastle(Government.getGovernments().get(7), size - 40, size - 40);
            dropStockpile(30, 40, Government.getGovernments().get(0));
            dropStockpile(30, size / 2, Government.getGovernments().get(1));
            dropStockpile(30, size - 40, Government.getGovernments().get(2));
            dropStockpile(size / 2 - 10, 40, Government.getGovernments().get(3));
            dropStockpile(size / 2 - 10, size - 40, Government.getGovernments().get(4));
            dropStockpile(size - 50, 40, Government.getGovernments().get(5));
            dropStockpile(size - 50, size / 2, Government.getGovernments().get(6));
            dropStockpile(size - 50, size - 40, Government.getGovernments().get(7));
        }

        for (Government government : Government.getGovernments()) {
            government.increaseAmountOfGood(Good.GOLD, 100);
            government.increaseAmountOfGood(Good.IRON, 100);
            government.increaseAmountOfGood(Good.STONE, 100);
        }
    }

    public static void placeCastle(Government government, int x, int y) {
        Castle castle = new Castle(x, y, x + 12, y + 12);
        government.setCastle(castle);
        castle.setGovernment(government);
        for (int i = x; i <= x + 12; i++)
            for (int j = y; j <= y + 12; j++)
                Map.getMap()[i][j].setCastle(government.getCastle());
    }
}
