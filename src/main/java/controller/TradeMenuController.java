package controller;


import model.Good;
import model.Government;
import model.TradeRequest;

public class TradeMenuController {
    private static Government currentGovernment, targetGovernment;

    public static String showNotification() {
        StringBuilder output = new StringBuilder("Notifications: \n");
        for (TradeRequest request : currentGovernment.getRequests()) {
            if (!request.getHasSeen()) {
                String username = request.getSender().getOwner().getUsername();
                output.append(request.getId()).append(") username: ").append(username).append("\n   count: ").append(request.getCount()).append("\n   price: ").append(request.getPrice()).append("\n   ").append(username).append("'s message: ").append(request.getSenderMessage()).append("\n");
                request.setHasSeen(true);
            }
        }
        return output.toString();
    }

    public static String sendRequest(String type, int amount, int price, String message, String username) {
        try {
            Good commodity = Good.valueOf(type.toUpperCase());
            targetGovernment.addRequest(new TradeRequest(currentGovernment, targetGovernment, commodity, price, amount, message));
            return "request sent";
        } catch (IllegalArgumentException e) {
            return "invalid resource type";
        }
    }

    public static String showTradeList() {
        StringBuilder output = new StringBuilder("Unaccepted Requests:");
        for (TradeRequest request : currentGovernment.getRequests()) {
            if (!request.getAccepted()) {
                String username = request.getSender().getOwner().getUsername();
                output.append("\n").append(request.getId()).append(") username: ").append(username).append("\n   count: ")
                        .append(request.getCount()).append("\n").append("   price: ").append(request.getPrice())
                        .append("\n   ").append(username).append("'s message: ").append(request.getSenderMessage()).append("\n");
            }
        }
        if (!output.toString().contains("count"))
            return "no exist unaccepted requests";
        return output.toString();
    }

    public static String acceptTrade(int id, String message) {
        TradeRequest request = currentGovernment.getRequestById(id);
        if (request == null) return "invalid id!";
        targetGovernment = request.getSender();

        int numOfCommodity = currentGovernment.getAmountOfGood(request.getCommodity());
        if (request.getCount() > numOfCommodity) return "you haven't enough " + request.getCommodity();

        int numOfGold = targetGovernment.getAmountOfGood(Good.GOLD);
        if (request.getPrice() > numOfGold) return targetGovernment.getOwner().getUsername() + " haven't enough gold";

        int emptySpace = targetGovernment.getNumOfEmptySpace(request.getCommodity().getType());
        if (request.getCount() > emptySpace) return targetGovernment.getOwner().getUsername() + " haven't enough space";

        currentGovernment.decreaseAmountOfGood(request.getCommodity(), request.getCount());
        targetGovernment.increaseAmountOfGood(request.getCommodity(), request.getCount());
        currentGovernment.increaseAmountOfGood(Good.GOLD, request.getPrice());
        targetGovernment.decreaseAmountOfGood(Good.GOLD, request.getPrice());
        request.setReceiverMessage(message);
        request.setAccepted(true);
        return "trade successful";
    }

    public static String showTradeHistory() {
        StringBuilder output = new StringBuilder("All Request:");
        for (TradeRequest request : currentGovernment.getRequests()) {
            String username = request.getSender().getOwner().getUsername();
            output.append("\n").append(request.getId()).append(") username: ").append(username).append("\n   count: ").append(request.getCount()).append("\n   price: ").append(request.getPrice()).append("\n   ").append(username).append("'s message: ").append(request.getSenderMessage());
            if (request.getReceiverMessage() != null)
                output.append("\n   your message: ").append(request.getReceiverMessage());
        }
        if (!output.toString().contains("count"))
            return "no exist request";
        return output.toString();
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        TradeMenuController.currentGovernment = currentGovernment;
    }


    public static String showGovernment() {
        StringBuilder output = new StringBuilder("Governments : \n");
        for (Government government : Government.getGovernments()) {
            if (!government.equals(currentGovernment)) {
                output.append(" ").append(government.getOwner().getUsername()).append("\n");
            }
        }
        return output.toString();
    }

    public static Boolean isGovernmentValid(String username) {
        return (targetGovernment = Government.getGovernmentByUser(ConnectToServer.getUserByUsername(username))) != null;
    }
}