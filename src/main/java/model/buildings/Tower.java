package model.buildings;

public class Tower extends Building{
    private boolean canHoldEquipments;
    private final int defenceRange,attackRange;

    public Tower(String name, int height, int width, int hp, int[] cost, boolean canHoldEquipments, int defenceRange, int attackRange) {
        super(name,height , width, hp, cost);
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
