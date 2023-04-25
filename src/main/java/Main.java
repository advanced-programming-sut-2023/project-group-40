import controller.LoginMenuController;
import view.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ReflectiveOperationException {
        LoginMenu.run(new Scanner(System.in));
    }
}
