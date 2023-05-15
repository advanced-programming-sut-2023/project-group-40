import controller.GameMenuController;
import controller.LoginMenuController;
import controller.UserController;
import model.User;
import org.junit.*;
import view.enums.Commands;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static controller.UserController.*;

public class LoginMenuTest {
    private static final ArrayList<User> users = User.getUsers();

    @BeforeClass
    public static void addTestDB() {
        users.add(new User("user1", "User12345@!#", "nickname1", "user1@user.ir", "slogan"));
        users.add(new User("user2", "User12345@!#", "nickname2", "user2.user.ir", "slogan"));
        users.add(new User("user3", "User12345@!#", "nickname3", "user3@user.ir", "slogan"));
        users.add(new User("user4", "User12345@!#", "nickname4", "user4@user.ir", "slogan"));
        users.add(new User("user5", "User12345@!#", "nickname5", "user5@user.ir", "slogan"));
        users.add(new User("user6", "User12345@!#", "nickname6", "user6@user.ir", "slogan"));
        users.add(new User("user7", "User12345@!#", "nickname7", "user7@user.ir", "slogan"));
        users.add(new User("user8", "User12345@!#", "nickname8", "user8@user.ir", "slogan"));
        users.add(new User("user9", "User12345@!#", "nickname9", "user9@user.ir", "slogan"));
        User.setUsers(users);
    }

    @Test
    public void checkFormatMethods() {
        assertTrue(checkUsernameFormat("ali_123"));
        assertFalse(checkUsernameFormat("ali 123"));
        assertFalse(checkUsernameFormat("ali@123"));
        assertTrue(checkEmailFormat("ali@sharif.email.ir"));
        assertTrue(checkEmailFormat("ali_@sharif.email.ir"));
        assertFalse(checkEmailFormat("ali_@ir."));
        assertFalse(checkEmailFormat("ali_@ir"));
        assertFalse(checkPasswordFormat("ali majidi"));
        assertFalse(checkPasswordFormat("aliABC   #$@045"));
        assertTrue(checkPasswordFormat("aliABC#$@045"));
        assertFalse(checkPasswordFormat("aA1@"));
    }

    @Test
    public void checkExistsMethods() {
        assertTrue(isUsernameExists("user5"));
        assertTrue(isUsernameExists("user6"));
        assertFalse(isUsernameExists("user11"));
        assertTrue(isEmailExists("user8@user.ir"));
        assertTrue(isEmailExists("user9@user.ir"));
        assertFalse(isEmailExists("user13@user.ir"));
    }

    @Test
    public void loginTest(){
        assertTrue(Commands.LOGIN.canMatch("user login -u username2 -p Password2*"));
        assertFalse(Commands.LOGIN.canMatch("user login -p Password2*"));
        assertEquals(LoginMenuController.login("user1","User12345@!#",false),"user logged in successfully!");
        assertThrows(RuntimeException.class,() -> LoginMenuController.login("user1","user12345@!#",false));
        assertThrows(RuntimeException.class,() -> LoginMenuController.login("user12","User12345@!#",false));
    }

}
