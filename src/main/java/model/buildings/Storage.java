package model.buildings;

import controller.GameMenuController;
import model.*;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.HashSet;

public class Storage<T> extends Building {
    private final HashMap<T, Integer> products = new HashMap<>();
    private Class<T> clazz;

    private final int capacity;

    public Storage(String name, int height, int width, int hp, int[] cost, int capacity, HashSet<Texture> textures, boolean isIllegal, Class<T> clazz, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.capacity = capacity;
        this.clazz = clazz;
        if (clazz.equals(Weapon.class)) GameMenuController.getCurrentGovernment().addWeaponStorage(this);
        else if (clazz.equals(Food.class)) GameMenuController.getCurrentGovernment().addFoodStorage(this);
        else if (clazz.equals(Material.class)) GameMenuController.getCurrentGovernment().addMaterialStorage(this);
    }

    public void addProduct(T product, int number) {
        products.put(product, products.get(product) + number);
    }

    public void removeProduct(T product) {
        products.remove(product);
    }

    public HashMap<T, Integer> getProducts() {
        return products;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSumOfProducts(T key){
        int count = 0;
        for (int i = 0 ; i < products.size() ; i ++)
            count += products.get(key);
        return count;
    }
}
