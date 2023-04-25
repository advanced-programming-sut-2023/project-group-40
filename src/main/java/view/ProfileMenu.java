package view;

import controller.MainController;
import controller.ProfileMenuController;
import view.enums.Commands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    public static void run() throws ReflectiveOperationException {
        while (true) {
            String command = MainController.scanner.nextLine();
            String result = Commands.regexFinder(command, ProfileMenu.class);
            if (command != null) System.out.println(result);
        }
    }
    public static String changeUsername(Matcher matcher){
        String username = matcher.group("username");
        return ProfileMenuController.changeUsername(username);
    }
    public static String changePassword(Matcher matcher){
        String oldPass = matcher.group("oldPassword");
        String newPass = matcher.group("newPassword");
        return ProfileMenuController.changePassword(oldPass, newPass);
    }
    public static String changeNickname(Matcher matcher){
        String nickname = matcher.group("nickname");
        return ProfileMenuController.changeNickname(nickname);
    }
    public static String changeEmail(Matcher matcher){
        String email = matcher.group("email");
        return ProfileMenuController.changeEmail(email);
    }

    public static String changeSlogan(Matcher matcher){
        String slogan = matcher.group("slogan");
        return ProfileMenuController.changeSlogan(slogan);
    }

    public static String displayHighScore(Matcher matcher){
        return ProfileMenuController.displayHighScore();
    }

    public static String displayRank(Matcher matcher){
        return ProfileMenuController.displayRank();
    }
    public static String displaySlogan(Matcher matcher) {
        return ProfileMenuController.displaySlogan();
    }

    public static String displayProfile(Matcher matcher){
        return ProfileMenuController.displayProfile();
    }

    public static String removeSlogan(){
        return ProfileMenuController.removeSlogan();
    }
}
