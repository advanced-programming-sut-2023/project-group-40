package model.buildings;

import controller.GameMenuController;
import model.Texture;

import java.util.HashSet;

public class Church extends Building {
    private final int increasePopularity;

    public Church(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal,
                  BuildingGroups group, int increasePopularity) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.increasePopularity = increasePopularity;
    }

    @Override
    public void action() {
        GameMenuController.getCurrentGovernment().changePopularity(increasePopularity);
    }
}
