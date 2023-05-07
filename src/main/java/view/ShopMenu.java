package view;

import controller.MainController;
import controller.ShopMenuController;
import view.enums.Commands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in shop menu!");
        while (true) {
            String command = MainController.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, TradeMenu.class));
        }
    }

    public static String showPriceList(Matcher matcher) {
        return ShopMenuController.showPriceList();
    }

    public static String buy(Matcher matcher) {
        return null;
    }

    public static String sell(Matcher matcher) {
        return null;
    }
}
