package model;

public enum Tool {
    BATTERING_RAM(1,4,0);
    private int velocity;
    private int numberOfEngineer = 0;
    private int hp;

    Tool(int velocity, int numberOfEngineer, int hp) {
        this.velocity = velocity;
        this.numberOfEngineer = numberOfEngineer;
        this.hp = hp;
    }

    public static Tool getToolByName(String name){
        for (Tool tool : values()) {
            if (tool.name().toLowerCase().equals(name))
                return tool;
        }
        return null;
    }

    public int getNumberOfEngineer() {
        return numberOfEngineer;
    }
}
