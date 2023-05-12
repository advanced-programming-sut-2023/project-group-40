package model;

public enum Texture {
    LAND("land","dry"),
    LAND_WITH_PEBBLES("land with pebbles","dry"),
    STONE_BLOCKS("stone blocks","dry"),
    IRON("iron","dry"),
    GRASS("grass","dry"),
    GRASS_LAND("grassland","dry"),
    STONE("stone","dry"),
    DENSE_GRASS_LAND("dense grassland","dry"),
    OIL("oil","water"),
    PLAIN("plain","water"),
    SHALLOW_WATER("shallow water","water"),
    RIVER("river","water"),
    KOCH_POND("koch pond","water"),
    BIG_POND("big pond","water"),
    SEA_COAST("sea coast","water"),
    SEA("sea","water");
    private final String name;
    private final String type;

    Texture(String name ,String type) {
        this.name = name;
        this.type = type;
    }

    public static Texture getTextureByName(String name) {
        for (Texture texture : values())
            if (texture.name.equals(name)) return texture;
        return null;
    }

    public String getType() {
        return type;
    }
}
