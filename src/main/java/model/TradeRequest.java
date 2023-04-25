package model;

public class TradeRequest<T> {
    private static int lastId = 1000;
    private Government sender,receiver;
    private T type;
    private int price,count;
    private String message;
    private int id = lastId++;
    private boolean isAccepted = false,hasSeen = false;

    public TradeRequest(Government sender, Government receiver, T type, int price, int count, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.price = price;
        this.count = count;
        this.message = message;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public static int getLastId() {
        return lastId;
    }

    public T getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public Government getSender() {
        return sender;
    }

    public Government getReceiver() {
        return receiver;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }
}
