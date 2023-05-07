package model.buildings;

import model.Food;
import model.Texture;

import java.util.HashSet;

public class FoodProcessing extends Building {
    private Food material;
    private Food product;
    private Integer productRate;

    public FoodProcessing(String name, int height, int width, int hp, int[] cost, int workersRequired, Food material, Food product, Integer productRate, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, workersRequired, textures, isIllegal, group);
        this.material = material;
        this.product = product;
        this.productRate = productRate;
    }

    public Food getMaterial() {
        return material;
    }

    public void setMaterial(Food material) {
        this.material = material;
    }

    public Food getProduct() {
        return product;
    }

    public void setProduct(Food product) {
        this.product = product;
    }

    public Integer getProductRate() {
        return productRate;
    }

    public void setProductRate(Integer productRate) {
        this.productRate = productRate;
    }
}
