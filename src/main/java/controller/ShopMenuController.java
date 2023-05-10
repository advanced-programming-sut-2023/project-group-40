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
                        "  number of " + name + " in storages: " + currentGovernment.getNumOfInStorages(good) + "\n";
            }
        }
        output += " -Materials: \n";
        for (Good good : Good.values()) {
            if (good.getType().equals("material")) {
                String name = good.name().toLowerCase();
                output += "   " + name + " ->  buy price: " + good.getBuyPrice() + "  sell price: " + good.getSellPrice() +
                        "  number of " + name + " in storages: " + currentGovernment.getNumOfInStorages(good) + "\n";
            }
        }
        output += " -Weapon: \n";
        for (Good good : Good.values()) {
            if (good.getType().equals("weapon")) {
                String name = good.name().toLowerCase();
                output += "   " + name + " ->  buy price: " + good.getBuyPrice() + "  sell price: " + good.getSellPrice() +
                        "  number of " + name + " in storages: " + currentGovernment.getNumOfInStorages(good) + "\n";
            }
        }
        return output;
    }

    public static String buy(String name, int count) {
        return null;
    }

    public static String sell(String name, int count) {
        return null;
    }

    public static void setCurrentGovernment(Government currentGovernment) {
        ShopMenuController.currentGovernment = currentGovernment;
    }
}
