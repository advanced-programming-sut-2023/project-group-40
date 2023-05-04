package model;

public enum Weapon {
    MACE("mace",0,0),//گرز
    SWORD("sword",0,0),//شمشیر
    BOW("bow",0,0),//کمان
    SPEAR("spear",0,0),//نیزه
    ARMOR("armor",0,0);//زره
    final String name;
    final int priceSell,priceBuy;

    Weapon(String name, int priceSell, int priceBuy) {
        this.name = name;
        this.priceSell = priceSell;
        this.priceBuy = priceBuy;
    }
}
