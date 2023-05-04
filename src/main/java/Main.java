import model.User;
import org.apache.commons.text.RandomStringGenerator;
import view.LoginMenu;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws ReflectiveOperationException {
        User.fetchDatabase();
        LoginMenu.run();
    }
}
