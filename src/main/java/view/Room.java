package view;

import controller.ConnectToServer;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.PrivateUser;

import java.util.List;
import java.util.Objects;

public class Room extends Application {
    private static Stage primaryStage;
    private final Button enterButton = new Button("enter");
    private final Button newButton = new Button("new");
    private final TextField roomNameTextField = new TextField();
    private final VBox mainVbox = new VBox();
    private final ScrollPane scrollPane = new ScrollPane(mainVbox);
    private final VBox vBox = new VBox(scrollPane, enterButton, newButton);
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
        vBox.translateXProperty().bind(Bindings.add(App.getWidth()/2,vBox.widthProperty().multiply(-1)));
        vBox.setTranslateY(App.getHeight() / 2 - 100);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.setSpacing(20);
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
            HBox hBox = new HBox(new Label(user.getUsername()), new CheckBox());
            hBox.setAlignment(Pos.CENTER);
            mainVbox.getChildren().add(hBox);
        }
    }

    private void setActions() {
        enterButton.setOnMouseClicked(event -> {
            if (mainVbox.isVisible()) {
                mainVbox.setVisible(false);
                roomNameTextField.setText("");
            } else {

            }
        });

        newButton.setOnMouseClicked(event -> {
            if (!mainVbox.isVisible()) {
                mainVbox.setVisible(true);
                roomNameTextField.setText("");
            } else {

            }
        });
    }
}
