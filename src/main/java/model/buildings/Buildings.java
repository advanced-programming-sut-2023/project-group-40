package model.buildings;

import model.Good;
import model.Texture;

import java.util.HashSet;
import java.util.List;

public enum Buildings {
    SMALL_STONE_GATEHOUSE("small stone gatehouse", new GateHouse("small stone gatehouse", 6,6,1000, new int[]{0,0,0,0,0}, 8, null, false, BuildingGroups.CASTLE)),
    BIG_STONE_GATEHOUSE("big stone gatehouse", new GateHouse("big stone gatehouse", 8,8,2000, new int[]{0,0,20,0,0}, 10, null, false, BuildingGroups.CASTLE)),
    DRAW_BRIDGE("drawbridge", new Bridge("drawbridge", 5,5,3000, new int[]{0,10,0,0,0}, null, false, BuildingGroups.CASTLE)),
    LOOKOUT_TOWER("lookout tower", new Tower("lookout tower", 2,2, 1300, new int[]{0,0,10,0,0}, false, 10,5, null , false, BuildingGroups.CASTLE)),
    PERIMETER_TOWER("perimeter tower", new Tower("perimeter tower", 5,5,1000, new int[]{0,0,10,0,0}, false, 10,5, null , false, BuildingGroups.CASTLE)),
    DEFENCE_TURRENT("defence turrent", new Tower("defence turrent", 5,5, 1200, new int[]{0,0,15,0,0}, false, 10,5, null , false, BuildingGroups.CASTLE)),
    SQUARE_TOWER("square tower", new Tower("square tower", 5,5,1600,new int[]{0,0,35,0,0}, true, 10,5, null , false, BuildingGroups.CASTLE)),
    ROUND_TOWER("round tower", new Tower("round tower", 5,5,2000,new int[]{0,0,40,0,0}, true, 10,5, null , false, BuildingGroups.CASTLE)),
    ARMOURY("armoury", new Storage("armory", 6,6,500,new int[]{0,5,0,0,0}, 1000, null, false, BuildingGroups.CASTLE)),
    BARRACK("barrack", new Barrack("barrack", 8,8,500, new int[]{0,0,15,0,0}, null, false, BuildingGroups.CASTLE)),
    MERCENARY_POST("mercenary post", new Barrack("mercenary post", 6,6,500, new int[]{0,10,0,0,0}, null, false, BuildingGroups.CASTLE)),
    ENGINEER_GUILD("engineer guild", new Building("engineer guild", 4,6,500, new int[]{100,10,0,0,0}, null, false, BuildingGroups.CASTLE)),
    KILLING_PIT("killing pit", new KillingPit("killing pit", 3,4,0,new int[]{0,6,0,0,0}, 10, null, false, BuildingGroups.CASTLE)),
    INN("inn", new Inn("inn", 6,8, 300, new int[]{100,20,0,0,0}, 1, null, false, BuildingGroups.FOOD_PROCESSING)),
    MILL("mill", new FoodProcessing("mill", 3,3,300, new int[]{0,20,0,0,0}, 3, Good.FLOUR, Good.WHEAT, 15, null, false, BuildingGroups.FOOD_PROCESSING)),
    IRON_MINE("iron mine", new Mine("iron mine", 7,7,100, new int[]{0,20,0,0,0}, 2, Good.IRON, 20, new HashSet<>(List.of(Texture.IRON)), true, BuildingGroups.INDUSTRY)),
    SHOP("shop", new Building("shop", 9,9,300, new int[]{0,5,0,0,0}, 1, null, false, BuildingGroups.INDUSTRY)),
    OX_TETHER("ox tether", new Building("ox tether", 3,2,100, new int[]{0,5,0,0,0}, 1, null, false, BuildingGroups.INDUSTRY)),
    PITCH_RIG("pitch rig", new Mine("pitch rig", 5,5,100, new int[]{0,10,0,0,0}, 1, Good.PITCH, 10, new HashSet<>(List.of(Texture.PLAIN)), true, BuildingGroups.INDUSTRY)),
    QUARRY("quarry", new Mine("quarry", 7,7,300, new int[]{0,20,0,0,0}, 3, Good.STONE, 25, new HashSet<>(List.of(Texture.STONE)), true, BuildingGroups.INDUSTRY)),
    STOCKPILE("stockpile", new Storage("stockpile", 9, 9, 500, new int[]{0,0,0,0,0}, 2000, null, false, BuildingGroups.INDUSTRY)),
    WOODCUTTER("woodcutter", new Mine("woodcutter",5,5,100, new int[]{0,3,0,0,0}, 1, Good.WOOD, 30, null, false, BuildingGroups.INDUSTRY)),
    HOVEL("hovel", new Hovel("hovel", 4,4,100, new int[]{0,6,0,0,0}, null, false, BuildingGroups.TOWN_BUILDING)),
    CHURCH("church", new Church("church", 6,8,800, new int[]{500,0,0,0,0}, null, false, BuildingGroups.TOWN_BUILDING, 2)),
    CATHEDRAL("cathedral", new Church("cathedral", 8,10,1200, new int[]{1000,0,0,0,0}, null, false, BuildingGroups.TOWN_BUILDING, 4)),
    ARMOURER("armourer", new WeaponFactory("armourer", 5,5,300, new int[]{100,20,0,0,0}, 1, Good.IRON, Good.ARMOR, 30, null, false, BuildingGroups.WEAPON)),
    BLACKSMITH("black smith", new WeaponFactory("black smith", 5,5,300, new int[]{100,20,0,0,0}, 1, Good.IRON, Good.SWORD, 20, null, false, BuildingGroups.WEAPON)),
    FLETCHER("fletcher", new WeaponFactory("fletcher",  5,5,300, new int[]{100,20,0,0,0}, 1, Good.WOOD, Good.BOW, 40, null, false, BuildingGroups.WEAPON)),
    POLE_TURNER("pole turner", new WeaponFactory("pole turner", 5,5,300, new int[]{100,10,0,0,0}, 1, Good.WOOD, Good.SPEAR, 20, null, false, BuildingGroups.WEAPON)),
    OIL_SMELTER("oil smelter", new OilSmelter("oil smelter", 6,6,300, new int[]{100,0,0,10,0}, 1, 20, null, false, BuildingGroups.CASTLE)),
    PITCH_DITCH("pitch ditch", new Mine("pitch ditch", 2,3,300, new int[]{100,0,0,0,2}, 0, Good.PITCH, 10, null, false, BuildingGroups.CASTLE)),
    CAGED_WAR_DOGS("caged war dogs", new CagedWarDogs("caged war dogs", 4,4,100, new int[]{100,10,0,0,0}, null, false, BuildingGroups.CASTLE)),
    SIEGE_TENT("siege tent", new Building("siege tent", 4,4,100, new int[]{0,0,0,0,0}, null, false, BuildingGroups.CASTLE)),
    STABLE("stable", new Building("stable", 6,4,300, new int[]{400, 20,0,0,0}, null, false, BuildingGroups.CASTLE)),
    APPLE_ORCHARD("apple orchard" , new FoodProcessing("apple orchard", 4,4,100, new int[]{0,5,0,0,0},1, null, Good.APPLE, 20, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), true, BuildingGroups.FARM)),
    DIARY_FARMER("diary farmer", new FoodProcessing("diary farmer", 4,4, 100, new int[]{0,10,0,0,0}, 1, null, Good.CHEESE, 25, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), true, BuildingGroups.FARM)),
    HOPS_FARMER("hops farmer", new FoodProcessing("hops farmer", 6,6,100, new int[]{0,15,0,0,0}, 1, null, Good.HOP, 20, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), true, BuildingGroups.FARM)),
    HUNTER_POST("hunter post", new FoodProcessing("hunter post", 7,7,300, new int[]{0,5,0,0,0}, 1, null, Good.MEAT, 15, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), true, BuildingGroups.FARM)),
    WHEAT_FARMER("wheat farmer", new FoodProcessing("wheat farmer", 6,6,300, new int[]{0,15,0,0,0}, 1, null, Good.WHEAT, 30, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), true, BuildingGroups.FARM)),
    BAKERY("bakety", new FoodProcessing("bakery", 5,5,300,new int[]{0,10,0,0,}, 1, Good.FLOUR, Good.BREAD, 15, null, false, BuildingGroups.FOOD_PROCESSING)),
    BREWER("brewer", new FoodProcessing("brewer", 5,5,300, new int[]{0,10,0,0,0}, 1, Good.HOP, Good.BEER, 10, null, false, BuildingGroups.FOOD_PROCESSING)),
    GRANARY("granary", new Storage("Granary", 10,10,300, new int[]{0,5,0,0,0}, 2000, null, false, BuildingGroups.FOOD_PROCESSING));
    String fullName;
    Building buildingObject;

    Buildings(String fullName, Building buildingObject) {
        this.fullName = fullName;
        this.buildingObject = buildingObject;
    }

    public static Building getBuildingObjectByType(String type) {
        for (Buildings building : values())
            if (building.fullName.equals(type)) return building.buildingObject;
        return null;
    }
}