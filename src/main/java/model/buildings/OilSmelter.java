package model.buildings;

public class OilSmelter extends Building{
    private int rate;

    public OilSmelter(String name, int height, int width, int hp, int[] cost, int engineersRequired, int rate) {
        super(name, height, width, hp, cost, 0, engineersRequired);
        this.rate = rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRate() {
        return rate;
    }

}
