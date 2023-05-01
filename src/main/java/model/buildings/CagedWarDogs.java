package model.buildings;

import model.Texture;

import java.util.HashSet;

public class CagedWarDogs extends Building{
    private boolean isOpen = false;

    public CagedWarDogs(String name, int height, int width, int hp, int[] cost, HashSet<Texture> textures,boolean isIllegal) {
        super(name, height, width, hp, cost,textures,isIllegal);
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
