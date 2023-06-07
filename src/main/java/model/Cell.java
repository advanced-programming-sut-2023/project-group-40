package model;

import model.buildings.Building;

import java.util.ArrayList;

public class Cell {
    private final ArrayList<Unit> units = new ArrayList<>();
    private boolean isAvailable = true, isPassable = true;
    private Building building = null;
    private Tree tree;
    private Rock rock;
    private Texture texture;
    private Wall wall;
    private boolean isStartDigging;
    private boolean haveDitch;
    private Government ditchOwner;
    private Castle castle;
    private Tool tool;
    private boolean isStair;

    public Cell(Texture texture) {
        this.texture = texture;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Rock getRock() {
        return rock;
    }

    public void setRock(Rock rock) {
        this.rock = rock;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public boolean isAvailable() {
        if (texture.getType().equals("water"))
            return false;
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isPassable() {
        return isPassable;
    }

    public void setPassable(boolean passable) {
        isPassable = passable;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public boolean isHaveDitch() {
        return haveDitch;
    }

    public void setHaveDitch(boolean haveDitch) {
        this.haveDitch = haveDitch;
    }

    public boolean isStartDigging() {
        return isStartDigging;
    }

    public void setStartDigging(boolean startDigging) {
        isStartDigging = startDigging;
    }

    public Government getDitchOwner() {
        return ditchOwner;
    }

    public void setDitchOwner(Government ditchOwner) {
        this.ditchOwner = ditchOwner;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    public Unit getUnit() {
        if (units.size() == 0)
            return null;
        return units.get(0);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
        if (units.size() == 0) {
            this.setAvailable(true);
            this.setPassable(true);
        }
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public boolean isStair() {
        return isStair;
    }

    public void setStair(boolean stair) {
        isStair = stair;
    }
}
