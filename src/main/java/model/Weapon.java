package model;

public enum Weapon{
    WEAPON1(0,0);
    private final int sellPrice,buyPrice;

    Weapon(int sellPrice, int buyPrice) {
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }
    public int getSellPrice() {
        return sellPrice;
    }
    public int getBuyPrice() {
        return buyPrice;
    }
    public static Weapon getMaterialByName(String name) {
        for (Weapon value : Weapon.values()) {
            if(value.name().toLowerCase().equals(name))
                return value;
        }
        return null;
    }
}
