package model;

import java.util.Arrays;

public enum Color {
    RED("red"),
    BLUE("blue"),
    GREEN("green"),
    YELLOW("yellow"),
    BLACK("black"),
    WHITE("white"),
    BROWN("brown"),
    GREY("grey");

    private final String colorName;
    private Government government;

    Color(String colorName) {
        this.colorName = colorName;
    }

    public static String setOwnerOfColor(String colorName, Government government) {
        boolean isColorValid = Arrays.stream(values())
                .anyMatch(color -> color.colorName.equals(colorName) && color.government == null);
        if (!isColorValid)
            throw new RuntimeException("your color isn't exists or used by another government!");
        for (Color c : values()) {
            if (c.getColorName().equals(colorName)) {
                c.government = government;
                government.setColor(c);
            }
        }
        return "you successfully choose your government color";
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public String getColorName() {
        return colorName;
    }
}
