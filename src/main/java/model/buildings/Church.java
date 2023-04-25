package model.buildings;

public class Church extends Building{
    private final int increasePopularity = 2;

    public Church(String name, int height, int width, int hp, int[] cost) {
        super(name, height,width , hp, cost);
    }
}
