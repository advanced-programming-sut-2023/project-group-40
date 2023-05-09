package controller;


import model.Good;
import model.Government;
import model.TradeRequest;
import model.User;
import model.buildings.Storage;

import java.util.Optional;

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

    public static String sendRequest(String type, String name, int amount, int price, String message, String username) {
        if ((targetGovernment = Government.getGovernmentByUser(User.getUserByUsername(username))) == null)
            return "username in not exist";
        if (!type.equals("food") && !type.equals("weapon") && !type.equals("Material"))
            return "invalid resource type";
        try {
            Good commodity = Good.valueOf(name.toUpperCase());
            targetGovernment.addRequest(new TradeRequest(currentGovernment, targetGovernment, commodity, price, amount, message));
            return "request sent";
        } catch (IllegalArgumentException e) {
            return "invalid resource name";
        }
    }

    public static String showTradeList() {
        String output = "Unaccepted Requests: \n";
        for (TradeRequest request : currentGovernment.getRequests()) {
            if (!request.getAccepted()) {
                String username = request.getSender().getOwner().getUsername();
                output += request.getId() + ") username: " + username + "\n   count: " + request.getCount() + "\n" + "   price: " + request.getPrice() + "\n   " + username + "'s message: " + request.getSenderMessage();
            }
        }
        return output;
    }

    public static String acceptTrade(int id, String message) {
        TradeRequest request = currentGovernment.getRequestById(id);
        if (request == null) return "invalid id!";
        targetGovernment = request.getSender();
        int numOfCommodity = 0;
        for (Storage storage : currentGovernment.getStorages()) {
            if (storage.getProducts().get(request.getCommodity()) != null)
                numOfCommodity += storage.getProducts().get(request.getCommodity());
        }
        if (request.getCount() > numOfCommodity) return "you haven't enough " + request.getCommodity();

        int numOfGold = 0;
        for (Storage storage : targetGovernment.getStorages()) {
            if (storage.getProducts().get(Good.GOLD) != null) numOfGold += storage.getProducts().get(Good.GOLD);
        }
        if (request.getPrice() > numOfGold) return targetGovernment.getOwner().getUsername() + " haven't enough gold";

        currentGovernment.decreaseAmountOfGood(request.getCommodity(), request.getCount());
        targetGovernment.increaseAmountOfGood(request.getCommodity(), request.getCount());
        currentGovernment.increaseAmountOfGood(Good.GOLD, request.getPrice());
        targetGovernment.decreaseAmountOfGood(Good.GOLD, request.getPrice());
        request.setReceiverMessage(message);
        request.setAccepted(true);
        return "trade successful";
    }

    public static String showTradeHistory() {
        String output = "All Request: \n";
        for (TradeRequest request : currentGovernment.getRequests()) {
            String username = request.getSender().getOwner().getUsername();
            output += request.getId() + ") username: " + username + "\n   count: " + request.getCount() + "\n   price: " + request.getPrice() + "\n   " + username + "'s message: " + request.getSenderMessage();
            if (request.getReceiverMessage() != null) output += "\n   your message: " + request.getReceiverMessage();
        }
        return output;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        TradeMenuController.currentGovernment = currentGovernment;
    }

    public static void setTargetGovernment(Government targetGovernment) {
        TradeMenuController.targetGovernment = targetGovernment;
    }

    public static String showGovernment() {
        if (Government.getGovernments().size() == 1) return "no government exist except you";
        String output = "Governments : \n";
        for (Government government : Government.getGovernments()) {
            if (!government.equals(currentGovernment)) {
                output += " " + government.getOwner().getUsername() + "\n";
            }
        }
        return output;
    }
}
