package controller;


import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import model.Good;
import model.Government;
import model.TradeRequest;
import view.LoginMenu;

import java.io.IOException;
import java.util.HashMap;

import static controller.MainController.dataInputStream;
import static controller.MainController.dataOutputStream;

public class TradeMenuController {
    private static Government currentGovernment = Government.getGovernmentByUser(MainMenuController.getCurrentUser().getUsername()), targetGovernment;

    public static String calculateUnseenRequests() {
        int count = 0;
        for (TradeRequest request : currentGovernment.getIncomingRequests())
            if (!request.getHasSeen()) count++;
        if (count == 0) return null;
        return "you have " + count + "new Trade Requests";
    }

    public static String sendRequest(HashMap<Good,Integer> products, String message) {
        TradeRequest tradeRequest = new TradeRequest(currentGovernment, targetGovernment,products, message);
        targetGovernment.getOutgoingRequests().add(tradeRequest);
        String token = JWT.create().withSubject("send trade request")
                .withExpiresAt(MainController.getExpirationDate())
                .withClaim("trade request", new Gson().toJson(products))
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

//    public static String acceptTrade(int id, String message) {
//        TradeRequest request = currentGovernment.getRequestById(id);
//        if (request == null) return "invalid id!";
//        targetGovernment = request.getSender();
//
//        int numOfCommodity = currentGovernment.getAmountOfGood(request.getCommodity());
//        if (request.getCount() > numOfCommodity) return "you haven't enough " + request.getCommodity();
//
//        int numOfGold = targetGovernment.getAmountOfGood(Good.GOLD);
//        if (request.getPrice() > numOfGold) return targetGovernment.getOwner().getUsername() + " haven't enough gold";
//
//        int emptySpace = targetGovernment.getNumOfEmptySpace(request.getCommodity().getType());
//        if (request.getCount() > emptySpace) return targetGovernment.getOwner().getUsername() + " haven't enough space";
//
//        currentGovernment.decreaseAmountOfGood(request.getCommodity(), request.getCount());
//        targetGovernment.increaseAmountOfGood(request.getCommodity(), request.getCount());
//        currentGovernment.increaseAmountOfGood(Good.GOLD, request.getPrice());
//        targetGovernment.decreaseAmountOfGood(Good.GOLD, request.getPrice());
//        request.setReceiverMessage(message);
//        request.setAccepted(true);
//        return "trade successful";
//    }

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

    public static void setCurrentGovernment(Government currentGovernment) {
        TradeMenuController.currentGovernment = currentGovernment;
    }


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
}