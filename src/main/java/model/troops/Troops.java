package model.troops;

import javafx.scene.image.Image;
import model.Good;
import org.apache.commons.lang3.SerializationUtils;

public enum Troops {
    ARCHER("archer", new Troop("archer", "european", 9, 4, 5, 12, false, 5, Good.BOW)),
    CROSSBOWMEN("crossbowmen", new Troop("crossbowmen", "european", 6, 7, 7, 20, false, 3, Good.BOW)),
    SPEARMEN("spearmen", new Troop("spearmen", "european", 6, 5, 2, 8, false, 0, Good.BOW)),
    PICKMEN("pikemen", new Troop("pikemen", "european", 5, 6, 8, 20, true, 0, Good.SPEAR)),
    MACEMEN("macemen", new Troop("macemen", "european", 7, 8, 7, 20, false, 0, Good.MACE)),
    SWORDSMEN("swordsmen", new Troop("swordsmen", "european", 3, 10, 10, 40, true, 0, Good.SWORD)),
    KNIGHT("knight", new Troop("knight", "european", 10, 10, 9, 20, false, 0, Good.MACE)),
    TUNNELER("tunneler", new Troop("tunneler", "european", 8, 6, 3, 20, false, 0, null)),
    LADDERMEN("laddermen", new Troop("laddermen", "european", 8, 0, 3, 8, false, 0, null)),
    ENGINEER("engineer", new Troop("engineer", "european", 5, 0, 3, 20, false, 0, null)),
    BLACK_MONK("black monk", new Troop("black monk", "european", 3, 7, 5, 10, false, 0, Good.SWORD)),
    ARCHER_BOW("archer bow", new Troop("archer bow", "arabian", 9, 4, 5, 8, false, 5, Good.BOW)),
    SLAVE("slaves", new Troop("slaves", "arabian", 9, 1, 2, 5, false, 0, null)),
    SLINGER("slinger", new Troop("slinger", "arabian", 9, 4, 2, 12, false, 2, Good.STONE)),
    ASSASSIN("assassin", new Troop("assassin", "arabian", 7, 7, 7, 60, false, 0, Good.MACE)),
    HORSE_ARCHER("horse archer", new Troop("horse archer", "arabian", 10, 4, 6, 80, false, 5, Good.BOW)),
    ARABIAN_SWORDSMEN("arabian swordsmen", new Troop("arabian swordsmen", "arabian", 4, 9, 9, 80, false, 0, Good.SWORD)),
    FIRE_THROWERS("fire throwers", new Troop("fire throwers", "arabian", 9, 8, 4, 100, false, 5, null));
    String fullName;
    Troop troopObject;

    Troops(String fullName, Troop troopObject) {
        this.fullName = fullName;
        this.troopObject = troopObject;
    }

    public static Troop getTroopObjectByType(String type) {
        for (Troops troop : values())
            if (troop.fullName.equals(type))
                return SerializationUtils.clone(troop.troopObject);
        return null;
    }

    public Image getImage() {
        return new Image(Troops.class.getResource("/images/troops/archer.png").toString());
    }

    public String getName() {
        return fullName;
    }

    public String getRegion() {
        return Troops.getTroopObjectByType(fullName).getRegion();
    }
}
