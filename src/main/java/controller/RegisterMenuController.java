package controller;

import model.User;

public class RegisterMenuController {
    public static String register(String username , String password ,String confirmPassword ,
                                  String email, String nickname ,
                                  String sloganExist ,String slogan) {
        if (username == null || username.equals("")) return "username is empty!";
        if (password == null || password.equals("")) return "password is empty!";
        if (email == null || email.equals("")) return "email is empty!";
        if (nickname == null || nickname.equals("")) return "nickname is empty!";
        if (sloganExist != null && slogan.equals("")) return "slogan is empty!";
        if (!User.checkUsernameFormat(username)) return "username is invalid!";
        if (User.isUsernameExists(username)) {
            String result = "username is exists!\n";
            while (User.isUsernameExists(username)) username += username.charAt(username.length() - 1);
            result+= "suggested username: " + username + "\n confirm [Y/N]: ";
            System.out.print(result);
            if (MainController.scanner.nextLine().equalsIgnoreCase("N")) return "register failed";
        }
        if (password.equals("random")) password = User.generateRandomPassword();
        if (!User.checkPasswordFormat(password)) return "password is weak!";
        if (!password.equals(confirmPassword)) return "confirm password doesn't match with password!";
        if (User.isEmailExists(email)) return "email is exists!";
        if (!User.checkEmailFormat(email)) return "email is invalid!";
        if (slogan != null && slogan.equals("random")) slogan = User.generateRandomSlogan();
        User.addUser(new User(username,password,nickname,email,slogan));
        return "register successful!";
    }
    public String pickSecurityQuestion(String questionNumber , String answer , String answerConfirmation){
        return null;
    }
}
