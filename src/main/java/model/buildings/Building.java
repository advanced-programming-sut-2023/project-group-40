package model.buildings;

import model.Government;
import model.Texture;
import model.Unit;

import java.io.Serializable;
import java.util.HashSet;

public class Building implements Serializable,BuildingAction {
    protected int x1, y1, x2, y2;
    private boolean isIllegal;
    protected BuildingGroups group;
    private HashSet<Texture> textures;
    protected String name;
    protected int maxHp;
    protected int hp;
    // index 0 -> gold 1 -> wood 2 -> stone 3 -> iron 4 -> pitch
    protected int[] cost;
    protected int height, width;
    protected Government owner;
    protected int workersRequired = 0, engineersRequired = 0;

    public Building(String name, int height, int width, int hp, int[] cost) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.maxHp = hp;
        this.hp = hp;
        this.cost = cost;
    }

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired, int engineersRequired, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
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

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
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

    public Building(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
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

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public Government getOwner() {
        return owner;
    }

    public boolean checkTexture(Texture texture) {
        if (isIllegal && textures.contains(texture)) return false;
        else return isIllegal || textures.contains(texture);
    }

    public String getName() {
        return name;
    }

    public boolean isNearGate(Unit unit) {
        if (x1 - 1 == unit.getX() || x2 + 1 == unit.getX())
            return true;
        return y2 + 1 == unit.getY() || y1 - 1 == unit.getY();
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

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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
}