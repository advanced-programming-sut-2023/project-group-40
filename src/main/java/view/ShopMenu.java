package view;

import controller.ShopMenuController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class ShopMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in shop menu!");
        while (true) {
            String command = Commands.scanner.nextLine();
            if (command.equals("return")) {
                System.out.println("you are in game menu");
                return;
            }
            System.out.println(Commands.regexFinder(command, ShopMenu.class));
        }
    }

    public static String showPriceList(Matcher matcher) {
        return ShopMenuController.showPriceList();
    }

    public static String buy(Matcher matcher) {
        String name = matcher.group("name");
        int count = Integer.parseInt(matcher.group("amount"));
        return ShopMenuController.buy(name, count);
    }

    public static String sell(Matcher matcher) {
        String name = matcher.group("name");
        int count = Integer.parseInt(matcher.group("amount"));
        return ShopMenuController.sell(name, count);
    }
}
