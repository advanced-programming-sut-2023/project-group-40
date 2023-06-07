package model;

public enum DefaultSlogans {
    NO_1("Let us fight for the truth"),
    NO_2("Start Your Journey"),
    NO_3("Join the Crusade and Lead the Way!"),
    NO_4("Choose Greatness"),
    NO_5("It's Your Chance to Make a Difference"),
    NO_6("Believe in Miracles"),
    NO_7("Shape the Future");

    private final String slogan;

    DefaultSlogans(String slogan) {
        this.slogan = slogan;
    }

    public String getSlogan() {
        return slogan;
    }
}
