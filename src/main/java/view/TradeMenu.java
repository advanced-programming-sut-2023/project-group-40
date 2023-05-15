package view;

import controller.TradeMenuController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class TradeMenu {
    private static String targetUsername;

    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in trade menu");
        System.out.print(TradeMenuController.showNotification());
        System.out.println("--------------------------------------------");
        System.out.println(TradeMenuController.showGovernment());
        while (true) {
            System.out.print("select the username you want to exchange with: ");
            targetUsername = Commands.scanner.nextLine();
            if (!TradeMenuController.isGovernmentValid(targetUsername)) {
                System.out.println("invalid username");
                continue;
            }
            break;
        }
        while (true) {
            String command = Commands.scanner.nextLine();
            if (command.equals("return")) {
                System.out.println("you are in game menu");
                return;
            }
            System.out.println(Commands.regexFinder(command, TradeMenu.class));
        }
    }

    public static String sendRequest(Matcher matcher) {
        String resourceType = Commands.eraseQuot(matcher.group("resourceType"));
        int resourceAmount = Integer.parseInt(matcher.group("resourceAmount"));
        int price = Integer.parseInt(matcher.group("price"));
        String message = Commands.eraseQuot(matcher.group("message"));
        return TradeMenuController.sendRequest(resourceType, resourceAmount, price, message, targetUsername);
    }

    public static String showTradeList(Matcher matcher) {
        return TradeMenuController.showTradeList();
    }

    public static String acceptTrade(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String message = Commands.eraseQuot(matcher.group("message"));
        return TradeMenuController.acceptTrade(id, message);
    }

    public static String showTradeHistory(Matcher matcher) {
        return TradeMenuController.showTradeHistory();
    }
}
