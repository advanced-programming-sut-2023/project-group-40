package view;

import controller.LoginMenuController;
import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.enums.Commands;

import java.io.IOException;
import java.util.regex.Matcher;

public class LoginMenu extends Application {
    private Pane root;
    private TextField username, email, nickname, slogan;
    private TextField password;
    VBox loginVbox;
    private HBox usernameHbox, passwordHbox, emailHbox, nicknameHBox, buttonHbox, sloganHbox;
    private Image hideIconImage;
    private Image showIconImage;
    private Label usernameError = new Label("username is empty!");
    private Label passwordError = new Label("password is empty!");
    private Label emailError = new Label("email is empty!");
    private Label nicknameError = new Label("nickname is empty!");
    private Label sloganError = new Label("slogan is empty!");
    private ImageView eyeIcon;
    private Button generateRandomPassword = new Button("random password");
    private Button generateRandomSlogan = new Button("random slogan");
    private Button register = new Button("register"), login;
    private CheckBox sloganCheckBox = new CheckBox("activate slogan");
    private Bounds usernameBounds, passwordLabelBounds, emailBounds;

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
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(LoginMenu.class.getResource("/css/loginMenu.css").toString());
        loginVbox = new VBox();
        username = new TextField();
        password = new PasswordField();
        email = new TextField();
        nickname = new TextField();
        slogan = new TextField();
        usernameHbox = new HBox(new Label("username :"), username);
        passwordHbox = new HBox(new Label("password :"), password);
        emailHbox = new HBox(new Label("email :"), email);
        nicknameHBox = new HBox(new Label("nickname :"), nickname);
        sloganHbox = new HBox(new Label("slogan :"), slogan);
        buttonHbox = new HBox(generateRandomPassword, register);
        loginVbox.getChildren().addAll(usernameHbox, passwordHbox, emailHbox, nicknameHBox, sloganCheckBox, buttonHbox);
        root.getChildren().addAll(loginVbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        setSizes();
        setActions();
    }

    private void setActions() {
        passwordError.setStyle("-fx-text-fill: red");
        usernameError.setStyle("-fx-text-fill: red");
        emailError.setStyle("-fx-text-fill: red");
        nicknameError.setStyle("-fx-text-fill: red");
        usernameError.setStyle("-fx-text-fill: red");
        sloganError.setStyle("-fx-text-fill: red");
        eyeIcon = new ImageView(hideIconImage);
        passwordHbox.getChildren().addAll(eyeIcon);
        username.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkUsernameFormat(t1)) {
                usernameError.setText("username is invalid!");
                if (usernameHbox.getChildren().size() == 2) usernameHbox.getChildren().add(usernameError);
//                setSizes();
            } else {
                usernameHbox.getChildren().remove(usernameError);
//                setSizes();
            }
        });
        password.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkPasswordFormat(t1)) {
                passwordError.setText("password is weak!");
                if (passwordHbox.getChildren().size() == 3)
                    passwordHbox.getChildren().add(passwordError);
            } else {
                passwordHbox.getChildren().remove(passwordError);
            }
        });
        eyeIcon.setOnMouseClicked(mouseEvent -> {

        });
        generateRandomPassword.setOnMouseClicked(mouseEvent -> {
            password.setText(UserController.generateRandomPassword());
        });
        sloganCheckBox.setOnMouseClicked(mouseEvent -> {
            Bounds sloganBounds = sloganHbox.getChildren().get(0).getBoundsInParent();
            Bounds usernameBounds = usernameHbox.getChildren().get(0).getBoundsInParent();
            if (sloganCheckBox.isSelected()) {
                loginVbox.getChildren().add(4, sloganHbox);
                sloganHbox.setSpacing(20 + usernameBounds.getWidth() - sloganBounds.getWidth());
            } else loginVbox.getChildren().remove(sloganHbox);
        });
        register.setOnMouseClicked(mouseEvent -> {
            checkUsername();
            checkPassword();
            checkEmail();
            checkNickname();
            checkSlogan();
        });
    }

    private void checkUsername() {
        if (username.getText().length() == 0) {
            usernameError.setText("username is empty!");
            if (usernameHbox.getChildren().size() == 2)
                usernameHbox.getChildren().add(usernameError);
        } else if (UserController.isUsernameExists(username.getText())) {
            usernameError.setText("username is exists!");
            if (usernameHbox.getChildren().size() == 2) usernameHbox.getChildren().add(usernameError);
        } else usernameHbox.getChildren().remove(usernameError);
    }

    private void checkPassword() {
        if (password.getText().length() == 0) {
            passwordError.setText("password is empty!");
            if (passwordHbox.getChildren().size() == 3)
                passwordHbox.getChildren().add(passwordError);
        } else passwordHbox.getChildren().remove(passwordError);
    }

    private void checkSlogan() {
        if (slogan.getText().length() == 0) {
            if (sloganHbox.getChildren().size() == 2)
                sloganHbox.getChildren().add(sloganError);
        }
        else sloganHbox.getChildren().remove(sloganError);
    }

    private void checkNickname() {
        if (nickname.getText().length() == 0) {
            if (nicknameHBox.getChildren().size() == 2)
                nicknameHBox.getChildren().add(nicknameError);
        }
        else nicknameHBox.getChildren().remove(nicknameError);
    }

    private void checkEmail() {
        if (email.getText().length() == 0 ) {
            if (emailHbox.getChildren().size() == 2)emailHbox.getChildren().add(emailError);
            emailHbox.getChildren().get(2).setTranslateX(usernameBounds.getWidth() - emailBounds.getWidth());
        } else emailHbox.getChildren().remove(emailError);
    }

    private void setSizes() {
        int distance = 20;
        loginVbox.setTranslateY(App.getHeight() / 10);
        int width = (int) passwordHbox.getChildren().get(1).getBoundsInParent().getHeight();
        loginVbox.setTranslateX(App.getWidth() / 2 - loginVbox.getWidth() / 2 - width / 2.0);
        loginVbox.setSpacing(App.getHeight() / 20);
        usernameBounds = usernameHbox.getChildren().get(0).getBoundsInParent();
        passwordLabelBounds = passwordHbox.getChildren().get(0).getBoundsInParent();
        emailBounds = emailHbox.getChildren().get(0).getBoundsInParent();
        usernameHbox.setSpacing(distance);
        passwordHbox.setSpacing(distance + usernameBounds.getWidth() - passwordLabelBounds.getWidth());
        emailHbox.setSpacing(distance);
        nicknameHBox.setSpacing(distance);
        buttonHbox.setSpacing(distance);
        emailHbox.getChildren().get(1).setTranslateX(usernameBounds.getWidth() - emailBounds.getWidth());
        hideIconImage = new Image(LoginMenu.class.getResource("/images/hideIcon.png").toString(), width, width, false, false);
        showIconImage = new Image(LoginMenu.class.getResource("/images/showIcon.png").toString(), width, width, false, false);
    }
}