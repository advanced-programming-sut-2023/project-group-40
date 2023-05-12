package model.buildings;

import controller.GameMenuController;
import model.*;

import java.util.HashMap;
import java.util.HashSet;

public class Storage extends Building {
    private final HashMap<Good, Integer> products = new HashMap<>();

    private final int capacity;
    private String productType;

    public Storage(String name, int height, int width, int hp, int[] cost, int capacity, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.capacity = capacity;
        GameMenuController.getCurrentGovernment().addStorage(this);
    }

    public void addProduct(Good product, int number) {
        products.put(product, products.get(product) + number);
        this.productType = product.getType();
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

    public int getSumOfProducts(Good key) {
        int count = 0;
        for (int i = 0; i < products.size(); i++)
            count += products.get(key);
        return count;
    }

}
