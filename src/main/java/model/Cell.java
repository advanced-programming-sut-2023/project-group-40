package model;

import model.buildings.Building;
import model.troops.Troop;

public class Cell {
    private boolean isAvailable = true,isPassable = true;
    private Building building = null;
    private Tree tree;
    private Rock rock;
    private Texture texture;
    private Unit unit;

    public Cell(Texture texture) {
        this.texture = texture;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }

    public void setRock(Rock rock) {
        this.rock = rock;
    }

    public Rock getRock() {
        return rock;
    }

    public void changeTroopsVelocity(int percent) {
        for (Troop troop : unit.getTroops())
            troop.changeVelocity(percent);
    }

    public Unit getUnit() {
        return unit;
    }
}
