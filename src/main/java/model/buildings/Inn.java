package model.buildings;

import model.Texture;

import java.util.HashSet;

public class Inn extends Building{
    private int popularityRate,wineUsage,rate;

    public Inn(String name, int height, int width, int hp, int[] cost, int workersRequired, HashSet<Texture> textures,boolean isIllegal) {
        super(name,height,width, hp, cost, workersRequired,textures,isIllegal);
    }

    public int getPopularityRate() {
        return popularityRate;
    }

    public void setPopularityRate(int popularityRate) {
        this.popularityRate = popularityRate;
    }

    public int getWineUsage() {
        return wineUsage;
    }

    public void setWineUsage(int wineUsage) {
        this.wineUsage = wineUsage;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
