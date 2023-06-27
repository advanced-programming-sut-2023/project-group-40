package controller;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.PrivateUser;
import model.User;
import view.ProfileMenu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static controller.MainController.dataInputStream;
import static controller.MainController.dataOutputStream;

public class ConnectToServer {

    static {
        try {
            MainController.setupCalender();
            Socket socket = new Socket("2.190.254.123", 80);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String register(String username, String password, String nickname, String email, String slogan) throws
            IOException {
        String token = JWT.create().withSubject("register")
                .withExpiresAt(MainController.getExpirationDate())
                .withIssuer(username)
                .withClaim("passwordHash", UserController.generatePasswordHash(password))
                .withClaim("nickname", nickname)
                .withClaim("email", email)
                .withClaim("slogan", slogan)
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        dataOutputStream.writeUTF(token);
        return dataInputStream.readUTF();
    }

    public static String securityAnswer(String securityAnswer, int securityNumber) throws IOException {
        String token = JWT.create().withSubject("securityAnswer")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("securityAnswer", securityAnswer)
                .withClaim("securityNumber", securityNumber)
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        dataOutputStream.writeUTF(token);
        return dataInputStream.readUTF();
    }

    public static String login(String username, String password) throws IOException {
        String token = JWT.create().withSubject("login")
                .withExpiresAt(MainController.getExpirationDate())
                .withIssuer(username)
                .withClaim("passwordHash", UserController.generatePasswordHash(password))
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        dataOutputStream.writeUTF(token);

        String s = dataInputStream.readUTF();
        if (s.startsWith("{")) {
            MainMenuController.setCurrentUser(new Gson().fromJson(s, User.class));
            return dataInputStream.readUTF();
        } else return s;
    }

    public static void updateRank() throws IOException {
        User user = MainMenuController.getCurrentUser();
        String token = JWT.create().withSubject("update rank")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("score", user.getHighScore())
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        dataOutputStream.writeUTF(token);
    }

    public static List<PrivateUser> getUsers() {
        try {
            String token = JWT.create().withSubject("get private users")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            dataOutputStream.writeUTF(token);
            List<PrivateUser> privateUsers = new Gson().fromJson(dataInputStream.readUTF(), new TypeToken<List<PrivateUser>>() {
            }.getType());
            for (PrivateUser privateUser : privateUsers) {
                privateUser.setAvatarPath(privateUser.getAvatarPath().contains("\\")
                        ? privateUser.getAvatarPath()
                        : ProfileMenu.class.getResource("/avatars/") + privateUser.getAvatarPath());
            }
            return privateUsers;
        } catch (IOException exception) {
            return new ArrayList<>();
        }
    }

    public static User getUserByUsername(String username) {
        try {
            String token = JWT.create().withSubject("get user")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withIssuer(username)
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            dataOutputStream.writeUTF(token);
            return new Gson().fromJson(dataInputStream.readUTF(), User.class);
        } catch (IOException exception) {
            return null;
        }
    }
}
