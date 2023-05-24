package view;

import controller.LoginMenuController;
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
import model.SecurityQuestions;

import java.io.File;
import java.util.Objects;
import java.util.Random;

public class LoginMenu extends Application {
    private Pane root;
    private TextField username,password,securityAnswer;
    VBox loginVbox;
    private HBox usernameHBox, passwordHBox,buttonHBox,securityQuestionsHBox,securityAnswerHBox,captchaHBox;
    private Image hideIconImage;
    private Image showIconImage;
    private Image captchaImage;
    private ImageView captchaImageView;
    private final File captchaDirectory = new File(RegisterMenu.class.getResource("/captcha/").toExternalForm().substring(6));

    private final Label usernameError = new Label("username is empty!");
    private final Label passwordError = new Label("password is empty!");
    private final Label securityQuestionError = new Label("security question is incorrect!");
    private final Label securityAnswerError = new Label("security answer is incorrect!");
    private final Label captchaError = new Label("captcha is incorrect!");
    private final TextField captchaAnswerTextField = new TextField();
    private final Image reloadCaptchaImage = new Image(Objects.requireNonNull(RegisterMenu
            .class.getResource("/images/reloadCaptchaIcon.png")).toExternalForm());
    private final ImageView captchaRefreshImageView = new ImageView(reloadCaptchaImage);
    private ImageView eyeIcon;
    private final Button login = new Button("login");
    private final ToggleButton forgetMyPassword = new ToggleButton("forget my password");
    private final Button signup = new Button("signup");
    private boolean isSuccessful = false;
    private ComboBox<String> securityQuestions;
    private Label passwordLabel;
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
        loginVbox = new VBox();
        username = new TextField();
        password = new PasswordField();
        securityAnswer = new TextField();
        passwordLabel =  new Label("password :");
        usernameHBox = new HBox(new Label("username :"), username);
        passwordHBox = new HBox(passwordLabel, password);
        securityQuestions = new ComboBox<>();
        securityQuestions.getItems().add(SecurityQuestions.NO_1.getQuestion());
        securityQuestions.getItems().add(SecurityQuestions.NO_2.getQuestion());
        securityQuestions.getItems().add(SecurityQuestions.NO_3.getQuestion());
        securityQuestionsHBox = new HBox(new Label("security question :"),securityQuestions);
        securityAnswerHBox = new HBox(new Label("security answer"),securityAnswer);
        captchaImage = new Image("file:/" + Objects.
                requireNonNull(captchaDirectory.listFiles())[new Random().nextInt(0, Objects.requireNonNull(captchaDirectory.listFiles()).length)].getPath());
        captchaImageView = new ImageView(captchaImage);
        captchaRefreshImageView.setPreserveRatio(true);
        captchaRefreshImageView.setFitHeight(captchaImage.getHeight());
        captchaHBox = new HBox(captchaAnswerTextField,captchaImageView, captchaRefreshImageView);
        captchaHBox.setSpacing(20);
        buttonHBox = new HBox(forgetMyPassword, login,signup);
        loginVbox.getChildren().addAll(usernameHBox, passwordHBox, captchaHBox,buttonHBox);
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
        usernameError.setStyle("-fx-text-fill: red");
        passwordError.setStyle("-fx-text-fill: red");
        securityQuestionError.setStyle("-fx-text-fill: red");
        securityAnswerError.setStyle("-fx-text-fill: red");
        captchaError.setStyle("-fx-text-fill: red");

        eyeIcon = new ImageView(hideIconImage);
        passwordHBox.getChildren().addAll(eyeIcon);
        username.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkUsernameFormat(t1)) {
                usernameError.setText("username is invalid!");
                if (usernameHBox.getChildren().size() == 2) usernameHBox.getChildren().add(usernameError);
            }
            else usernameHBox.getChildren().remove(usernameError);
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
        login.setOnMouseClicked(mouseEvent -> {
            isSuccessful = true;
            checkUsername();
            checkPassword();
            checkSecurity();
            checkCaptcha();
            if (isSuccessful) System.out.println("login successful");
        });
        forgetMyPassword.setOnMouseClicked(mouseEvent -> {
            if (loginVbox.getChildren().size() == 3) {
                loginVbox.getChildren().add(2, securityQuestionsHBox);
                loginVbox.getChildren().add(3,securityAnswerHBox);
                passwordLabel.setText("new password : ");
            }
            else {
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
        captchaRefreshImageView.setOnMouseClicked(event -> {
            captchaImage = new Image("file:/" + Objects.
                    requireNonNull(captchaDirectory.listFiles())[new Random().nextInt(0, Objects.requireNonNull(captchaDirectory.listFiles()).length)].getPath());
            captchaImageView.setImage(captchaImage);
        });
    }

    private void checkCaptcha() {
        String url = captchaImage.getUrl();
        if (url.substring(url.length() - 8, url.length() - 4).equals(captchaAnswerTextField.getText())) {
            captchaHBox.getChildren().remove(captchaError);
        }
        else{
            if (captchaHBox.getChildren().size() == 3) captchaHBox.getChildren().add(captchaError);
            captchaImage = new Image("file:/" + Objects.
                    requireNonNull(captchaDirectory.listFiles())[new Random().nextInt(0, Objects.requireNonNull(captchaDirectory.listFiles()).length)].getPath());
            captchaImageView.setImage(captchaImage);
            isSuccessful = false;
        }
    }

    private void checkSecurity() {
        if (!forgetMyPassword.isSelected()) return;
        if (!UserController.isUsernameExists(username.getText())) return;
        if (securityQuestions.getItems().indexOf(securityQuestions.getValue()) + 1
                != LoginMenuController.getSecurityQuestionNo(username.getText())) {
            if (securityQuestionsHBox.getChildren().size() == 2)
                securityQuestionsHBox.getChildren().add(securityQuestionError);
        }
        if (!securityAnswer.getText().equals(LoginMenuController.getSecurityAnswer(username.getText()))) {
            if (securityAnswerHBox.getChildren().size() == 2)
                securityAnswerHBox.getChildren().add(securityAnswerError);
        }
        else securityAnswerHBox.getChildren().remove(securityAnswerError);
    }

    private void checkUsername() {
        if (username.getText().length() == 0) {
            isSuccessful = false;
            usernameError.setText("username is empty!");
            if (usernameHBox.getChildren().size() == 2)
                usernameHBox.getChildren().add(usernameError);
        }
        else if (!UserController.isUsernameExists(username.getText())) {
            isSuccessful = false;
            usernameError.setText("username is not exists!");
            if (usernameHBox.getChildren().size() == 2) usernameHBox.getChildren().add(usernameError);
        }
        else usernameHBox.getChildren().remove(usernameError);
    }

    private void checkPassword() {
        if (password.getText().length() == 0) {
            isSuccessful = false;
            passwordError.setText("password is empty!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(passwordError);
        }
        else if (!UserController.checkPasswordFormat(password.getText())) {
            isSuccessful = false;
            passwordError.setText("password is weak!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(passwordError);
        }
        else if (!passwordLabel.getText().equals("new password : ") &&
                !LoginMenuController.isPasswordCorrect(username.getText(),password.getText())){
            isSuccessful = false;
            passwordError.setText("password is incorrect!");
            if (passwordHBox.getChildren().size() == 3)
                passwordHBox.getChildren().add(passwordError);
        }
        else passwordHBox.getChildren().remove(passwordError);
    }

}