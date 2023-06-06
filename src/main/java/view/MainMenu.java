package view;

import controller.MainMenuController;
import controller.ProfileMenuController;
import controller.UserController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class MainMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in main menu!");
        while (true) {
            String command = Commands.scanner.nextLine();
            String result = Commands.regexFinder(command, MainMenu.class);
            UserController.updateDatabase();
            if (result != null) System.out.println(result);
        }
    }

    public static String enterProfileMenu(Matcher matcher) throws ReflectiveOperationException {
        System.out.println("you are in profile menu!");
        ProfileMenuController.setCurrentUser(MainMenuController.getCurrentUser());
//        ProfileMenu.run();
        return null;
    }


}
