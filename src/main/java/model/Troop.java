package model;



public class Troop extends People {
    private Government owner;
    private String region;
    private int velocity;
    private int powerOfAttack;
    private int powerOfDefence;
    private int value;
    private boolean hasArmor, canPushLadder, canDigMoat, isHidden, canClimb;
    private int shootingRange;

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

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isHasArmor() {
        return hasArmor;
    }

    public void setHasArmor(boolean hasArmor) {
        this.hasArmor = hasArmor;
    }

    public boolean isCanPushLadder() {
        return canPushLadder;
    }

    public void setCanPushLadder(boolean canPushLadder) {
        this.canPushLadder = canPushLadder;
    }

    public boolean isCanDigMoat() {
        return canDigMoat;
    }

    public void setCanDigMoat(boolean canDigMoat) {
        this.canDigMoat = canDigMoat;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public boolean isCanClimb() {
        return canClimb;
    }

    public void setCanClimb(boolean canClimb) {
        this.canClimb = canClimb;
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

    public void decreaseVelocity(int percent){
        velocity *= percent;
    }
}
