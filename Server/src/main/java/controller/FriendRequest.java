package controller;

import com.auth0.jwt.JWT;
import model.FriendStatus;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class FriendRequest extends Request {
    public FriendRequest(ConnectedClient client, DataOutputStream dataOutputStream,
                         DataInputStream dataInputStream) {
        super(client, dataOutputStream, dataInputStream);
    }

    public void changeRequestStatus(String token) {
        String username = JWT.decode(token).getClaim("username").asString();
        String action = JWT.decode(token).getClaim("action").asString();
        switch (action) {
            case "accept" -> {
                client.getCurrentUser().getRequestInbox().put(username, FriendStatus.ACCEPTED);
                UserController.getUserByUsername(username).getRequestOutbox().put(client.getCurrentUser().getUsername(), FriendStatus.ACCEPTED);
                client.getCurrentUser().getFriends().add(username);
                UserController.getUserByUsername(username).getFriends().add(client.getCurrentUser().getUsername());
            }
            case "reject" -> {
                client.getCurrentUser().getRequestInbox().put(username, FriendStatus.REJECTED);
                UserController.getUserByUsername(username).getRequestOutbox().put(client.getCurrentUser().getUsername(), FriendStatus.REJECTED);
                client.getCurrentUser().getFriends().remove(username);
                UserController.getUserByUsername(username).getFriends().remove(client.getCurrentUser().getUsername());
            }
            case "follow" -> {
                client.getCurrentUser().getRequestOutbox().put(username, FriendStatus.NO_ACTION);
                UserController.getUserByUsername(username).getRequestInbox().put(client.getCurrentUser().getUsername(), FriendStatus.NO_ACTION);
            }
        }
    }
}
