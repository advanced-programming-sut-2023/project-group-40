package model.buildings;

import model.Texture;
import model.User;

import java.io.Serializable;
import java.util.HashSet;

public class Building implements BuildingAction {
    int x1, x2, y1, y2;
    private boolean isIllegal;
    protected BuildingGroups group;
    private final HashSet<Texture> textures;
    protected String name;
    protected int hp;
    // index 0 -> gold 1 -> wood 2 -> stone 3 -> iron 4 -> pitch
    protected int[] cost;
    protected int height, width;
    protected User owner;
    protected int workersRequired = 0, engineersRequired = 0;

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired, int engineersRequired, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.hp = hp;
        this.cost = cost;
        this.workersRequired = workersRequired;
        this.engineersRequired = engineersRequired;
        this.textures = textures;
        this.isIllegal = isIllegal;
        this.group = group;
    }

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.hp = hp;
        this.cost = cost;
        this.workersRequired = workersRequired;
        this.textures = textures;
        this.isIllegal = isIllegal;
        this.group = group;
    }

    public Building(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.hp = hp;
        this.cost = cost;
        this.textures = textures;
        this.isIllegal = isIllegal;
        this.group = group;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public boolean checkTexture(Texture texture) {
        if (isIllegal && textures.contains(texture)) return false;
        else return isIllegal || textures.contains(texture);
    }

    public String getName() {
        return name;
    }

    public void setCoordinates(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }
    public int getX2() { return x2;}
    public int getY1() {
        return y1;
    }
    public int getY2() {
        return y2;
    }

    public int[] getCost() {
        return cost;
    }
}