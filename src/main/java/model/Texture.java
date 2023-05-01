package model;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum Texture {
    LAND("land");
    private final String name;

    Texture(String name) {
        this.name = name;
    }

    public static Texture getTextureByName(String name) {
        for (Texture texture : values())
            if (texture.name.equals(name)) return texture;
        return null;
    }
}
