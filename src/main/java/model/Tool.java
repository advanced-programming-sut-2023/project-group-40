package model;

public enum Tool {
    SIEGE_TOWER("siege tower",0,4,0,150),
    PORTABLE_SHIELD("portable shield",0,1,0,5),
    BATTERING_RAM("battering ram",2,4,1000,100),
    CATAPULT("catapult",6,4,100,50),
    CATAPULT_WITH_BALANCE_WEIGHT("catapult with balance weight",3,2,200,100),
    FIERY_STONE_THROWER("fiery stone thrower",2,2,150,70);
    private final int velocity;
    private int numberOfEngineer = 0;
    private final int damage;
    private final String name;
    private final int price;
    Tool(String name,int velocity, int numberOfEngineer, int damage,int price) {
        this.name = name;
        this.velocity = velocity;
        this.numberOfEngineer = numberOfEngineer;
        this.damage = damage;
        this.price = price;
    }

    public static Tool getToolByName(String name){
        for (Tool tool : values()) {
            if (tool.name.equals(name))
                return tool;
        }
        return null;
    }

    public int getNumberOfEngineer() {
        return numberOfEngineer;
    }
}
