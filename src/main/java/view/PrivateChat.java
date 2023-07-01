package view;

import controller.ConnectToServer;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.ChatType;
import model.PrivateUser;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class PrivateChat extends Application {
    private static Stage primaryStage;
    private final Button enterButton = new Button("enter");
    private final ComboBox<String> userComboBox = new ComboBox<>();
    private final VBox vBox = new VBox(userComboBox, enterButton);
    private Pane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        PrivateChat.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(MainMenu.class.getResource("/images/backgrounds/loginMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(MainMenu.class.getResource("/css/mainMenu.css")).toExternalForm());
        vBox.setSpacing(40);
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(App.getWidth());
        vBox.setTranslateY(App.getHeight() / 2 - 100);
        root.getChildren().add(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        enterChat();
    }

    private void enterChat() {
        List<PrivateUser> users = ConnectToServer.getUsers();
        for (PrivateUser user : users) {
            if (user.getUsername().equals(MainMenuController.getCurrentUser().getUsername())) continue;
            userComboBox.getItems().add(user.getUsername());
            userComboBox.setValue(user.getUsername());
        }
        enterButton.setOnMouseClicked(event -> {
            HashSet<String> hashSet = new HashSet<>();
            hashSet.add(MainMenuController.getCurrentUser().getUsername());
            hashSet.add(userComboBox.getValue());
            try {
                new Chat(ChatType.PRIVATE,"", hashSet).start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
