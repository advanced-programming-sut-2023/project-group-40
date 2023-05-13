package model.troops;

import model.Good;

public class directTroops extends Troop{

    public directTroops(String name, String region, int velocity, int powerOfAttack, int powerOfDefence, int value, boolean hasArmor, int shootingRange, Good weapon) {
        super(name,region,velocity,powerOfAttack,powerOfDefence,value,hasArmor,shootingRange,weapon);
    }
}
