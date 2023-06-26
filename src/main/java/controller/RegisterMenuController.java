package controller;

import model.User;

public class RegisterMenuController {

    public static void register(String username, String password, String email, String nickname, String slogan,
                                int securityQuestionNo, String securityQuestionAnswer) {
        User user = new User(username, password, nickname, email, slogan);
        user.setSecurityQuestionNo(securityQuestionNo);
        user.setSecurityAnswer(securityQuestionAnswer);
        User.addUser(user);
        //UserController.updateDatabase();
    }

}
