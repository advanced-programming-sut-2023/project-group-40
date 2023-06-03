package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Cell;
import model.Map;
import model.Texture;
import model.Unit;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MapMenu extends Application {
    private final ImageView[][] map = new ImageView[200][200];
    Pane root;
    double textureSize;
    private Stage stage;
    private int hoverX;
    private int hoverY;
    private final Timeline hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> showDetails()));
    private VBox showDetailsBox = new VBox();
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(LoginMenu.class.getResource("/css/mapMenu.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        App.setupStage(stage);
        App.setWindowSize(stage.getWidth(), stage.getHeight());

        textureSize = stage.getScene().getWidth() / 50;
        Map.initMap(200);
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
                map[i][j] = new ImageView(Texture.LAND_WITH_PEBBLES.getImage());
                map[i][j].setFitWidth(textureSize);
                map[i][j].setFitHeight(textureSize);
                Map.getMap()[i][j].setTexture(Texture.SEA);
                if (i == j) {
                    map[i][j] = new ImageView(Texture.PONE.getImage());
                    map[i][j].setFitWidth(textureSize);
                    map[i][j].setFitHeight(textureSize);
                    Map.getMap()[i][j].setTexture(Texture.BEACH);
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
            root.getChildren().remove(showDetailsBox);
        });

        root.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
            double mouseX = event.getX(), mouseY = event.getY();
            double x = map[0][0].getTranslateX();
            double y = map[0][0].getTranslateY();
            hoverX = (int) Math.ceil((mouseX - x) / textureSize);
            hoverY = (int) Math.ceil((mouseY - y) / textureSize);
            hoverTimeline.play();
        });

        root.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                double mouseX = event.getX(), mouseY = event.getY();
                double x = map[0][0].getTranslateX();
                double y = map[0][0].getTranslateY();
                int indexI = (int) Math.ceil((mouseX - x) / textureSize) - 1;
                int indexJ = (int) Math.ceil((mouseY - y) / textureSize) - 1;
                map[indexI][indexJ].setStyle("-fx-background-insets : 0 0 0 0 ; -fx-border-style: solid solid solid solid; -fx-border-color: white ; -fx-border-radius: 30");
            }
        });
    }

    private void showDetails() {
        showDetailsBox = new VBox();
        Cell cell = Map.getMap()[hoverX][hoverY];
        HBox textureHBox = new HBox();
        textureHBox.setSpacing(10);
        textureHBox.setStyle("-fx-text-fill: white");
        textureHBox.getChildren().addAll(new Label("Texture: "),new Label(cell.getTexture().name().toLowerCase()));
        showDetailsBox.getChildren().add(textureHBox);
        if (cell.getBuilding() != null) {
            HBox buildingHBox = new HBox();
            buildingHBox.setSpacing(10);
            buildingHBox.setStyle("-fx-text-fill: white");
            buildingHBox.getChildren().addAll(new Label("Building: "),new Label(cell.getBuilding().getName()));
            showDetailsBox.getChildren().add(buildingHBox);;
        }
        if (cell.getUnit() != null) {
            HBox unitTypeHBox = new HBox();
            unitTypeHBox.setSpacing(10);
            unitTypeHBox.getChildren().addAll(new Label("unit type: "),new Label(cell.getUnit().getType()));
            HBox unitSizeHBox = new HBox();
            unitSizeHBox.setSpacing(10);
            unitSizeHBox.getChildren().addAll(new Label("unit size: "),new Label(String.valueOf(cell.getUnit().getTroops().size())));
            HBox unitPowerHBox = new HBox();
            unitPowerHBox.setSpacing(10);
            unitPowerHBox.getChildren().addAll(new Label("unit power: "),new Label(String.valueOf(cell.getUnit().getPower())));
            HBox unitHealthHBox = new HBox();
            unitHealthHBox.setSpacing(10);
            unitHealthHBox.getChildren().addAll(new Label("unit health: "),new Label(String.valueOf(cell.getUnit().getHp())));
            showDetailsBox.getChildren().addAll(unitTypeHBox,unitPowerHBox,unitSizeHBox,unitHealthHBox);
        }
        if (cell.getTree() != null) {
            HBox treeHBox = new HBox();
            treeHBox.setSpacing(10);
            treeHBox.getChildren().addAll(new Label("tree type: "),new Label(cell.getTree().name().toLowerCase()));
        }
        root.getChildren().add(showDetailsBox);
        showDetailsBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 10 ; -fx-padding: 8");
        showDetailsBox.setTranslateX(hoverX * textureSize);
        showDetailsBox.setTranslateY(hoverY * textureSize);
//        showDetailsBox.translateXProperty().bind(showDetailsBox.widthProperty().multiply(-1).add(Math.min(hoverX * textureSize,App.getWidth())).add(-10));
//        showDetailsBox.translateYProperty().bind(showDetailsBox.heightProperty().multiply(-1).add(Math.min(hoverY * textureSize,App.getHeight())).add(-10));
    }


}
