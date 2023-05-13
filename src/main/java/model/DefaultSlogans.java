package model;

public enum DefaultSlogans {
    SLOGAN1("DEAD1"),
    SLOGAN2("DEAD2"),
    SLOGAN3("DEAD3");

    DefaultSlogans(String slogan) {
        this.slogan = slogan;
    }

    private String slogan;

    public String getSlogan() {
        return slogan;
    }
}
