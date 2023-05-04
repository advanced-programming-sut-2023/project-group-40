package model;

public class Map {
private int size;
private Cell[][] map;

    public Map(int size) {
        this.size = size;
        map = new Cell[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                map[i][j] = new Cell(Texture.LAND);
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getMap() {
        return map;
    }
}
