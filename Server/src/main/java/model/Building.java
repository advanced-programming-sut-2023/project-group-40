package model;


import java.io.Serializable;
import java.util.HashSet;

public class Building implements Serializable {
    protected int x1, y1, x2, y2;
    protected BuildingGroups group;
    protected String name;
    protected int maxHp;
    protected int hp;
    // index 0 -> gold 1 -> wood 2 -> stone 3 -> iron 4 -> pitch
    protected int[] cost;
    protected int height, width;
    protected String owner;
    protected int workersRequired = 0, engineersRequired = 0;
    private boolean isIllegal;
    private HashSet<Texture> textures;

    public Building(String name, int height, int width, int hp, int[] cost) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.maxHp = hp;
        this.hp = hp;
        this.cost = cost;
    }

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired, int engineersRequired,
                    HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.maxHp = hp;
        this.hp = hp;
        this.cost = cost;
        this.workersRequired = workersRequired;
        this.engineersRequired = engineersRequired;
        this.textures = textures;
        this.isIllegal = isIllegal;
        this.group = group;
    }

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired,
                    HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.maxHp = hp;
        this.hp = hp;
        this.cost = cost;
        this.workersRequired = workersRequired;
        this.textures = textures;
        this.isIllegal = isIllegal;
        this.group = group;
    }

    public Building(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures,
                    boolean isIllegal, BuildingGroups group) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.maxHp = hp;
        this.hp = hp;
        this.cost = cost;
        this.textures = textures;
        this.isIllegal = isIllegal;
        this.group = group;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean checkTexture(Texture texture) {
        if (isIllegal && textures.contains(texture)) return false;
        else return isIllegal || textures.contains(texture);
    }

    public String getName() {
        return name;
    }


    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int[] getCost() {
        return cost;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public BuildingGroups getGroup() {
        return group;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getWorkersRequired() {
        return workersRequired;
    }

    public void setXCoordinates(int x1) {
        this.x1 = x1;
        this.x2 = x1 + height;
    }

    public void setYCoordinates(int y1) {
        this.y1 = y1;
        this.y2 = y1 + width;
    }

}