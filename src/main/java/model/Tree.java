package model;

import javafx.scene.image.Image;

public enum Tree {
    DESERT_SHRUB("desert shrub"),
    CHERRY_PALM("cheery palm"),
    OLIVE_TREE("olive tree"),
    COCONUT_PALM("coconut palm"),
    DATE_PALM("date palm");
    private final String name;
    private Image image;

    Tree(String name) {
        this.name = name;
    }

    public static Tree getTree(String name) {
        for (Tree tree : values())
            if (tree.name.equals(name)) return tree;
        return null;
    }

    public Image getImage() {
        return new Image(Tree.class.getResource("/images/trees/" + name + ".png").toString());
    }

    public String getName() {
        return name;
    }
}
