package model.buildings;

import model.Texture;

import java.util.HashSet;

public class Bridge extends Building{
    boolean isUp = false;

    public Bridge(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures,boolean isIllegal) {
        super(name,height ,width , hp, cost,textures,isIllegal);
    }

    public void setUp(boolean up) {
        isUp = up;
    }
}
