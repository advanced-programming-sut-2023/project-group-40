import controller.UserController;
import view.LoginMenu;


public class Main {
    public static void main(String[] args) throws ReflectiveOperationException {
        UserController.fetchDatabase();
        LoginMenu.run();
    }
}
