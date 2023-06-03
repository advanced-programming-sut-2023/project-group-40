package view;

import controller.LoginMenuController;
import controller.ProfileMenuController;
import controller.UserController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TextFieldController {
    private static boolean successful = true;

    public static void checkExistUsername(HBox usernameHBox , TextField username) {
        if (username.getText().length() == 0) {
            successful = false;
            Errors.USERNAME_ERROR.getErrorLabel().setText("username is empty!");
            if (usernameHBox.getChildren().size() == 2)
                usernameHBox.getChildren().add(Errors.USERNAME_ERROR.getErrorLabel());
        } else if (UserController.isUsernameExists(username.getText())) {
            successful = false;
            Errors.USERNAME_ERROR.getErrorLabel().setText("username is exists!");
            if (usernameHBox.getChildren().size() == 2) usernameHBox.getChildren().add(Errors.USERNAME_ERROR.getErrorLabel());
        } else usernameHBox.getChildren().remove(Errors.USERNAME_ERROR.getErrorLabel());
    }

    public static void checkNotExistUsername(HBox usernameHBox , TextField username) {
        if (username.getText().length() == 0) {
            successful = false;
            Errors.USERNAME_ERROR.getErrorLabel().setText("username is empty!");
            if (usernameHBox.getChildren().size() == 2)
                usernameHBox.getChildren().add(Errors.USERNAME_ERROR.getErrorLabel());
        }
        else if (!UserController.isUsernameExists(username.getText())) {
            successful = false;
            Errors.USERNAME_ERROR.getErrorLabel().setText("username is not exists!");
            if (usernameHBox.getChildren().size() == 2) usernameHBox.getChildren().add(Errors.USERNAME_ERROR.getErrorLabel());
        }
        else usernameHBox.getChildren().remove(Errors.USERNAME_ERROR.getErrorLabel());
    }

    public static void checkSlogan(HBox sloganHBox , TextField slogan) {
        if (slogan.getText().length() == 0 && sloganHBox.getChildren().size() == 2)   {
            successful = false;
            if (sloganHBox.getChildren().size() == 2)
                sloganHBox.getChildren().add(Errors.SLOGAN_ERROR.getErrorLabel());
        }
        else sloganHBox.getChildren().remove(Errors.SLOGAN_ERROR.getErrorLabel());
    }

    public static void checkNickname(HBox nicknameHBox , TextField nickname) {
        if (nickname.getText().length() == 0) {
            successful = false;
            if (nicknameHBox.getChildren().size() == 2)
                nicknameHBox.getChildren().add(Errors.NICKNAME_ERROR.getErrorLabel());
        } else nicknameHBox.getChildren().remove(Errors.NICKNAME_ERROR.getErrorLabel());
    }

    public static void checkEmail(HBox emailHBox , TextField email) {
        if (email.getText().length() == 0) {
            successful = false;
            Errors.EMAIL_ERROR.getErrorLabel().setText("email is empty!");
            if (emailHBox.getChildren().size() == 2)
                emailHBox.getChildren().add(Errors.EMAIL_ERROR.getErrorLabel());
        }
        else if (UserController.isEmailExists(email.getText())) {
            Errors.EMAIL_ERROR.getErrorLabel().setText("email is exists!");
            if (emailHBox.getChildren().size() == 2)
                emailHBox.getChildren().add(Errors.EMAIL_ERROR.getErrorLabel());
        } else emailHBox.getChildren().remove(Errors.EMAIL_ERROR.getErrorLabel());
    }

    public static void checkPassword(HBox passwordHBox,TextField password) {
        if (password.getText().length() == 0) {
            successful = false;
            Errors.PASSWORD_ERROR.getErrorLabel().setText("password is empty!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(Errors.PASSWORD_ERROR.getErrorLabel());
        } else if (!UserController.checkPasswordFormat(password.getText())) {
            successful = false;
            Errors.PASSWORD_ERROR.getErrorLabel().setText("password is weak!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(Errors.PASSWORD_ERROR.getErrorLabel());
        } else passwordHBox.getChildren().remove(Errors.PASSWORD_ERROR.getErrorLabel());
    }

    public static void checkPassword(HBox passwordHBox,Label passwordLabel,TextField username,TextField password, Label passwordError) {
        if (password.getText().length() == 0) {
            successful = false;
            passwordError.setText("password is empty!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(passwordError);
        }
        else if (!UserController.checkPasswordFormat(password.getText())) {
            successful = false;
            passwordError.setText("password is weak!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(passwordError);
        }
        else if (!passwordLabel.getText().equals("new password : ") &&
                !LoginMenuController.isPasswordCorrect(username.getText(),password.getText())){
            successful = false;
            passwordError.setText("password is incorrect!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(passwordError);
        }
        else passwordHBox.getChildren().remove(passwordError);
    }

    public static void checkPassword(HBox newPasswordHBox,TextField newPassword,HBox oldPasswordHBox,TextField oldPassword) {
        if (!ProfileMenuController.isPasswordCorrect(oldPassword.getText())){
            successful = false;
            Errors.OLD_PASSWORD_ERROR.getErrorLabel().setText("old password is incorrect!");
            if (oldPasswordHBox.getChildren().size() == 2)
                oldPasswordHBox.getChildren().add(Errors.OLD_PASSWORD_ERROR.getErrorLabel());
        }
        else oldPasswordHBox.getChildren().remove(Errors.OLD_PASSWORD_ERROR.getErrorLabel());
        if (newPassword.getText().length() == 0) {
            successful = false;
            Errors.PASSWORD_ERROR.getErrorLabel().setText("password is empty!");
            if (newPasswordHBox.getChildren().size() == 2)
                newPasswordHBox.getChildren().add(Errors.PASSWORD_ERROR.getErrorLabel());
        }
        else if (!UserController.checkPasswordFormat(newPassword.getText())) {
            successful = false;
            Errors.PASSWORD_ERROR.getErrorLabel().setText("password is weak!");
            if (newPasswordHBox.getChildren().size() == 2)
                newPasswordHBox.getChildren().add(Errors.PASSWORD_ERROR.getErrorLabel());
        }
        else newPasswordHBox.getChildren().remove(Errors.PASSWORD_ERROR.getErrorLabel());
    }

    public static void checkSecurity(TextField username, ComboBox<String> securityQuestions,HBox securityQuestionsHBox,Label securityQuestionError,HBox securityAnswerHBox,TextField securityAnswer, Label securityAnswerError) {
        if (!UserController.isUsernameExists(username.getText())) return;
        if (securityQuestions.getItems().indexOf(securityQuestions.getValue()) + 1
                != LoginMenuController.getSecurityQuestionNo(username.getText())) {
            if (securityQuestionsHBox.getChildren().size() == 2)
                securityQuestionsHBox.getChildren().add(securityQuestionError);
            successful = false;
        }
        if (!securityAnswer.getText().equals(LoginMenuController.getSecurityAnswer(username.getText()))) {
            if (securityAnswerHBox.getChildren().size() == 2)
                securityAnswerHBox.getChildren().add(securityAnswerError);
            successful = false;
        }
        else securityAnswerHBox.getChildren().remove(securityAnswerError);
    }

    public static boolean isSuccessful() {
        return successful;
    }

    public static void setSuccessful(boolean successful) {
        TextFieldController.successful = successful;
    }

    public static void checkSecurityEmpty(ComboBox<String> securityQuestions, HBox securityQuestionsHBox, HBox securityAnswerHBox, TextField securityAnswer) {
        if (securityAnswer.getText().isEmpty()) {
            if (securityAnswerHBox.getChildren().size() == 2)
                securityAnswerHBox.getChildren().add(Errors.SECURITY_ANSWER_ERROR.getErrorLabel());
            successful = false;
        }
        else securityAnswerHBox.getChildren().remove(Errors.SECURITY_ANSWER_ERROR.getErrorLabel());
        if (securityQuestions.getValue() == null) {
            if (securityQuestionsHBox.getChildren().size() == 2)
                securityQuestionsHBox.getChildren().add(Errors.SECURITY_QUESTION_ERROR.getErrorLabel());
            successful = false;
        }
        else securityQuestionsHBox.getChildren().remove(Errors.SECURITY_QUESTION_ERROR.getErrorLabel());
    }
}
