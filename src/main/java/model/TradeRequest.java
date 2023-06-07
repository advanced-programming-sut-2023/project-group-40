package model;


public class TradeRequest {
    private static int lastId = 1000;
    private final Government sender, receiver;
    private final Good commodity;
    private final int price, count;
    private final String senderMessage;
    private final Integer id = lastId++;
    private String receiverMessage;
    private boolean isAccepted = false, hasSeen = false;

    public TradeRequest(Government sender, Government receiver, Good commodity, int price, int count, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.commodity = commodity;
        this.price = price;
        this.count = count;
        this.senderMessage = message;
    }

    public static int getLastId() {
        return lastId;
    }

    public boolean getAccepted() {
        return isAccepted;
    }

    public Good getCommodity() {
        return commodity;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
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