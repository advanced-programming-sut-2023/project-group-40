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
