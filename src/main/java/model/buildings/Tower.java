package model.buildings;

import model.Map;
import model.Texture;
import model.Tool;
import model.Unit;

import java.util.ArrayList;
import java.util.HashSet;

public class Tower extends Building {
    private boolean canHoldEquipments;
    private final int defenceRange, attackRange;
    private final ArrayList<Tool> tools = new ArrayList<Tool>();
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
    public void action() {
        if (name.equals("lookout tower")) {
            int amount = 3;
            for (int i = x1 - amount; i < x2 + amount; i++)
                for (int j = y1 - amount; j < y2 + amount; j++) {
                    Unit unit = Map.getMap()[i][j].getUnit();
                    if (unit != null && unit.getTroops().get(0).getName().startsWith("archer"))
                        unit.setCanDamage(true);
                }
        }

    }
    public void addTool(Tool tool) {
        tools.add(tool);
    }
}
