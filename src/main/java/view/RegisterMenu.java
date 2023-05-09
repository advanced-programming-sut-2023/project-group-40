package view;

import controller.MainController;
import controller.RegisterMenuController;
import view.enums.Commands;

import java.io.IOException;
import java.util.regex.Matcher;

public class RegisterMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in register menu");
        while (true) {
            String command = MainController.scanner.nextLine();
            String result = Commands.regexFinder(command, RegisterMenu.class);
            if (result != null) System.out.println(result);
        }
    }

    public static String register(Matcher matcher) throws IOException {
        String username = matcher.group("username");
        String password = matcher.group("password");
        String passwordConfirmation = matcher.group("passwordConfirmation");
        String nickname = matcher.group("nickname");
        String email = matcher.group("email");
        String slogan = matcher.group("slogan");
        String sloganExist = matcher.group("sloganExist");
        return RegisterMenuController.register(username, password, passwordConfirmation, email, nickname, sloganExist, slogan);
    }

    public static String login(Matcher matcher) throws ReflectiveOperationException {
        LoginMenu.run();
        return null;
    }

    public static String pickSecurityQuestion(Matcher matcher) {
        return null;
    }
}
