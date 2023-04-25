package model;

public enum Weapon {
    ;
    final String name;
    final int priceSell,priceBuy;

    Weapon(String name, int priceSell, int priceBuy) {
        this.name = name;
        this.priceSell = priceSell;
        this.priceBuy = priceBuy;
    }
}
