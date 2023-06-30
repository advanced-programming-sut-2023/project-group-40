package controller;


import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import model.Good;
import model.Government;
import model.TradeRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static controller.MainController.*;

public class TradeMenuController {
    private static Government currentGovernment, targetGovernment;

    public static String calculateUnseenRequests() {
        int count = 0;
        for (TradeRequest request : currentGovernment.getIncomingRequests())
            if (!request.getHasSeen()) count++;
        if (count == 0) return null;
        return "you have " + count + " new Requests";
    }

    public static String sendRequest(String type, HashMap<Good, Integer> products, String message) {
        TradeRequest tradeRequest = new TradeRequest(currentGovernment.getUsername(), targetGovernment.getUsername(), type, products, message);
        targetGovernment.getOutgoingRequests().add(tradeRequest);
        String token = JWT.create().withSubject("send trade request")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("trade request", new Gson().toJson(tradeRequest))
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        try {
            dataOutputStream.writeUTF(token);
            return "request sent";
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

//    public static String showTradeList() {
//        StringBuilder output = new StringBuilder("Unaccepted Requests:");
//        for (TradeRequest request : currentGovernment.getRequests()) {
//            if (!request.getAccepted()) {
//                String username = request.getSender().getOwner().getUsername();
//                output.append("\n").append(request.getId()).append(") username: ").append(username).append("\n   count: ")
//                        .append(request.getCount()).append("\n").append("   price: ").append(request.getPrice())
//                        .append("\n   ").append(username).append("'s message: ").append(request.getSenderMessage()).append("\n");
//            }
//        }
//        if (!output.toString().contains("count"))
//            return "no exist unaccepted requests";
//        return output.toString();
//    }

    public static String acceptTrade(int id, int totalPrice) {
        TradeRequest tradeRequest = null;
        for (TradeRequest incomingRequest : currentGovernment.getIncomingRequests())
            if (incomingRequest.getId() == id) tradeRequest = incomingRequest;
        targetGovernment = Government.getGovernmentByUser(tradeRequest.getSenderUsername());
        for (Map.Entry<Good, Integer> entry : tradeRequest.getProductList().entrySet()) {
            Good product = entry.getKey();
            int count = entry.getValue();

            if (count > currentGovernment.getAmountOfGood(product))
                return "you haven't enough " + product;

            int emptySpace = targetGovernment.getNumOfEmptySpace(product.getType());
            if (count > emptySpace)
                return targetGovernment.getUsername() + " haven't enough space";
        }

        if (totalPrice > targetGovernment.getAmountOfGood(Good.GOLD))
            return targetGovernment.getUsername() + " haven't enough gold";

        for (Map.Entry<Good, Integer> entry : tradeRequest.getProductList().entrySet()) {
            Good product = entry.getKey();
            int count = entry.getValue();
            currentGovernment.decreaseAmountOfGood(product, count);
            targetGovernment.increaseAmountOfGood(product, count);
        }
        targetGovernment.decreaseAmountOfGood(Good.GOLD, totalPrice);
        acceptRequest(tradeRequest);
        return "Accept Trade Request Successful";
    }

//    public static String showTradeHistory() {
//        StringBuilder output = new StringBuilder("All Request:");
//        for (TradeRequest request : currentGovernment.getRequests()) {
//            String username = request.getSender().getOwner().getUsername();
//            output.append("\n").append(request.getId()).append(") username: ").append(username).append("\n   count: ").append(request.getCount()).append("\n   price: ").append(request.getPrice()).append("\n   ").append(username).append("'s message: ").append(request.getSenderMessage());
//            if (request.getReceiverMessage() != null)
//                output.append("\n   your message: ").append(request.getReceiverMessage());
//        }
//        if (!output.toString().contains("count"))
//            return "no exist request";
//        return output.toString();
//    }

    public static String showGovernment() {
        StringBuilder output = new StringBuilder("Governments : \n");
        for (Government government : Government.getGovernments()) {
            if (!government.equals(currentGovernment)) {
                output.append(" ").append(government.getUsername()).append("\n");
            }
        }
        return output.toString();
    }

    public static void setTargetGovernment(Government targetGovernment) {
        TradeMenuController.targetGovernment = targetGovernment;
    }

    public static Government getCurrentGovernment() {
        return currentGovernment;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        TradeMenuController.currentGovernment = currentGovernment;
    }

    public static void setSeen(TradeRequest outgoingRequest) {
        String token = JWT.create().withSubject("set request seen")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("id", outgoingRequest.getId())
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        try {
            dataOutputStream.writeUTF(token);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void acceptRequest(TradeRequest outgoingRequest) {
        String token = JWT.create().withSubject("accept request")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("id", outgoingRequest.getId())
                .withHeader(MainController.headerClaims)
                .sign(MainController.tokenAlgorithm);
        try {
            dataOutputStream.writeUTF(token);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}