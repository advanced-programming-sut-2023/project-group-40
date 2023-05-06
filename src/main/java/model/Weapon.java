package model;

public enum Weapon {
    MACE("mace",0,0),
    SWORD("sword",0,0),
    BOW("bow",0,0),
    SPEAR("spear",0,0),
    ARMOR("armor",0,0);
    final String name;
    enum type{};
    final int priceSell,priceBuy;

    Weapon(String name, int priceSell, int priceBuy) {
        this.name = name;
        this.priceSell = priceSell;
        this.priceBuy = priceBuy;
    }
}
