package model;

import model.troops.Troop;

import java.util.ArrayList;


public class Unit {
    private Government  government;
    boolean canDamage = true;
    private final ArrayList<Troop> troops = new ArrayList<>();
    private final String state;
    private int hp;

    public Unit(Government government, String state, int hp) {
        this.government = government;
        this.state = state;
        this.hp = hp;
    }

    public void addTroop(Troop troop, int count) {
        for (int i = 0; i < count; i++)
            troops.add(troop);
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

    public void decreaseHpOfUnit(int amount){
        hp -= amount * troops.size();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Government getGovernment() {
        return government;
    }
}
