package model;

import model.buildings.Building;

import java.util.ArrayList;

public class Cell {
    private boolean isAvailable = true,isPassable = true;
    private Building building;
    private Tree tree;
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
}
