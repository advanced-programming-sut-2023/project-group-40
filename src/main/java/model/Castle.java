package model;

public class Castle {
    int hp;
    int cornerUpLeft;
    int cornerDownRight;
    private int population = 5;
    private int numberOfWorker = 5;
    private int numberOfActiveWorker = 0;

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
    public int getNumberOfWorker() {
        return this.numberOfWorker;
    }
    public int getNumberOfActiveWorker() {
        return numberOfActiveWorker;
    }
    public void increaseNumberOfActiveWorker(int count) {
        numberOfWorker += count;
    }
}
