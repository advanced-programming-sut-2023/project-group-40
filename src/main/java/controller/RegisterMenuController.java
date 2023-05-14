package controller;

import model.User;

import java.util.Random;

public class RegisterMenuController {
    private String username;
    private String password;
    private final String confirmPassword;
    private final String email;
    private final String nickname;
    private String slogan;
    private User registeredUser;
    private boolean isPasswordRandom, isSloganRandom;

    public RegisterMenuController(String username, String password, String confirmPassword, String email,
                                  String nickname, String slogan) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.nickname = nickname;
        this.slogan = slogan;
    }

    public String register() throws Exception {
        String emptyField = checkEmptyField();
        if (emptyField != null) return emptyField;
        if (!UserController.checkUsernameFormat(username)) throw new RuntimeException("username is invalid!");
        if (UserController.isUsernameExists(username)) throw new InterruptedException("username is exists!");
        if (!password.equals(confirmPassword))
            throw new RuntimeException("confirm password doesn't match with password!");
        if (password.equals("random")) {
            password = UserController.generateRandomPassword();
            isPasswordRandom = true;
        }
        if (!UserController.checkPasswordFormat(password)) throw new RuntimeException("password is weak!");
        if (UserController.isEmailExists(email)) throw new RuntimeException("email is exists!");
        if (!UserController.checkEmailFormat(email)) throw new RuntimeException("email is invalid!");
        if (slogan != null && slogan.equals("random")) {
            slogan = UserController.generateRandomSlogan();
            isSloganRandom = true;
        }
        registeredUser = new User(username, password, nickname, email, slogan);
        User.addUser(registeredUser);
        return "first step successful!";
    }

    public String pickSecurityQuestion(int securityQuestionNo, String answer, String answerConfirm) {
        if(securityQuestionNo > 3) throw new RuntimeException("invalid number!");
        if (!answer.equals(answerConfirm)) {
            User.removeUser(registeredUser);
            throw new RuntimeException("confirm answer doesn't match with answer!");
        }
        registeredUser.setSecurityQuestionNo(securityQuestionNo);
        registeredUser.setSecurityAnswer(answer);
        return "second step successful!";
    }

    private String checkEmptyField() {
        if (username == null || username.equals("")) return "username is empty!";
        if (password == null || password.equals("")) return "password is empty!";
        if (confirmPassword == null || confirmPassword.equals("")) return "password confirmation is empty!";
        if (email == null || email.equals("")) return "email is empty!";
        if (nickname == null || nickname.equals("")) return "nickname is empty!";
        return null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPasswordRandom() {
        return isPasswordRandom;
    }

    public boolean isSloganRandom() {
        return isSloganRandom;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
