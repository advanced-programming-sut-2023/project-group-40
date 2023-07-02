package controller;

import com.auth0.jwt.JWT;
import com.google.gson.Gson;
import model.Government;
import model.TradeRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class TradeRequests extends Request {
    public TradeRequests(ConnectedClient client, DataOutputStream dataOutputStream,
                         DataInputStream dataInputStream) {
        super(client, dataOutputStream, dataInputStream);
    }

    public void sendTradeRequest(String token) {
        TradeRequest tradeRequest = new Gson().fromJson(JWT.decode(token).getClaim("trade request").asString(), TradeRequest.class);
        Government.getGovernmentByUser(tradeRequest.getSenderUsername()).getOutgoingRequests().add(tradeRequest);
        Government.getGovernmentByUser(tradeRequest.getReceiverUsername()).getIncomingRequests().add(tradeRequest);
        Government.updateDatabase();
    }

    public void getGovernments(String token) {
        try {
            dataOutputStream.writeUTF(new Gson().toJson(Government.getGovernments()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setRequestSeen(String token) {
        int id = JWT.decode(token).getClaim("id").asInt();
        for (TradeRequest incomingRequest : Government.getGovernmentByUser(client.getCurrentUser().getUsername()).getIncomingRequests()) {
            if (incomingRequest.getId() == id) {
                incomingRequest.setHasSeen(true);
            }
        }
        Government.updateDatabase();
    }

    public void acceptRequest(String token) {
        int id = JWT.decode(token).getClaim("id").asInt();
        for (TradeRequest incomingRequest : Government.getGovernmentByUser(client.getCurrentUser().getUsername()).getOutgoingRequests()) {
            if (incomingRequest.getId() == id) {
                incomingRequest.setAccepted(true);
            }
        }
        Government.updateDatabase();
    }

}
