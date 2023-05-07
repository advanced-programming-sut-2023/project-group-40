package model.buildings;

public enum Buildings{
    ;
    String fullName;
    Building buildingObject;

    Buildings(String fullName, Building buildingObject) {
        this.fullName = fullName;
        this.buildingObject = buildingObject;
    }

    public static Building getBuildingObjectByType(String type){
        for (Buildings building:values())
            if(building.fullName.equals(type))
                return building.buildingObject;
        return null;
    }
}