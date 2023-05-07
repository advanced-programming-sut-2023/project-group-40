package model;

public enum Food {
    FOOD1(0,0),
    FOOD2(0,0),
    FOOD3(0,0),
    FOOD4(0,0);
    private final int sellPrice,buyPrice;

    Food(int sellPrice, int buyPrice) {
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }
    public int getSellPrice() {
        return sellPrice;
    }
    public int getBuyPrice() {
        return buyPrice;
    }
    public static Food getMaterialByName(String name) {
        for (Food value : Food.values()) {
            if(value.name().toLowerCase().equals(name))
                return value;
        }
        return null;
    }
}
