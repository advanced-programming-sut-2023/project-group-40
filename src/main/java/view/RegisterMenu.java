package view;

import controller.RegisterMenuController;
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

import java.util.Objects;
import java.util.regex.Matcher;

public class RegisterMenu extends Application {
    private Pane root;
    private TextField username, email, nickname, slogan;
    private TextField password;
    VBox loginVbox;
    private HBox usernameHBox, passwordHBox, emailHBox, nicknameHBox, buttonHBox, sloganHBox,sloganToolsHBox;
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
    private boolean isSuccessful = false;

    {
        generateRandomSlogan.setVisible(false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
        loginVbox = new VBox();
        username = new TextField();
        password = new PasswordField();
        email = new TextField();
        nickname = new TextField();
        slogan = new TextField();
        usernameHBox = new HBox(new Label("username :"), username);
        passwordHBox = new HBox(new Label("password :"), password);
        emailHBox = new HBox(new Label("email :"), email);
        nicknameHBox = new HBox(new Label("nickname :"), nickname);
        sloganHBox = new HBox(new Label("slogan :"), slogan);
        buttonHBox = new HBox(generateRandomPassword, register);
        sloganToolsHBox = new HBox(sloganCheckBox,generateRandomSlogan);
        loginVbox.getChildren().addAll(usernameHBox, passwordHBox, emailHBox, nicknameHBox,sloganToolsHBox, buttonHBox);
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
        passwordHBox.getChildren().addAll(eyeIcon);
        username.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkUsernameFormat(t1)) {
                usernameError.setText("username is invalid!");
                if (usernameHBox.getChildren().size() == 2) usernameHBox.getChildren().add(usernameError);
                setSizes();
            } else {
                usernameHBox.getChildren().remove(usernameError);
                setSizes();
            }
        });
        password.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkPasswordFormat(t1)) {
                passwordError.setText("password is weak!");
                if (passwordHBox.getChildren().size() == 3)
                    passwordHBox.getChildren().add(passwordError);
            } else {
                passwordHBox.getChildren().remove(passwordError);
            }
        });

        eyeIcon.setOnMouseClicked(mouseEvent -> {
            String currentText = password.getText();
            passwordHBox.getChildren().remove(password);
            if(eyeIcon.getImage().equals(hideIconImage)) {
                password = new TextField(currentText);
                eyeIcon.setImage(showIconImage);
            }
            else {
                password = new PasswordField();
                password.setText(currentText);
                eyeIcon.setImage(hideIconImage);
            }
            passwordHBox.getChildren().add(1,password);
            password.requestFocus();
        });
        generateRandomPassword.setOnMouseClicked(mouseEvent -> {
            password.setText(UserController.generateRandomPassword());
        });
        sloganCheckBox.setOnMouseClicked(mouseEvent -> {
            Bounds sloganBounds = sloganHBox.getChildren().get(0).getBoundsInParent();
            Bounds usernameBounds = usernameHBox.getChildren().get(0).getBoundsInParent();
            if (sloganCheckBox.isSelected()) {
                generateRandomSlogan.setVisible(true);
                loginVbox.getChildren().add(4, sloganHBox);
                sloganHBox.setSpacing(20 + usernameBounds.getWidth() - sloganBounds.getWidth());
            } else {
                generateRandomSlogan.setVisible(false);
                loginVbox.getChildren().remove(sloganHBox);
            }
        });
        generateRandomSlogan.setOnMouseClicked(event -> {
            slogan.setText(UserController.generateRandomSlogan());
        });
        register.setOnMouseClicked(mouseEvent -> {
            isSuccessful = true;
            checkUsername();
            checkPassword();
            checkEmail();
            checkNickname();
            checkSlogan();
        });
    }

    private void checkUsername() {
        if (username.getText().length() == 0) {
            isSuccessful = false;
            usernameError.setText("username is empty!");
            if (usernameHBox.getChildren().size() == 2)
                usernameHBox.getChildren().add(usernameError);
        } else if (UserController.isUsernameExists(username.getText())) {
            usernameError.setText("username is exists!");
            if (usernameHBox.getChildren().size() == 2) usernameHBox.getChildren().add(usernameError);
        } else usernameHBox.getChildren().remove(usernameError);
    }

    private void checkPassword() {
        if (password.getText().length() == 0) {
            isSuccessful = false;
            passwordError.setText("password is empty!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(passwordError);
        } else passwordHBox.getChildren().remove(passwordError);
    }

    private void checkSlogan() {
        if (slogan.getText().length() == 0) {
            isSuccessful = false;
            if (sloganHBox.getChildren().size() == 2)
                sloganHBox.getChildren().add(sloganError);
        }
        else sloganHBox.getChildren().remove(sloganError);
    }

    private void checkNickname() {
        if (nickname.getText().length() == 0) {
            isSuccessful = false;
            if (nicknameHBox.getChildren().size() == 2)
                nicknameHBox.getChildren().add(nicknameError);
        }
        else nicknameHBox.getChildren().remove(nicknameError);
    }

    private void checkEmail() {
        if (email.getText().length() == 0 ) {
            isSuccessful = false;
            emailError.setText("email is empty!");
            if (emailHBox.getChildren().size() == 2) emailHBox.getChildren().add(emailError);
            emailHBox.getChildren().get(2).setTranslateX(usernameBounds.getWidth() - emailBounds.getWidth());
        }else if(UserController.isEmailExists(email.getText())){
            emailError.setText("email is exists!");
            if (emailHBox.getChildren().size() == 2) emailHBox.getChildren().add(emailError);
            emailHBox.getChildren().get(2).setTranslateX(usernameBounds.getWidth() - emailBounds.getWidth());
        }
        else emailHBox.getChildren().remove(emailError);
    }

    private void setSizes() {
        int distance = 20;
        loginVbox.setTranslateY(App.getHeight() / 10);
        int width = (int) passwordHBox.getChildren().get(1).getBoundsInParent().getHeight();
        loginVbox.setTranslateX(App.getWidth() / 2 - loginVbox.getWidth() / 2 - width / 2.0);
        loginVbox.setSpacing(App.getHeight() / 20);
        usernameBounds = usernameHBox.getChildren().get(0).getBoundsInParent();
        passwordLabelBounds = passwordHBox.getChildren().get(0).getBoundsInParent();
        emailBounds = emailHBox.getChildren().get(0).getBoundsInParent();
        usernameHBox.setSpacing(distance);
        sloganToolsHBox.setSpacing(distance);
        passwordHBox.setSpacing(distance + usernameBounds.getWidth() - passwordLabelBounds.getWidth());
        emailHBox.setSpacing(distance);
        nicknameHBox.setSpacing(distance);
        buttonHBox.setSpacing(distance);
        emailHBox.getChildren().get(1).setTranslateX(usernameBounds.getWidth() - emailBounds.getWidth());
        hideIconImage = new Image(LoginMenu.class.getResource("/images/hideIcon.png").toExternalForm(), width, width, false, false);
        showIconImage = new Image(LoginMenu.class.getResource("/images/showIcon.png").toExternalForm(), width, width, false, false);
    }
}
