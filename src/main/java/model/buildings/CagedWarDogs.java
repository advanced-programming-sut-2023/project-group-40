package model.buildings;

import controller.GameMenuController;
import model.Map;
import model.Texture;
import model.Unit;

import java.util.HashSet;

public class CagedWarDogs extends Building {
    private boolean isOpen = false;

    public CagedWarDogs(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal, BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public void action() {
        if (isOpen) {
            Unit unit = GameMenuController.findNearestUnit(4,(x1+x2)/2,(y1+y2)/2);
            if (unit != null) {
                unit.decreaseHpOfUnit(300);
                if (unit.getHp() < 0) {
                    Map.getMap()[unit.getX()][unit.getY()].removeUnit(unit);
                    Map.getMap()[unit.getX()][unit.getY()].setPassable(true);
                    Map.getMap()[unit.getX()][unit.getY()].setAvailable(true);
                }
            }
        }
    }
}
