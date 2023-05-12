package model.troops;

public enum Troops {
    ARCHER("archer", new rangedTroop("european", 9, 4, 5, 120, false, true, 20)),
    ARABIAN_ARCHER("Archer Bow",new rangedTroop("arabian",9,5,5,75,false,true,20))

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
