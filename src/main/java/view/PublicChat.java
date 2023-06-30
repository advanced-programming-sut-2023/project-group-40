package view;

import controller.MainController;
import controller.MainMenuController;
import controller.ProfileMenuController;
import controller.PublicChatController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PublicChat extends Application {
    private static Stage primaryStage;
    private final TextField newMessage = new TextField();
    private final Button sendMessage = new Button("send");
    private Pane root;
    private final VBox messagesVBox = new VBox();
    private final ScrollPane messagesScrollPane = new ScrollPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        PublicChat.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(MainMenu.class.getResource("/images/backgrounds/loginMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        messagesScrollPane.setContent(messagesVBox);
        messagesScrollPane.setMaxHeight(App.getHeight() - 150);
        messagesScrollPane.setMinWidth(App.getWidth());
        messagesScrollPane.setStyle("-fx-background-color: transparent");
        messagesVBox.setStyle("-fx-background-color: transparent");
        messagesVBox.setSpacing(35);
        root.getChildren().add(messagesScrollPane);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(MainMenu.class.getResource("/css/chat.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        updateMessages();
    }

    private void handleSendMessage() {
        sendMessage.setOnMouseClicked(event -> {

        });
    }

    private void updateMessages() {
        PublicChatController.updateChat();
        messagesVBox.getChildren().clear();
        Message.getMessages().forEach(message -> messagesVBox.getChildren().add(MessageBox.createBox(message)));
    }
}