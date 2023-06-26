package controller;

import model.User;

public class ProfileMenuController {
    private static User currentUser = new User("username", "password", "nickname", "email", null);

    public static void changeUsername(String username) {
        currentUser.setUsername(username);
    }

    public static void changePassword(String newPassword) {
        currentUser.setPassword(newPassword);
    }

    public static String changeNickname(String nickname) {
        if (nickname == null || nickname.equals("")) return "nickname is empty!";
        currentUser.setNickname(nickname);
        return "nickname changed!";
    }

    public static String changeEmail(String email) {
        if (email == null || email.equals("")) return "email is empty!";
        if (!UserController.checkEmailFormat(email)) return "email is invalid!";
        currentUser.setEmail(email);
        return "email changed!";
    }

    public static void changeSlogan(String slogan) {
        currentUser.setSlogan(slogan);
    }

    public static boolean isPasswordCorrect(String newPassword) {
        return currentUser.getPasswordHash().equals(UserController.generatePasswordHash(newPassword));
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        ProfileMenuController.currentUser = currentUser;
    }

    public static void changeAvatar(String avatarPath) {
        currentUser.setAvatarPath(avatarPath);
    }
}
