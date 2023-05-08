package model.buildings;
import controller.GameMenuController;
import model.Government;
import model.Map;
import model.Texture;
import model.Unit;
import view.GameMenu;

import java.util.HashSet;

public class KillingPit extends Building {
    //don't see
    private final int damage;

    public KillingPit(String name, int height, int width, int hp, int[] cost, int damage, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void action() {
        Government government = GameMenuController.getCurrentGovernment();
        for (int i = x - 1; i < x + 1; i++)
            for (int j = y - 1; j < y + 1; j++) {
                Unit unit = Map.getMap()[i][j].getUnit();
                if (unit.getGovernment() == government)
                    unit.decreaseHpOfUnit(damage);
            }
    }
}
