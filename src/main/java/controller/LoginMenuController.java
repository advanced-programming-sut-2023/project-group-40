package controller;

import model.User;

import java.nio.channels.UnresolvedAddressException;
import java.time.Clock;

public class LoginMenuController {
    private User user;


    public static boolean isPasswordCorrect(String username, String password) {
        return UserController.getUserByUsername(username).getPasswordHash().equals(UserController.generatePasswordHash(password));
    }

    public static int getSecurityQuestionNo(String username) {
        return UserController.getUserByUsername(username).getSecurityQuestionNo();
    }

    public static String getSecurityAnswer(String username) {
        return UserController.getUserByUsername(username).getSecurityAnswer();
    }
}
