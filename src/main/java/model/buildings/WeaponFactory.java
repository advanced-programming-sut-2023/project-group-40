package model.buildings;

import controller.GameMenuController;
import model.Good;
import model.Government;
import model.Texture;

import java.util.HashSet;

import java.util.HashSet;

public class WeaponFactory extends Building {
    private final Good material;
    private final Good weapon;
    private int produceRate;

    public WeaponFactory(String name, int height, int width, int hp, int[] cost, int workersRequired, Good material, Good weapon, int produceRate, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
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

    @Override
    public void action() {
        Government government = GameMenuController.getCurrentGovernment();
        if (government.getAmountOfGood(material) < produceRate) return;
        if (government.getNumOfEmptySpace("weapon") < produceRate) return;
        government.decreaseAmountOfGood(material,produceRate);
        government.increaseAmountOfGood(weapon,produceRate);
        if (name.equals("blacksmith"))
            government.increaseAmountOfGood(Good.MACE, produceRate);
    }
}
