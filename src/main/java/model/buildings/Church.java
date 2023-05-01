package model.buildings;

import model.Texture;

import java.util.HashSet;

public class Church extends Building{
    private final int increasePopularity = 2;

    public Church(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures,boolean isIllegal) {
        super(name, height,width , hp, cost,textures,isIllegal);
    }
}
