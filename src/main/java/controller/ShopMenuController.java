package controller;

import model.Food;
import model.Government;
import model.Material;
import model.Weapon;

import java.util.regex.Matcher;

public class ShopMenuController {
    private static Government currentGovernment;

    public static String showPriceList() {
        String output = "Items: \n";
        output += " -Food: \n";
        for (Food value : Food.values()) {
            output += "   " + value.name().toLowerCase() + " ->  by " + value.getPriceBuy() + "  sell " + value.getPriceSell() + "\n";
        }
        output += " -Material: \n";
        for (Material value : Material.values()) {
            output += "   " + value.name().toLowerCase() + " ->  by " + value.getPriceBuy() + "  sell " + value.getPriceSell() + "\n";
        }
        output += " -Weapon: \n";
        for (Weapon value : Weapon.values()) {
            output += "   " + value.name().toLowerCase() + " ->  by " + value.getPriceBuy() + "  sell " + value.getPriceSell() + "\n";
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
