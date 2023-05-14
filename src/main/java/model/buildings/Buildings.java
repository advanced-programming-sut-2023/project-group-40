package model.buildings;

import org.apache.commons.lang3.SerializationUtils;

public enum Buildings {
    ;
    String fullName;
    Building buildingObject;

    Buildings(String fullName, Building buildingObject) {
        this.fullName = fullName;
        this.buildingObject = buildingObject;
    }

    public static Building getBuildingObjectByType(String type) {
        for (Buildings building : values())
            if (building.fullName.equals(type)) return SerializationUtils.clone(building.buildingObject);
        return null;
    }
}