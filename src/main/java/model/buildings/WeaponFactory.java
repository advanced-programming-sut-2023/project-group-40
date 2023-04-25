package model.buildings;

import model.Material;
import model.Weapon;

public class WeaponFactory extends Building{
    private final Material material;
    private final Weapon weapon;
    private int produceRate;

    public WeaponFactory(String name, int height, int width, int hp, int[] cost, int workersRequired, Material material, Weapon weapon,
                         int produceRate) {
        super(name,height,width, hp, cost, workersRequired);
        this.material = material;
        this.weapon = weapon;
        this.produceRate = produceRate;
    }

    public int getProduceRate() {
        return produceRate;
    }

    public void setProduceRate(int produceRate) {
        this.produceRate = produceRate;
    }
}
