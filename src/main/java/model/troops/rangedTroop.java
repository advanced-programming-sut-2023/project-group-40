package model.troops;

public class rangedTroop extends Troop{
    public rangedTroop(String name, String region, int velocity, int powerOfAttack, int powerOfDefence, int value, boolean hasArmor, boolean canDigMoat, int shootingRange) {
        super(name, region, velocity, powerOfAttack, powerOfDefence, value, hasArmor, canDigMoat, shootingRange);
    }
}
