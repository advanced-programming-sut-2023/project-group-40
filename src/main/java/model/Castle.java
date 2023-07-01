package model;

import controller.ConnectToServer;
import controller.GameMenuController;

public class Castle {
    private final int x1, y1, x2, y2;
    private final int maxPopulation = 5;
    private int hp = 10000;
    private int population = 5;
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

    public void checkCastle() {
        if (hp <= 0) {
            Government.getGovernments().remove(government);
//            System.out.println(government.getOwner().getUsername() + "lose!");
            if (Government.getGovernments().size() == 1) {
//                User winner = Government.getGovernments().get(0).getOwner();
//                System.out.println(winner.getUsername() + " wins!");
//                winner.setHighScore(1000 * GameMenuController.getNumberOfPlayers());
                ConnectToServer.updateRank();
                System.exit(0);
            }
        }
    }

    public void changeNumberOfActiveWorkers(int amount) {
        numberOfActiveWorker += amount;
    }
}
