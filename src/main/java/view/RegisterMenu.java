package view;

import controller.RegisterMenuController;
import controller.UserController;
import view.enums.Commands;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.regex.Matcher;

public class RegisterMenu {
    public static void run() throws ReflectiveOperationException{
        System.out.println("you are in register menu");
        while (true) {
            String command = Commands.scanner.nextLine();
            String result = Commands.regexFinder(command, RegisterMenu.class);
            UserController.updateDatabase();
            if (result != null) System.out.println(result);
        }
    }

    public static String register(Matcher matcher) throws Exception {
        RegisterMenuController registerMenuController = initializeVariables(matcher);
        try {
            System.out.println(registerMenuController.register());
            handleRandomConfirm(registerMenuController);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            try {
                suggestUsername(registerMenuController);
            } catch (RuntimeException r) {
                return r.getMessage();
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }

        try {
            System.out.println(pickSecurityQuestion(registerMenuController));
        } catch (RuntimeException e) {
            return e.getMessage();
        }

        return LoginMenu.handleCaptcha();
    }


    private static RegisterMenuController initializeVariables(Matcher matcher) {
        String username = Commands.eraseQuot(matcher.group("username"));
        String password = Commands.eraseQuot(matcher.group("password"));
        String passwordConfirmation = Commands.eraseQuot(matcher.group("passwordConfirmation"));
        String nickname = Commands.eraseQuot(matcher.group("nickname"));
        String email = Commands.eraseQuot(matcher.group("email"));
        String slogan = Commands.eraseQuot(matcher.group("slogan"));
        return new RegisterMenuController(username, password, passwordConfirmation,
                email, nickname, slogan);
    }

    private static void suggestUsername(RegisterMenuController registerMenuController) throws Exception {
        String suggestedUsername = registerMenuController.getUsername();
        while (UserController.isUsernameExists(suggestedUsername))
            suggestedUsername += suggestedUsername.charAt(suggestedUsername.length() - 1);
        registerMenuController.setUsername(suggestedUsername);
        System.out.print("suggested username: " + suggestedUsername + "\n confirm [Y/N]: ");
        while (true) {
            String response = Commands.scanner.nextLine();
            if (response.equalsIgnoreCase("N")) throw new RuntimeException("register failed!");
            else if (response.equalsIgnoreCase("Y")) {
                System.out.println(registerMenuController.register());
                handleRandomConfirm(registerMenuController);
                break;
            } else System.out.println("invalid command!");
        }
    }

    private static void handleRandomConfirm(RegisterMenuController registerMenuController) {
        if (registerMenuController.isSloganRandom())
            System.out.println("Your slogan is: \"" + registerMenuController.getSlogan() + "\"");
        if (registerMenuController.isPasswordRandom()) {
            System.out.println("Your password is: \"" + registerMenuController.getPassword() + "\"");
            while (true) {
                System.out.print("retype your password: ");
                String confirm = Commands.scanner.nextLine();
                if (confirm.equals(registerMenuController.getPassword())) break;
                else System.out.println("doesn't match!");
            }
        }
    }

    public static String pickSecurityQuestion(RegisterMenuController registerMenuController) {
        System.out.println("Pick your security question:");
        System.out.print(UserController.getSecurityQuestionsList());
        while (true) {
            String input = Commands.scanner.nextLine();
            if (!Commands.PICK_SECURITY_QUESTION.canMatch(input))
                System.out.println("invalid command!");
            else {
                Matcher matcher = Commands.PICK_SECURITY_QUESTION.getMatcher(input);
                matcher.find();
                int securityQuestionNo = Integer.parseInt(matcher.group("securityQuestionNo"));
                String answer = Commands.eraseQuot(matcher.group("answer"));
                String answerConfirm = Commands.eraseQuot(matcher.group("answerConfirm"));
                return registerMenuController.pickSecurityQuestion(securityQuestionNo, answer, answerConfirm);
            }
        }
    }

    public static String login(Matcher matcher) throws ReflectiveOperationException {
        LoginMenu.run();
        return null;
    }
}
