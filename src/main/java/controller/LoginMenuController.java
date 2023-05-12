package controller;

import model.User;

import java.time.Clock;

public class LoginMenuController {
    private static int countOfTry = 0;
    private static long endOfBanTime = 0;

    public static String login(String username, String password, boolean isStayLoggedIn) {
        long millis = Clock.systemDefaultZone().millis();
        if (millis < endOfBanTime)
            throw new RuntimeException("you are ban for " + (endOfBanTime - millis) / 1000 + "seconds");
        User user = UserController.getUserByUsername(username);
        if (user == null) throw new RuntimeException("username or password is wrong!");
        if (!user.getPasswordHash().equals(UserController.generatePasswordHash(password))) {
            countOfTry++;
            endOfBanTime = millis + countOfTry * 5 * 1000L;
            throw new RuntimeException("username or password didn't match!\n" + "you banned for " + countOfTry * 5 + " seconds");
        }
        MainMenuController.setCurrentUser(user);
        if (isStayLoggedIn) user.setStayLoggedIn(true);
        countOfTry = 0;
        endOfBanTime = 0;
        return "user logged in successfully!";
    }

    public static String checkSecurityQuestion(String username, String answer) {
        User user = UserController.getUserByUsername(username);
        if (user.getSecurityAnswer().equals(answer)) return "enter your new password: ";
        else throw new RuntimeException("wrong answer!");
    }

    public static String changePassword(String username, String newPassword) {
        User user = UserController.getUserByUsername(username);
        if (!UserController.checkPasswordFormat(newPassword))
            throw new RuntimeException("password is weak!");
        user.setPassword(newPassword);
        return "your password will change after verify!";
    }
}
