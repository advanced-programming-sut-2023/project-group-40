package model.troops;


import model.Good;
import model.Government;
import model.People;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Troop extends People {
    private Good weapon;
    private int hp;
    private Government owner;
    private final String region;
    private int velocity,powerOfAttack;
    private final int powerOfDefence,value;
    private final boolean hasArmor;
    private int shootingRange;

    public Troop(String region, int velocity, int powerOfAttack, int powerOfDefence, int value, boolean hasArmor, int shootingRange,Good weapon) {
        this.region = region;
        this.velocity = velocity;
        this.powerOfAttack = powerOfAttack;
        this.powerOfDefence = powerOfDefence;
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

    public int getPowerOfDefence() {
        return powerOfDefence;
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

    public void setShootingRange(int shootingRange) {
        this.shootingRange = shootingRange;
    }

    public Government getOwner() {
        return owner;
    }

    public void setOwner(Government owner) {
        this.owner = owner;
    }

    public void changeVelocity(int percent){
        velocity *= percent;
    }

    public Good getWeapon() {
        return weapon;
    }

    public int getHp() {
        return hp;
    }
}
