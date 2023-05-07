package model;

public enum Material {
    MATERIAL1(0,0);
    private final int sellPrice,buyPrice;

    Material(int sellPrice, int buyPrice) {
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }
    public int getSellPrice() {
        return sellPrice;
    }
    public int getBuyPrice() {
        return buyPrice;
    }
    public static Material getMaterialByName(String name) {
        for (Material value : Material.values()) {
            if(value.name().toLowerCase().equals(name))
                return value;
        }
        return null;
    }
}
