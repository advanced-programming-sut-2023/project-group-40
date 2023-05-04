package model;

public class Map {
private static int size;
private static Cell[][] map;

    public static void initMap(int size){
        Map.size = size;
        map = new Cell[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                map[i][j] = new Cell(Texture.LAND);
    }
    public static int getSize() {
        return size;
    }

    public static Cell[][] getMap() {
        return Map.map;
    }
}
