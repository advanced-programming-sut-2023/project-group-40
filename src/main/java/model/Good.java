package model;

public enum Good {
    MACE(0,0,"weapon"),
    SWORD(0,0,"weapon"),
    BOW(0,0,"weapon"),
    SPEAR(0,0,"weapon"),
    ARMOR(0,0,"weapon"),
    GOLD(0,0,"material"),
    WOOD(0,0,"material"),
    STONE(0,0,"material"),
    IRON(0,0,"material"),
    PITCH(0,0,"material"),
    STONE_BLOCK(0,0,"material"),
    MELTING_POT(0,0,"material"),
    LEATHER_VEST(0,0,"material"),
    FOOD1(0,0,"food"),
    FOOD2(0,0,"food"),
    FOOD3(0,0,"food"),
    FOOD4(0,0,"food");
    private final int sellPrice, buyPrice;
    private final String type;

    Good(int priceSell, int priceBuy, String type) {
        this.sellPrice = priceSell;
        this.buyPrice = priceBuy;
        this.type = type;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }
    public String getType() {
        return type;
    }
}
