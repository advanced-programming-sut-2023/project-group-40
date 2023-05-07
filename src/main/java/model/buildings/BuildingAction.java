package model.buildings;

import model.Government;

public interface BuildingAction {
    default String action() {
     return null;
    };
    default String action(Government government) {
        return null;
    };
}
