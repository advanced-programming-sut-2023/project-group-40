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
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.enums.Commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

public class ProfileMenu extends Application {
    private Pane root;
    private final String EMPTY_SLOGAN = "slogan is empty!";
    private Bounds usernameBounds;
    private TextField username, newPassword, oldPassword, nickname, email, slogan;
    private VBox profileMenuVbox;
    private HBox usernameHBox, nicknameHBox, emailHBox, sloganHBox, buttonHBox, oldPasswordHBox, newPasswordHBox;
    private final Button save = new Button("save");
    private Label passwordLabel;
    private Stage primaryStage;
    private final CheckBox sloganCheckBox = new CheckBox("show slogan");
    private final Button changePasswordButton = new Button("change password");
    private VBox changePasswordVbox;
    private final ImageView avatar = new ImageView(new Image(ProfileMenuController.getCurrentUser().getAvatarPath(), 100, 100, false, false));

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(RegisterMenu.class.getResource("/images/backgrounds/profileMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image,null,null,null,new BackgroundSize(App.getWidth(),App.getHeight(),false,false
                ,true,true))));
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
        ArrayList<ImageView> imageViews = new ArrayList<>();
        avatar.setTranslateX(App.getWidth() / 50);
        avatar.setTranslateY(App.getHeight() / 50);
        root.getChildren().add(avatar);
        final VBox[] avatarBox = {new VBox()};
        avatar.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> {
            if (avatarBox[0].getChildren().size() != 0) return;
            avatarBox[0].setSpacing(10);
            avatarBox[0].setTranslateX(avatar.getTranslateX());
            avatarBox[0].setTranslateY(avatar.getTranslateY() + 120);
            HBox firstRowHbox = new HBox();
            for (int i = 1; i <= 5; i++) {
                ImageView imageView = new ImageView(new Image(ProfileMenu.class.getResource("/avatars/" + i + ".png").toString(), 30, 30, true, true, true));
                imageViews.add(imageView);
                firstRowHbox.getChildren().add(imageView);
            }
            HBox secondRowHbox = new HBox();
            for (int i = 6; i <= 10; i++) {
                ImageView imageView = new ImageView(new Image(ProfileMenu.class.getResource("/avatars/" + i + ".png").toString(), 30, 30, true, true, true));
                imageViews.add(imageView);
                secondRowHbox.getChildren().add(imageView);
            }
            for (ImageView imageView : imageViews) {
                imageView.setOnMouseClicked(mouseEvent2 -> {
                    avatar.setImage(new Image(imageView.getImage().getUrl(),100,100,true,true));
                    ProfileMenuController.changeAvatar(imageView.getImage().getUrl());
                });
            }
            avatarBox[0].getChildren().addAll(firstRowHbox, secondRowHbox);
            avatarBox[0].getStylesheets().add(ProfileMenu.class.getResource("/css/changePasswordBoxStyle.css").toString());
            root.getChildren().add(avatarBox[0]);
        });
        avatarBox[0].addEventFilter(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            root.getChildren().remove(avatarBox[0]);
            avatarBox[0] = new VBox();
        });
        avatar.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file == null) return;
            avatar.setImage(new Image(file.getAbsolutePath(),100,100,true,true));
            ProfileMenuController.changeAvatar(file.getAbsolutePath());
        });
        avatar.setOnDragDropped(dragEvent -> {
            List<File> files = dragEvent.getDragboard().getFiles();
            try {
                avatar.setImage(new Image(new FileInputStream(files.get(0)),100,100,true,true));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            dragEvent.consume();
        });
        avatar.setOnDragOver(dragEvent -> {
            if (dragEvent.getDragboard().hasFiles()) {
                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
            dragEvent.consume();
        });
    }

    private void setupBoxes() {
        usernameHBox = new HBox(new Label("username :"), username);
        nicknameHBox = new HBox(new Label("nickname :"), nickname);
        emailHBox = new HBox(new Label("email :"), email);
        sloganHBox = new HBox(sloganCheckBox);
        CaptchaController.setUpCaptcha();
        buttonHBox = new HBox(save, changePasswordButton);
        save.setVisible(false);
        profileMenuVbox.getChildren().addAll(usernameHBox, emailHBox, nicknameHBox, sloganHBox, buttonHBox);
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
        profileMenuVbox.translateXProperty().bind(profileMenuVbox.widthProperty().divide(-2).add(App.getWidth()/2));
        profileMenuVbox.translateYProperty().bind(profileMenuVbox.heightProperty().divide(-2).add(App.getHeight()/2));
        profileMenuVbox.setSpacing(App.getHeight() / 20);
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
                if (usernameHBox.getChildren().size() == 2)
                    usernameHBox.getChildren().add(Errors.USERNAME_ERROR.getErrorLabel());
            } else usernameHBox.getChildren().remove(Errors.USERNAME_ERROR.getErrorLabel());
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
                sloganHBox.getChildren().addAll(new Label("slogan :"), slogan);
            }
        });
        save.setOnMouseClicked(mouseEvent -> {
            TextFieldController.checkExistUsername(usernameHBox, username);
            TextFieldController.checkEmail(emailHBox, email);
            TextFieldController.checkNickname(nicknameHBox, nickname);
            if (TextFieldController.isSuccessful()) {
                ProfileMenuController.changeUsername(username.getText());
                ProfileMenuController.changeNickname(nickname.getText());
                ProfileMenuController.changeEmail(email.getText());
                if (!slogan.getText().equals(EMPTY_SLOGAN))
                    ProfileMenuController.changeSlogan(slogan.getText());
            }
        });
        changePasswordButton.setOnMouseClicked(mouseEvent -> {
            changePasswordVbox = new VBox();
            changePasswordVbox.setSpacing(10);
            oldPasswordHBox = new HBox(new Label("old password : "), oldPassword);
            newPasswordHBox = new HBox(new Label("new password : "), newPassword);
            changePasswordVbox.translateXProperty().bind(changePasswordVbox.widthProperty().divide(-2).add(App.getWidth() / 2));
            changePasswordVbox.translateYProperty().bind(changePasswordVbox.heightProperty().divide(-2).add(App.getHeight() / 2));
            changePasswordVbox.getStylesheets().add(ProfileMenu.class.getResource("/css/changePasswordBoxStyle.css").toString());
            CaptchaController.getImageHBox().setPrefWidth(240);
            Button submit = new Button("submit");
            Button cancel = new Button("cancel");
            HBox buttons = new HBox(submit, cancel);
            buttons.translateXProperty().bind(Bindings.add(changePasswordVbox.widthProperty().divide(2), submit.widthProperty().divide(-1)).add(-10));
            changePasswordVbox.getChildren().addAll(oldPasswordHBox, newPasswordHBox, CaptchaController.getCaptchaHBox(), buttons);
            setChangePasswordButtonActions(submit, cancel);
            root.getChildren().add(changePasswordVbox);
        });
    }

    private void setChangePasswordButtonActions(Button submit, Button cancel) {
        setErrorListener(newPassword, newPasswordHBox);
        submit.setOnMouseClicked(mouseEvent -> {
            TextFieldController.setSuccessful(true);
            CaptchaController.checkCaptcha();
            TextFieldController.checkPassword(newPasswordHBox, newPassword, oldPasswordHBox, oldPassword);
            if (TextFieldController.isSuccessful()) {
                ProfileMenuController.changePassword(newPassword.getText());
                root.getChildren().remove(changePasswordVbox);
                changePasswordVbox.getChildren().clear();
                newPassword.clear();
                oldPassword.clear();
                CaptchaController.getCaptchaAnswerTextField().clear();
            }
        });
        cancel.setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(changePasswordVbox);
            newPassword.clear();
            oldPassword.clear();
            CaptchaController.getCaptchaAnswerTextField().clear();
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
