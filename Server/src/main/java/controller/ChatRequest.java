package controller;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.ChatType;
import model.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class ChatRequest extends Request {

    public ChatRequest(ConnectedClient client, DataOutputStream dataOutputStream,
                       DataInputStream dataInputStream) {
        super(client, dataOutputStream, dataInputStream);
    }

    public void update(String token) {
        String type = JWT.decode(token).getClaim("type").asString();
        String roomName = JWT.decode(token).getClaim("roomName").asString();
        try {
            if (!type.equals(ChatType.ONLY_FETCH.name()))
                Message.getMessages().forEach(message -> {
                    if (!message.getType().name().equals(type)) return;
                    if (!type.equals(ChatType.PUBLIC.name()) && !message.getAvailableTo().contains(client.getCurrentUser().getUsername()))
                        return;
                    if (type.equals(ChatType.ROOM.name()) && !message.getRoomName().equals(roomName)) return;
                    if (!message.getSenderUsername().equals(client.getCurrentUser().getUsername()))
                        message.setSeen(true);
                });
            dataOutputStream.writeUTF(new Gson().toJson(Message.getMessages()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void newMessages(String token) {
        try {
            String json = dataInputStream.readUTF();
            Message.setMessages(new Gson().fromJson(json, new TypeToken<List<Message>>() {
            }.getType()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
