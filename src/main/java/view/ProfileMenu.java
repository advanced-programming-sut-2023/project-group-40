package view;

import controller.ProfileMenuController;
import controller.UserController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class ProfileMenu {
    public static void run() throws ReflectiveOperationException {
        while (true) {
            String command = Commands.scanner.nextLine();
            if(command.equals("return")){
                ProfileMenuController.setCurrentUser(null);
                System.out.println("you are in main menu");
                return;
            }
            String result = Commands.regexFinder(command, ProfileMenu.class);
            UserController.updateDatabase();
            if (result != null) System.out.println(result);
        }
    }

    public static String changeUsername(Matcher matcher) {
        String username = Commands.eraseQuot(matcher.group("username"));
        return ProfileMenuController.changeUsername(username);
    }

    public static String changePassword(Matcher matcher) {
        String oldPass = Commands.eraseQuot(matcher.group("oldPassword"));
        String newPass = Commands.eraseQuot(matcher.group("newPassword"));
        return ProfileMenuController.changePassword(oldPass, newPass);
    }

    public static String changeNickname(Matcher matcher) {
        String nickname = Commands.eraseQuot(matcher.group("nickname"));
        return ProfileMenuController.changeNickname(nickname);
    }

    public static String changeEmail(Matcher matcher) {
        String email = Commands.eraseQuot(matcher.group("email"));
        return ProfileMenuController.changeEmail(email);
    }

    public static String changeSlogan(Matcher matcher) {
        String slogan = Commands.eraseQuot(matcher.group("slogan"));
        return ProfileMenuController.changeSlogan(slogan);
    }

    public static String removeSlogan(Matcher matcher) {
        return ProfileMenuController.removeSlogan();
    }

    public static String displayHighScore(Matcher matcher) {
        return ProfileMenuController.displayHighScore();
    }

    public static String displayRank(Matcher matcher) {
        return ProfileMenuController.displayRank();
    }

    public static String displaySlogan(Matcher matcher) {
        return ProfileMenuController.displaySlogan();
    }

    public static String displayProfile(Matcher matcher) {
        return ProfileMenuController.displayProfile();
    }

}
