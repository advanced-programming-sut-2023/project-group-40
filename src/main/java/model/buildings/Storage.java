package model.buildings;

import model.Texture;

import java.util.HashMap;
import java.util.HashSet;

public class Storage<T> extends Building{
    private HashMap<T, Integer> products = new HashMap<>();

    private int capacity;

    public Storage(String name, int height, int width, int hp, int[] cost, int capacity, HashSet<Texture> textures,boolean isIllegal) {
        super(name, height, width, hp, cost,textures,isIllegal);
        this.capacity = capacity;
    }

    public void addProduct(T product, int number){
        products.put(product,products.get(product) + number);
    }

    public void removeProduct(T product){
        products.remove(product);
    }

    public int getCapacity() {
        return capacity;
    }
}
