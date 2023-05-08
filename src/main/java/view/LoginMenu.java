package view;

import controller.LoginMenuController;
import controller.MainController;
import controller.UserController;
import view.enums.Commands;

import java.io.IOException;
import java.util.regex.Matcher;

public class LoginMenu {
    public static void run() throws ReflectiveOperationException {
        UserController.autoLogin();
        System.out.println("you are in login menu");
        while (true) {
            String command = MainController.scanner.nextLine();
            String result = Commands.regexFinder(command, LoginMenu.class);
            if (result != null) System.out.println(result);
        }
    }

    public static String exit(Matcher matcher) throws IOException {
        UserController.updateDatabase();
        System.exit(0);
        return null;
    }

    public static String login(Matcher matcher) throws ReflectiveOperationException {
        String username = matcher.group("username");
        String password = matcher.group("password");
        boolean isStayLoggedIn = matcher.group("stayLoggedIn") != null;
        try {
            System.out.println(LoginMenuController.login(username, password, isStayLoggedIn));
            UserController.handleCaptcha();
            MainMenu.run();
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public static String register(Matcher matcher) throws ReflectiveOperationException {
        RegisterMenu.run();
        return null;
    }

    public static String forgetPassword(Matcher matcher) {
        String username = matcher.group("username");
        if (UserController.getUserByUsername(username) == null) return "username not exist!";
        System.out.println(UserController.getSecurityQuestionsList());
        String answer = Commands.scanner.nextLine();
        try {
            System.out.print(LoginMenuController.checkSecurityQuestion(username, answer));
            String newPassword = Commands.scanner.nextLine();
            try {
                System.out.println(LoginMenuController.changePassword(username, newPassword));
                UserController.handleCaptcha();
                return "your password will successfully changed!";
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}