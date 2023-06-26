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
    public static String register() throws IOException {
        Socket socket = new Socket("ap.ali83.ml", 80);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ", "JWT");
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        c.add(Calendar.SECOND, 20);
        Date expirationDate = c.getTime();
        String token = JWT.create().withSubject("register")
                .withExpiresAt(expirationDate)
                .withIssuer("username")
                .withIssuedAt(now)
                .withNotBefore(now)
                .withClaim("password", "password")
                .withHeader(headerClaims)
                .sign(Algorithm.HMAC256("ya sattar"));
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
