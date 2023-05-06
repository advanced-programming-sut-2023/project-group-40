package view;

import com.sun.tools.javac.Main;
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
        while (true){
            String command = MainController.scanner.nextLine();
            String result = Commands.regexFinder(command,MainMenu.class);
            if (result != null) System.out.println(result);
        }
    }
    public static String enterProfileMenu(Matcher matcher) throws ReflectiveOperationException {
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
        MainMenuController.setCurrentUser(null);
        LoginMenu.run();
        return null;
    }
}
