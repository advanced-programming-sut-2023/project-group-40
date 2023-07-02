package model;


public class Castle {
    private final int x1, y1, x2, y2;
    private final int maxPopulation = 5;
    private int hp = 10000;
    private int population = 0;
    private int numberOfActiveWorker = 5;
    private Government government;

    public Castle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public int getPopulation() {
        return population;
    }


    public void changePopulation(int amount) {
        if (population + amount > maxPopulation) population = maxPopulation;
        else population += amount;
    }

    public int getNumberOfActiveWorker() {
        return numberOfActiveWorker;
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

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public void decreaseHp(int amount) {
        hp -= amount;
    }

    public void changeNumberOfActiveWorkers(int amount) {
        numberOfActiveWorker += amount;
    }
}
