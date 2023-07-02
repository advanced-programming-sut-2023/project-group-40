package controller;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import model.Government;
import model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterLoginRequest extends Request {
    private User uncompletedRegisterUser;

    public RegisterLoginRequest(ConnectedClient client, DataOutputStream dataOutputStream,
                                DataInputStream dataInputStream) {
        super(client, dataOutputStream, dataInputStream);
    }

    public void register(String token) {
        String username = JWT.decode(token).getIssuer();
        String passwordHash = JWT.decode(token).getClaim("passwordHash").asString();
        String email = JWT.decode(token).getClaim("email").asString();
        String nickname = JWT.decode(token).getClaim("nickname").asString();
        String slogan = JWT.decode(token).getClaim("slogan").asString();
        try {
            if (UserController.isUsernameExists(username))
                dataOutputStream.writeUTF("your username exists!");
            else if (UserController.isEmailExists(email))
                dataOutputStream.writeUTF("your email exists!");
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
                uncompletedRegisterUser = new User(username, passwordHash, nickname, email, slogan, formatter.format(new Date()));
                dataOutputStream.writeUTF("your register verified!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSecurity(String token) {
        int securityNo = JWT.decode(token).getClaim("securityNumber").asInt();
        String securityAnswer = JWT.decode(token).getClaim("securityAnswer").asString();
        uncompletedRegisterUser.setSecurityQuestionNo(securityNo);
        uncompletedRegisterUser.setSecurityAnswer(securityAnswer);
        User.addUser(uncompletedRegisterUser);
        client.setCurrentUser(uncompletedRegisterUser);
        client.getCurrentUser().setOnline(true);
        if (Government.getGovernmentByUser(uncompletedRegisterUser.getUsername()) == null) {
            Government.getGovernments().add(new Government(uncompletedRegisterUser.getUsername()));
            Government.updateDatabase();
        }
        UserController.updateDatabase();
        try {
            dataOutputStream.writeUTF(new Gson().toJson(uncompletedRegisterUser));
            uncompletedRegisterUser = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login(String token) {
        String username = JWT.decode(token).getIssuer();
        String passwordHash = JWT.decode(token).getClaim("passwordHash").asString();
        try {
            if (!UserController.isUsernameExists(username) || !UserController.isPasswordCorrect(username, passwordHash))
                dataOutputStream.writeUTF("incorrect username or pass!");
            else {
                User user = UserController.getUserByUsername(username);
                client.setCurrentUser(user);
                client.getCurrentUser().setOnline(true);
                if (Government.getGovernmentByUser(username) == null) {
                    Government.getGovernments().add(new Government(user.getUsername()));
                    Government.updateDatabase();
                }
                dataOutputStream.writeUTF(new Gson().toJson(UserController.getUserByUsername(username)));
                dataOutputStream.writeUTF("your login verified!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getSecurity(String token) {
        String username = JWT.decode(token).getIssuer();
        try {
                dataOutputStream.writeUTF(String.valueOf(UserController.getUserByUsername(username).getSecurityQuestionNo()));
                dataOutputStream.writeUTF(UserController.getUserByUsername(username).getSecurityAnswer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changePasswordByUsername(String token) {
        String username = JWT.decode(token).getIssuer();
        String newPassword = JWT.decode(token).getClaim("new password").asString();
        UserController.getUserByUsername(username).setPasswordHash(newPassword);
    }


}
