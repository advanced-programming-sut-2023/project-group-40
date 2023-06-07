package model;

import javafx.scene.image.Image;

public enum Rock {
    ROCK1("rock1"),
    ROCK2("rock2"),
    ROCK3("rock3"),
    ROCK4("rock4"),
    ROCK5("rock5"),
    ROCK6("rock6"),
    ROCK7("rock7");
    String name;

    Rock(String name) {
        this.name = name;
    }

    public Image getImage(){
        return new Image(Rock.class.getResource("/images/rocks/" + name + ".png").toString());
    }

    public static Rock getRock(String name) {
        for (Rock rock : values())
            if (rock.name.equals(name)) return rock;
        return null;
    }

    public String getName() {
        return name;
    }
}
