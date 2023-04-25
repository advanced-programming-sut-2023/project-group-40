package model.buildings;

import model.Food;
import model.Weapon;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

enum Buildings{
    ;
    String fullName;
    Building buildingObject;

    Buildings(String fullName, Building buildingObject) {
        this.fullName = fullName;
        this.buildingObject = buildingObject;
    }

    public static Building getBuildingObjectByName(String name){
        for (Buildings building:values()) {
            if(building.fullName.equals(name)){

            }
        }
        return null;
    }
}