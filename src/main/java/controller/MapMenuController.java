package controller;

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
}
