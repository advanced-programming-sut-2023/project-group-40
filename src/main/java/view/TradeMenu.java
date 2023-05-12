package view;

import controller.TradeMenuController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class TradeMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in trade menu");
        System.out.println(TradeMenuController.showNotification());

        while (true) {
            String command = Commands.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, TradeMenu.class));
        }
    }

    public static String sendRequest(Matcher matcher) {
        String resourceType = matcher.group("resourceType");
        String resourceName = matcher.group("resourceName");
        int resourceAmount = Integer.parseInt(matcher.group("resourceAmount"));
        int price = Integer.parseInt(matcher.group("price"));
        String message = matcher.group("message");

        String output = TradeMenuController.showGovernment();
        if (output.equals("no government exist except you")) return "no government exist except you";
        System.out.println(output);
        System.out.print("select the username you want to exchange with: ");
        String username = Commands.scanner.nextLine();
        return TradeMenuController.sendRequest(resourceType, resourceName, resourceAmount, price, message, username);
    }

    public static String showTradeList(Matcher matcher) {
        return TradeMenuController.showTradeList();
    }

    public static String acceptTrade(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String message = matcher.group("message");
        return TradeMenuController.acceptTrade(id, message);
    }

    public static String showTradeHistory(Matcher matcher) {
        return TradeMenuController.showTradeHistory();
    }
}
