package model.buildings;

import model.Texture;
import model.troops.Troop;
import model.troops.Troops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Barrack extends Building {
    private final HashMap<String,Integer> troopsList = new HashMap<>();

    public Barrack(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal,
                   BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }

    public void addTroop(String type, int count) {
        troopsList.merge(type,count,Integer :: sum);
    }

    public void removeTroop(Troop troop) {
        troopsList.remove(troop);
    }

    public HashMap<String, Integer> getTroopsList() {
        return troopsList;
    }
}
