package model;

import model.troops.Troop;

import java.util.ArrayList;


public class Unit {
    int x;
    int y;
    private Government government;
    boolean canDamage = true;
    private final ArrayList<Troop> troops = new ArrayList<>();
    private final String state;
    private int hp;
    private int velocity;
    private String type;
    private boolean canClimb = false;
    private int shootingRange;

    public Unit(int x , int y,Government government, String state, int hp) {
        this.x = x;
        this.y = y;
        this.government = government;
        this.state = state;
        this.hp = hp;
    }

    public void addTroop(Troop troop, int count) {
        for (int i = 0; i < count; i++)
            troops.add(troop);
        if (troop.getName().equals("Macemen") || troop.getName().equals("Spearmen") ||troop.getName().equals("Assassins"))
            canClimb = true;
        velocity = troop.getVelocity();
        type = troop.getName();
        shootingRange = troop.getShootingRange();
    }

    public String getState() {
        return state;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void setCanDamage(boolean canDamage) {
        this.canDamage = canDamage;
    }

    public void decreaseHpOfUnit(int amount) {
        hp -= amount * troops.size();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Government getGovernment() {
        return government;
    }

    public void decreaseVelocity(int amount){
        velocity -= amount;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelocity() {
        return velocity;
    }

    public void changeX(int amount){
        Map.getMap()[x][y].setUnit(null);
        x += amount;
        Map.getMap()[x][y].setUnit(this);
    }

    public void changeY(int amount){
        Map.getMap()[x][y].setUnit(null);
        y += amount;
        Map.getMap()[x][y].setUnit(this);
    }

    public boolean isCanClimb() {
        return canClimb;
    }

    public void increaseShootingRange(int percent) {
        this.shootingRange *= (1 + (percent/100));
    }
}
