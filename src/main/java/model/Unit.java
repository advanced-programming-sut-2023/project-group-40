package model;

import model.troops.Troop;
import model.troops.Troops;

import java.util.ArrayList;
import java.util.HashMap;


public class Unit {
    private Government government;
    private final HashMap<Troop,Integer> troops = new HashMap<>();
    private final int sightRange = 3;
    int x;
    int y;
    boolean canDamage = true;
    private String state = "standby";
    private int hp;
    private int velocity;
    private String type;
    private boolean canClimb = false;
    private int shootingRange;
    private int power;
    private boolean havePortableShield;
    private boolean isPatrolling;
    private int patrolTargetX, patrolTargetY;

    public Unit(int x, int y, Government government) {
        this.x = x;
        this.y = y;
        this.government = government;
    }

    public Unit (){

    }

    public void addTroop(Troop troop, int count) {
        power += troop.getPowerOfAttack() * count;
        troops.merge(troop,count,Integer::sum);
        if (troop.getName().equals("Macemen") || troop.getName().equals("Spearmen") ||
                troop.getName().equals("Assassins") || troop.getName().equals("Laddermen"))
            canClimb = true;
        velocity = troop.getVelocity();
        hp = troop.getHp() * count;
        type = troop.getName();
        shootingRange = troop.getShootingRange();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public HashMap<Troop, Integer> getTroops() {
        return troops;
    }

    public void setCanDamage(boolean canDamage) {
        this.canDamage = canDamage;
    }

    public void decreaseHpOfUnit(int amount) {
        hp -= amount * troops.size();
    }

    public Government getGovernment() {
        return government;
    }

    public void decreaseVelocity(int amount) {
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

    public void changeXY(int newX, int newY) {
        Map.getMap()[x][y].removeUnit(this);
        Map.getMap()[x][y].setAvailable(true);
        Map.getMap()[x][y].setPassable(true);
        x = newX;
        y = newY;
        Map.getMap()[x][y].addUnit(this);
        Map.getMap()[x][y].setAvailable(false);
        Map.getMap()[x][y].setPassable(false);
    }


    public boolean isCanClimb() {
        return canClimb;
    }

    public void increaseShootingRange(int percent) {
        this.shootingRange *= (1 + (percent / 100));
    }

    public void changePower(int rate) {
        power *= Math.round((1 + (rate * 5) / 100.0) * rate);
    }

    public int getSightRange() {
        return sightRange;
    }

    public int getShootingRange() {
        return shootingRange;
    }

    public int getPower() {
        return power;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isHavePortableShield() {
        return havePortableShield;
    }

    public void setHavePortableShield(boolean havePortableShield) {
        this.havePortableShield = havePortableShield;
    }

    public boolean isPatrolling() {
        return isPatrolling;
    }

    public void setPatrolling(boolean patrolling) {
        isPatrolling = patrolling;
    }

    public int getPatrolTargetX() {
        return patrolTargetX;
    }

    public int getPatrolTargetY() {
        return patrolTargetY;
    }

    public void setPatrolTargetXY(int patrolTargetX, int patrolTargetY) {
        this.patrolTargetX = patrolTargetX;
        this.patrolTargetY = patrolTargetY;
    }

    public void setXY(int x,int y) {
        this.x = x;
        this.y = y;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void setType(String type) {
        this.type = type;
    }
}
