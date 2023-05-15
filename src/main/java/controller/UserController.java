package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.DefaultSlogans;
import model.SecurityQuestions;
import model.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.RandomStringGenerator;
import view.MainMenu;

import java.awt.*;
import java.awt.image.BufferedImage;
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

    public static boolean isUsernameExists(String username) {
        return User.getUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public static boolean isEmailExists(String email) {
        return User.getUsers().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public static void fetchDatabase() {
        if (!new File(User.getPATH()).exists()) return;
        try (FileReader reader = new FileReader(User.getPATH())) {
            ArrayList<User> copy = new Gson().fromJson(reader, new TypeToken<List<User>>() {
            }.getType());
            if (copy != null) User.setUsers(copy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateDatabase() {
        File file = new File(User.getPATH());
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            System.out.println("update database failed");
        }
        try (FileWriter writer = new FileWriter(User.getPATH(), false)) {
            writer.write(new Gson().toJson(User.getUsers()));
        } catch (IOException e) {
            System.out.println("update database failed");
        }
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

    public static String generateCaptcha(int randomNum) {
        StringBuilder result = new StringBuilder();
        BufferedImage bufferedImage = new BufferedImage(39, 14, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, 39, 14);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(new Font("Arial", Font.BOLD, 16));
        String randomNumString = Integer.toString(randomNum);
        graphics2D.drawString(randomNumString, 2, 13);
        for (int i = 0; i < bufferedImage.getHeight(); i++) {
            for (int j = 0; j < bufferedImage.getWidth(); j++) {
                String chars = "QWERTY";
                String noise = ".-`',";
                int index = (Integer.parseInt(randomNumString) + i + j) % 5;
                if (bufferedImage.getRGB(j, i) == Color.BLACK.getRGB())
                    result.append(chars.charAt(index));
                else
                    result.append(noise.charAt(index));
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static void autoLogin() throws ReflectiveOperationException {
        User stayedLoginUser = UserController.getStayedLoginUser();
        if (stayedLoginUser != null) {
            MainMenuController.setCurrentUser(stayedLoginUser);
            System.out.println("user " + stayedLoginUser.getUsername() + " logged in");
            MainMenu.run();
        }
    }
}
