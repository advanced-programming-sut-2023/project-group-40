import controller.Server;
import controller.UserController;
import model.Government;
import model.Message;
import model.User;

public class Main {
    public static void main(String[] args) throws Exception {
        UserController.fetchDatabase();
        Government.fetchDatabase();
        Message.fetchDatabase();
        User.getUsers().forEach(user -> user.setOnline(false));
        new Server();
    }
}