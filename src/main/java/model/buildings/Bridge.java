package model.buildings;

public class Bridge extends Building{
    boolean isUp = false;

    public Bridge(String name, int height, int width, int hp, int[] cost) {
        super(name,height ,width , hp, cost);
    }

    public void setUp(boolean up) {
        isUp = up;
    }
}
