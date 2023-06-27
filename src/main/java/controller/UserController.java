package controller;

import model.DefaultSlogans;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.RandomStringGenerator;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

}
