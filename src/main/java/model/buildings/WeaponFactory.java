package model.buildings;

import model.BuildingGroups;
import model.Material;
import model.Texture;
import model.Weapon;

import java.util.HashSet;

public class WeaponFactory extends Building {
    private final Material material;
    private final Weapon weapon;
    private int produceRate;

    public WeaponFactory(String name, int height, int width, int hp, int[] cost, int workersRequired, Material material, Weapon weapon,
                         int produceRate, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, workersRequired, textures, isIllegal, group);
        this.material = material;
        this.weapon = weapon;
        this.produceRate = produceRate;
    }

    public int getProduceRate() {
        return produceRate;
    }

    public void setProduceRate(int produceRate) {
        this.produceRate = produceRate;
    }
}
