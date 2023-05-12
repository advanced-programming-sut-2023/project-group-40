package model.buildings;

import model.Texture;

import java.util.HashSet;

public class EngineerGuild extends Building {
    private final int costOfLadderMan = 0, costOfEngineer = 0;

    public EngineerGuild(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }


    public int getCostOfEngineer() {
        return costOfEngineer;
    }

    public int getCostOfLadderMan() {
        return costOfLadderMan;
    }
}
