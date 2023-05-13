package model.troops;

import model.Good;

public enum Troops {
    ARCHER("archer", new Troop("archer", "european", 9, 4, 5, 12, false, 5, Good.BOW)),
    CROSSBOWMEN("Crossbowmen", new Troop("Crossbowmen", "european", 6, 7, 7, 20, false,3, Good.BOW)),
    SPEARMEN("Spearmen", new Troop("Spearmen", "european", 6, 5, 2, 8, false, 0, Good.BOW)),
    PICKMEN("Pikemen", new Troop("Pikemen", "european", 5, 6, 8, 20, true, 0, Good.SPEAR)),
    MACEMEN("Macemen", new Troop("Macemen", "european", 7, 8, 7, 20, false, 0, Good.MACE)),
    SWORDSMEN("Swordsmen", new Troop("Swordsmen", "european", 3, 10, 10, 40, true, 0, Good.SWORD)),
    KNIGHT("Knight", new Troop("Knight", "european", 10, 10, 9, 20, false, 0, Good.MACE)),
    TUNNELER("Tunneler", new Troop("Tunneler", "european", 8, 6, 3, 20, false, 0, null)),
    LADDERMEN("Laddermen", new Troop("Laddermen", "european", 8, 0, 3, 8, false, 0, null)),
    ENGINEER("Engineer", new Troop("Engineer", "european", 5, 0, 3, 20, false, 0, null)),
    BLACK_MONK("Black Monk", new Troop("Black Monk", "european", 3, 7, 5, 10, false, 0, Good.SWORD)),
    ARCHER_BOW("Archer Bow", new Troop("Archer Bow", "arabian", 9, 4, 5, 8, false, 5, Good.BOW)),
    SLAVE("Slaves", new Troop("Slaves", "arabian", 9, 1, 2, 5, false, 0, null)),
    SLINGERS("Slingers", new Troop("Slingers", "arabian", 9, 4, 2, 12, false, 2, Good.STONE)),
    ASSASSINS("Assassins", new Troop("Assassins", "arabian", 7, 7, 7, 60, false, 0, Good.MACE)),
    HORSE_ARCHERS("Horse Archers", new Troop("Horse Archers", "arabian", 10, 4, 6, 80, false, 5, Good.BOW)),
    ARABIAN_SWORDSMEN("Arabian Swordsmen", new Troop("Arabian Swordsmen", "arabian", 4, 9, 9, 80, false, 0, Good.SWORD)),
    FIRE_THROWERS("Fire Throwers", new Troop("Fire Throwers", "arabian", 9, 8, 4, 100, false, 5,null));
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
