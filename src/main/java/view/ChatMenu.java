package view;

import controller.MainMenuController;
import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

public class ChatMenu extends Application {
    private static Stage primaryStage;
    VBox vbox;
    private Pane root;
    private Button enterPublicChat, enterRoom, enterPrivateChat, back;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ChatMenu.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(MainMenu.class.getResource("/images/backgrounds/loginMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(MainMenu.class.getResource("/css/mainMenu.css")).toExternalForm());
        vbox = new VBox();
        enterPublicChat = new Button("public chat");
        enterRoom = new Button("rooms");
        enterPrivateChat = new Button("private chat");
        back = new Button("back");
        vbox.getChildren().addAll(enterPublicChat, enterRoom, enterPrivateChat, back);
        root.getChildren().add(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        setSizes();
        setActions();
    }

    private void setSizes() {
        vbox.translateXProperty().bind(vbox.widthProperty().divide(-2).add(App.getWidth() / 2));
        vbox.translateYProperty().bind(vbox.heightProperty().divide(-2).add(App.getHeight() / 2));
        vbox.setSpacing(App.getHeight() / 20);
    }

    private void setActions() {
        enterPublicChat.setOnMouseClicked(event -> {
            try {
                new PublicChat().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        enterRoom.setOnMouseClicked(event -> {
            try {
                new Room().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        enterPrivateChat.setOnMouseClicked(event -> {
            try {
                new PrivateChat().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        back.setOnMouseClicked(event -> {
            MainMenuController.setCurrentUser(null);
            try {
                new MainMenu().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
