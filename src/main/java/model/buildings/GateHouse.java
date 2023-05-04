package model.buildings;

import model.BuildingGroups;
import model.Texture;

import java.util.HashSet;

public class GateHouse extends Building {
    private final int maxCapacity;
    private int capacity = 0;

    public GateHouse(String name, int height, int width, int hp, int[] cost, int maxCapacity, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.maxCapacity = maxCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
