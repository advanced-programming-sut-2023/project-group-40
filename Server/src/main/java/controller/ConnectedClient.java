package controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.TokenExpiredException;
import model.Message;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectedClient extends Thread {
    private final Socket socket;
    private User currentUser = null;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String token;


    public ConnectedClient(Socket socket) {
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @Override
    public void run() {
        RegisterLoginRequest registerLoginRequest = new RegisterLoginRequest(this, dataOutputStream, dataInputStream);
        ProfileRequest profileRequest = new ProfileRequest(this, dataOutputStream, dataInputStream);
        FriendRequest friendRequest = new FriendRequest(this, dataOutputStream, dataInputStream);
        TradeRequests tradeRequest = new TradeRequests(this, dataOutputStream, dataInputStream);
        ChatRequest chatRequest = new ChatRequest(this, dataOutputStream, dataInputStream);

        while (true) {
            try {
                token = dataInputStream.readUTF();
                JWTVerifier verifier = JWT.require(Server.ALGORITHM).build();
                try {
                    verifier.verify(token);
                } catch (TokenExpiredException e) {
                    dataOutputStream.writeUTF("Session Expired!");
                    if (currentUser != null) {
                        currentUser.setOnline(false);
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
                        currentUser.setLastSeen(formatter.format(new Date()));
                    }
                    return;
                }

                switch (JWT.decode(token).getSubject()) {
                    case "register" -> registerLoginRequest.register(token);
                    case "securityAnswer" -> registerLoginRequest.setSecurity(token);
                    case "login" -> registerLoginRequest.login(token);
                    case "update rank" -> profileRequest.updateRank(token);
                    case "get private users" -> profileRequest.getPrivateUsers(token);
                    case "change username" -> profileRequest.changeUsername(token);
                    case "change password" -> profileRequest.changePassword(token);
                    case "change nickname" -> profileRequest.changeNickname(token);
                    case "change email" -> profileRequest.changeEmail(token);
                    case "change slogan" -> profileRequest.changeSlogan(token);
                    case "change avatar" -> profileRequest.changeAvatar(token);
                    case "change request status" -> friendRequest.changeRequestStatus(token);
                    case "send trade request" -> tradeRequest.sendTradeRequest(token);
                    case "get governments" -> tradeRequest.getGovernments(token);
                    case "set request seen" -> tradeRequest.setRequestSeen(token);
                    case "accept request" -> tradeRequest.acceptRequest(token);
                    case "update chat" -> chatRequest.update(token);
                    case "new messages" -> chatRequest.newMessages(token);
                    case "get security" -> registerLoginRequest.getSecurity(token);
                    case "change password by username" -> registerLoginRequest.changePasswordByUsername(token);
                }
                UserController.updateDatabase();
                Message.updateDatabase();
            } catch (IOException e) {
                if (currentUser != null) {
                    currentUser.setOnline(false);
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
                    currentUser.setLastSeen(formatter.format(new Date()));
                    System.out.println(currentUser.getUsername());
                }
                return;
            }
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
