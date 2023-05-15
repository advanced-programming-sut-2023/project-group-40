package model.buildings;

import model.Map;
import model.Texture;
import model.Unit;

import java.util.HashSet;

public class Bridge extends Building {
    boolean isUp = false;

    public Bridge(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures, boolean isIllegal,
                  BuildingGroups group) {
        super(name, height, width, hp, cost, textures, isIllegal, group);
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    @Override
    public void action() {
        if (isUp) {
            for (int i = x1; i <= x2; i++)
                for (int j = y1; j <= y2; j++)
                    Map.getMap()[i][j].setPassable(false);
        } else {
            for (int i = x1; i <= x2; i++) {
                for (int j = y1; j <= y2; j++) {
                    Unit unit = Map.getMap()[i][j].getUnit();
                    if (unit != null)
                        unit.decreaseVelocity(1);
                }
            }
        }
    }
}
