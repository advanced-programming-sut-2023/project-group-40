package controller;


import model.Good;
import model.Government;
import model.TradeRequest;

import java.util.Optional;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class TradeMenuController {
    private static Government currentGovernment, targetGovernment;

    public static String showNotification() {
        String output = "Notifications: \n";
        for (TradeRequest request : currentGovernment.getRequests()) {
            if (!request.getHasSeen()) {
                String username = request.getSender().getOwner().getUsername();
                output += request.getId() + ") username: " + username + "\n   count: " + request.getCount() + "\n   price: " + request.getPrice() + "\n   " + username + "'s message: " + request.getSenderMessage();
                request.setHasSeen(true);
            }
        }
        return output;
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
        String output = "Unaccepted Requests:";
        for (TradeRequest request : currentGovernment.getRequests()) {
            if (!request.getAccepted()) {
                String username = request.getSender().getOwner().getUsername();
                output += "\n" + request.getId() + ") username: " + username + "\n   count: " + request.getCount() + "\n" + "   price: " + request.getPrice() + "\n   " + username + "'s message: " + request.getSenderMessage();
            }
        }
        if(!output.contains("count"))
            return "no exist unaccepted requests";
        return output;
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
        String output = "All Request:";
        for (TradeRequest request : currentGovernment.getRequests()) {
            String username = request.getSender().getOwner().getUsername();
            output += "\n" + request.getId() + ") username: " + username + "\n   count: " + request.getCount() + "\n   price: " + request.getPrice() + "\n   " + username + "'s message: " + request.getSenderMessage();
            if (request.getReceiverMessage() != null) output += "\n   your message: " + request.getReceiverMessage();
        }
        if(!output.contains("count"))
            return "no exist request";
        return output;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        TradeMenuController.currentGovernment = currentGovernment;
    }

    public static void setTargetGovernment(Government targetGovernment) {
        TradeMenuController.targetGovernment = targetGovernment;
    }

    public static String showGovernment() {
        String output = "Governments : \n";
        for (Government government : Government.getGovernments()) {
            if (!government.equals(currentGovernment)) {
                output += " " + government.getOwner().getUsername() + "\n";
            }
        }
        return output;
    }
    public static Boolean isGovernmentValid(String username) {
        return (targetGovernment = Government.getGovernmentByUser(UserController.getUserByUsername(username))) != null;
    }
}