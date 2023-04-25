package model.buildings;

public class KillingPit extends Building{
    private final int damage;

    public KillingPit(String name, int height, int width, int hp, int[] cost, int damage) {
        super(name,height ,width , hp, cost);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
