package model.buildings;

public class CagedWarDogs extends Building{
    private boolean isOpen = false;

    public CagedWarDogs(String name, int height, int width, int hp, int[] cost) {
        super(name, height, width, hp, cost);
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
