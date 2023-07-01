package view;

import controller.ChatController;
import controller.ConnectToServer;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.ChatType;
import model.Message;
import model.PrivateUser;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Room extends Application {
    private static Stage primaryStage;
    private final Button enterButton = new Button("enter");
    private final Button newButton = new Button("new");
    private final TextField roomNameTextField = new TextField();
    private final VBox mainVbox = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(mainVbox);
    private final VBox vBox = new VBox(scrollPane, roomNameTextField, enterButton, newButton);
    private Pane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Room.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(MainMenu.class.getResource("/images/backgrounds/loginMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(MainMenu.class.getResource("/css/room.css")).toExternalForm());
        vBox.setSpacing(40);
        vBox.setAlignment(Pos.CENTER);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setSpacing(20);
        vBox.translateXProperty().bind(Bindings.add(App.getWidth() / 2, vBox.widthProperty().multiply(-0.5)));
        vBox.translateYProperty().bind(Bindings.add(App.getHeight() / 2, vBox.heightProperty().multiply(-0.5)));
        root.getChildren().add(vBox);
        scrollPane.setMaxHeight(300);
        scrollPane.setMinWidth(200);
        scrollPane.setStyle("-fx-background-color: transparent");
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        setupList();
        setActions();

    }

    private void setupList() {
        List<PrivateUser> users = ConnectToServer.getUsers();
        for (PrivateUser user : users) {
            if (user.getUsername().equals(MainMenuController.getCurrentUser().getUsername())) continue;
            Label label = new Label(user.getUsername());
            label.setStyle("-fx-text-fill: white");
            HBox hBox = new HBox(label, new CheckBox());
            hBox.setSpacing(40);
            hBox.setAlignment(Pos.CENTER);
            mainVbox.getChildren().add(hBox);
        }
    }

    private void setActions() {
        enterButton.setOnMouseClicked(event -> {
            if (scrollPane.isVisible()) {
                scrollPane.setVisible(false);
                roomNameTextField.setText("");
            } else {
                ChatController.fetchChat(ChatType.ONLY_FETCH, "");
                Message m = Message.getMessages().stream().filter(message ->
                        message.getRoomName().equals(roomNameTextField.getText())).findFirst().orElse(null);
                if (m != null && m.getAvailableTo().contains(MainMenuController.getCurrentUser().getUsername())) {
                    try {
                        new Chat(ChatType.ROOM, m.getRoomName(), m.getAvailableTo()).start(primaryStage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else
                    new ErrorDialog(root, "no room | no access").make();
            }
        });

        newButton.setOnMouseClicked(event -> {
            if (!scrollPane.isVisible()) {
                scrollPane.setVisible(true);
                roomNameTextField.setText("");
            } else {
                ChatController.fetchChat(ChatType.ONLY_FETCH, "");
                Message m = Message.getMessages().stream().filter(message ->
                        message.getRoomName().equals(roomNameTextField.getText())).findFirst().orElse(null);
                if (m == null) {
                    HashSet<String> available = new HashSet<>();
                    for (Node child : mainVbox.getChildren())
                        if (child instanceof HBox hBox)
                            if (((CheckBox) hBox.getChildren().get(1)).isSelected())
                                available.add(((Label) hBox.getChildren().get(0)).getText());
                    available.add(MainMenuController.getCurrentUser().getUsername());
                    try {
                        new Chat(ChatType.ROOM, roomNameTextField.getText(), available).start(primaryStage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else
                    new ErrorDialog(root, "name exists").make();
            }
        });
    }
}
