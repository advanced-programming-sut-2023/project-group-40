package view;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Castle;
import model.Color;
import model.Good;
import model.Government;
import model.buildings.Buildings;
import model.buildings.Storage;

import java.util.ArrayList;


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
        Government.setGovernments(ConnectToServer.getGovernments());
        GameMenuController.setCurrentGovernment(Government.getGovernments().get(0));
        for (Government government : Government.getGovernments())
            government.setBuildings(new ArrayList<>());
        for (Government government : Government.getGovernments())
            government.setCastle(new Castle(0,0,0,0));
        GameMenuController.getCurrentGovernment().getBuildings().add(Buildings.getBuildingObjectByType("stockpile"));
        GameMenuController.getCurrentGovernment().getBuildings().add(Buildings.getBuildingObjectByType("armoury"));
        GameMenuController.getCurrentGovernment().getBuildings().add(Buildings.getBuildingObjectByType("granary"));
        GameMenuController.getCurrentGovernment().increaseAmountOfGood(Good.GOLD, 200);
        GameMenuController.getCurrentGovernment().increaseAmountOfGood(Good.STONE, 200);
        GameMenuController.getCurrentGovernment().increaseAmountOfGood(Good.IRON, 200);
        GameMenuController.getCurrentGovernment().increaseAmountOfGood(Good.WOOD, 200);
        GameMenuController.getCurrentGovernment().increaseAmountOfGood(Good.PITCH, 200);
        GameMenuController.getCurrentGovernment().increaseAmountOfGood(Good.BOW, 10);


        new MapMenu().start(primaryStage);
//        new LoginMenu().start(primaryStage);
    }
}
