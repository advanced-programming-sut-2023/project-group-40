package model;

import model.buildings.Building;

import java.util.ArrayList;

public class Cell {
    private boolean isAvailable = true, isPassable = true;
    private Building building = null;
    private Tree tree;
    private Rock rock;
    private Texture texture;
    private final ArrayList<Unit> units = new ArrayList<>();
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

    public Castle getCastle() {
        return castle;
    }

    public void addUnit(Unit unit){
        units.add(unit);
    }

    public Unit getUnit() {
        if(units.size() == 0)
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

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public Tool getTool() {
        return tool;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public void setStair(boolean stair) {
        isStair = stair;
    }

    public boolean isStair() {
        return isStair;
    }
}
