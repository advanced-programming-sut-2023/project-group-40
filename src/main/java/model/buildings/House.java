package model.buildings;

public class House extends Building{
    private final int increasePopulation = 8;

    public House(String name, int height, int width, int hp, int[] cost) {
        super(name,height ,width , hp, cost);
    }
}
