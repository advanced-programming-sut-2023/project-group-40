package model;

import javafx.scene.image.Image;
import view.MapMenu;



public enum Texture {
    LAND("land", "dry", new Image(MapMenu.class.getResource("/images/textures/land.jpg").toString(), true)),
    LAND_WITH_PEBBLES("land with pebbles", "dry", new Image(MapMenu.class.getResource("/images/textures/pebbles.jpg").toString(), true)),
    STONE_BLOCKS("stone blocks", "dry", new Image(MapMenu.class.getResource("/images/textures/stoneBlock.jpeg").toString(), true)),
    IRON("iron", "dry", new Image(MapMenu.class.getResource("/images/textures/iron.jpeg").toString(), true)),
    GRASS("grass", "dry", new Image(MapMenu.class.getResource("/images/textures/grass.jpg").toString(), true)),
    GRASS_LAND("grassland", "dry", new Image(MapMenu.class.getResource("/images/textures/grassland.jpeg").toString(), true)),
    DENSE_GRASS_LAND("dense grassland", "dry", new Image(MapMenu.class.getResource("/images/textures/denseGrassland.jpeg").toString(), true)),
    STONE("stone", "dry", new Image(MapMenu.class.getResource("/images/textures/stone.jpeg").toString(), true)),
    OIL("oil", "water", new Image(MapMenu.class.getResource("/images/textures/oil.jpeg").toString(), true)),
    PLAIN("plain", "water", new Image(MapMenu.class.getResource("/images/textures/plane.jpg").toString(), true)),
    SHALLOW_WATER("shallow water", "water", new Image(MapMenu.class.getResource("/images/textures/shallowWater.jpeg").toString(), true)),
    RIVER("river", "water", new Image(MapMenu.class.getResource("/images/textures/river.jpeg").toString(), true)),
    PONE("pond", "water", new Image(MapMenu.class.getResource("/images/textures/pone.jpg").toString(), true)),
    BEACH("beach", "water", new Image(MapMenu.class.getResource("/images/textures/beach.jpeg").toString(), true)),
    BEACH1("beach1", "water", new Image(MapMenu.class.getResource("/images/textures/beach1.jpeg").toString(), true)),
    BEACH2("beach2", "water", new Image(MapMenu.class.getResource("/images/textures/beach2.jpeg").toString(), true)),
    BEACH3("beach3", "water", new Image(MapMenu.class.getResource("/images/textures/beach3.jpeg").toString(), true)),
    BEACH4("beach4", "water", new Image(MapMenu.class.getResource("/images/textures/beach4.jpeg").toString(), true)),
    SEA("sea", "water", new Image(MapMenu.class.getResource("/images/textures/sea.jpg").toString(), true));
    private final String name;
    private final String type;
    private final Image image;

    Texture(String name, String type, Image image) {
        this.name = name;
        this.type = type;
        this.image = image;
    }

    public static Texture getTextureByName(String name) {
        for (Texture texture : values())
            if (texture.name.equals(name)) return texture;
        return null;
    }

    public String getType() {
        return type;
    }

    public Image getImage() {
        return image;
    }
}
