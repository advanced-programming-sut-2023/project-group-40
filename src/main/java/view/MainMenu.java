package view;

import controller.GameMenuController;
import controller.MainMenuController;
import controller.ProfileMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Government;
import model.User;

import java.util.Objects;

public class MainMenu extends Application {
    private static Stage primaryStage;
    private static boolean gameStarted = false;

    VBox vbox;
    private Pane root;
    private Button startNewGame, continueGame, enterProfileMenu, enterChatMenu, logout;

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
        enterChatMenu = new Button("chat menu");
        logout = new Button("logout");
        vbox.getChildren().addAll(startNewGame, continueGame, enterProfileMenu, enterChatMenu, logout);
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
            User user = MainMenuController.getCurrentUser();
            GameMenuController.setCurrentGovernment(Government.getGovernmentByUser(user.getUsername()));
            try {
                if (Government.getPlayedGovernments().contains(GameMenuController.getCurrentGovernment())) {
                    GameMenuController.setCurrentGovernment(null);
                    return;
                }
                if (gameStarted) new MapMenu().start(primaryStage);
                else new EnvironmentMenu().start(primaryStage);
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
        enterChatMenu.setOnMouseClicked(event -> {
            try {
                new ChatMenu().start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
    public static void setGameStarted(boolean b) {
        MainMenu.gameStarted = b;
    }
}