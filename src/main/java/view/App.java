package view;

import controller.UserController;
import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;


public class App extends Application {
    private static double width,height;
    @Override
    public void start(Stage primaryStage) throws Exception {
        UserController.fetchDatabase();
        setupStage(primaryStage);
        new MapMenu().start(primaryStage);
    }

    public static void setupStage(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public static void setWindowSize(double width, double height) {
        App.width = width;
        App.height = height;
    }

    public static double getHeight() {
        return height;
    }

    public static double getWidth() {
        return width;
    }
}
