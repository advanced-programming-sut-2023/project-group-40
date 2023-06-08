package model;

import javafx.scene.image.Image;
import view.MapMenu;



public enum Texture {
    LAND("land", "dry", new Image(MapMenu.class.getResource("/images/textures/land.jpg").toString(), true)),
    LAND_WITH_PEBBLES("land with pebbles", "dry", new Image(MapMenu.class.getResource("/images/textures/land with pebbles.jpeg").toString(), true)),
    STONE_BLOCK("stone block", "dry", new Image(MapMenu.class.getResource("/images/textures/stone block.jpeg").toString(), true)),
    IRON("iron", "dry", new Image(MapMenu.class.getResource("/images/textures/iron.jpeg").toString(), true)),
    GRASS("grass", "dry", new Image(MapMenu.class.getResource("/images/textures/grass.jpeg").toString(), true)),
    GRASS_LAND("grassland", "dry", new Image(MapMenu.class.getResource("/images/textures/grassland.jpg").toString(), true)),
    DENSE_GRASS_LAND("dense grassland", "dry", new Image(MapMenu.class.getResource("/images/textures/dense grassland.jpeg").toString(), true)),
    STONE("stone", "dry", new Image(MapMenu.class.getResource("/images/textures/stone.jpg").toString(), true)),
    OIL("oil", "water", new Image(MapMenu.class.getResource("/images/textures/oil.jpeg").toString(), true)),
    PLAIN("plain", "water", new Image(MapMenu.class.getResource("/images/textures/plain.jpeg").toString(), true)),
    SHALLOW_WATER("shallow water", "water", new Image(MapMenu.class.getResource("/images/textures/shallow water.jpeg").toString(), true)),
    RIVER("river", "water", new Image(MapMenu.class.getResource("/images/textures/river.jpeg").toString(), true)),
    PONE("pone", "water", new Image(MapMenu.class.getResource("/images/textures/pone.jpg").toString(), true)),
    BEACH("beach", "water", new Image(MapMenu.class.getResource("/images/textures/beach.jpeg").toString(), true)),
    SEA("sea", "water", new Image(MapMenu.class.getResource("/images/textures/sea.jpeg").toString(), true));
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
            if (texture.name.equals(name)) {
                return texture;
            }
        return null;
    }

    public String getType() {
        return type;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
