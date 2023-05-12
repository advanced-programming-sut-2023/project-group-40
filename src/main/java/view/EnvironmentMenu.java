package view;

import controller.GameMenuController;
import controller.MainController;
import controller.EnvironmentMenuController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class EnvironmentMenu {
    public static String run() throws ReflectiveOperationException {
        System.out.println("you are in map menu!");
        if (EnvironmentMenuController.isFirstPlayer()) {
            chooseMapSize();
            setPlayers();
            GameMenuController.chooseColor();
        } else {
            if (!EnvironmentMenuController.isUserInGame(GameMenuController.getCurrentGovernment().getOwner()))
                return "you are not in game";
            if (EnvironmentMenuController.isCurrentGovernmentChooseColor(GameMenuController.getCurrentGovernment().getOwner()))
                return "you choose your color wait for starting game!";
            GameMenuController.chooseColor();
            EnvironmentMenuController.checkGameStarted();
        }
        while (true) {
            String command = MainController.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, EnvironmentMenu.class));
        }
    }

    public static String setTexture(Matcher matcher) {
        String type = matcher.group("type");
        if (matcher.group("x") != null) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            return EnvironmentMenuController.setTexture(x, y, type);
        } else {
            int x1 = Integer.parseInt(matcher.group("x1"));
            int x2 = Integer.parseInt(matcher.group("x2"));
            int y1 = Integer.parseInt(matcher.group("y1"));
            int y2 = Integer.parseInt(matcher.group("y2"));
            return EnvironmentMenuController.setTexture(x1, x2, y1, y2, type);
        }
    }

    public static String clearBlock(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return EnvironmentMenuController.clearBlock(x,y);
    }

    public static String dropRock(Matcher matcher) {
        //which shape ??
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String direction = matcher.group("direction");
        return EnvironmentMenuController.dropRock(x, y, direction);
    }

    public static String dropTree(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = matcher.group("type");
        return EnvironmentMenuController.dropTree(x,y,type);
    }

    public static void chooseMapSize() {
        System.out.println("choose your size for map: ");
        GameMenuController.setMapSize(Integer.parseInt(MainController.scanner.nextLine()));
    }

    public static void setPlayers() {
        int countOfPlayers = setNumberOfPlayers();
        GameMenuController.setOnGovernment();
        EnvironmentMenuController.addPlayer(GameMenuController.getCurrentGovernment().getOwner().getUsername());
        System.out.println("choose your opponents : (write usernames)");
        int selectedPlayer = 0;
        while (true) {
            String username = MainController.scanner.nextLine();
            if (EnvironmentMenuController.isPlayerValid(username)) {
                if (EnvironmentMenuController.isPlayerAdded(username)) {
                    System.out.println("this player already added");
                    continue;
                }
                System.out.println("player " + username + " added");
                selectedPlayer++;
                if (selectedPlayer == countOfPlayers - 1) break;
            } else System.out.println("username not exist!");
        }
        System.out.println("players successfully added");
    }

    public static int setNumberOfPlayers() {
        int countOfPlayers = 0;
        while (true) {
            System.out.println("choose number of players of game :");
            countOfPlayers = Integer.parseInt(MainController.scanner.nextLine());
            if (countOfPlayers < 2 || countOfPlayers > 8) System.out.println("you enter invalid count number!");
            else break;
        }
        return countOfPlayers;
    }

    public static void nextTurn(Matcher matcher) throws ReflectiveOperationException {
        LoginMenu.run();
    }
}
