package view;

import com.google.gson.Gson;
import controller.ConnectToServer;
import controller.MainMenuController;
import controller.UserController;
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
import model.DefaultSlogans;
import model.SecurityQuestions;
import model.User;

import java.util.Objects;

public class RegisterMenu extends Application {
    private final Button generateRandomPassword = new Button("random password");
    private final Button generateRandomSlogan = new Button("random slogan");
    private final Button register = new Button("register");
    private final CheckBox sloganCheckBox = new CheckBox("activate slogan");
    private final Button submitButton = new Button("submit");
    private final ComboBox<String> sloganComboBox = new ComboBox<>();
    VBox registerVbox;
    private Pane root;
    private TextField username, email, nickname, slogan, securityAnswer;
    private TextField password;
    private HBox usernameHBox, passwordHBox, emailHBox, nicknameHBox, buttonHBox, sloganHBox, sloganToolsHBox, securityQuestionsHBox, securityAnswerHBox;
    private Image hideIconImage;
    private Image showIconImage;
    private ImageView eyeIcon;
    private ComboBox<String> securityQuestions;
    private Stage primaryStage;

    {
        generateRandomSlogan.setVisible(false);
        sloganComboBox.setVisible(false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(RegisterMenu.class.getResource("/images/backgrounds/registerMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false, true, true))));
        Scene scene = new Scene(root);
        registerVbox = new VBox();
        registerVbox.getStylesheets().add(Objects.requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
        username = new TextField();
        password = new PasswordField();
        email = new TextField();
        nickname = new TextField();
        slogan = new TextField();
        securityAnswer = new TextField();

        usernameHBox = new HBox(new Label("username :"), username);
        passwordHBox = new HBox(new Label("password :"), password);
        emailHBox = new HBox(new Label("email :"), email);
        nicknameHBox = new HBox(new Label("nickname :"), nickname);
        sloganHBox = new HBox(new Label("slogan :"), slogan);
        buttonHBox = new HBox(generateRandomPassword, register);
        sloganToolsHBox = new HBox(sloganCheckBox, generateRandomSlogan, sloganComboBox);
        registerVbox.getChildren().addAll(usernameHBox, passwordHBox, emailHBox, nicknameHBox, sloganToolsHBox, buttonHBox);
        securityQuestions = new ComboBox<>();
        securityQuestions.getItems().add(SecurityQuestions.NO_1.getQuestion());
        securityQuestions.getItems().add(SecurityQuestions.NO_2.getQuestion());
        securityQuestions.getItems().add(SecurityQuestions.NO_3.getQuestion());
        securityQuestionsHBox = new HBox(new Label("security question :"), securityQuestions);
        securityAnswerHBox = new HBox(new Label("security answer : "), securityAnswer);
        for (DefaultSlogans slogan : DefaultSlogans.values())
            sloganComboBox.getItems().add(slogan.getSlogan());
        sloganComboBox.setMaxWidth(200);
        root.getChildren().addAll(registerVbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        setSizes();
        setActions();
    }

    private void setActions() {
        eyeIcon = new ImageView(hideIconImage);
        passwordHBox.getChildren().addAll(eyeIcon);
        username.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkUsernameFormat(t1)) {
                Errors.USERNAME_ERROR.getErrorLabel().setText("username is invalid!");
                if (usernameHBox.getChildren().size() == 2)
                    usernameHBox.getChildren().add(Errors.USERNAME_ERROR.getErrorLabel());
                setSizes();
            } else {
                usernameHBox.getChildren().remove(Errors.USERNAME_ERROR.getErrorLabel());
                setSizes();
            }
        });
        password.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkPasswordFormat(password.getText())) {
                Errors.PASSWORD_ERROR.getErrorLabel().setText("password is weak!");
                if (passwordHBox.getChildren().size() == 3)
                    passwordHBox.getChildren().add(Errors.PASSWORD_ERROR.getErrorLabel());
            } else passwordHBox.getChildren().remove(Errors.PASSWORD_ERROR.getErrorLabel());
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
        generateRandomPassword.setOnMouseClicked(mouseEvent -> {
            password.setText(UserController.generateRandomPassword());
        });
        sloganCheckBox.setOnMouseClicked(mouseEvent -> {
            Bounds sloganBounds = sloganHBox.getChildren().get(0).getBoundsInParent();
            Bounds usernameBounds = usernameHBox.getChildren().get(0).getBoundsInParent();
            if (sloganCheckBox.isSelected()) {
                generateRandomSlogan.setVisible(true);
                sloganComboBox.setVisible(true);
                registerVbox.getChildren().add(4, sloganHBox);
                sloganHBox.setSpacing(20 + usernameBounds.getWidth() - sloganBounds.getWidth());
            } else {
                generateRandomSlogan.setVisible(false);
                sloganComboBox.setVisible(false);
                registerVbox.getChildren().remove(sloganHBox);
            }
        });
        generateRandomSlogan.setOnMouseClicked(event -> {
            slogan.setText(UserController.generateRandomSlogan());
        });
        sloganComboBox.valueProperty().addListener((observableValue, s, t1) -> {
            slogan.setText(sloganComboBox.getValue());
        });
        register.setOnMouseClicked(mouseEvent -> {
            TextFieldController.setSuccessful(true);
            TextFieldController.checkEmptyUsername(usernameHBox, username);
            TextFieldController.checkNickname(nicknameHBox, nickname);
            TextFieldController.checkEmail(emailHBox, email);
            TextFieldController.checkPassword(passwordHBox, password);
            TextFieldController.checkSlogan(sloganHBox, slogan);
            if (TextFieldController.isSuccessful()) {
                String message = ConnectToServer.register(username.getText(), password.getText(),
                        nickname.getText(), email.getText(), slogan.getText());
                if (message.startsWith("your register verified")) {
                    SuccessfulDialog dialog = new SuccessfulDialog(root, "register successful");
                    dialog.make();
                    new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
                        dialog.removeDialog();
                        chooseSecurityQuestion();
                    })).play();
                } else {
                    ErrorDialog dialog = new ErrorDialog(root, message);
                    dialog.make();
                }
            }
        });

        submitButton.setOnMouseClicked(event -> {
            TextFieldController.setSuccessful(true);
            CaptchaController.checkCaptcha();
            TextFieldController.checkSecurityEmpty(securityQuestions, securityQuestionsHBox, securityAnswerHBox, securityAnswer);
            if (TextFieldController.isSuccessful()) {
                try {
                    String message = ConnectToServer.securityAnswer(securityAnswer.getText(), Integer.parseInt(securityQuestions.getValue().substring(0, 1)));
                    MainMenuController.setCurrentUser(new Gson().fromJson(message, User.class));
                    new MainMenu().start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }

    private void chooseSecurityQuestion() {
        registerVbox.getChildren().clear();
        CaptchaController.setUpCaptcha();
        registerVbox.getChildren().addAll(securityQuestionsHBox, securityAnswerHBox, CaptchaController.getCaptchaHBox());
//        submitButton.translateXProperty().bind(Bindings.add(loginVbox.widthProperty().divide(2),submitButton.translateXProperty().divide(-2)));
        registerVbox.getChildren().addAll(submitButton);
    }

    private void setSizes() {
        registerVbox.translateXProperty().bind(registerVbox.widthProperty().divide(-2).add(App.getWidth() / 2));
        registerVbox.translateYProperty().bind(registerVbox.heightProperty().divide(-2).add(App.getHeight() / 2));
        int width = (int) passwordHBox.getChildren().get(1).getBoundsInParent().getHeight();
        registerVbox.setSpacing(App.getHeight() / 20);
        hideIconImage = new Image(LoginMenu.class.getResource("/images/hideIcon.png").toExternalForm(), width, width, false, false);
        showIconImage = new Image(LoginMenu.class.getResource("/images/showIcon.png").toExternalForm(), width, width, false, false);
    }
}
