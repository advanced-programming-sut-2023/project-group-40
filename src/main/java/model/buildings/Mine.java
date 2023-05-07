package model.buildings;

import model.Texture;

import java.util.HashSet;

public class Mine extends Building {
    private Material material;
    private int productRate;

    public Mine(String name, int height, int width, int hp, int[] cost, int workersRequired, Material material, int productRate, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, workersRequired, textures, isIllegal, group);
        this.material = material;
        this.productRate = productRate;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getProductRate() {
        return productRate;
    }

    public void setProductRate(int productRate) {
        this.productRate = productRate;
    }

    @Override
    public String action() {
        return super.action();
    }
}
