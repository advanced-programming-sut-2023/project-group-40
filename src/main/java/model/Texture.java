package model;

import static controller.MapMenuController.*;
public enum Texture {
    LAND("land","dry", ANSI_BLACK_BACKGROUND,ANSI_WHITE),
    LAND_WITH_PEBBLES("land with pebbles","dry",ANSI_BLACK_BACKGROUND,ANSI_WHITE),
    STONE_BLOCKS("stone blocks","dry",ANSI_WHITE_BACKGROUND,ANSI_BLACK),
    IRON("iron","dry",ANSI_BLACK_BACKGROUND,ANSI_WHITE),
    GRASS("grass","dry",ANSI_GREEN_BACKGROUND,ANSI_RED),
    GRASS_LAND("grassland","dry",ANSI_GREEN_BACKGROUND,ANSI_RED),
    STONE("stone","dry",ANSI_WHITE_BACKGROUND,ANSI_BLACK),
    DENSE_GRASS_LAND("dense grassland","dry",ANSI_GREEN_BACKGROUND,ANSI_RED),
    OIL("oil","water",ANSI_CYAN_BACKGROUND,ANSI_PURPLE),
    PLAIN("plain","water",ANSI_CYAN_BACKGROUND,ANSI_PURPLE),
    SHALLOW_WATER("shallow water","water",ANSI_CYAN_BACKGROUND,ANSI_PURPLE),
    RIVER("river","water",ANSI_CYAN_BACKGROUND,ANSI_PURPLE),
    KOCH_POND("koch pond","water",ANSI_CYAN_BACKGROUND,ANSI_PURPLE),
    BIG_POND("big pond","water",ANSI_CYAN_BACKGROUND,ANSI_PURPLE),
    SEA_COAST("sea coast","water",ANSI_CYAN_BACKGROUND,ANSI_PURPLE),
    SEA("sea","water",ANSI_CYAN_BACKGROUND,ANSI_PURPLE);
    private final String name;
    private final String type;
    private final String backGroundColor;
    private final String textColor;

    Texture(String name ,String type, String backGroundColor,String textColor) {
        this.name = name;
        this.type = type;
        this.backGroundColor = backGroundColor;
        this.textColor = textColor;
    }

    public static Texture getTextureByName(String name) {
        for (Texture texture : values())
            if (texture.name.equals(name)) return texture;
        return null;
    }

    public String getType() {
        return type;
    }

    public String getBackGroundColor() {
        return backGroundColor;
    }

    public String getTextColor() {
        return textColor;
    }
}
