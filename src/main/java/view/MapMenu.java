package view;

import controller.GameMenuController;
import controller.MapMenuController;
import controller.ProfileMenuController;
import org.apache.commons.lang3.StringUtils;
import view.enums.Commands;

import java.util.regex.Matcher;

public class MapMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in map menu!");
        while (true) {
            String command = Commands.scanner.nextLine();
            if(command.equals("return")){
                System.out.println("you are in game menu");
                return;
            }
            System.out.println(Commands.regexFinder(command, MapMenu.class));
        }
    }
    public static String showMap(Matcher matcher) {
        int x= Integer.parseInt(matcher.group("x"));
        int y= Integer.parseInt(matcher.group("y"));
        return MapMenuController.showMap(x,y);
    }
    public static String changeSightArea(Matcher matcher) {
        int leftNumber = 0,topNumber = 0,rightNumber = 0,downNumber = 0;
        //left in another first
        while (true) {
            if (matcher.group("left") != null) leftNumber = StringUtils.isNotBlank(matcher.group("leftNumber")) ? Integer.parseInt(matcher.group("leftNumber")) : 1;
            if (matcher.group("top") != null) topNumber = StringUtils.isNotBlank(matcher.group("topNumber")) ? Integer.parseInt(matcher.group("topNumber")) : 1;
            if (matcher.group("right") != null) rightNumber = StringUtils.isNotBlank(matcher.group("rightNumber")) ? Integer.parseInt(matcher.group("rightNumber")) : 1;
            if (matcher.group("down") != null) downNumber = StringUtils.isNotBlank(matcher.group("downNumber")) ? Integer.parseInt(matcher.group("downNumber")) : 1;
            boolean res = matcher.find();
            if (!res) break;
        }

        return MapMenuController.showMap(MapMenuController.getCenterX() - topNumber + downNumber,
                MapMenuController.getCenterY() - leftNumber + rightNumber);
    }

    public static String showDetails(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return MapMenuController.showDetails(x,y);
    }
}
