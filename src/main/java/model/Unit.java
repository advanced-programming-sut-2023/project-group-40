package model;

import model.troops.Troop;

import java.util.ArrayList;

enum UnitState {
}

public class Unit {
    boolean canDamage = true;
    private ArrayList<Troop> troops = new ArrayList<>();
    private UnitState state;

    public Unit(UnitState state) {
        this.state = state;
    }

    public void addTroop(Troop troop) {
    }

    public UnitState getState() {
        return state;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void setCanDamage(boolean canDamage) {
        this.canDamage = canDamage;
    }
}
