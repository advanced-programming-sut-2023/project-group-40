package model;

public enum Food {
    FOOD1(0,0),
    FOOD2(0,0),
    FOOD3(0,0),
    FOOD4(0,0);
    private final int priceSell,priceBuy;

    Food(int priceSell, int priceBuy) {
        this.priceSell = priceSell;
        this.priceBuy = priceBuy;
    }
    public int getPriceSell() {
        return priceSell;
    }
    public int getPriceBuy() {
        return priceBuy;
    }
}
