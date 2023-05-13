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
      int amount = 3;
      for (int i = x1 - amount; i <= x2 + amount ; i++)
         for (int j = y1 - amount; j <= y2 + amount; j++) {
            Unit unit = Map.getMap()[i][j].getUnit();
            if (unit != null)
               unit.increaseShootingRange(10);
         }
   }

   public void addTool(Tool tool) {
      tools.add(tool);
   }
}
