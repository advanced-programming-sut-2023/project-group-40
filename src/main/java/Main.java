import controller.LoginMenuController;
import model.User;
import org.apache.commons.lang3.StringUtils;
import view.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ReflectiveOperationException {
//        User.fetchDatabase();
//        LoginMenu.run();
        String top = null;
        String topNumber = "4";
        int topNumber2 = StringUtils.isNotBlank(topNumber) ? Integer.parseInt(topNumber) : 1;
        System.out.println(topNumber2);    }
}
