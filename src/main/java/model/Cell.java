package model;

import model.buildings.Building;

import java.util.ArrayList;

public class Cell {
    private boolean isAvailable = true,isPassable = true;
    private Building building = null;
    private Tree tree;
    private Rock rock;
    private Texture texture;
    private ArrayList<Troop> troops = new ArrayList<>();

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

    public void addTroop(Troop troop){
        troops.add(troop);
    }

    public void removeTroop(Troop troop){
        troops.remove(troop);
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

    public void decreaseTroopsVelocity(int percent) {
        for (Troop troop : troops)
            troop.decreaseVelocity(percent);
    }
}
