package model.buildings;

import controller.GameMenuController;
import model.Good;
import model.Government;
import model.Texture;

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
        int gold = 0, wood = 0;
        gold = government.getNumOfInStorages(Good.GOLD);
        wood = government.getNumOfInStorages(Good.WOOD);
        if (gold < cost[0]) return;
//            return "you don't have enough gold";
        if (wood < cost[1]) return;
//            return "you don't have enough wood";
        switch (name) {
            case "armourer" -> government.increaseAmountOfGood(Good.ARMOR, produceRate);
            case "blacksmith" -> {
                government.increaseAmountOfGood(Good.SWORD, produceRate);
                government.increaseAmountOfGood(Good.MACE, produceRate);
            }
            case "Fletcher" -> government.increaseAmountOfGood(Good.BOW, produceRate);
            case "Poleturner" -> government.increaseAmountOfGood(Good.SPEAR, produceRate);
        }
    }
}
