package model;

import java.util.ArrayList;
import java.util.HashSet;

public class Message {
    private final ChatType type;
    private static ArrayList<Message> messages = new ArrayList<>();
    private String text;
    private boolean isDeleteOnlyForSender = false;
    private HashSet<String> availableTo = new HashSet<>();
    private String senderUsername;
    private String timeSent;
    private boolean isSeen = false;

    public Message(ChatType type,String text, String senderUsername, String timeSent,HashSet<String> availableTo) {
        this.type = type;
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
}
