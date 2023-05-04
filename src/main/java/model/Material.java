package model;

public enum Material {
    GOLD("gold",0,0),
    WOOD("wood",0,0),
    STONE("stone",0,0),
    IRON("iron",0,0),
    PITCH("pitch",0,0);
    private final int priceSell,priceBuy;
    final String name;

    Material(String name,int priceSell, int priceBuy) {
        this.name = name;
        this.priceSell = priceSell;
        this.priceBuy = priceBuy;
    }
}
