package view;

import controller.GameMenuController;
import controller.MainMenuController;
import controller.ProfileMenuController;
import controller.UserController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class MainMenu {
    public static void run() throws ReflectiveOperationException {
        while (true) {
            String command = Commands.scanner.nextLine();
            String result = Commands.regexFinder(command, MainMenu.class);
            if (result != null) System.out.println(result);
        }
    }

    public static String enterProfileMenu(Matcher matcher) throws ReflectiveOperationException {
        System.out.println("you are in profile menu!");
        ProfileMenuController.setCurrentUser(MainMenuController.getCurrentUser());
        ProfileMenu.run();
        return null;
    }

    public static String enterGameMenu(Matcher matcher) throws ReflectiveOperationException {
        try {
            System.out.println(MainMenuController.enterGameMenu());
            GameMenu.run();
            return null;
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

    public static String logout(Matcher matcher) throws ReflectiveOperationException {
        System.out.println("user logged out successfully!");
        MainMenuController.setCurrentUser(null);
        if(UserController.getStayedLoginUser() != null) UserController.getStayedLoginUser().setStayLoggedIn(false);
        LoginMenu.run();
        return null;
    }
}
