package model;


import java.util.HashMap;

public class TradeRequest {
    private static int lastId = 1000;
    private final Government sender, receiver;
    private final HashMap <Good,Integer> productList;
    private final String senderMessage;
    private final Integer id = lastId++;
    private String receiverMessage;
    private boolean isAccepted = false, hasSeen = false;

    public TradeRequest(Government sender, Government receiver, HashMap<Good,Integer> productList, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.productList = productList;
        this.senderMessage = message;
    }

    public static int getLastId() {
        return lastId;
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

    public String getReceiverMessage() {
        return receiverMessage;
    }

    public void setReceiverMessage(String message) {
        this.receiverMessage = message;
    }

    public Integer getId() {
        return id;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Government getSender() {
        return sender;
    }

    public Government getReceiver() {
        return receiver;
    }

    public boolean getHasSeen() {
        return this.hasSeen;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }
}