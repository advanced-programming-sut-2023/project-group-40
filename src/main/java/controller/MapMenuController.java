package controller;

import model.Government;
import model.User;
import view.GameMenu;

public class MapMenuController {

    public static boolean isFirstPlayer() {
        return Government.getGovernmentsSize() == 0;
    }

    public static boolean isUserInGame(User user) {
        return Government.getGovernmentByUser(user) != null;
    }

    public static boolean isCurrentGovernmentChooseColor(User user) {
        return Government.getGovernmentByUser(user).getColor() != null;
    }

    public static void checkGameStarted() {
        if (Government.checkAllGovernmentsChooseColor())
            GameMenu.setGameStarted(true);
    }

    public static void addPlayer(String username) {
        Government.addGovernment(username);
    }

    public static boolean isPlayerAdded(String username) {
        return Government.getGovernmentByUser(User.getUserByUsername(username)) != null;
    }

    public static boolean isPlayerValid(String username) {
        return User.isUsernameExists(username);
    }
}
