package controller;

import model.User;

public class MainMenuController {
    private static User currentUser;

    public static void setCurrentUser(User currentUser) {
        MainMenuController.currentUser = currentUser;
    }
}
