package controller;

import model.Government;
import model.Map;
import model.User;
import view.MainMenu;

public class MainMenuController {
    private static User currentUser;

    public static String logout() {
        return "log out!";
    }


    public static void setCurrentUser(User currentUser) {
        MainMenuController.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static String enterGameMenu() {
        Government governmentByUser = Government.getGovernmentByUser(currentUser);
        if (Government.getGovernments().size() == 0) {
            Government.addGovernment(currentUser.getUsername());
            GameMenuController.setCurrentGovernment(Government.getGovernmentByUser(currentUser));
            return "you entered a new game!";
        }
        if (governmentByUser == null)
            throw new RuntimeException("game in progress and you can't access game");
        GameMenuController.setCurrentGovernment(governmentByUser);
        return "you entered in progress game";
    }

    public static void continueGame() {
        User user = MainMenuController.getCurrentUser();
        GameMenuController.setCurrentGovernment(Government.getGovernmentByUser(user));
    }
}
