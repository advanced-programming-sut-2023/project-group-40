package view;

import controller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Good;
import model.Government;
import model.SecurityQuestions;
import model.buildings.Buildings;

import java.util.ArrayList;
import java.util.Objects;

public class LoginMenu extends Application {
    private final Button login = new Button("login");
    private final ToggleButton forgetMyPassword = new ToggleButton("forget my password");
    private final Button signup = new Button("signup");
    VBox loginVbox;
    private Pane root;
    private TextField username, password, securityAnswer;
    private HBox usernameHBox, passwordHBox, buttonHBox, securityQuestionsHBox, securityAnswerHBox;
    private Image hideIconImage;
    private Image showIconImage;
    private ImageView eyeIcon;
    private ComboBox<String> securityQuestions;
    private Label passwordLabel;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(RegisterMenu.class.getResource("/images/backgrounds/loginMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
        loginVbox = new VBox();
        username = new TextField();
        password = new PasswordField();
        securityAnswer = new TextField();
        passwordLabel = new Label("password :");
        usernameHBox = new HBox(new Label("username :"), username);
        passwordHBox = new HBox(passwordLabel, password);
        securityQuestions = new ComboBox<>();
        securityQuestions.getItems().add(SecurityQuestions.NO_1.getQuestion());
        securityQuestions.getItems().add(SecurityQuestions.NO_2.getQuestion());
        securityQuestions.getItems().add(SecurityQuestions.NO_3.getQuestion());
        securityQuestionsHBox = new HBox(new Label("security question :"), securityQuestions);
        securityAnswerHBox = new HBox(new Label("security answer"), securityAnswer);
        CaptchaController.setUpCaptcha();
        buttonHBox = new HBox(forgetMyPassword, login, signup);
        loginVbox.getChildren().addAll(usernameHBox, passwordHBox, CaptchaController.getCaptchaHBox(), buttonHBox);
        root.getChildren().addAll(loginVbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        setSizes();
        setActions();
    }

    private void setSizes() {
        int distance = 20;
        loginVbox.setTranslateY(App.getHeight() / 10);
        int width = (int) passwordHBox.getChildren().get(1).getBoundsInParent().getHeight();
        loginVbox.setTranslateX(App.getWidth() / 2 - loginVbox.getWidth() / 2 - width / 2.0);
        loginVbox.setSpacing(App.getHeight() / 20);
        Bounds usernameBounds = usernameHBox.getChildren().get(0).getBoundsInParent();
        Bounds passwordLabelBounds = passwordHBox.getChildren().get(0).getBoundsInParent();
        usernameHBox.setSpacing(distance);
        passwordHBox.setSpacing(distance + usernameBounds.getWidth() - passwordLabelBounds.getWidth());
        buttonHBox.setSpacing(distance);
        securityQuestionsHBox.setSpacing(distance);
        securityAnswerHBox.setSpacing(distance);
        hideIconImage = new Image(LoginMenu.class.getResource("/images/hideIcon.png").toExternalForm(), width, width, false, false);
        showIconImage = new Image(LoginMenu.class.getResource("/images/showIcon.png").toExternalForm(), width, width, false, false);
    }

    private void setActions() {
        eyeIcon = new ImageView(hideIconImage);
        passwordHBox.getChildren().addAll(eyeIcon);
        username.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkUsernameFormat(t1)) {
                Errors.USERNAME_ERROR.getErrorLabel().setText("username is invalid!");
                if (usernameHBox.getChildren().size() == 2)
                    usernameHBox.getChildren().add(Errors.USERNAME_ERROR.getErrorLabel());
            } else usernameHBox.getChildren().remove(Errors.USERNAME_ERROR.getErrorLabel());
        });
        eyeIcon.setOnMouseClicked(mouseEvent -> {
            String currentText = password.getText();
            passwordHBox.getChildren().remove(password);
            if (eyeIcon.getImage().equals(hideIconImage)) {
                password = new TextField(currentText);
                eyeIcon.setImage(showIconImage);
            } else {
                password = new PasswordField();
                password.setText(currentText);
                eyeIcon.setImage(hideIconImage);
            }
            passwordHBox.getChildren().add(1, password);
            password.requestFocus();
        });
        login.setOnMouseClicked(mouseEvent -> {
            TextFieldController.setSuccessful(true);
            TextFieldController.checkEmptyUsername(usernameHBox, username);
            TextFieldController.checkPassword(forgetMyPassword,passwordHBox, password);
            if (forgetMyPassword.isSelected())
                TextFieldController.checkSecurity(username, securityQuestions, securityQuestionsHBox, securityAnswerHBox, securityAnswer,password);
            CaptchaController.checkCaptcha();
            if (TextFieldController.isSuccessful()) {
                String message = ConnectToServer.login(username.getText(), password.getText());
                if (message.startsWith("your login verified")) {
                    SuccessfulDialog successfulDialog = new SuccessfulDialog(root, "login successful!");
                    successfulDialog.make();
                    new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
                        successfulDialog.removeDialog();
                        try {
                            new MainMenu().start(primaryStage);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })).play();
                } else {
                    ErrorDialog dialog = new ErrorDialog(root, message);
                    dialog.make();
                }
            }
        });
        forgetMyPassword.setOnMouseClicked(mouseEvent -> {
            if (loginVbox.getChildren().size() == 4) {
                loginVbox.getChildren().add(2, securityQuestionsHBox);
                loginVbox.getChildren().add(3, securityAnswerHBox);
                passwordLabel.setText("new password: ");
            } else {
                passwordLabel.setText("password: ");
                loginVbox.getChildren().remove(securityQuestionsHBox);
                loginVbox.getChildren().remove(securityAnswerHBox);
            }
        });
        signup.setOnMouseClicked(mouseEvent -> {
            try {
                new RegisterMenu().start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


}