package model.buildings;

import model.BuildingGroups;
import model.Texture;

import java.util.HashSet;

public class KillingPit extends Building {
    private final int damage;

    public KillingPit(String name, int height, int width, int hp, int[] cost, int damage, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
