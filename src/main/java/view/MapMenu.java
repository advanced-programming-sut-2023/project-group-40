package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MapMenu extends Application {
    private final ImageView[][] map = new ImageView[200][200];
    Pane root;
    Image image1;
    Image image2;
    double textureSize;
    private Stage stage;
    private int hoverX;
    private int hoverY;
    private final Timeline hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> showDetails()));


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        App.setupStage(stage);
        App.setWindowSize(stage.getWidth(), stage.getHeight());

        textureSize = stage.getScene().getWidth() / 50;

        image1 = new Image(MapMenu.class.getResource("/textures/land.jpg").toString(), textureSize, textureSize, false, true, true);
        image2 = new Image(MapMenu.class.getResource("/textures/grass.jpg").toString(), textureSize, textureSize, false, true, true);
        setupMap();
    }

    private void changeMapSight(double deltaX, double deltaY) {
        if (map[map.length - 1][map[0].length - 1].getTranslateY() + deltaY < App.getHeight())
            deltaY = App.getHeight() - map[map.length - 1][map[0].length - 1].getTranslateY();

        if (map[map.length - 1][map[0].length - 1].getTranslateX() + deltaX < App.getWidth())
            deltaX = App.getWidth() - map[map.length - 1][map[0].length - 1].getTranslateX();

        if (map[0][0].getTranslateY() + deltaY > 0) deltaY = -map[0][0].getTranslateY();
        if (map[0][0].getTranslateX() + deltaX > 0) deltaX = -map[0][0].getTranslateX();

        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                map[i][j].setTranslateX(map[i][j].getTranslateX() + deltaX);
                map[i][j].setTranslateY(map[i][j].getTranslateY() + deltaY);
            }
        }
    }

    private void setupMap() {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                map[i][j] = new ImageView(image1);
                if (i == j) {
                    map[i][j] = new ImageView(image2);
                }
                map[i][j].setTranslateX((stage.getScene().getWidth() / 50) * i);
                map[i][j].setTranslateY((stage.getScene().getWidth() / 50) * j);
                root.getChildren().add(map[i][j]);

            }
        }
        AtomicReference<Double> startX = new AtomicReference<>((double) 0);
        AtomicReference<Double> startY = new AtomicReference<>((double) 0);
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                startX.set(event.getX());
                startY.set(event.getY());
            }
        });
        root.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY))
                changeMapSight(event.getX() - startX.get(), event.getY() - startY.get());
        });

        root.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> {
            hoverTimeline.stop();
        });

        root.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
            double mouseX = event.getX(), mouseY = event.getY();
            double x = map[0][0].getTranslateX();
            double y = map[0][0].getTranslateY();
            hoverX = (int) Math.ceil((mouseX - x) / textureSize);
            hoverY = (int) Math.ceil((mouseY - y) / textureSize);
            hoverTimeline.play();
        });
    }

    private void showDetails() {
        System.out.println(map[hoverX][hoverY].getImage().getUrl());
    }


}
