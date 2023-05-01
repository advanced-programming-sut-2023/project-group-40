package model.buildings;

import model.Texture;

import java.util.HashSet;

public class Tower extends Building{
    private boolean canHoldEquipments;
    private final int defenceRange,attackRange;

    public Tower(String name, int height, int width, int hp, int[] cost, boolean canHoldEquipments, int defenceRange, int attackRange, HashSet<Texture> textures,boolean isIllegal) {
        super(name,height , width, hp, cost,textures,isIllegal);
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
}
