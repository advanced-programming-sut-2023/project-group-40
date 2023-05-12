package model;

public class Castle {
    int hp;
    int cornerUpLeft;
    int cornerDownRight;
    private int population = 5;
    private int numberOfLadderMan = 0;

    public Castle(int hp, int cornerUpLeft, int cornerDownRight, int population) {
        this.hp = hp;
        this.cornerUpLeft = cornerUpLeft;
        this.cornerDownRight = cornerDownRight;
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public void changePopulation(int amount){
        population += amount;
    }
    public void increaseNumberOfLadderMan(int amount) {
        this.numberOfLadderMan += amount;
    }
    public int getNumberOfLadderMan() {
        return this.numberOfLadderMan;
    }
}
