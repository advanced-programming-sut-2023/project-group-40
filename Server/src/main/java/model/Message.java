package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Message {
    private static final String PATH = "src/main/resources/database/chat.json";
    private static ArrayList<Message> messages = new ArrayList<>();
    private final ChatType type;
    private final String roomName;
    private String text;
    private boolean isDeleteOnlyForSender = false;
    private HashSet<String> availableTo = new HashSet<>();
    private String senderUsername;
    private String timeSent;
    private boolean isSeen = false;

    public Message(ChatType type, String roomName, String text, String senderUsername, String timeSent,
                   HashSet<String> availableTo) {
        this.type = type;
        this.roomName = roomName;
        this.text = text;
        this.senderUsername = senderUsername;
        this.timeSent = timeSent;
        this.availableTo = availableTo;
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }

    public static void setMessages(ArrayList<Message> messages) {
        Message.messages = messages;
    }

    public static void fetchDatabase() {
        if (!new File(PATH).exists()) return;
        try (FileReader reader = new FileReader(PATH)) {
            ArrayList<Message> copy = new Gson().fromJson(reader, new TypeToken<List<Message>>() {
            }.getType());
            if (copy != null) messages = copy;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateDatabase() {
        File file = new File(PATH);
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            System.out.println("update database failed");
        }
        try (FileWriter writer = new FileWriter(PATH, false)) {
            writer.write(new Gson().toJson(messages));
        } catch (IOException e) {
            System.out.println("update database failed");
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public boolean isDeleteOnlyForSender() {
        return isDeleteOnlyForSender;
    }

    public void setDeleteOnlyForSender(boolean deleteOnlyForSender) {
        isDeleteOnlyForSender = deleteOnlyForSender;
    }

    public HashSet<String> getAvailableTo() {
        return availableTo;
    }

    public ChatType getType() {
        return type;
    }

    public String getRoomName() {
        return roomName;
    }
}
