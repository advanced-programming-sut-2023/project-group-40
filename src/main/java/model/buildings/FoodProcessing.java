package model.buildings;


import controller.GameMenuController;
import model.*;
import view.GameMenu;

import java.util.HashSet;

public class FoodProcessing extends Building {
    private Good material;
    private Good product;
    private Integer productRate;

    public FoodProcessing(String name, int height, int width, int hp, int[] cost, int workersRequired, Good material, Good product, Integer productRate, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, workersRequired, textures, isIllegal, group);
        this.material = material;
        this.product = product;
        this.productRate = productRate;
    }

    public Good getMaterial() {
        return material;
    }

    public void setMaterial(Good material) {
        this.material = material;
    }

    public Good getProduct() {
        return product;
    }

    public void setProduct(Good product) {
        this.product = product;
    }

    public Integer getProductRate() {
        return productRate;
    }

    public void setProductRate(Integer productRate) {
        this.productRate = productRate;
    }

    @Override
    public void action() {
        Government government = GameMenuController.getCurrentGovernment();
        String message = government.decreaseAmountOfGood(material,productRate);
        if (message.endsWith("successfully")) government.increaseAmountOfGood(product,productRate);
    }
}
