package view;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    private static double width, height;

    public static void setupStage(Stage primaryStage) {
//        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
//        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        // UserController.fetchDatabase();
        setupStage(primaryStage);
        // new ShopMenu().start(primaryStage);
        new LoginMenu().start(primaryStage);
    }
}
