package model;

import java.util.ArrayList;

public class Message {
    private static ArrayList<Message> messages = new ArrayList<>();
    private String text;
    private String senderUsername;
    private String timeSent;
    private boolean isSeen = false;

    public Message(String text, String senderUsername, String timeSent) {
        this.text = text;
        this.senderUsername = senderUsername;
        this.timeSent = timeSent;
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
}
