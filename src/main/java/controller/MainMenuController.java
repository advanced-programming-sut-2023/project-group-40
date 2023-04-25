package controller;

import model.User;

public class MainMenuController {
    private static User currentUser;
    public static String logout(){
        return "log out!";
    }

    public static void setCurrentUser(User currentUser) {
        MainMenuController.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
