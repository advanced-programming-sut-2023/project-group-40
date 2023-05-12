import model.User;
import view.LoginMenu;


public class Main {
    public static void main(String[] args) throws ReflectiveOperationException {
        User.fetchDatabase();
        LoginMenu.run();
    }
}
