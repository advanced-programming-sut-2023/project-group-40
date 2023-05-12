package model;

import model.buildings.Building;
import model.troops.Troop;

import java.util.ArrayList;

public class Cell {
    private boolean isAvailable = true, isPassable = true;
    private Building building = null;
    private Tree tree;
    private Rock rock;
    private Texture texture;
    private Unit unit;
    private Wall wall;
    private boolean isStartDigging;
    private boolean haveDitch;
    private Government ditchOwner;

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

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public Tree getTree() {
        return tree;
    }


    public boolean isAvailable() {
        if (texture.getType().equals("water"))
            return false;
        return isAvailable;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public Wall getWall() {
        return wall;
    }

    public void setHaveDitch(boolean haveDitch) {
        this.haveDitch = haveDitch;
    }

    public void setStartDigging(boolean startDigging) {
        isStartDigging = startDigging;
    }

    public boolean isHaveDitch() {
        return haveDitch;
    }

    public boolean isStartDigging() {
        return isStartDigging;
    }

    public void setDitchOwner(Government ditchOwner) {
        this.ditchOwner = ditchOwner;
    }

    public Government getDitchOwner() {
        return ditchOwner;
    }
}
