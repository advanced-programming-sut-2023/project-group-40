package view;

import view.enums.Commands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    public static void run(Scanner scanner) throws ReflectiveOperationException {
        while (true) {
            String command = scanner.nextLine();
            System.out.println(Commands.regexFinder(command));
        }
    }

    public static String exit(Matcher matcher) {
        System.exit(0);
        return null;
    }

    public static String login(Matcher matcher) {
        return null;
    }

    public static void register(Matcher matcher) {
    }

    public static String forgetPassword() {
        return null;
    }
}