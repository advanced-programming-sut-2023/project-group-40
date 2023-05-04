package model.buildings;

import model.BuildingGroups;
import model.Texture;

import java.util.HashSet;

public class EngineerGuild extends Building {
    private final int CostOfLadderMan = 0, CostOfEngineer = 0;

    public EngineerGuild(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }
}
