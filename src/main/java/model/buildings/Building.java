package model.buildings;

import model.User;

import java.io.Serializable;

public class Building implements Serializable {
    protected String name;
    protected int hp;
    // index 0 -> gold 1 -> wood 2 -> stone 3 -> iron 4 -> pitch
    protected int[] cost;
    protected int height,width;
    protected int centerY,centerX;
    protected User owner;
    protected int workersRequired = 0, engineersRequired = 0;

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired, int engineersRequired) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.hp = hp;
        this.cost = cost;
        this.workersRequired = workersRequired;
        this.engineersRequired = engineersRequired;
    }

    public Building(String name, int height, int width, int hp, int[] cost, int workersRequired) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.hp = hp;
        this.cost = cost;
        this.workersRequired = workersRequired;
    }


    public Building(String name, int height, int width, int hp, int[] cost) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.hp = hp;
        this.cost = cost;
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
}