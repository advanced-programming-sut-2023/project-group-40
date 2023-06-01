package view;

import controller.ProfileMenuController;
import controller.UserController;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.enums.Commands;

import java.io.File;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;

public class ProfileMenu  extends Application {
    private Pane root;
    private final String EMPTY_SLOGAN = "slogan is empty!";
    private final Label usernameError = new Label("username is empty!");
    private final Label passwordError = new Label("password is empty!");
    private final Label captchaError = new Label("captcha is incorrect!");
    private final Label emailError = new Label("email is empty!");
    private final Label nicknameError = new Label("nickname is empty!");
    private final Label sloganError = new Label("slogan is empty!");
    private Bounds usernameBounds;
    private TextField username,password,nickname,email,slogan;
    private VBox profileMenuVbox;
    private HBox usernameHBox, passwordHBox,nicknameHBox, emailHBox, sloganHBox,buttonHBox,captchaHBox;
    private Image captchaImage;
    private ImageView captchaImageView;
    private final File captchaDirectory = new File(RegisterMenu.class.getResource("/captcha/").toExternalForm().substring(6));
    private final TextField captchaAnswerTextField = new TextField();
    private final Image reloadCaptchaImage = new Image(Objects.requireNonNull(RegisterMenu
            .class.getResource("/images/reloadCaptchaIcon.png")).toExternalForm());
    private final ImageView captchaRefreshImageView = new ImageView(reloadCaptchaImage);
    private final Button save = new Button("save");
    private Label passwordLabel;
    private Stage primaryStage;
    private final CheckBox sloganCheckBox = new CheckBox("show slogan");
    private Bounds emailBounds;
    private ImageView avatar = new ImageView(new Image(ProfileMenuController.getCurrentUser().getAvatarPath(),100,100,false,false));

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
        profileMenuVbox = new VBox();
        setupTextFields();
        handleCaptcha();
        setupHboxes();
        root.getChildren().addAll(profileMenuVbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        setSizes();
        setActions();
        setupAvatar();
    }

    private void setupAvatar() {
        avatar.setTranslateX(App.getWidth() / 50);
        avatar.setTranslateY(App.getHeight() / 50);
        root.getChildren().add(avatar);
        avatar.setOnMouseClicked(mouseEvent -> {
            Menu fileMenu = new Menu("File");
            ImageView imgView1 = new ImageView(new Image(ProfileMenu.class.getResource("/avatars/2.png").toString()));
            ImageView imgView2 = new ImageView(new Image(ProfileMenu.class.getResource("/avatars/3.png").toString()));
            ImageView imgView3 = new ImageView(new Image(ProfileMenu.class.getResource("/avatars/4.png").toString()));
            MenuItem item1 = new MenuItem("image 1", imgView1);
            MenuItem item2 = new MenuItem("image 2", imgView2);
            MenuItem item3 = new MenuItem("image 3", imgView3);
            //Adding all the menu items to the menu
            fileMenu.getItems().addAll(item1, item2, item3);
            MenuBar menuBar = new MenuBar(fileMenu);
            fileMenu.show();
        });
    }

    private void setupHboxes() {
        usernameHBox = new HBox(new Label("username :"), username);
        passwordLabel = new Label("password :");
        passwordHBox = new HBox(passwordLabel, password);
        nicknameHBox = new HBox(new Label("nickname :"),nickname);
        emailHBox = new HBox(new Label("email :"),email);
        sloganHBox = new HBox(sloganCheckBox);
        buttonHBox = new HBox(save);
        save.setVisible(false);
        profileMenuVbox.getChildren().addAll(usernameHBox, passwordHBox, emailHBox,nicknameHBox, sloganHBox,buttonHBox);
    }

    private void setupTextFields() {
        username = new TextField(ProfileMenuController.getCurrentUser().getUsername());
        password = new PasswordField();
        email = new TextField(ProfileMenuController.getCurrentUser().getEmail());
        nickname = new TextField(ProfileMenuController.getCurrentUser().getNickname());
        slogan = new TextField();
        if (ProfileMenuController.getCurrentUser().getSlogan() == null)
            slogan.setText(EMPTY_SLOGAN);
        else slogan.setText(ProfileMenuController.getCurrentUser().getSlogan());
    }

    private void handleCaptcha() {
        captchaImage = new Image("file:/" + Objects.
                requireNonNull(captchaDirectory.listFiles())[new Random().nextInt(0, Objects.requireNonNull(captchaDirectory.listFiles()).length)].getPath());
        captchaImageView = new ImageView(captchaImage);
        captchaRefreshImageView.setPreserveRatio(true);
        captchaRefreshImageView.setFitHeight(captchaImage.getHeight());
        captchaHBox = new HBox(captchaAnswerTextField,captchaImageView, captchaRefreshImageView);
        captchaHBox.setSpacing(20);
    }

    private void setSizes() {
        int distance = 20;
        profileMenuVbox.setTranslateY(App.getHeight() / 10);
        int width = (int) passwordHBox.getChildren().get(1).getBoundsInParent().getHeight();
        profileMenuVbox.setTranslateX(App.getWidth() / 2 - profileMenuVbox.getWidth() / 2 - width / 2.0);
        profileMenuVbox.setSpacing(App.getHeight() / 20);
        usernameBounds = usernameHBox.getChildren().get(0).getBoundsInParent();
        Bounds passwordLabelBounds = passwordHBox.getChildren().get(0).getBoundsInParent();
        emailBounds = emailHBox.getChildren().get(0).getBoundsInParent();
        usernameHBox.setSpacing(distance);
        passwordHBox.setSpacing(distance + usernameBounds.getWidth() - passwordLabelBounds.getWidth());
        emailHBox.setSpacing(distance);
        nicknameHBox.setSpacing(distance);
        sloganHBox.setSpacing(distance);
        emailHBox.getChildren().get(1).setTranslateX(usernameBounds.getWidth() - emailBounds.getWidth());

        buttonHBox.setSpacing(distance);
    }

    private void setActions() {
        usernameError.setStyle("-fx-text-fill: red");
        passwordError.setStyle("-fx-text-fill: red");
        emailError.setStyle("-fx-text-fill: red");
        nicknameError.setStyle("-fx-text-fill: red");
        sloganError.setStyle("-fx-text-fill: red");
        captchaError.setStyle("-fx-text-fill: red");
        username.setEditable(false);
        username.setOnMouseClicked(mouseEvent -> {
            username.setEditable(true);
        });
        username.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) username.setEditable(false);
        });
        username.textProperty().addListener((observableValue, s, t1) -> {
            save.setVisible(true);
            if (!UserController.checkUsernameFormat(t1)) {
                usernameError.setText("username is invalid!");
                if (usernameHBox.getChildren().size() == 2) usernameHBox.getChildren().add(usernameError);
            }
            else usernameHBox.getChildren().remove(usernameError);
        });
        password.textProperty().addListener((observableValue, s, t1) -> {
            save.setVisible(true);
            if (!UserController.checkPasswordFormat(t1)) {
                passwordError.setText("password is weak!");
                if (passwordHBox.getChildren().size() == 3)
                    passwordHBox.getChildren().add(passwordError);
            } else {
                passwordHBox.getChildren().remove(passwordError);
            }
        });
        email.textProperty().addListener((observableValue, s, t1) -> {
            save.setVisible(true);
        });
        nickname.textProperty().addListener((observableValue, s, t1) -> {
            save.setVisible(true);
        });
        slogan.textProperty().addListener((observableValue, s, t1) -> {
            save.setVisible(true);
            if (t1.length() == 0)
                slogan.setText(EMPTY_SLOGAN);
        });
        sloganCheckBox.setOnMouseClicked(mouseEvent -> {
            if (sloganCheckBox.isSelected()) {
                sloganHBox.getChildren().remove(sloganCheckBox);
                sloganHBox.getChildren().addAll(new Label("slogan :"),slogan);
                sloganHBox.getChildren().get(1).setTranslateX(usernameBounds.getWidth() - sloganHBox.getChildren().get(0).getBoundsInParent().getWidth() - 110);
            }
        });
        save.setOnMouseClicked(mouseEvent -> {
            TextFieldController.checkExistUsername(usernameHBox,username,usernameError);
            TextFieldController.checkEmail(emailHBox,email,emailError,usernameBounds.getWidth() - emailBounds.getWidth());
            TextFieldController.checkNickname(nicknameHBox,nickname,nicknameError);
            TextFieldController.checkSlogan(sloganHBox,slogan,sloganError);
//            checkPassword();
//            checkCaptcha();
            if (TextFieldController.isSuccessful()) {
                ProfileMenuController.changeUsername(username.getText());
                ProfileMenuController.changeNickname(nickname.getText());
                ProfileMenuController.changeEmail(email.getText());
                if (!slogan.getText().equals(EMPTY_SLOGAN))
                    ProfileMenuController.changeSlogan(slogan.getText());
            }
        });
        captchaRefreshImageView.setOnMouseClicked(event -> {
            captchaImage = new Image("file:/" + Objects.
                    requireNonNull(captchaDirectory.listFiles())[new Random().nextInt(0, Objects.requireNonNull(captchaDirectory.listFiles()).length)].getPath());
            captchaImageView.setImage(captchaImage);
        });
    }



//    private void checkPassword() {
//        if (password.getText().length() == 0) {
//            isSuccessful = false;
//            passwordError.setText("password is empty!");
//            if (passwordHBox.getChildren().size() == 3)
//                passwordHBox.getChildren().add(passwordError);
//        }
//        else if (!UserController.checkPasswordFormat(password.getText())) {
//            isSuccessful = false;
//            passwordError.setText("password is weak!");
//            if (passwordHBox.getChildren().size() == 3)
//                passwordHBox.getChildren().add(passwordError);
//        }
//        else if (!passwordLabel.getText().equals("new password : ") &&
//                !LoginMenuController.isPasswordCorrect(username.getText(),password.getText())){
//            isSuccessful = false;
//            passwordError.setText("password is incorrect!");
//            if (passwordHBox.getChildren().size() == 3)
//                passwordHBox.getChildren().add(passwordError);
//        }
//        else passwordHBox.getChildren().remove(passwordError);
//    }

    public static String changePassword(Matcher matcher) {
        String oldPass = Commands.eraseQuot(matcher.group("oldPassword"));
        String newPass = Commands.eraseQuot(matcher.group("newPassword"));
        return ProfileMenuController.changePassword(oldPass, newPass);
    }
}
