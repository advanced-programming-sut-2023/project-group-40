package model.buildings;

import controller.GameMenuController;
import model.Texture;
import model.troops.Troop;
import model.troops.Troops;

import java.util.ArrayList;
import java.util.HashSet;

public class Church extends Building {
    private final int increasePopularity;
    private final ArrayList<Troop> troopsList = new ArrayList<>();

    public Church(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal,
                  BuildingGroups group, int increasePopularity) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.increasePopularity = increasePopularity;
    }
    public void addTroop(String type, int count) {
        for (int i = 0; i < count; i++)
            troopsList.add(Troops.getTroopObjectByType(type));
    }
    @Override
    public void action() {
        GameMenuController.getCurrentGovernment().changePopularity(increasePopularity);
    }
}
