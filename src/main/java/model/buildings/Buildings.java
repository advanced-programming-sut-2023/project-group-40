package model.buildings;

import javafx.scene.image.Image;
import model.Good;
import model.Texture;
import org.apache.commons.lang3.SerializationUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

public enum Buildings {
    SMALL_STONE_GATEHOUSE("small stone gatehouse", new GateHouse("small stone gatehouse", 3, 3, 1000, new int[]{0, 0, 0, 0, 0}, 8, new HashSet<>(), true, BuildingGroups.CASTLE)),
    BIG_STONE_GATEHOUSE("big stone gatehouse", new GateHouse("big stone gatehouse", 4, 4, 2000, new int[]{0, 0, 20, 0, 0}, 10, new HashSet<>(), true, BuildingGroups.CASTLE)),
    DRAW_BRIDGE("drawbridge", new Bridge("drawbridge", 3, 3, 3000, new int[]{0, 10, 0, 0, 0}, new HashSet<>(), true, BuildingGroups.CASTLE)),
    LOOKOUT_TOWER("lookout tower", new Tower("lookout tower", 2, 2, 1300, new int[]{0, 0, 10, 0, 0}, false, 10, 5, new HashSet<>(), true, BuildingGroups.CASTLE)),
    PERIMETER_TOWER("perimeter tower", new Tower("perimeter tower", 3, 3, 1000, new int[]{0, 0, 10, 0, 0}, false, 10, 5, new HashSet<>(), true, BuildingGroups.CASTLE)),
    DEFENCE_TURRET("defence turret", new Tower("defence turret", 3, 3, 1200, new int[]{0, 0, 15, 0, 0}, false, 10, 5, new HashSet<>(), true, BuildingGroups.CASTLE)),
    SQUARE_TOWER("square tower", new Tower("square tower", 3, 3, 1600, new int[]{0, 0, 35, 0, 0}, true, 10, 5, new HashSet<>(), true, BuildingGroups.CASTLE)),
    ROUND_TOWER("round tower", new Tower("round tower", 3, 3, 2000, new int[]{0, 0, 40, 0, 0}, true, 10, 5, new HashSet<>(), true, BuildingGroups.CASTLE)),
    ARMOURY("armoury", new Storage("armory", 3, 3, 500, new int[]{0, 5, 0, 0, 0}, 1000, new HashSet<>(), true, BuildingGroups.CASTLE, "weapon")),
    BARRACK("barrack", new Barrack("barrack", 5, 5, 500, new int[]{0, 0, 15, 0, 0}, new HashSet<>(), true, BuildingGroups.CASTLE)),
    MERCENARY_POST("mercenary post", new Barrack("mercenary post", 4, 4, 500, new int[]{0, 10, 0, 0, 0}, new HashSet<>(), true, BuildingGroups.CASTLE)),
    ENGINEER_GUILD("engineer guild", new Building("engineer guild", 3, 4, 500, new int[]{100, 10, 0, 0, 0}, new HashSet<>(), true, BuildingGroups.CASTLE)),
    KILLING_PIT("killing pit", new KillingPit("killing pit", 2, 3, 0, new int[]{0, 6, 0, 0, 0}, 10, new HashSet<>(), true, BuildingGroups.CASTLE)),
    INN("inn", new Inn("inn", 5, 3, 300, new int[]{100, 20, 0, 0, 0}, 1, new HashSet<>(), true, BuildingGroups.FOOD_PROCESSING)),
    MILL("mill", new FoodProcessing("mill", 2, 2, 300, new int[]{0, 20, 0, 0, 0}, 3, Good.FLOUR, Good.WHEAT, 15, new HashSet<>(), true, BuildingGroups.FOOD_PROCESSING)),
    IRON_MINE("iron mine", new Mine("iron mine", 4, 4, 100, new int[]{0, 20, 0, 0, 0}, 2, Good.IRON, 20, new HashSet<>(List.of(Texture.IRON)), false, BuildingGroups.INDUSTRY)),
    SHOP("shop", new Building("shop", 6, 6, 300, new int[]{0, 5, 0, 0, 0}, 1, new HashSet<>(), true, BuildingGroups.INDUSTRY)),
    OX_TETHER("ox tether", new Building("ox tether", 2, 1, 100, new int[]{0, 5, 0, 0, 0}, 1, new HashSet<>(), true, BuildingGroups.INDUSTRY)),
    PITCH_RIG("pitch rig", new Mine("pitch rig", 3, 3, 100, new int[]{0, 10, 0, 0, 0}, 1, Good.PITCH, 10, new HashSet<>(List.of(Texture.PLAIN)), false, BuildingGroups.INDUSTRY)),
    QUARRY("quarry", new Mine("quarry", 4, 4, 300, new int[]{0, 20, 0, 0, 0}, 3, Good.STONE, 25, new HashSet<>(List.of(Texture.STONE)), false, BuildingGroups.INDUSTRY)),
    STOCKPILE("stockpile", new Storage("stockpile", 6, 6, 500, new int[]{0, 0, 0, 0, 0}, 2000, new HashSet<>(), true, BuildingGroups.INDUSTRY, "material")),
    WOODCUTTER("woodcutter", new Mine("woodcutter", 5, 5, 100, new int[]{0, 3, 0, 0, 0}, 1, Good.WOOD, 30, new HashSet<>(), true, BuildingGroups.INDUSTRY)),
    HOVEL("hovel", new Hovel("hovel", 3, 3, 100, new int[]{0, 6, 0, 0, 0}, new HashSet<>(), true, BuildingGroups.TOWN_BUILDING)),
    CHURCH("church", new Church("church", 3, 5, 800, new int[]{500, 0, 0, 0, 0}, new HashSet<>(), true, BuildingGroups.TOWN_BUILDING, 2)),
    CATHEDRAL("cathedral", new Church("cathedral", 4, 6, 1200, new int[]{1000, 0, 0, 0, 0}, new HashSet<>(), true, BuildingGroups.TOWN_BUILDING, 4)),
    ARMOURER("armourer", new WeaponFactory("armourer", 3, 3, 300, new int[]{100, 20, 0, 0, 0}, 1, Good.IRON, Good.ARMOR, 30, new HashSet<>(), true, BuildingGroups.WEAPON)),
    BLACKSMITH("black smith", new WeaponFactory("black smith", 3, 3, 300, new int[]{100, 20, 0, 0, 0}, 1, Good.IRON, Good.SWORD, 20, new HashSet<>(), true, BuildingGroups.WEAPON)),
    FLETCHER("fletcher", new WeaponFactory("fletcher", 3, 3, 300, new int[]{100, 20, 0, 0, 0}, 1, Good.WOOD, Good.BOW, 40, new HashSet<>(), true, BuildingGroups.WEAPON)),
    POLE_TURNER("pole turner", new WeaponFactory("pole turner", 3, 3, 300, new int[]{100, 10, 0, 0, 0}, 1, Good.WOOD, Good.SPEAR, 20, new HashSet<>(), true, BuildingGroups.WEAPON)),
    OIL_SMELTER("oil smelter", new OilSmelter("oil smelter", 3, 3, 300, new int[]{100, 0, 0, 10, 0}, 1, 20, new HashSet<>(), true, BuildingGroups.CASTLE)),
    PITCH_DITCH("pitch ditch", new Mine("pitch ditch", 1, 2, 300, new int[]{100, 0, 0, 0, 2}, 0, Good.PITCH, 10, new HashSet<>(), true, BuildingGroups.CASTLE)),
    CAGED_WAR_DOGS("caged war dogs", new CagedWarDogs("caged war dogs", 3, 2, 100, new int[]{100, 10, 0, 0, 0}, new HashSet<>(), true, BuildingGroups.CASTLE)),
    //SIEGE_TENT("siege tent", new Building("siege tent", 2,2,100, new int[]{0,0,0,0,0}, new HashSet<>(), false, BuildingGroups.CASTLE)),
    // چادر محاصره
    STABLE("stable", new Building("stable", 4, 2, 300, new int[]{400, 20, 0, 0, 0}, new HashSet<>(), true, BuildingGroups.CASTLE)),
    APPLE_ORCHARD("apple orchard", new FoodProcessing("apple orchard", 3, 3, 100, new int[]{0, 5, 0, 0, 0}, 1, null, Good.APPLE, 20, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), false, BuildingGroups.FARM)),
    DIARY_FARMER("diary farmer", new FoodProcessing("diary farmer", 3, 3, 100, new int[]{0, 10, 0, 0, 0}, 1, null, Good.CHEESE, 25, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), false, BuildingGroups.FARM)),
    HOPS_FARMER("hops farmer", new FoodProcessing("hops farmer", 4, 4, 100, new int[]{0, 15, 0, 0, 0}, 1, null, Good.HOP, 20, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), false, BuildingGroups.FARM)),
    HUNTER_POST("hunter post", new FoodProcessing("hunter post", 5, 5, 300, new int[]{0, 5, 0, 0, 0}, 1, null, Good.MEAT, 15, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), false, BuildingGroups.FARM)),
    WHEAT_FARMER("wheat farmer", new FoodProcessing("wheat farmer", 4, 4, 300, new int[]{0, 15, 0, 0, 0}, 1, null, Good.WHEAT, 30, new HashSet<>(List.of(Texture.GRASS, Texture.DENSE_GRASS_LAND)), false, BuildingGroups.FARM)),
    BAKERY("bakery", new FoodProcessing("bakery", 3, 3, 300, new int[]{0, 10, 0, 0,}, 1, Good.FLOUR, Good.BREAD, 15, new HashSet<>(), true, BuildingGroups.FOOD_PROCESSING)),
    BREWER("brewer", new FoodProcessing("brewer", 3, 3, 300, new int[]{0, 10, 0, 0, 0}, 1, Good.HOP, Good.BEER, 10, new HashSet<>(), true, BuildingGroups.FOOD_PROCESSING)),
    GRANARY("granary", new Storage("Granary", 6, 6, 300, new int[]{0, 5, 0, 0, 0}, 2000, new HashSet<>(), true, BuildingGroups.FOOD_PROCESSING, "food"));
    String fullName;
    Building buildingObject;
    Image buildingImage;

    Buildings(String fullName, Building buildingObject) {
        this.fullName = fullName;
        this.buildingObject = buildingObject;
        String[] buildingNames = new String[]{"diary farmer", "hops farmer", "quarry"};
        this.buildingImage = new Image(Buildings.class.getResource("/images/buildings/" + buildingNames[new Random().nextInt(0, 3)] + ".png").toString());
    }

    public static Building getBuildingObjectByType(String type) {
        for (Buildings building : values())
            if (building.fullName.equals(type)) return SerializationUtils.clone(building.buildingObject);
        return null;
    }

    public Image getBuildingImage() {
        return buildingImage;
    }

    public String getFullName() {
        return fullName;
    }
}