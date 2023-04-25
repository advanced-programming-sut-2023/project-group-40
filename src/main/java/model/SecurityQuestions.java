package model;

public enum SecurityQuestions {
    NO_1("1- What is my father’s name?"),
    NO_2("2- What was my first pet’s name?"),
    NO_3("3- What is my mother’s last name?");
    private final String question;

    SecurityQuestions(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}