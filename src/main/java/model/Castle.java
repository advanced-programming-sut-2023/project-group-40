package model;

public class Castle {
    int hp;
    int cornerUpLeft;
    int cornerDownRight;
    private int population = 5;

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
}
