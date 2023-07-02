package controller;

import model.Government;
import model.User;

public class MainMenuController {
    private static User currentUser;

    public static String logout() {
        return "log out!";
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        MainMenuController.currentUser = currentUser;
    }

    public static String enterGameMenu() {
        Government governmentByUser = Government.getGovernmentByUser(currentUser.getUsername());
        if (Government.getGovernments().size() == 0) {
            Government.addGovernment(currentUser.getUsername());
            GameMenuController.setCurrentGovernment(Government.getGovernmentByUser(currentUser.getUsername()));
            return "you entered a new game!";
        }
        if (governmentByUser == null)
            throw new RuntimeException("game in progress and you can't access game");
        GameMenuController.setCurrentGovernment(governmentByUser);
        return "you entered in progress game";
    }

}
