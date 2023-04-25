package model.buildings;

import model.Troop;

import java.util.ArrayList;

public class Barrack extends Building{
    private int costOfTroop;
    private ArrayList<Troop>  troopsList = new ArrayList<>();

    public Barrack(String name, int height, int width, int hp, int[] cost, int costOfTroop) {
        super(name,height ,width , hp, cost);
        this.costOfTroop = costOfTroop;
    }

    public void addTroop(Troop troop){
        troopsList.add(troop);
    }

    public void removeTroop(Troop troop){
        troopsList.remove(troop);
    }


}
