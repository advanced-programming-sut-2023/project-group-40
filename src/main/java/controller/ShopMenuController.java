package controller;

import model.Good;
import model.Government;



public class ShopMenuController {
    private static Government currentGovernment;

    public static String showPriceList() {
        String output = "Items: \n";
        output += " -Food: \n";
        for (Good good : Good.values()) {
            if (good.getType().equals("food")) {
                String name = good.name().toLowerCase();
                output += "   " + name + " ->  buy price: " + good.getBuyPrice() + "  sell price: " + good.getSellPrice() +
                        "  number of " + name + " in storages: " + currentGovernment.getAmountOfGood(good) + "\n";
            }
        }
        output += " -Materials: \n";
        for (Good good : Good.values()) {
            if (good.getType().equals("material")) {
                String name = good.name().toLowerCase();
                output += "   " + name + " ->  buy price: " + good.getBuyPrice() + "  sell price: " + good.getSellPrice() +
                        "  number of " + name + " in storages: " + currentGovernment.getAmountOfGood(good) + "\n";
            }
        }
        output += " -Weapon: \n";
        for (Good good : Good.values()) {
            if (good.getType().equals("weapon")) {
                String name = good.name().toLowerCase();
                output += "   " + name + " ->  buy price: " + good.getBuyPrice() + "  sell price: " + good.getSellPrice() +
                        "  number of " + name + " in storages: " + currentGovernment.getAmountOfGood(good) + "\n";
            }
        }
        return output;
    }

    public static String buy(String name, int count) {
        try {
            Good good = Good.valueOf(name.toUpperCase());
            int price = good.getBuyPrice() * count;
            if (price > currentGovernment.getAmountOfGood(Good.GOLD))
                return "you haven't enough gold";
//            if (count > currentGovernment.getNumOfEmptySpace(good.getType())) {
//                return "you haven't enough space";
//            }
            currentGovernment.increaseAmountOfGood(good, count);
            currentGovernment.decreaseAmountOfGood(Good.GOLD, price);
            return "buy successful";
        } catch (IllegalArgumentException e) {
            return "invalid resource name";
        }
    }

    public static String sell(String name, int count) {
        try {
            Good good = Good.valueOf(name.toUpperCase());
            int price = good.getSellPrice() * count;
            if (count > currentGovernment.getAmountOfGood(good)) {
                return "you haven't enough " + name;
            }
            currentGovernment.increaseAmountOfGood(Good.GOLD, price);
            currentGovernment.decreaseAmountOfGood(good, count);
            return "sell successful";
        } catch (IllegalArgumentException e) {
            return "invalid resource name";
        }
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        ShopMenuController.currentGovernment = currentGovernment;
    }
}
