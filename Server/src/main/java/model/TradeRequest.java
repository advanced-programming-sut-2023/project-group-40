package model;


import java.util.HashMap;

public class TradeRequest {
    private final String senderUsername, receiverUsername;
    private final HashMap<Good, Integer> productList;
    private final String senderMessage;
    private int id;
    private final String type;
    private boolean isAccepted = false, hasSeen = false;

    public TradeRequest(String sender, String receiver, String type, HashMap<Good, Integer> productList,
                        String message) {
        this.senderUsername = sender;
        this.receiverUsername = receiver;
        this.productList = productList;
        this.type = type;
        this.senderMessage = message;
    }

    public boolean getAccepted() {
        return isAccepted;
    }

    public HashMap<Good, Integer> getProductList() {
        return productList;
    }

    public String getSenderMessage() {
        return senderMessage;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public boolean getHasSeen() {
        return this.hasSeen;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }

    public int getId() {
        return id;
    }
}