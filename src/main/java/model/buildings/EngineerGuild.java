package model.buildings;

import model.Texture;

import java.util.HashSet;

public class EngineerGuild extends Building {
    private final int costOfLadderMan = 0, costOfEngineer = 0;
    private int numberOfEngineer, numberOfLadderMan;

    public EngineerGuild(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }

    public void increaseNumberOfEngineer(int amount) {
        this.numberOfEngineer += amount;
    }

    public void increaseNumberOfLadderMan(int amount) {
        this.numberOfLadderMan += amount;
    }

    public int getCostOfEngineer() {
        return costOfEngineer;
    }

    public int getCostOfLadderMan() {
        return costOfLadderMan;
    }
}
