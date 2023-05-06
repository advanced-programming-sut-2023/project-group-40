package controller;


import com.google.gson.internal.bind.util.ISO8601Utils;
import model.*;

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
                output += request.getId() + ") username: " + username + "\n   count: " + request.getCount() +
                        "\n   price: " + request.getPrice() + "\n   " + username + "'s message: " + request.getSenderMessage();
                request.setHasSeen(true);
            }
        }
        return output;
    }

    public static String sendRequest(String type, String name, int amount, int price, String message, String username) {
        Optional<Government> result = Government.getGovernments().stream().filter(government -> government.getOwner().getUsername().equals(username)).findFirst();
        if (result.isPresent()) {
            targetGovernment = result.get();
        } else {
            return "username is not exist";
        }
        try {
            Class.forName("model." + type);
        } catch (ClassNotFoundException e) {
            return "invalid resource type!";
        }

        switch (type) {
            case "Food":
                if (Food.valueOf(name.toUpperCase()) == null) return "";
                targetGovernment.addRequest(new TradeRequest<>(currentGovernment, targetGovernment, Food.valueOf(name.toUpperCase()), price, amount, message));
                break;
            case "Material":
                targetGovernment.addRequest(new TradeRequest<>(currentGovernment, targetGovernment, Material.valueOf(name.toUpperCase()), price, amount, message));
                break;
            case "Weapon":
                targetGovernment.addRequest(new TradeRequest<>(currentGovernment, targetGovernment, Weapon.valueOf(name.toUpperCase()), price, amount, message));
                break;
            default:
                return "resource type is invalid";
        }
        return "request sent";
    }

    public static String showTradeList() {
        String output = "Unaccepted Requests: \n";
        for (TradeRequest request : currentGovernment.getRequests()) {
            if (!request.getAccepted()) {
                String username = request.getSender().getOwner().getUsername();
                output += request.getId() + ") username: " + username + "\n   count: " + request.getCount() +
                        "\n   price: " + request.getPrice() + "\n   " + username + "'s message: " + request.getSenderMessage();
            }
        }
        return output;
    }

    public static String acceptTrade(int id, String message) {
        TradeRequest request = currentGovernment.getRequestById(id);
        if (request == null)
            return "invalid id!";
        targetGovernment = request.getSender();
        // TODO: ۰۶/۰۵/۲۰۲۳  

        request.setReceiverMessage(message);
        request.setAccepted(true);
        return "trade successful";
    }

    public static String showTradeHistory() {
        String output = "All Request: \n";
        for (TradeRequest request : currentGovernment.getRequests()) {
            String username = request.getSender().getOwner().getUsername();
            output += request.getId() + ") username: " + username + "\n   count: " + request.getCount() +
                    "\n   price: " + request.getPrice() + "\n   " + username + "'s message: " + request.getSenderMessage();
            if (request.getReceiverMessage() != null)
                output += "\n   your message: " + request.getReceiverMessage();
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
        if (Government.getGovernments().size() == 1)
            return "no government exist except you";
        String output = "Governments : \n";
        for (Government government : Government.getGovernments()) {
            if (!government.equals(currentGovernment)) {
                output += " " + government.getOwner().getUsername() + "\n";
            }
        }
        return output;

    }
}
