package view;

import controller.LoginMenuController;
import controller.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.enums.Commands;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;

public class LoginMenu extends Application {
    public static void run() throws ReflectiveOperationException {
        UserController.autoLogin();
        System.out.println("you are in login menu");
        while (true) {
            String command = Commands.scanner.nextLine();
            String result = Commands.regexFinder(command, LoginMenu.class);
            UserController.updateDatabase();
            if (result != null) System.out.println(result);
        }
    }

    public static String exit(Matcher matcher) throws IOException {
        UserController.updateDatabase();
        System.exit(0);
        return null;
    }

    public static String login(Matcher matcher) throws ReflectiveOperationException {
        String username = Commands.eraseQuot(matcher.group("username"));
        String password = Commands.eraseQuot(matcher.group("password"));
        boolean isStayLoggedIn = matcher.group("stayLoggedIn") != null;
        try {
            System.out.println(LoginMenuController.login(username, password, isStayLoggedIn));
            MainMenu.run();
            return null;
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public static String register(Matcher matcher) throws ReflectiveOperationException {
        RegisterMenu.run();
        return null;
    }

    public static String forgetPassword(Matcher matcher) {
        String username = matcher.group("username");
        if (UserController.getUserByUsername(username) == null) return "username not exist!";
        System.out.println(UserController.getSecurityQuestionsList());
        String answer = Commands.scanner.nextLine();
        try {
            System.out.print(LoginMenuController.checkSecurityQuestion(username, answer));
            String newPassword = Commands.scanner.nextLine();
            try {
                System.out.println(LoginMenuController.changePassword(username, newPassword));
                return "your password will successfully changed!";
            } catch (RuntimeException e) {
                return e.getMessage();
            }
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane pane = new Pane();
        Scene scene = new Scene(pane);
        Label label = new Label("xxx");
        label.setFont(new Font(150));
        pane.getChildren().addAll(label);
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        label.setTranslateX(App.getWidth()/2 - label.getWidth()/2);
    }
}