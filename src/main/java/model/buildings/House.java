package model.buildings;

import model.Texture;

import java.util.HashSet;

public class House extends Building{
    private final int increasePopulation = 8;

    public House(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures,boolean isIllegal) {
        super(name,height ,width , hp, cost,textures,isIllegal);
    }
}
