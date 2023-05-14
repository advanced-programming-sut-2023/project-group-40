package model;

public class Castle {
    private int hp = 10000;
    private final int x1,y1,x2,y2;
    private int population = 5;
    private int numberOfWorkers = 5;
    private int numberOfActiveWorker;

    public Castle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getPopulation() {
        return population;
    }

    public void changePopulation(int amount) {
        population += amount;
    }

    public void changeNumberOfWorkers(int amount) {
        numberOfWorkers += amount;
    }
    public void setNumberOfActiveWorker(int amount) {
        numberOfActiveWorker += amount;
    }
    public int getNumberOfActiveWorker() {
        return numberOfActiveWorker;
    }

    public int getNumberOfWorkers() {
        return numberOfWorkers;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }
}
