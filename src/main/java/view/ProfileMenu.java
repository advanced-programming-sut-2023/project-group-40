package view;

import controller.ProfileMenuController;
import controller.UserController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.enums.Commands;

import java.util.Objects;
import java.util.regex.Matcher;

public class ProfileMenu  extends Application {
    private Pane root;
    private final String EMPTY_SLOGAN = "slogan is empty!";
    private Bounds usernameBounds;
    private TextField username,newPassword,oldPassword,nickname,email,slogan;
    private VBox profileMenuVbox;
    private HBox usernameHBox,nicknameHBox, emailHBox, sloganHBox,buttonHBox,oldPasswordHBox,newPasswordHBox;
    private final Button save = new Button("save");
    private Label passwordLabel;
    private Stage primaryStage;
    private final CheckBox sloganCheckBox = new CheckBox("show slogan");
    private final Button changePasswordButton = new Button("change password");
    private Bounds emailBounds;
    private final ImageView avatar = new ImageView(new Image(ProfileMenuController.getCurrentUser().getAvatarPath(),100,100,false,false));

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
        profileMenuVbox = new VBox();
        setupTextFields();
        setupBoxes();
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

    private void setupBoxes() {
        usernameHBox = new HBox(new Label("username :"), username);
        nicknameHBox = new HBox(new Label("nickname :"),nickname);
        emailHBox = new HBox(new Label("email :"),email);
        sloganHBox = new HBox(sloganCheckBox);
        CaptchaController.setUpCaptcha();
        buttonHBox = new HBox(save,changePasswordButton);
        save.setVisible(false);
        profileMenuVbox.getChildren().addAll(usernameHBox, emailHBox,nicknameHBox, sloganHBox,buttonHBox);
    }

    private void setupTextFields() {
        username = new TextField(ProfileMenuController.getCurrentUser().getUsername());
        oldPassword = new TextField();
        newPassword = new TextField();
        email = new TextField(ProfileMenuController.getCurrentUser().getEmail());
        nickname = new TextField(ProfileMenuController.getCurrentUser().getNickname());
        slogan = new TextField();
        if (ProfileMenuController.getCurrentUser().getSlogan() == null)
            slogan.setPromptText(EMPTY_SLOGAN);
        else slogan.setText(ProfileMenuController.getCurrentUser().getSlogan());
    }


    private void setSizes() {
        int distance = 20;
        profileMenuVbox.setTranslateY(App.getHeight() / 10);
        profileMenuVbox.setTranslateX(App.getWidth() / 2.0 - profileMenuVbox.getWidth() / 2.0);
        profileMenuVbox.setSpacing(App.getHeight() / 20);
        usernameBounds = usernameHBox.getChildren().get(0).getBoundsInParent();
        emailBounds = emailHBox.getChildren().get(0).getBoundsInParent();
        usernameHBox.setSpacing(distance);
        emailHBox.setSpacing(distance);
        nicknameHBox.setSpacing(distance);
        sloganHBox.setSpacing(distance);
        emailHBox.getChildren().get(1).setTranslateX(usernameBounds.getWidth() - emailBounds.getWidth());
        buttonHBox.setSpacing(distance);
    }

    private void setActions() {
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
                Errors.USERNAME_ERROR.getErrorLabel().setText("username is invalid!");
                if (usernameHBox.getChildren().size() == 2) usernameHBox.getChildren().add(Errors.USERNAME_ERROR.getErrorLabel());
            }
            else usernameHBox.getChildren().remove(Errors.USERNAME_ERROR.getErrorLabel());
        });
        email.textProperty().addListener((observableValue, s, t1) -> {
            save.setVisible(true);
        });
        nickname.textProperty().addListener((observableValue, s, t1) -> {
            save.setVisible(true);
        });
        slogan.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) slogan.setEditable(false);
        });
        sloganCheckBox.setOnMouseClicked(mouseEvent -> {
            if (sloganCheckBox.isSelected()) {
                sloganHBox.getChildren().remove(sloganCheckBox);
                sloganHBox.getChildren().addAll(new Label("slogan :"),slogan);
                sloganHBox.getChildren().get(1).setTranslateX(usernameBounds.getWidth() - sloganHBox.getChildren().get(0).getBoundsInParent().getWidth() - 110);
            }
        });
        save.setOnMouseClicked(mouseEvent -> {
            TextFieldController.checkExistUsername(usernameHBox,username,Errors.USERNAME_ERROR.getErrorLabel());
            TextFieldController.checkEmail(emailHBox,email,Errors.EMAIL_ERROR.getErrorLabel(),usernameBounds.getWidth() - emailBounds.getWidth());
            TextFieldController.checkNickname(nicknameHBox,nickname,Errors.NICKNAME_ERROR.getErrorLabel());
            if (TextFieldController.isSuccessful()) {
                ProfileMenuController.changeUsername(username.getText());
                ProfileMenuController.changeNickname(nickname.getText());
                ProfileMenuController.changeEmail(email.getText());
                if (!slogan.getText().equals(EMPTY_SLOGAN))
                    ProfileMenuController.changeSlogan(slogan.getText());
            }
        });
        changePasswordButton.setOnMouseClicked(mouseEvent -> {
            VBox changePasswordVbox = new VBox();
            changePasswordVbox.setSpacing(10);
            oldPasswordHBox = new HBox(new Label("old password : "),oldPassword);
            newPasswordHBox = new HBox(new Label("new password : "),newPassword);
            changePasswordVbox.translateXProperty().bind(changePasswordVbox.widthProperty().divide(-2).add(App.getWidth() / 2));
            changePasswordVbox.translateYProperty().bind(changePasswordVbox.heightProperty().divide(-2).add(App.getHeight() / 2));
            changePasswordVbox.getStylesheets().add(ProfileMenu.class.getResource("/css/changePasswordBoxStyle.css").toString());
            CaptchaController.getImageHBox().setPrefWidth(240);
            Button submit = new Button("submit");
            Button cancel = new Button("cancel");
            HBox buttons = new HBox(submit,cancel);
            buttons.translateXProperty().bind(Bindings.add(changePasswordVbox.widthProperty().divide(2),submit.widthProperty().divide(-1)).add(-10));
            changePasswordVbox.getChildren().addAll(oldPasswordHBox,newPasswordHBox,CaptchaController.getCaptchaHBox(),buttons);
            setChangePasswordButtonActions(changePasswordVbox,submit,cancel);
            root.getChildren().add(changePasswordVbox);
        });
    }

    private void setChangePasswordButtonActions(VBox changePasswordVbox,Button submit, Button cancel) {
        setErrorListener(newPassword, newPasswordHBox);
        submit.setOnMouseClicked(mouseEvent -> {
            CaptchaController.checkCaptcha();
            TextFieldController.checkPassword(newPasswordHBox,newPassword,oldPasswordHBox,oldPassword);
            if (TextFieldController.isSuccessful()) {
                ProfileMenuController.changePassword(newPassword.getText());
                root.getChildren().remove(changePasswordVbox);
            }
        });
        cancel.setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(changePasswordVbox);
        });
    }

    private void setErrorListener(TextField password, HBox passwordHBox) {
        password.textProperty().addListener((observableValue, s, t1) -> {
            if (!UserController.checkPasswordFormat(t1)) {
                Errors.PASSWORD_ERROR.getErrorLabel().setText("password is weak!");
                if (passwordHBox.getChildren().size() == 2)
                    passwordHBox.getChildren().add(Errors.PASSWORD_ERROR.getErrorLabel());
            } else {
                passwordHBox.getChildren().remove(Errors.PASSWORD_ERROR.getErrorLabel());
            }
        });
    }

}
