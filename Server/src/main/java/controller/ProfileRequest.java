package controller;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import model.PrivateUser;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class ProfileRequest extends Request {

    public ProfileRequest(ConnectedClient client, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        super(client, dataOutputStream, dataInputStream);
    }

    public void updateRank(String token) {
        int score = JWT.decode(token).getClaim("score").asInt();
        client.getCurrentUser().setHighScore(score);
    }

    public void getPrivateUsers(String token) {
        List<PrivateUser> privateUserList = User.getUsers().stream().map(user ->
                new PrivateUser(user.getUsername(), user.getRank(), user.isOnline(), user.getHighScore()
                        , user.getAvatarByteArray(), user.getLastSeen())).toList();
        try {
            dataOutputStream.writeUTF(new Gson().toJson(client.getCurrentUser().getFriends()));
            dataOutputStream.writeUTF(new Gson().toJson(client.getCurrentUser().getRequestInbox()));
            dataOutputStream.writeUTF(new Gson().toJson(client.getCurrentUser().getRequestOutbox()));
            dataOutputStream.writeUTF(new Gson().toJson(privateUserList));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeUsername(String token) {
        String newUsername = JWT.decode(token).getClaim("new username").asString();
        client.getCurrentUser().setUsername(newUsername);
    }

    public void changePassword(String token) {
        String newPassword = JWT.decode(token).getClaim("new password").asString();
        client.getCurrentUser().setPasswordHash(newPassword);
    }

    public void changeNickname(String token) {
        String newNickname = JWT.decode(token).getClaim("new nickname").asString();
        client.getCurrentUser().setNickname(newNickname);
    }

    public void changeEmail(String token) {
        String newEmail = JWT.decode(token).getClaim("new email").asString();
        client.getCurrentUser().setEmail(newEmail);
    }

    public void changeSlogan(String token) {
        String newSlogan = JWT.decode(token).getClaim("new slogan").asString();
        client.getCurrentUser().setSlogan(newSlogan);
    }

    public void changeAvatar(String token) {
        try {
            client.getCurrentUser().setAvatarByteArray(new Gson().fromJson(dataInputStream.readUTF(),User.class).getAvatarByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
