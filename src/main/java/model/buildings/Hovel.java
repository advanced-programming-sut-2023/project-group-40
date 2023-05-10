package model.buildings;

import model.Texture;

import java.util.HashSet;

public class Hovel extends Building {
    private final int maxCapacity = 8;
    private int capacity;

    public Hovel(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
