package view;

import controller.LoginMenuController;
import controller.MainController;
import model.User;
import view.enums.Commands;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in login menu");
        while (true) {
            String command = MainController.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, LoginMenu.class));
        }
    }

    public static String exit(Matcher matcher) throws IOException {
        User.updateDatabase();
        System.exit(0);
        return null;
    }

    public static String login(Matcher matcher) throws InterruptedException, ReflectiveOperationException {
        String username = matcher.group("username");
        String password = matcher.group("password");
        boolean isStayLoggedIn = matcher.group("stayLoggedIn") != null;
        return LoginMenuController.login(username, password, isStayLoggedIn);
    }

    public static String register(Matcher matcher) throws ReflectiveOperationException {
        RegisterMenu.run();
        return null;
    }

    public static String forgetPassword(Matcher matcher) {
        String username = matcher.group("username");
        return LoginMenuController.forgetPassword(username);
    }
}