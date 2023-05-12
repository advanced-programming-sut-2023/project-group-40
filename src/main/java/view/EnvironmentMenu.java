package view;

import controller.GameMenuController;
import controller.EnvironmentMenuController;
import controller.UserController;
import view.enums.Commands;

import java.util.regex.Matcher;

public class EnvironmentMenu {
    public static String run() throws ReflectiveOperationException {
        System.out.println("you are in environment menu!");
        if (EnvironmentMenuController.isFirstPlayer()) {
            chooseMapSize();
            setPlayers();
            chooseColor();
        } else {
            if (EnvironmentMenuController.isCurrentGovernmentChoseColor(GameMenuController.getCurrentGovernment().getOwner()))
                return "you chose your color wait for starting game!";
            chooseColor();
            EnvironmentMenuController.checkGameStarted();
        }
        while (true) {
            String command = Commands.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, EnvironmentMenu.class));
        }
    }

    public static void chooseColor() {
        while (true) {
            System.out.print("type your color: ");
            System.out.print(GameMenuController.getColorList());
            try {
                System.out.println(GameMenuController.chooseColor(Commands.scanner.nextLine()));
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
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
        System.out.print("choose your size for map (A: 200X200 B: 400X400): ");
        while (true){
            String response = Commands.scanner.nextLine();
            int size = response.equalsIgnoreCase("A") ? 200 : response.equalsIgnoreCase("B") ? 400 : 0;
            if(size != 0) {
                GameMenuController.setMapSize(size);
                break;
            }
            else System.out.println("enter valid value: ");
        }
    }

    public static void setPlayers() {
        int countOfPlayers = setNumberOfPlayers();
        GameMenuController.setOnGovernment();
        int selectedPlayer = 0;
        while (true) {
            System.out.print("choose your opponents (write usernames): ");
            String username = Commands.scanner.nextLine();
            if (UserController.isUsernameExists(username)) {
                if (EnvironmentMenuController.isPlayerAdded(username)) {
                    System.out.println("this player already added");
                    continue;
                }
                EnvironmentMenuController.addPlayer(username);
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
            System.out.print("choose number of players of game: ");
            try {
                countOfPlayers = Integer.parseInt(Commands.scanner.nextLine());
                if (countOfPlayers != 2 && countOfPlayers != 4 && countOfPlayers != 8)
                    System.out.print("please enter invalid number (2 or 4 or 8): ");
                else break;
            } catch (NumberFormatException e) {
                System.out.print("please enter a number: ");
            }
        }
        return countOfPlayers;
    }

    public static void nextTurn(Matcher matcher) throws ReflectiveOperationException {
        LoginMenu.run();
    }
}
