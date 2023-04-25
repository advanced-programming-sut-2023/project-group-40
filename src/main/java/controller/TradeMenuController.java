package controller;


import model.Government;

import java.util.regex.Matcher;

public class TradeMenuController {
    private static Government currentGovernment ,targetGovernment;
    public static String sendRequest(String type,int amount,int price,String message){
        return null;
    }
    public static String showTradeList(){
        return null;
    }
    public static String acceptTrade(int id,String message){
        return null;
    }
    public static String showTradeHistory(){
        return null;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        TradeMenuController.currentGovernment = currentGovernment;
    }

    public static void setTargetGovernment(Government targetGovernment) {
        TradeMenuController.targetGovernment = targetGovernment;
    }
}
