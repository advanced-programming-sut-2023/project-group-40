package controller;

import model.Good;
import model.Government;


public class ShopMenuController {
    private static Government currentGovernment;

    public static String buy(String name, int count) {
        Good good = Good.valueOf(name.toUpperCase());
        int price = good.getBuyPrice() * count;
        if (price > currentGovernment.getAmountOfGood(Good.GOLD))
            return "you haven't enough gold";
        if (count > currentGovernment.getNumOfEmptySpace(good.getType())) {
            return "you haven't enough space";
        }
        currentGovernment.increaseAmountOfGood(good, count);
        currentGovernment.decreaseAmountOfGood(Good.GOLD, price);
        return "buy successful";
    }

    public static String sell(String name, int count) {
        Good good = Good.valueOf(name.toUpperCase());
        int price = good.getSellPrice() * count;
        if (count > currentGovernment.getAmountOfGood(good)) {
            return "you haven't enough " + name.toLowerCase();
        }
        if (price > currentGovernment.getNumOfEmptySpace(Good.GOLD.getType()))
            return "you haven't enough space for gold";
        currentGovernment.increaseAmountOfGood(Good.GOLD, price);
        currentGovernment.decreaseAmountOfGood(good, count);
        return "sell successful";
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        ShopMenuController.currentGovernment = currentGovernment;
    }
}
