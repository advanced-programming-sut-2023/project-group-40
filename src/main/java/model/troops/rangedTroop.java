package model.troops;

import model.Good;

public class rangedTroop extends Troop{
    public rangedTroop(String name, String region, int velocity, int powerOfAttack, int powerOfDefence, int value, boolean hasArmor, int shootingRange, Good weapon) {
        super(name,region,velocity,powerOfAttack,powerOfDefence,value,hasArmor,shootingRange,weapon);
    }
}
