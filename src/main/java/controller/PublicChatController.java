package controller;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Message;
import model.PrivateUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static controller.MainController.dataInputStream;
import static controller.MainController.dataOutputStream;

public class PublicChatController {
    public static void updateChat() {
        String token = JWT.create().withSubject("update public chat")
                .withExpiresAt(MainController.getExpirationDate())
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        try {
            dataOutputStream.writeUTF(token);
            ArrayList<Message> messages = new Gson().fromJson(dataInputStream.readUTF(), new TypeToken<List<Message>>() {
            }.getType());
            Message.setMessages(messages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
