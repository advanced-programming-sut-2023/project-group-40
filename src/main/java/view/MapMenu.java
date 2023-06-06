package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Cell;
import model.Map;
import model.Texture;
import model.buildings.Buildings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MapMenu extends Application {
    private final ImageView[][] map = new ImageView[200][200];
    private final ArrayList<Line> borderLines = new ArrayList<>();
    private final SimpleDoubleProperty textureSize = new SimpleDoubleProperty();
    private final SimpleDoubleProperty deltaX = new SimpleDoubleProperty();
    private final SimpleDoubleProperty deltaY = new SimpleDoubleProperty();
    Pane root;
    private double defaultTextureSize;
    private Stage stage;
    private int hoverX;
    private int hoverY;
    private VBox showDetailsBox = new VBox();
    private final Timeline hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> showDetails()));
    private Cell selectedCell;

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
        defaultTextureSize = App.getWidth() / 50;
        textureSize.set(defaultTextureSize);
        Map.initMap(200);
        setupMap();
        setUpBuildingScrollPane();
    }

    private void setUpBuildingScrollPane() {
        ScrollPane pane = new ScrollPane();
        pane.getStylesheets().add(MapMenu.class.getResource("/css/scrollPane.css").toString());
        pane.translateYProperty().bind(pane.heightProperty().multiply(-1).add(App.getHeight()));
        pane.setMinHeight(200);
        pane.setMaxWidth(App.getWidth() / 2);
        Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                , false, false))));
        hBox.setSpacing(20);
        hBox.minHeightProperty().bind(pane.minHeightProperty());
        for (Buildings value : Buildings.values()) {
            ImageView buildingImageView = new ImageView(value.getBuildingImage());
            buildingImageView.setPreserveRatio(true);
            buildingImageView.setFitHeight(80);

            VBox vBox = new VBox();
            vBox.setSpacing(20);
            vBox.setStyle("-fx-background-color: transparent; -fx-padding: 10");
            vBox.setAlignment(Pos.CENTER);
            vBox.minHeightProperty().bind(hBox.minHeightProperty());
            Label label = new Label(value.getFullName());
            label.setMaxWidth(150);
            label.setWrapText(true);
            label.setTextAlignment(TextAlignment.CENTER);
            vBox.getChildren().addAll(buildingImageView, label);
            hBox.getChildren().add(vBox);
        }
        pane.setContent(hBox);
        root.getChildren().add(pane);
    }

    private boolean canZoom() {
        return !(map[map.length - 1][map[0].length - 1].getTranslateY() < App.getHeight()
                || map[map.length - 1][map[0].length - 1].getTranslateX() < App.getWidth()
                || map[0][0].getTranslateY() > 0 || map[0][0].getTranslateX() > 0);
    }

    private void changeMapSight() {
        if (map[map.length - 1][map[0].length - 1].getTranslateY() < App.getHeight())
            deltaY.set(deltaY.get() + App.getHeight() - map[map.length - 1][map[0].length - 1].getTranslateY());

        if (map[map.length - 1][map[0].length - 1].getTranslateX() < App.getWidth())
            deltaX.set(deltaX.get() + App.getWidth() - map[map.length - 1][map[0].length - 1].getTranslateX());

        if (map[0][0].getTranslateY() > 0) deltaY.set(-map[0][0].getTranslateY() + deltaY.get());
        if (map[0][0].getTranslateX() > 0) deltaX.set(-map[0][0].getTranslateX() + deltaX.get());

    }

    private void setupMap() {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                map[i][j] = new ImageView(Texture.LAND.getImage());
                if (i == j) {
                    map[i][j] = new ImageView(Texture.GRASS_LAND.getImage());
                }
                map[i][j].fitHeightProperty().bind(textureSize);
                map[i][j].fitWidthProperty().bind(textureSize);
                map[i][j].translateXProperty().bind(Bindings.add(textureSize.multiply(i), deltaX));
                map[i][j].translateYProperty().bind(Bindings.add(textureSize.multiply(j), deltaY));
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
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                deltaX.set(deltaX.get() + event.getX() - startX.get());
                deltaY.set(deltaY.get() + event.getY() - startY.get());
                changeMapSight();
            }
        });

        root.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> {
            hoverTimeline.stop();
            root.getChildren().remove(showDetailsBox);
        });

        root.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
            double mouseX = event.getX(), mouseY = event.getY();
            double x = map[0][0].getTranslateX();
            double y = map[0][0].getTranslateY();
            hoverX = (int) Math.ceil((mouseX - x) / textureSize.get()) - 1;
            hoverY = (int) Math.ceil((mouseY - y) / textureSize.get()) - 1;
            hoverTimeline.play();
        });
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                double mouseX = event.getX(), mouseY = event.getY();
                double x = map[0][0].getTranslateX();
                double y = map[0][0].getTranslateY();
                int indexI = (int) Math.ceil((mouseX - x) / textureSize.get()) - 1;
                int indexJ = (int) Math.ceil((mouseY - y) / textureSize.get()) - 1;
                if (selectedCell != null && selectedCell != Map.getMap()[indexI][indexJ]) return;
                if (selectedCell != null) {
                    for (Line borderLine : borderLines)
                        root.getChildren().remove(borderLine);
                    selectedCell = null;
                    return;
                }
                NumberBinding cornerLeftX = Bindings.add(map[indexI][indexJ].translateXProperty(), 0);
                NumberBinding cornerRightX = Bindings.add(map[indexI][indexJ].translateXProperty(), map[indexI][indexJ].fitWidthProperty());
                NumberBinding cornerTopY = Bindings.add(map[indexI][indexJ].translateYProperty(), 0);
                NumberBinding cornerBottomY = Bindings.add(map[indexI][indexJ].translateYProperty(), map[indexI][indexJ].fitHeightProperty());
                Line line1 = getLine(cornerLeftX, cornerTopY, cornerLeftX, cornerBottomY);
                Line line2 = getLine(cornerLeftX, cornerTopY, cornerRightX, cornerTopY);
                Line line3 = getLine(cornerRightX, cornerTopY, cornerRightX, cornerBottomY);
                Line line4 = getLine(cornerRightX, cornerBottomY, cornerLeftX, cornerBottomY);
                borderLines.addAll(List.of(line1, line2, line3, line4));

                root.getChildren().addAll(line1, line2, line3, line4);
                selectedCell = Map.getMap()[indexI][indexJ];
            }
        });

        KeyCodeCombination zoomInCombination = new KeyCodeCombination(KeyCode.EQUALS, KeyCodeCombination.CONTROL_DOWN);
        root.requestFocus();
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (textureSize.get()/defaultTextureSize < 3)
                if (zoomInCombination.match(event)) zoom(10);
        });

        KeyCodeCombination zoomOutCombination = new KeyCodeCombination(KeyCode.MINUS, KeyCodeCombination.CONTROL_DOWN);
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (zoomOutCombination.match(event))
                if (textureSize.get()/defaultTextureSize > 1/3.0) {
                    zoom(-10);
                    if (!canZoom()) zoom(100 / 9.0);
                }
        });
    }

    private void zoom(double percentage) {
        textureSize.set(textureSize.get() * (100 + percentage) / 100);
    }

    private Line getLine(NumberBinding startX, NumberBinding startY, NumberBinding endX, NumberBinding endY) {
        Line line = new Line();
        line.startXProperty().bind(startX);
        line.startYProperty().bind(startY);
        line.endXProperty().bind(endX);
        line.endYProperty().bind(endY);
        return line;
    }

    private void showDetails() {
        showDetailsBox = new VBox();
        Cell cell = Map.getMap()[hoverX][hoverY];
        HBox textureHBox = new HBox();
        textureHBox.setSpacing(10);
        textureHBox.setStyle("-fx-text-fill: white");
        textureHBox.getChildren().addAll(new Label("Texture: "), new Label(cell.getTexture().name().toLowerCase()));
        showDetailsBox.getChildren().add(textureHBox);
        if (cell.getBuilding() != null) {
            HBox buildingHBox = new HBox();
            buildingHBox.setSpacing(10);
            buildingHBox.setStyle("-fx-text-fill: white");
            buildingHBox.getChildren().addAll(new Label("Building: "), new Label(cell.getBuilding().getName()));
            showDetailsBox.getChildren().add(buildingHBox);
        }
        if (cell.getUnit() != null) {
            HBox unitTypeHBox = new HBox();
            unitTypeHBox.setSpacing(10);
            unitTypeHBox.getChildren().addAll(new Label("unit type: "), new Label(cell.getUnit().getType()));
            HBox unitSizeHBox = new HBox();
            unitSizeHBox.setSpacing(10);
            unitSizeHBox.getChildren().addAll(new Label("unit size: "), new Label(String.valueOf(cell.getUnit().getTroops().size())));
            HBox unitPowerHBox = new HBox();
            unitPowerHBox.setSpacing(10);
            unitPowerHBox.getChildren().addAll(new Label("unit power: "), new Label(String.valueOf(cell.getUnit().getPower())));
            HBox unitHealthHBox = new HBox();
            unitHealthHBox.setSpacing(10);
            unitHealthHBox.getChildren().addAll(new Label("unit health: "), new Label(String.valueOf(cell.getUnit().getHp())));
            showDetailsBox.getChildren().addAll(unitTypeHBox, unitPowerHBox, unitSizeHBox, unitHealthHBox);
        }
        if (cell.getTree() != null) {
            HBox treeHBox = new HBox();
            treeHBox.setSpacing(10);
            treeHBox.getChildren().addAll(new Label("tree type: "), new Label(cell.getTree().name().toLowerCase()));
        }
        root.getChildren().add(showDetailsBox);
        showDetailsBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 10 ; -fx-padding: 8");
        System.out.println(hoverX);
        System.out.println(hoverY);
        showDetailsBox.translateXProperty().bind(Bindings.min((hoverX + 1) * textureSize.get() + map[0][0].translateXProperty().get()
                , showDetailsBox.widthProperty().multiply(-1).add(App.getWidth()).add(-10)));

        showDetailsBox.translateYProperty().bind(Bindings.max((hoverY - 1) * textureSize.get() + map[0][0].translateYProperty().get()
                , showDetailsBox.heightProperty().add(-20)));
    }


}
