package view;

import controller.GameMenuController;
import controller.MainController;
import controller.MapMenuController;
import org.apache.commons.lang3.StringUtils;
import view.enums.Commands;

import java.util.regex.Matcher;

public class MapMenu {
    public static void run() throws ReflectiveOperationException {
        System.out.println("you are in map menu!");
        while (true) {
            String command = MainController.scanner.nextLine();
            System.out.println(Commands.regexFinder(command, MapMenu.class));
        }
    }
    public static String changeSightArea(Matcher matcher) throws ReflectiveOperationException {
        //left in another first
        while (matcher.find()) {
            int leftNumber = StringUtils.isNotBlank(matcher.group("leftNumber")) ? Integer.parseInt(matcher.group("leftNumber")) : 1;
            int topNumber = StringUtils.isNotBlank(matcher.group("topNumber")) ? Integer.parseInt(matcher.group("topNumber")) : 1;
            int rightNumber = StringUtils.isNotBlank(matcher.group("rightNumber")) ? Integer.parseInt(matcher.group("rightNumber")) : 1;
            int downNumber = StringUtils.isNotBlank(matcher.group("downNumber")) ? Integer.parseInt(matcher.group("downNumber")) : 1;
            if (matcher.group("left") != null) MapMenuController.increaseX(-1 * leftNumber);
            if (matcher.group("top") != null) MapMenuController.increaseY(topNumber);
            if (matcher.group("right") != null) MapMenuController.increaseX(rightNumber);
            if (matcher.group("down") != null) MapMenuController.increaseY(-1 * downNumber);
        }
        GameMenuController.showMap(MapMenuController.getX(),MapMenuController.getY());
        return "sight area changed!";
    }

    public static String showDetails(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return MapMenuController.showDetails(x,y);
    }
}
