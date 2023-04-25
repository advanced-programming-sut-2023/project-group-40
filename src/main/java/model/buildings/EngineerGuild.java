package model.buildings;

public class EngineerGuild extends Building{
    private final int CostOfLadderMan = 0,CostOfEngineer = 0;

    public EngineerGuild(String name, int height, int width, int hp, int[] cost) {
        super(name,height ,width , hp, cost);
    }
}
