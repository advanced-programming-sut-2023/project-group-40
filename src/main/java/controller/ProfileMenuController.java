package controller;

import model.User;

public class ProfileMenuController {
    private static User currentUser = new User("username","password","nickname","email",null);

    public static void changeUsername(String username) {
        currentUser.setUsername(username);
    }

    public static String changePassword(String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.equals("")) return "old password is empty!";
        if (newPassword == null || newPassword.equals("")) return "new password is empty!";
        if (!currentUser.getPasswordHash().equals(UserController.generatePasswordHash(oldPassword)))
            return "old password is incorrect!";
        if (oldPassword.equals(newPassword)) return "new password is equal to your current password!";
        if (!UserController.checkPasswordFormat(newPassword)) return "new password is weak!";
        currentUser.setPassword(newPassword);
        return "password changed!";
    }

    public static String changeNickname(String nickname) {
        if (nickname == null || nickname.equals("")) return "nickname is empty!";
        currentUser.setNickname(nickname);
        return "nickname changed!";
    }

    public static String changeEmail(String email) {
        if (email == null || email.equals("")) return "email is empty!";
        if (!UserController.checkEmailFormat(email)) return "email is invalid!";
        if (UserController.isEmailExists(email)) return "email is exists!";
        currentUser.setEmail(email);
        return "email changed!";
    }

    public static String changeSlogan(String slogan) {
        if (slogan == null || slogan.equals("")) return "slogan is empty!";
        currentUser.setSlogan(slogan);
        return "slogan changed!";
    }

    public static String displayHighScore() {
        return "your highscore: " + currentUser.getHighScore();
    }

    public static String displayRank() {
        return "your rank: " + currentUser.getRank();
    }

    public static String displaySlogan() {
        String slogan = currentUser.getSlogan();
        if (slogan == null || slogan.equals("")) return "slogan is empty!";
        return "your slogan: " + slogan;
    }

    public static String displayProfile() {
        String result = "username: " + currentUser.getUsername() + '\n';
        result += "nickname: " + currentUser.getNickname() + '\n';
        result += "email: " + currentUser.getEmail() + '\n';
        result += "rank: " + currentUser.getRank() + '\n';
        result += "highscore: " + currentUser.getHighScore() + '\n';
        result += "slogan: " + currentUser.getSlogan();
        return result;
    }

    public static String removeSlogan() {
        currentUser.setSlogan(null);
        return "slogan deleted";
    }

    public static void setCurrentUser(User currentUser) {
        ProfileMenuController.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
