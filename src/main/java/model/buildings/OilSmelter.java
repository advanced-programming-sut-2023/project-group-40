package model.buildings;

import model.Texture;

import java.util.HashSet;

public class OilSmelter extends Building{
    private int rate;

    public OilSmelter(String name, int height, int width, int hp, int[] cost, int engineersRequired, int rate, HashSet<Texture> textures,boolean isIllegal) {
        super(name, height, width, hp, cost, 0, engineersRequired, textures,isIllegal);
        this.rate = rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

}
