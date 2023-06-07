package model.buildings;

import controller.GameMenuController;
import model.Good;
import model.Texture;

import java.util.HashSet;

public class OilSmelter extends Building {
    private int rate;

    public OilSmelter(String name, int height, int width, int hp, int[] cost, int engineersRequired, int rate,
                      HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, 0, engineersRequired, textures, isIllegal, group);
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public void action() {
        GameMenuController.getCurrentGovernment().increaseAmountOfGood(Good.MELTING_POT, rate);
    }
}
