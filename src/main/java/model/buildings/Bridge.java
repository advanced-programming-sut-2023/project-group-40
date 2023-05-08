package model.buildings;

import model.Map;
import model.Texture;

import java.util.HashSet;

public class Bridge extends Building {
    boolean isUp = false;

    public Bridge(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    @Override
    public void action() {
        if (isUp) Map.getMap()[x][y].setPassable(false);
    }
}
