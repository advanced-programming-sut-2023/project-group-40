package view;

import controller.MainController;
import view.enums.Commands;

import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in trade menu");
        while (true) {
            String command = MainController.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, TradeMenu.class));
        }
    }
    public static String sendRequest(Matcher matcher){
        return null;
    }
    public static String showTradeList(Matcher matcher){
        return null;
    }
    public static String acceptTrade(Matcher matcher){
        return null;
    }
    public static String showTradeHistory(Matcher matcher){
        return null;
    }
}
