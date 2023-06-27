package model.buildings;

import model.Texture;
import model.troops.Troop;
import model.troops.Troops;

import java.util.ArrayList;
import java.util.HashSet;

public class Barrack extends Building {
    private final ArrayList<Troop> troopsList = new ArrayList<>();

    public Barrack(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal,
                   BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }

    public void addTroop(String type, int count) {
        for (int i = 0; i < count; i++)
            troopsList.add(Troops.getTroopObjectByType(type));
    }

    public void removeTroop(Troop troop) {
        troopsList.remove(troop);
    }


}
