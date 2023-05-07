package model.buildings;

import controller.GameMenuController;
import model.Texture;

import java.util.HashSet;

public class WeaponFactory extends Building {
    private final Material material;
    private final Weapon weapon;
    private int produceRate;

    public WeaponFactory(String name, int height, int width, int hp, int[] cost, int workersRequired, Material material, Weapon weapon,
                         int produceRate, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, workersRequired, textures, isIllegal, group);
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

    @Override
    public String action() {
        int gold = 0,wood = 0;
        for (Storage<Material> storage :GameMenuController.getCurrentGovernment().
                getMaterialStorages()) {
            gold += storage.getProducts().get(Material.GOLD);
            wood += storage.getProducts().get(Material.WOOD);
        }
        if (gold < cost[0]) return "you don't have enough gold";
        if (wood < cost[1]) return "you don't have enough wood";
        switch (name){
            case "armourer" -> addWeapon(Weapon.ARMOR);
            case "blacksmith" -> {
                addWeapon(Weapon.SWORD);
                addWeapon(Weapon.MACE);
            }
            case "Fletcher" -> addWeapon(Weapon.BOW);
            case "Poleturner" -> addWeapon(Weapon.SPEAR);
        }
        return null;
    }
    public void addWeapon(Weapon weapon){
        int sumOfCapacities = 0;
        int makeWeapons = produceRate;
        for (Storage<Weapon> storage :GameMenuController.getCurrentGovernment().getWeaponStorages())
            sumOfCapacities += storage.getCapacity() - storage.getSumOfProducts(weapon);
        if (produceRate > sumOfCapacities) makeWeapons = sumOfCapacities;
        for (Storage<Weapon> storage :GameMenuController.getCurrentGovernment().getWeaponStorages()) {
            int capacity =  storage.getCapacity() - storage.getSumOfProducts(weapon);
            if (makeWeapons == 0) break;
            if (makeWeapons > capacity) {
                storage.addProduct(weapon, capacity);
                makeWeapons -= capacity;
            }
            else {
                storage.addProduct(weapon,makeWeapons);
                break;
            }
        }
    }
}
