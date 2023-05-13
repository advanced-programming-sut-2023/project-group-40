package model;

public class Castle {
    private int hp = 10000;
    private final int x1,y1,x2,y2;
    private int population = 5;
    private int numberOfWorkers;

    public Castle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getPopulation() {
        return population;
    }

    public void changePopulation(int amount){
        population += amount;
    }

    public void changeNumberOfWorkers(int amount) {
        numberOfWorkers += amount;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }
}
