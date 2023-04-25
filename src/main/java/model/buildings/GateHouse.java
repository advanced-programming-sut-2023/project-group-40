package model.buildings;

public class GateHouse extends Building{
    private final int maxCapacity;
    private int capacity = 0;

    public GateHouse(String name, int height, int width, int hp, int[] cost, int maxCapacity) {
        super(name, height, width, hp, cost);
        this.maxCapacity = maxCapacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
