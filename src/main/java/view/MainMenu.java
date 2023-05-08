package view;

import controller.GameMenuController;
import controller.MainController;
import controller.MainMenuController;
import controller.ProfileMenuController;
import model.Government;
import view.enums.Commands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in main menu");
        while (true){
            String command = MainController.scanner.nextLine();
            String result = Commands.regexFinder(command,MainMenu.class);
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
        GameMenuController.setCurrentGovernment(MainMenuController.getCurrentUser());
        GameMenu.run();
        return null;
    }
    public static String logout(Matcher matcher) throws ReflectiveOperationException {
        System.out.println("user logged out successfully!");
        MainMenuController.setCurrentUser(null);
        LoginMenu.run();
        return null;
    }
}
