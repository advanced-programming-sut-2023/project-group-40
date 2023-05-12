package model;

import model.buildings.Building;
import model.troops.Troop;

import java.util.ArrayList;

public class Turret extends Building {
   ArrayList<Troop> troops = new ArrayList<>();
   int capacity;
   ArrayList<Tool> tools = new ArrayList<>();

   public Turret(String name, int height, int width, int hp, int[] cost, int capacity) {
      super(name, height, width, hp, cost);
      this.capacity = capacity;
   }

   public void addTroop(Unit unit) {
      if (troops.size() == capacity || troops.size() + unit.getTroops().size() > capacity)
         return;
      troops.addAll(unit.getTroops());
   }
   @Override
   public void action() {
      for (int i = x - 1; i < x + 1 ; i++)
         for (int j = y - 1; j < y + 1; j++) {
            Unit unit = Map.getMap()[i][j].getUnit();
            if (unit != null)
               unit.increaseShootingRange(20);
         }
   }

   public void addTool(Tool tool) {
      tools.add(tool);
   }
}
