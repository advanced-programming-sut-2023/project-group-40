package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.PrivateUser;
import model.User;

public class ConnectToServer {
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;
    public static String register(String username, String password, String nickname, String email, String slogan) throws IOException {
        Socket socket = new Socket("151.241.23.248", 80);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        System.out.println("111");
        MainController.setupCalender();

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
    public static String securityAnswer(String securityAnswer, int securityNumber) throws IOException{
        String token = JWT.create().withSubject("securityAnswer")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("securityAnswer", securityAnswer)
                .withClaim("securityNumber", securityNumber)
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        dataOutputStream.writeUTF(token);
        return dataInputStream.readUTF();
    }
    public static String login(String username, String password) throws IOException{
        String token = JWT.create().withSubject("login")
                .withExpiresAt(MainController.getExpirationDate())
                .withIssuer(username)
                .withClaim("password", password)
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        dataOutputStream.writeUTF(token);
        return dataInputStream.readUTF();
    }

    public static void updateRank() throws IOException{
        User user = MainMenuController.getCurrentUser();
        String token = JWT.create().withSubject("update rank")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("score",user.getHighScore())
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        MainController.dataOutputStream.writeUTF(token);
    }
    public static List<PrivateUser> getUsers() {
        try {
            String token = JWT.create().withSubject("get private users")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            MainController.dataOutputStream.writeUTF(token);
            return new Gson().fromJson(MainController.dataInputStream.readUTF()
                    , new TypeToken<List<PrivateUser>>() {
            }.getType());
        }catch (IOException exception){
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
            MainController.dataOutputStream.writeUTF(token);
            return new Gson().fromJson(MainController.dataInputStream.readUTF(), User.class);
        }catch (IOException exception){
            return null;
        }
    }
}
