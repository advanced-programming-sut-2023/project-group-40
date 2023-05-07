package model.buildings;

import model.Map;
import model.Texture;
import model.Unit;

import java.util.HashSet;

public class Tower extends Building {
    private boolean canHoldEquipments;
    private final int defenceRange, attackRange;

    public Tower(String name, int height, int width, int hp, int[] cost, boolean canHoldEquipments, int defenceRange, int attackRange, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
        this.canHoldEquipments = canHoldEquipments;
        this.defenceRange = defenceRange;
        this.attackRange = attackRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public int getDefenceRange() {
        return defenceRange;
    }

    public boolean isCanHoldEquipments() {
        return canHoldEquipments;
    }

    @Override
    public String action() {
        if (name.equals("lookout tower")) {
            for (int i = x - 1; i < x + 2; i++)
                for (int j = y - 1; j < y + 2; j++) {
                    Unit unit = Map.getMap()[x][y].getUnit();
                    if (unit != null && unit.getTroops().get(0).getName().equals("archer"))
                        unit.setCanDamage(false);
                }
        }
        return null;

    }
}
