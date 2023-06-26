package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.*;
import model.buildings.Building;
import model.buildings.Buildings;
import view.GameMenu;

public class EnvironmentMenuController {
    private static ImageView[][] map;
    private static SimpleDoubleProperty textureSize;
    private static Pane mapPane;

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
        return Government.getGovernmentByUser(ConnectToServer.getUserByUsername(username)) != null;
    }


    public static boolean checkTexture(int x, int y, Texture texture) {
        return Map.getMap()[x][y].getBuilding() == null;
    }

    public static void setTexture(int i, int j, Texture texture) {
        if (texture == Texture.STONE || texture == Texture.PONE || texture == Texture.SEA)
            Map.getMap()[i][j].setPassable(false);
        if (texture == Texture.STONE) Map.getMap()[i][j].setAvailable(false);
        Map.getMap()[i][j].setTexture(texture);
    }


    public static String clearBlock(int x, int y) {
        Map.getMap()[x][y].setRock(null);
        Map.getMap()[x][y].setTree(null);
        Map.getMap()[x][y].setTexture(Texture.LAND);
        return "clear block successfully applied";
    }

    public static void dropRock(int x, int y, Rock rock) {
        Map.getMap()[x][y].setRock(rock);
        Map.getMap()[x][y].setAvailable(false);
        Map.getMap()[x][y].setPassable(false);
    }

    public static boolean checkDropRock(int x, int y, Rock rock) {
        if (Map.getMap()[x][y].getTree() != null)
            return false;
        return Map.getMap()[x][y].getBuilding() == null;
    }

    public static boolean checkDropTree(int x, int y, String type) {
        return Map.getMap()[x][y].getTexture() == Texture.LAND ||
                Map.getMap()[x][y].getTexture() == Texture.GRASS
                || Map.getMap()[x][y].getTexture() == Texture.GRASS_LAND ||
                Map.getMap()[x][y].getTexture() == Texture.DENSE_GRASS_LAND;
    }

    public static void dropTree(int x, int y, String type) {
        Map.getMap()[x][y].setTree(Tree.getTree(type));
        Map.getMap()[x][y].setAvailable(false);
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

    public static void organizeCastles(int countOfPlayers, int size) {
        if (countOfPlayers == 2) {
            placeCastle(40, 40);
            placeCastle(size - 40, size - 40);
//            dropStockpile(30, 40, Government.getGovernments().get(0));
//            dropStockpile(size - 50, size - 40, Government.getGovernments().get(1));
        }

        if (countOfPlayers == 4) {
            placeCastle(40, 40);
            placeCastle(40, size - 40);
            placeCastle(size - 40, 40);
            placeCastle(size - 40, size - 40);
//            dropStockpile(30, 40, Government.getGovernments().get(0));
//            dropStockpile(30, size - 40, Government.getGovernments().get(1));
//            dropStockpile(size - 50, 40, Government.getGovernments().get(2));
//            dropStockpile(size - 50, size - 40, Government.getGovernments().get(3));
        }

        if (countOfPlayers == 8) {
            placeCastle(40, 40);
            placeCastle(40, size / 2);
            placeCastle(40, size - 40);
            placeCastle(size / 2, 40);
            placeCastle(size / 2, size - 40);
            placeCastle(size - 40, 40);
            placeCastle(size - 40, size / 2);
            placeCastle(size - 40, size - 40);
//            dropStockpile(30, 40, Government.getGovernments().get(0));
//            dropStockpile(30, size / 2, Government.getGovernments().get(1));
//            dropStockpile(30, size - 40, Government.getGovernments().get(2));
//            dropStockpile(size / 2 - 10, 40, Government.getGovernments().get(3));
//            dropStockpile(size / 2 - 10, size - 40, Government.getGovernments().get(4));
//            dropStockpile(size - 50, 40, Government.getGovernments().get(5));
//            dropStockpile(size - 50, size / 2, Government.getGovernments().get(6));
//            dropStockpile(size - 50, size - 40, Government.getGovernments().get(7));
        }

        for (Government government : Government.getGovernments()) {
            government.increaseAmountOfGood(Good.GOLD, 100);
            government.increaseAmountOfGood(Good.IRON, 100);
            government.increaseAmountOfGood(Good.STONE, 100);
        }
    }

//    public static void placeCastle(Government government, int x, int y) {
//        government.setCastle(castle);
//        castle.setGovernment(government);
//    }

    public static void placeCastle(int x, int y) {
        Castle castle = new Castle(x, y, x + 12, y + 12);
        for (int i = x; i <= x + 12; i++)
            for (int j = y; j <= y + 12; j++)
                Map.getMap()[i][j].setCastle(castle);
        ImageView imageView = new ImageView(new Image(Rock.class.getResource("/images/castle.png").toString()));
        imageView.fitWidthProperty().bind(textureSize.multiply(12));
        imageView.fitHeightProperty().bind(textureSize.multiply(12));
        imageView.translateXProperty().bind(map[x][y].translateXProperty());
        imageView.translateYProperty().bind(map[x][y].translateYProperty());
        mapPane.getChildren().add(imageView);
    }

    public static void setMap(ImageView[][] map) {
        EnvironmentMenuController.map = map;
    }

    public static void setMapPane(Pane mapPane) {
        EnvironmentMenuController.mapPane = mapPane;
    }

    public static void setTextureSize(SimpleDoubleProperty textureSize) {
        EnvironmentMenuController.textureSize = textureSize;
    }
}
