package view;

import controller.ChatController;
import controller.MainMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ChatType;
import model.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

public class Chat extends Application {
    private static Stage primaryStage;
    private final ChatType type;
    private final String roomName;
    private final TextField newMessageTextBox = new TextField();
    private final Button sendMessage = new Button("send");
    private final VBox messagesVBox = new VBox();
    private final ScrollPane messagesScrollPane = new ScrollPane();
    private final HBox sendMessageHBox = new HBox(newMessageTextBox, sendMessage);
    private final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
    private final Button deleteForMe = new Button("delete for me");
    private final Button deleteForAll = new Button("delete for all");
    private final Button edit = new Button("edit");
    private final Button refresh = new Button("refresh");
    private final Button back = new Button("back");
    private final HBox messageActionHBox = new HBox(deleteForMe, deleteForAll, edit, refresh, back);
    private final HashSet<String> availableTo;
    private Message selectedMessage;
    private HBox selectedHBox;
    private Pane root;
    private Timeline timeline;

    public Chat(ChatType type, String roomName, HashSet<String> availableTo) {
        this.type = type;
        this.roomName = roomName;
        this.availableTo = availableTo;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Chat.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(MainMenu.class.getResource("/images/backgrounds/loginMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        sendMessage.setId("sendButton");
        messagesScrollPane.setContent(messagesVBox);
        messagesScrollPane.setMaxHeight(App.getHeight() - 80);
        messagesScrollPane.setMinWidth(App.getWidth());
        messagesScrollPane.setStyle("-fx-background-color: transparent");
        messagesVBox.setStyle("-fx-background-color: transparent");
        messagesVBox.setSpacing(35);
        sendMessageHBox.setAlignment(Pos.CENTER);
        sendMessageHBox.setMinWidth(App.getWidth());
        sendMessageHBox.setSpacing(40);
        sendMessageHBox.setTranslateY(App.getHeight() - 60);
        messageActionHBox.setSpacing(40);
        messageActionHBox.setTranslateY(15);
        messageActionHBox.setTranslateX(600);
        root.getChildren().addAll(messagesScrollPane, sendMessageHBox, messageActionHBox);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(MainMenu.class.getResource("/css/chat.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());

        ChatController.fetchChat(type, roomName);
        updateMessages();
        timeline = new Timeline(new KeyFrame(Duration.seconds(10), actionEvent -> {
            ChatController.fetchChat(type, roomName);
            updateMessages();
        }));
        timeline.setCycleCount(-1);
        timeline.play();
        handleSendMessage();
        handleActions();
    }

    private void handleSendMessage() {
        sendMessage.setOnMouseClicked(event -> {
            Message message = new Message(type, roomName, newMessageTextBox.getText(), MainMenuController.getCurrentUser().getUsername(), formatter.format(new Date()), availableTo);
            Message.getMessages().add(message);
            ChatController.sendMessages();
            updateMessages();
            newMessageTextBox.setText("");
        });
    }

    private void updateMessages() {
        messagesVBox.getChildren().clear();
        Message.getMessages().forEach(message -> {
            if (type.equals(ChatType.ROOM) && !message.getRoomName().equals(roomName)) return;
            if (!message.getAvailableTo().equals(availableTo)) return;
            if (message.getSenderUsername().equals(MainMenuController.getCurrentUser().getUsername()) && message.isDeleteOnlyForSender())
                return;
            HBox box = MessageBox.createBox(message);
            box.setOnMouseClicked(event -> {
                if (selectedHBox != null)
                    selectedHBox.setStyle("-fx-background-color: rgba(255,255,255,0.55);-fx-background-radius: 10px");
                if (!message.getSenderUsername().equals(MainMenuController.getCurrentUser().getUsername())) return;
                selectedMessage = message;
                selectedHBox = box;
                box.setStyle("-fx-background-color: white;-fx-background-radius: 10px");
            });
            messagesVBox.getChildren().add(box);
        });
    }

    private void handleActions() {
        deleteForMe.setOnMouseClicked(event -> {
            if (selectedMessage != null) {
                selectedMessage.setDeleteOnlyForSender(true);
                messagesVBox.getChildren().remove(selectedHBox);
                ChatController.sendMessages();
            }
        });

        deleteForAll.setOnMouseClicked(event -> {
            if (selectedMessage != null) {
                Message.getMessages().remove(selectedMessage);
                messagesVBox.getChildren().remove(selectedHBox);
                ChatController.sendMessages();
            }
        });

        edit.setOnMouseClicked(event -> {
            if (selectedMessage != null) {
                newMessageTextBox.setText(selectedMessage.getText());
                sendMessage.setOnMouseClicked(event1 -> {
                    selectedMessage.setText(newMessageTextBox.getText());
                    ChatController.sendMessages();
                    updateMessages();
                    handleSendMessage();
                    newMessageTextBox.setText("");
                });
            }
        });

        refresh.setOnMouseClicked(event -> {
            ChatController.fetchChat(type, roomName);
            updateMessages();
        });

        back.setOnMouseClicked(event -> {
            try {
                timeline.stop();
                new ChatMenu().start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}