package model;

public enum DefaultSlogans {
    ;

    DefaultSlogans(String slogan) {
        this.slogan = slogan;
    }

    private String slogan;

    public String getSlogan() {
        return slogan;
    }
}
