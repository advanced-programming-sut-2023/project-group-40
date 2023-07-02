package model.troops;


import model.Good;

import java.io.Serializable;


public class Troop implements Serializable {
    private final String name;
    private final String region;
    private final Good weapon;
    private final int powerOfAttack, value, shootingRange, hp;
    private final boolean hasArmor;
    private int velocity;

    public Troop(String name, String region, int velocity, int powerOfAttack, int hp, int value, boolean hasArmor,
                 int shootingRange, Good weapon) {
        this.name = name;
        this.region = region;
        this.velocity = velocity;
        this.powerOfAttack = powerOfAttack;
        this.hp = 10 * hp;
        this.value = value;
        this.hasArmor = hasArmor;
        this.shootingRange = shootingRange;
        this.weapon = weapon;
    }

    public String getRegion() {
        return region;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getPowerOfAttack() {
        return powerOfAttack;
    }


    public int getValue() {
        return value;
    }

    public boolean isHasArmor() {
        return hasArmor;
    }

    public int getShootingRange() {
        return shootingRange;
    }

    public void changeVelocity(int percent) {
        velocity *= percent;
    }

    public Good getWeapon() {
        return weapon;
    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

}