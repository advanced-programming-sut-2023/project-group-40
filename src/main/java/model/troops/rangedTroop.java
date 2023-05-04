package model.troops;

public class rangedTroop extends Troop{
    public rangedTroop(String region, int velocity, int powerOfAttack, int powerOfDefence, int value, boolean hasArmor, boolean canDigMoat, int shootingRange) {
        super(region, velocity, powerOfAttack, powerOfDefence, value, hasArmor, canDigMoat, shootingRange);
    }
}
