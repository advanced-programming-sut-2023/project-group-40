package model;

import javafx.scene.image.Image;

import java.util.Random;
import java.util.random.RandomGenerator;

public enum Good {
    MACE(30, 60, "weapon"),
    SWORD(30, 60, "weapon"),
    BOW(20, 40, "weapon"),
    SPEAR(10, 20, "weapon"),
    ARMOR(20, 40, "weapon"),
    GOLD(1, 1, "material"),
    WOOD(5, 10, "material"),
    STONE(15, 25, "material"),
    IRON(25, 40, "material"),
    PITCH(30, 45, "material"),
    STONE_BLOCK(0, 0, "material"),
    MELTING_POT(0, 0, "material"),
    LEATHER_VEST(40, 60, "material"),
    FLOUR(5, 10, "food"), //ard
    WHEAT(4, 6, "food"), //gandom
    HOP(8, 13, "food"), //jo
    BEER(20, 30, "food"), //abjo
    MEAT(10, 10, "food"),
    APPLE(10, 10, "food"),
    CHEESE(10, 10, "food"),
    BREAD(10, 10, "food");
    private final int sellPrice, buyPrice;
    private final String type;
    private final Image image;

    Good(int priceSell, int priceBuy, String type) {
        this.sellPrice = priceSell;
        this.buyPrice = priceBuy;
        this.type = type;
        String[] names = new String[2];
        names[0] = "apple";
        names[1] = "cheese";
        int random = RandomGenerator.getDefault().nextInt(2);
        System.out.println(random);
        this.image = new Image(Good.class.getResource("/images/goods/" + names[random] + ".png").toString());
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getType() {
        return this.type;
    }

    public Image getImage() {
        return image;
    }

    public static Good getGoodByName(String name){
        for (Good value : values()) {
            if (value.name().toLowerCase().equals(name)) return value;
        }
        return null;
    }
}