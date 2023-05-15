package model.buildings;

import controller.GameMenuController;
import model.Good;
import model.Government;
import model.Texture;

import java.util.HashSet;

public class Mine extends Building {
    private Good material;
    private int productRate;
    private int storage;
    private final int maxStorage = 0;

    public Mine(String name, int height, int width, int hp, int[] cost, int workersRequired, Good material,
                int productRate, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, workersRequired, textures, isIllegal, group);
        this.material = material;
        this.productRate = productRate;
    }

    public Good getMaterial() {
        return material;
    }

    public void setMaterial(Good material) {
        this.material = material;
    }

    public int getProductRate() {
        return productRate;
    }

    public void setProductRate(int productRate) {
        this.productRate = productRate;
    }

    public int getStorage() {
        return storage;
    }


    @Override
    public void action() {
        Government government = GameMenuController.getCurrentGovernment();
        if (name.equals("Quarry")) {
            storage += productRate;
            storage = Math.min(storage, maxStorage);
        }
        if (name.equals("Ox tether")) {
            government.increaseAmountOfGood(material, productRate);
            storage -= productRate;
        } else government.increaseAmountOfGood(material, productRate);
    }

    public void decreaseStorage(int amount) {
        storage -= amount;
    }
}
