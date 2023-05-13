package model;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum Tree {
    DESERT_SHRUB("desert shrub"),
    CHERRY_PALM("cherry  palm"),
    OLIVE_TREE("olive tree"),
    COCONUT_PALM("coconut"),
    DATE_PALM("date palm");
    final String name;

    Tree(String name) {
        this.name = name;
    }

    public static Tree getTree(String type) {
        for (Tree tree : values())
            if (tree.name.equals(type)) return tree;
        return null;
    }
}
