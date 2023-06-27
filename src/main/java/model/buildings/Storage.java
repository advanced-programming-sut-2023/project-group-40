package model.buildings;

import model.Good;
import model.Texture;

import java.util.HashMap;
import java.util.HashSet;

public class Storage extends Building {
    private final HashMap<Good, Integer> products = new HashMap<>();

    private final int capacity;
    private String productType;
    private int currentAmount = 0;

    public Storage(String name, int height, int width, int hp, int[] cost, int capacity, HashSet<Texture> textures,
                   boolean isIllegal, BuildingGroups group, String productType) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.capacity = capacity;
        this.productType = productType;
    }

    public void addProduct(Good product, int number) {
        products.merge(product, number, Integer::sum);
        this.productType = product.getType();
        currentAmount += number;
    }

    public String getProductType() {
        return productType;
    }

    public void removeProduct(Good product) {
        products.remove(product);
    }

    public void decreaseAmountOfProduct(Good product, int amount) {
        int size = products.get(product);
        products.remove(product);
        products.put(product, size - amount);
    }

    public HashMap<Good, Integer> getProducts() {
        return products;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }
}
