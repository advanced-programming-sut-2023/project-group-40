package model.buildings;

import model.BuildingGroups;
import model.Texture;
import model.Troop;

import java.util.ArrayList;
import java.util.HashSet;

public class Barrack extends Building{
    private int costOfTroop;
    private ArrayList<Troop>  troopsList = new ArrayList<>();

    public Barrack(String name, int height, int width, int hp, int[] cost, int costOfTroop, HashSet<Texture> textures,boolean isIllegal, BuildingGroups group) {
        super(name,height ,width , hp, cost,textures,isIllegal, group);
        this.costOfTroop = costOfTroop;
    }

    public void addTroop(Troop troop){
        troopsList.add(troop);
    }

    public void removeTroop(Troop troop){
        troopsList.remove(troop);
    }


}
