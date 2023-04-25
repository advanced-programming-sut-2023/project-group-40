package model;

import java.util.ArrayList;

enum UnitState {
}

enum Speed {
}

public class Unit {
    private ArrayList<Troop> troops = new ArrayList<>();
    private UnitState state;
    private int range;
    private Speed speed;

    public Unit( UnitState state, int range, Speed speed) {
        this.state = state;
        this.range = range;
        this.speed = speed;
    }

    public void addTroop(Troop troop){
    }

    public UnitState getState() {
        return state;
    }

    public int getRange() {
        return range;
    }

    public Speed getSpeed() {
        return speed;
    }
}
