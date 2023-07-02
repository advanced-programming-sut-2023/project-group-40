package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserController {
    public static User getUserByUsername(String username) {
        Stream<User> stream = User.getUsers().stream().filter(user -> user.getUsername().equals(username));
        Optional<User> user = stream.findAny();
        return user.orElse(null);
    }

    public static boolean isUsernameExists(String username) {
        return User.getUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }

    public static boolean isEmailExists(String email) {
        return User.getUsers().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public static boolean isPasswordCorrect(String username, String passwordHash) {
        return getUserByUsername(username).getPasswordHash().equals(passwordHash);
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


}
