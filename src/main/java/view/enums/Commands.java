package view.enums;

import view.LoginMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    PROFILE_CHANGE_USERNAME("profile change -u (?<username>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changeUsername"),
    PROFILE_CHANGE_NICKNAME("profile change -n (?<nickname>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changeNickname"),
    PROFILE_CHANGE_EMAIL("profile change -e (?<email>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changeEmail"),
    PROFILE_CHANGE_SLOGAN("profile change -s (?<slogan>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changeSlogan"),
    PROFILE_CHANGE_PASSWORD("profile change -o (?<oldPassword>(\"[^\"]*\")|([^\"\\s]*)) -n (?<newPassword>(\"[^\"]*\")|([^\"\\s]*))", ProfileMenu.class, "changePassword"),
    PROFILE_REMOVE_SLOGAN("profile remove slogan", ProfileMenu.class, "removeSlogan"),
    PROFILE_DISPLAY_HIGHSCORE("profile display highscore", ProfileMenu.class, "displayHighscore"),
    PROFILE_DISPLAY_RANK("profile display rank", ProfileMenu.class, "displayRank"),
    PROFILE_DISPLAY_SLOGAN("profile display slogan", ProfileMenu.class, "displaySlogan"),
    PROFILE_DISPLAY("profile display", ProfileMenu.class, "profileDisplay");
    private final String regex;
    private final String methodName;
    private final Class<?> menuClass;

    Commands(String regex, Class<?> menuClass, String methodName) {
        this.regex = regex;
        this.menuClass = menuClass;
        this.methodName = methodName;
    }

    public static String regexFinder (String inputCommand, Class<?> currentClass) throws ReflectiveOperationException{
        for (Commands command : values()) {
            Matcher matcher = Pattern.compile(command.regex).matcher(inputCommand);
            if (currentClass != command.menuClass) continue;
            if (matcher.matches())
                return (String) command.menuClass.getMethod(command.methodName,Matcher.class).
                        invoke(null,matcher);
        }
        return "invalid command!";
    }

    public String getRegex() {
        return regex;
    }
}
