package view.enums;

import view.LoginMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    ;
    private final String regex;
    private final String methodName;
    private final Class<?> menuClass;

    Commands(String regex, Class<?> menuClass, String methodName) {
        this.regex = regex;
        this.menuClass = menuClass;
        this.methodName = methodName;
    }

    public static String regexFinder(String inputCommand) throws ReflectiveOperationException {
        return null;
    }

    public String getRegex() {
        return regex;
    }
}
