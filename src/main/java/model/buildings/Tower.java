package model.buildings;

import controller.GameMenuController;
import model.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Tower extends Building {
    private final boolean canHoldEquipments;
    private final int defenceRange, attackRange;
    private final ArrayList<Tool> tools = new ArrayList<Tool>();

    public Tower(String name, int height, int width, int hp, int[] cost, boolean canHoldEquipments, int defenceRange,
                 int attackRange, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
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
        Government government = GameMenuController.getCurrentGovernment();
        if (name.equals("lookout tower")) {
            int amount = 3;
            for (int i = x1 - amount; i < x2 + amount; i++)
                for (int j = y1 - amount; j < y2 + amount; j++) {
                    Unit unit = Map.getMap()[i][j].getUnit();
                    if (unit != null && unit.getTroops().containsKey("archer"))
                        unit.setCanDamage(true);
                }
        }
        if (name.equals("square tower") || name.equals("round tower")) {
            for (Tool tool : tools)
                for (int j = y1; j <= y2; j++) {
                    Unit unit = Map.getMap()[x2 + tool.getRange()][j].getUnit();
                    if (unit != null && unit.getGovernment() != government) {
                        unit.decreaseHpOfUnit(tool.getDamage());
                        if (unit.getHp() <= 0) Map.getMap()[x2 + tool.getRange()][j].removeUnit(unit);
                    }
                    Castle castle = Map.getMap()[x2 + tool.getRange()][j].getCastle();
                    if (castle != null) {
                        if (tool == Tool.CATAPULT_WITH_BALANCE_WEIGHT) {
                            castle.decreaseHp(tool.getDamage());
                            castle.checkCastle();
                        }
                    }
                }

        }

    }

    public void addTool(Tool tool) {
        tools.add(tool);
    }
}
