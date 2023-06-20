package view;

import controller.RegisterMenuController;
import controller.UserController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DefaultSlogans;
import model.SecurityQuestions;
import model.Texture;

import java.io.File;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;

public class RegisterMenu extends Application {
    private final Button generateRandomPassword = new Button("random password");
    private final Button generateRandomSlogan = new Button("random slogan");
    private final Button register = new Button("register");
    private final CheckBox sloganCheckBox = new CheckBox("activate slogan");
    private final Button submitButton = new Button("submit");
    VBox loginVbox;
    private Pane root;
    private TextField username, email, nickname, slogan, securityAnswer;
    private TextField password;
    private HBox usernameHBox, passwordHBox, emailHBox, nicknameHBox, buttonHBox, sloganHBox,
            sloganToolsHBox, securityQuestionsHBox, securityAnswerHBox;
    private Image hideIconImage;
    private Image showIconImage;
    private ImageView eyeIcon;
    private ComboBox<String> securityQuestions,sloganComboBox = new ComboBox<>();
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
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        Scene scene = new Scene(root);
        loginVbox = new VBox();
        loginVbox.getStylesheets().add(Objects
                .requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
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
        sloganToolsHBox = new HBox(sloganCheckBox, generateRandomSlogan,sloganComboBox);
        loginVbox.getChildren().addAll(usernameHBox, passwordHBox, emailHBox, nicknameHBox, sloganToolsHBox, buttonHBox);
        securityQuestions = new ComboBox<>();
        securityQuestions.getItems().add(SecurityQuestions.NO_1.getQuestion());
        securityQuestions.getItems().add(SecurityQuestions.NO_2.getQuestion());
        securityQuestions.getItems().add(SecurityQuestions.NO_3.getQuestion());
        securityQuestionsHBox = new HBox(new Label("security question :"), securityQuestions);
        securityAnswerHBox = new HBox(new Label("security answer : "), securityAnswer);
        for (DefaultSlogans slogan : DefaultSlogans.values())
            sloganComboBox.getItems().add(slogan.getSlogan());
        sloganComboBox.setMaxWidth(200);
        root.getChildren().addAll(loginVbox);
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
                loginVbox.getChildren().add(4, sloganHBox);
                sloganHBox.setSpacing(20 + usernameBounds.getWidth() - sloganBounds.getWidth());
            } else {
                generateRandomSlogan.setVisible(false);
                sloganComboBox.setVisible(false);
                loginVbox.getChildren().remove(sloganHBox);
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
            TextFieldController.checkExistUsername(usernameHBox, username);
            TextFieldController.checkPassword(passwordHBox, password);
            TextFieldController.checkEmail(emailHBox, email);
            TextFieldController.checkNickname(nicknameHBox, nickname);
            TextFieldController.checkSlogan(sloganHBox, slogan);
            if (TextFieldController.isSuccessful()) {
                SuccessfulDialog dialog = new SuccessfulDialog(root, "register successful");
                dialog.make();
                new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
                    dialog.removeDialog();
                    chooseSecurityQuestion();
                })).play();
            }
        });

        submitButton.setOnMouseClicked(event -> {
            TextFieldController.setSuccessful(true);
            CaptchaController.checkCaptcha();
            TextFieldController.checkSecurityEmpty(securityQuestions, securityQuestionsHBox, securityAnswerHBox, securityAnswer);
            if (TextFieldController.isSuccessful()) {
                RegisterMenuController.register(username.getText(), password.getText(), email.getText(), nickname.getText()
                        , slogan.getText(), securityQuestions.getItems().indexOf(securityQuestions.getValue()) + 1, securityAnswer.getText());
                try {
                    new LoginMenu().start(primaryStage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void chooseSecurityQuestion() {
        loginVbox.getChildren().clear();
        CaptchaController.setUpCaptcha();
        loginVbox.getChildren().addAll(securityQuestionsHBox, securityAnswerHBox, CaptchaController.getCaptchaHBox());
//        submitButton.translateXProperty().bind(Bindings.add(loginVbox.widthProperty().divide(2),submitButton.translateXProperty().divide(-2)));
        loginVbox.getChildren().addAll(submitButton);
    }

    private void setSizes() {
        loginVbox.translateXProperty().bind(loginVbox.widthProperty().divide(-2).add(App.getWidth() / 2));
        loginVbox.translateYProperty().bind(loginVbox.heightProperty().divide(-2).add(App.getHeight() / 2));
        int width = (int) passwordHBox.getChildren().get(1).getBoundsInParent().getHeight();
        loginVbox.setSpacing(App.getHeight() / 20);
        hideIconImage = new Image(LoginMenu.class.getResource("/images/hideIcon.png").toExternalForm(), width, width, false, false);
        showIconImage = new Image(LoginMenu.class.getResource("/images/showIcon.png").toExternalForm(), width, width, false, false);
    }
}
