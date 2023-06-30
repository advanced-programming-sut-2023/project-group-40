package controller;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static controller.MainController.dataInputStream;
import static controller.MainController.dataOutputStream;

public class ConnectToServer {

    static {
        try {
            MainController.setupCalender();
            Socket socket = new Socket("localhost", 80);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static String register(String username, String password, String nickname, String email, String slogan) {
        String token = JWT.create().withSubject("register")
                .withExpiresAt(MainController.getExpirationDate())
                .withIssuer(username)
                .withClaim("passwordHash", UserController.generatePasswordHash(password))
                .withClaim("nickname", nickname)
                .withClaim("email", email)
                .withClaim("slogan", slogan)
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        try {
            dataOutputStream.writeUTF(token);
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public static String securityAnswer(String securityAnswer, int securityNumber) {
        String token = JWT.create().withSubject("securityAnswer")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("securityAnswer", securityAnswer)
                .withClaim("securityNumber", securityNumber)
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        try {
            dataOutputStream.writeUTF(token);
            return dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public static String login(String username, String password) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public static void updateRank() {
        try {
            User user = MainMenuController.getCurrentUser();
            String token = JWT.create().withSubject("update rank")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withClaim("score", user.getHighScore())
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            dataOutputStream.writeUTF(token);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static List<PrivateUser> getUsers() {
        try {
            String token = JWT.create().withSubject("get private users")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            dataOutputStream.writeUTF(token);
            User currentUser = MainMenuController.getCurrentUser();
            currentUser.setFriends(new Gson().fromJson(dataInputStream.readUTF(), new TypeToken<HashSet<String>>() {
            }.getType()));
            currentUser.setRequestInbox(new Gson().fromJson(dataInputStream.readUTF(), new TypeToken<HashMap<String, FriendStatus>>() {
            }.getType()));
            currentUser.setRequestOutbox(new Gson().fromJson(dataInputStream.readUTF(), new TypeToken<HashMap<String, FriendStatus>>() {
            }.getType()));
            List<PrivateUser> privateUsers = new Gson().fromJson(dataInputStream.readUTF(), new TypeToken<List<PrivateUser>>() {
            }.getType());
            return privateUsers;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
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
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public static void changeRequestStatus(String username, String action) {
        try {
            String token = JWT.create().withSubject("change request status")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withClaim("username", username)
                    .withClaim("action", action)
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            dataOutputStream.writeUTF(token);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static ArrayList<Government> getGovernments() {
        try {
            String token = JWT.create().withSubject("get governments")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            dataOutputStream.writeUTF(token);
            return new Gson().fromJson(dataInputStream.readUTF(), new TypeToken<List<Government>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            return new ArrayList<>();
        }
    }
}
