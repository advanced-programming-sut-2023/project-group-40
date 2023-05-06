package controller;

import model.User;

public class ProfileMenuController {
    private static User currentUser;

    public static String changeUsername(String username){
        if (username == null || username.equals("")) return "username is empty!";
        if (!User.checkUsernameFormat(username)) return "username is invalid!";
        if (User.isUsernameExists(username)) return "username is exists!";
        currentUser.setUsername(username);
        return "username changed!";
    }
    public static String changePassword(String oldPassword, String newPassword){
        if (oldPassword == null || oldPassword.equals("")) return "old-password is empty!";
        if (newPassword == null || newPassword.equals("")) return "new-password is empty!";
        if(!currentUser.checkPassword(oldPassword)) return "old-password is incorrect!";
        if(oldPassword.equals(newPassword)) return "Please enter a new password!";
        if (!User.checkPasswordFormat(newPassword)) return "new-password is weak!";

        System.out.print("Please enter your new password again: ");
        if(MainController.scanner.nextLine().equals(newPassword)) {
            currentUser.setPasswordHash(User.generatePasswordHash(newPassword));
            return "password changed!";
        } else return "new-password is wrong!";
    }
    public static String changeNickname(String nickname){
        if (nickname == null || nickname.equals("")) return "nickname is empty!";
        currentUser.setNickname(nickname);
        return "nickname changed!";
    }
    public static String changeEmail(String email){
        if (email == null || email.equals("")) return "email is empty!";
        if (!User.checkEmailFormat(email)) return "email is invalid!";
        if (User.isEmailExists(email)) return "email is exists!";
        currentUser.setEmail(email);
        return "email changed!";
    }

    public static String changeSlogan(String slogan){
        if (slogan == null || slogan.equals("")) return "slogan is empty!";
        currentUser.setSlogan(slogan);
        return "slogan changed!";
    }

    public static String displayHighScore(){
        return "your highscore: " + currentUser.getHighScore();
    }

    public static String displayRank(){
        return "your rank: " + currentUser.getRank();
    }
    public static String displaySlogan() {
        String slogan = currentUser.getSlogan();
        if(slogan == null || slogan.equals("")) return "slogan is empty!";
        return "your slogan: " + slogan;
    }
    public static String displayProfile(){
        String result = "username: " + currentUser.getUsername() + '\n';
        result += "nickname: " + currentUser.getNickname() + '\n';
        result += "email: " + currentUser.getEmail() + '\n';
        result += "rank: " + currentUser.getRank() + '\n';
        result += "highscore: " + currentUser.getHighScore() + '\n';
        result += "slogan: " + currentUser.getSlogan();
        return result;
    }

    public static String removeSlogan(){
        currentUser.setSlogan(null);
        return "slogan deleted";
    }

    public static void setCurrentUser(User currentUser) {
        ProfileMenuController.currentUser = currentUser;
    }
}
