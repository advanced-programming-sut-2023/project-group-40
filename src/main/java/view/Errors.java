package view;

import javafx.scene.control.Label;

public enum Errors {
    USERNAME_ERROR(new Label("username is empty!")),
    PASSWORD_ERROR(new Label("password is empty!")),
    OLD_PASSWORD_ERROR(new Label("password is empty!")),
    CAPTCHA_ERROR(new Label("captcha is incorrect!")),
    EMAIL_ERROR(new Label("email is empty!")),
    NICKNAME_ERROR(new Label("nickname is empty!")),
    SLOGAN_ERROR(new Label("slogan is empty!")),
    SECURITY_QUESTION_ERROR(new Label("security question is incorrect!")),
    SECURITY_ANSWER_ERROR(new Label("security answer is incorrect!"));
    private  Label errorLabel;
    Errors(Label errorLabel) {
        this.errorLabel = errorLabel;
    }

    public Label getErrorLabel() {
        Label label = errorLabel;
        label.setStyle("-fx-text-fill: red");
        return label;
    }
}
