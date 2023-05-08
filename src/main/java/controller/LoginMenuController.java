package controller;

import model.SecurityQuestions;
import model.User;
import view.MainMenu;

public class LoginMenuController {
    private static int countOfTry = 0;

    public static String login(String username, String password, boolean isStayLoggedIn) throws InterruptedException, ReflectiveOperationException {
        countOfTry++;
        User user = User.getStayedLoginUser();
        if (user == null) {
            user = User.getUserByUsername(username);
            if (user == null) return "Username and password didn't match!";
            if (!user.getPasswordHash().equals(password)) {
                int time = 5 * countOfTry;
                System.out.println("Username and password didn't match!\n" + "you banned for " + time + " seconds");
                Thread.sleep(time * 1000L);
                return null;
            }
        }
        MainMenuController.setCurrentUser(user);
        if (isStayLoggedIn) user.setStayLoggedIn(true);
        System.out.println("user logged in successfully!");
        MainMenu.run();
        return null;
    }

    public static String forgetPassword(String username) {
        User user = User.getUserByUsername(username);
        if (user == null) return "username not exist!";
        for (SecurityQuestions securityQuestion : SecurityQuestions.values())
            System.out.println(securityQuestion.getQuestion());
        String answer = MainController.scanner.nextLine();
        if (answer.equals(user.getSecurityAnswer())) {
            System.out.println("please enter your new password :");
            String newPassword = MainController.scanner.nextLine();
            user.setPasswordHash(newPassword);
            System.out.println("your password successfully changed!");
            return null;
        }
        return "your answer is not correct!";
    }
}
