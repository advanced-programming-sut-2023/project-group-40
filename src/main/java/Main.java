import controller.LoginMenuController;
import model.User;
import view.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ReflectiveOperationException {
        User.fetchDatabase();
        LoginMenu.run();
    }
}
