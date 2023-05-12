package model.troops;

import model.Good;

public enum Troops {
   //region velocity powerOfAttack powerOfDefence value hasArmor shootingRange weapon
    //armor spear bow  sword mace
    //شمشیر   گرز   کمان   نیزه     زره
    ARCHER("archer", new Troop("european", 9, 4, 5, 12, false, 5, Good.BOW)),
    CROSSBOWMEN("Crossbowmen", new Troop("european", 6, 7, 7, 20, false,3, Good.BOW)),
    SPEARMEN("Spearmen", new Troop("european", 6, 5, 2, 8, false, 0, Good.BOW)),
    PICKMEN("Pikemen", new Troop("european", 5, 6, 8, 20, true, 0, Good.SPEAR)),
    MACEMEN("Macemen", new Troop("european", 7, 8, 7, 20, false, 0, Good.MACE)),
    SWORDSMEN("Swordsmen", new Troop("european", 3, 10, 10, 40, true, 0, Good.SWORD)),
    KNIGHT("Knight", new Troop("european", 10, 10, 9, 20, false, 0, Good.MACE)),
    TUNNELER("Tunneler", new Troop("european", 8, 6, 3, 20, false, 0, null)),
    LADDERMEN("Laddermen", new Troop("european", 8, 0, 3, 8, false, 0, null)),
    ENGINEER("Engineer", new Troop("european", 5, 0, 3, 20, false, 0, null)),
    BLACK_MONK("Black Monk", new Troop("european", 3, 7, 5, 10, false, 0, Good.SWORD)),
    ARCHER_BOW("Archer Bow", new Troop("arabian", 9, 4, 5, 8, false, 5, Good.BOW)),
    SLAVE("Slaves", new Troop("arabian", 9, 1, 2, 5, false, 0, null)),
    SLINGERS("Slingers", new Troop("arabian", 9, 4, 2, 12, false, 2, Good.STONE)),
    ASSASSINS("Assassins", new Troop("arabian", 7, 7, 7, 60, false, 0, Good.MACE)),
    HORSE_ARCHERS("Horse Archers", new Troop("arabian", 10, 4, 6, 80, false, 5, Good.BOW)),
    ARABIAN_SWORDSMEN("Arabian Swordsmen", new Troop("arabian", 4, 9, 9, 80, false, 0, Good.SWORD)),
    FIRE_THROWERS("Fire Throwers", new Troop("arabian", 9, 8, 4, 100, false, 5,null));
    ;
    String fullName;
    Troop troopObject;

    Troops(String fullName, Troop troopObject) {
        this.fullName = fullName;
        this.troopObject = troopObject;
    }

    public static Troop getTroopObjectByType(String type) {
        for (Troops troop : values())
            if (troop.fullName.equals(type))
                return troop.troopObject;
        return null;
    }
}
