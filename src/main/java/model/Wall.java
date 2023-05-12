package model;

public class Wall {
   private final int height;
   private int hp;

   public Wall(int height, int hp) {
      this.height = height;
      this.hp = hp;
   }

   public void decreaseHp(int damage) {
      hp -= damage;
   }
}
