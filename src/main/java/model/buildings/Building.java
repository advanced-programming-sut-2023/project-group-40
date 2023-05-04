package model.buildings;

import model.BuildingGroups;
import model.Texture;
import model.User;

import java.io.Serializable;
import java.util.HashSet;

public class Building implements Serializable {
    private boolean isIllegal;
    protected BuildingGroups group;
    private final HashSet<Texture> textures;
    protected String name;
    protected int hp;
    // index 0 -> gold 1 -> wood 2 -> stone 3 -> iron 4 -> pitch
    protected int[] cost;
    protected int height,width;
    protected int centerY,centerX;
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

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired, HashSet<Texture> textures,boolean isIllegal, BuildingGroups group) {
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

    public Building(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures,boolean isIllegal, BuildingGroups group) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.hp = hp;
        this.cost = cost;
        this.textures = textures;
        this.isIllegal = isIllegal;
        this.group = group;
    }

    public void setCenter(int centerX,int centerY){
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public boolean checkTexture(Texture texture){
        if (isIllegal && textures.contains(texture))  return false;
        else return isIllegal || textures.contains(texture);
    }
}