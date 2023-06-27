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

public class MainMenu extends Application {
    private static Stage primaryStage;
    VBox vbox;
    private Pane root;
    private Button startNewGame, continueGame, enterProfileMenu, logout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainMenu.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(MainMenu.class.getResource("/images/backgrounds/loginMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(MainMenu.class.getResource("/css/mainMenu.css")).toExternalForm());
        vbox = new VBox();
        startNewGame = new Button("start new game");
        continueGame = new Button("continue game");
        enterProfileMenu = new Button("profile menu");
        logout = new Button("logout");
        vbox.getChildren().addAll(startNewGame, continueGame, enterProfileMenu, logout);
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
        startNewGame.setOnMouseClicked(event -> {
            try {
                new SetupGameMenu().start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        continueGame.setOnMouseClicked(event -> {
            MainMenuController.continueGame();
            // TODO: 6/7/2023
            try {
                new MapMenu().start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        enterProfileMenu.setOnMouseClicked(event -> {
            ProfileMenuController.setCurrentUser(MainMenuController.getCurrentUser());
            try {
                new ProfileMenu().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        logout.setOnMouseClicked(event -> {
            MainMenuController.setCurrentUser(null);
            try {
                new LoginMenu().start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


}