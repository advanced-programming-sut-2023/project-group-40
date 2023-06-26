package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.DefaultSlogans;
import model.SecurityQuestions;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.RandomStringGenerator;
import view.MainMenu;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class UserController {
    public static String generateRandomPassword() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        chars += chars.toUpperCase();
        chars += "0123456789!@#$%";
        RandomStringGenerator passwordGenerator = new RandomStringGenerator.Builder().selectFrom(chars.toCharArray()).build();
        String password;
        while (true) {
            password = passwordGenerator.generate(16);
            if (checkPasswordFormat(password)) return password;
        }
    }

    public static String generateRandomSlogan() {
        return DefaultSlogans.values()[new Random().nextInt(0, DefaultSlogans.values().length)].getSlogan();
    }

    public static boolean checkUsernameFormat(String username) {
        return username.matches("\\w+");
    }

    public static boolean checkPasswordFormat(String password) {
        if (!password.matches("\\S+")) return false;
        return password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s])^.{6,}");
    }

    public static boolean checkEmailFormat(String email) {
        Matcher matcher = Pattern
                .compile("([0-9a-zA-Z_.]+)@([0-9a-zA-Z_.]+)\\.([0-9a-zA-Z_.]+)").matcher(email);
        return matcher.matches();
    }

    public static String generatePasswordHash(String password) {
        return new DigestUtils("SHA3-256").digestAsHex(password);
    }


    public static User getUserByUsername(String username) {
        Stream<User> stream = User.getUsers().stream().filter(user -> user.getUsername().equals(username));
        Optional<User> user = stream.findAny();
        return user.orElse(null);
    }

    public static User getStayedLoginUser() {
        Optional<User> user = User.getUsers().stream().filter(User::isStayLoggedIn).findFirst();
        return user.orElse(null);
    }

    public static String getSecurityQuestionsList() {
        StringBuilder result = new StringBuilder();
        for (SecurityQuestions securityQuestion : SecurityQuestions.values())
            result.append(securityQuestion.getQuestion()).append("\n");
        return result.toString();
    }


    public static void autoLogin() throws ReflectiveOperationException {
        User stayedLoginUser = UserController.getStayedLoginUser();
        if (stayedLoginUser != null) {
            MainMenuController.setCurrentUser(stayedLoginUser);
            System.out.println("user " + stayedLoginUser.getUsername() + " logged in");
        }
    }
}
