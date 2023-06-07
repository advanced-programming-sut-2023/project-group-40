package view;

import controller.EnvironmentMenuController;
import controller.GameMenuController;
import controller.MainMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoublePropertyBase;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class EnvironmentMenu extends Application {
    private final int MAP_SIZE = Map.getSize();
    private final int countOfPlayers = GameMenuController.getNumberOfPlayers();
    private final ImageView[][] map = new ImageView[MAP_SIZE][MAP_SIZE];
    private final ArrayList<ImageView> images = new ArrayList<>();
    private final ArrayList<Line> borderLines = new ArrayList<>();
    private final SimpleDoubleProperty textureSize = new SimpleDoubleProperty();
    private final SimpleDoubleProperty deltaX = new SimpleDoubleProperty();
    private final SimpleDoubleProperty deltaY = new SimpleDoubleProperty();
    private String scrollPaneContent = "texture";
    private Pane root;
    private AnchorPane mapPane;
    private double defaultTextureSize;
    private int hoverX;
    private int hoverY;
    private VBox showDetailsBox = new VBox();
    private final Timeline hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> showDetails()));
    private Cell selectedCell;

    @Override
    public void start(Stage stage) throws Exception {
        root = new Pane();
        mapPane = new AnchorPane();
        root.getChildren().add(mapPane);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(LoginMenu.class.getResource("/css/mapMenu.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        App.setupStage(stage);
        App.setWindowSize(stage.getWidth(), stage.getHeight());

        defaultTextureSize = App.getWidth() / 50;
        textureSize.set(defaultTextureSize);
        Map.initMap(MAP_SIZE);
        mapPane.setMaxWidth(App.getWidth());
        mapPane.setMaxHeight(App.getHeight() - 170);
        mapPane.setPrefWidth(App.getWidth());
        mapPane.setPrefHeight(App.getHeight() - 170);
        setupMap();
        EnvironmentMenuController.setMap(map);
        EnvironmentMenuController.setMapPane(mapPane);
        EnvironmentMenuController.setTextureSize(textureSize);
        EnvironmentMenuController.organizeCastles(countOfPlayers, MAP_SIZE);
        HBox hBox = setUpBuildingScrollPane();
        updateScrollPane(hBox);
    }

    private void updateScrollPane(HBox hBox) {
        root.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.T) {
                scrollPaneContent = "tree";
                try {
                    hBox.getChildren().clear();
                    updateScrollPanePicture(hBox, Tree.class);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }
            if (keyEvent.getCode() == KeyCode.R) {
                scrollPaneContent = "rock";
                try {
                    hBox.getChildren().clear();
                    updateScrollPanePicture(hBox, Rock.class);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }
            if (keyEvent.getCode() == KeyCode.C) {
                scrollPaneContent = "texture";
                try {
                    hBox.getChildren().clear();
                    updateScrollPanePicture(hBox, Texture.class);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private HBox setUpBuildingScrollPane() {
        ScrollPane pane = new ScrollPane();
        pane.requestFocus();
        pane.getStylesheets().add(EnvironmentMenu.class.getResource("/css/scrollPane.css").toString());
        pane.translateYProperty().bind(pane.heightProperty().multiply(-1).add(App.getHeight()));
        pane.setMinHeight(170);
        pane.setMinWidth(App.getWidth() / 2);
        pane.setMaxWidth(App.getWidth() / 2);
        Image image = new Image(EnvironmentMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                , true, false))));
        hBox.setSpacing(20);
        try {
            updateScrollPanePicture(hBox, Texture.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        pane.setContent(hBox);
        root.getChildren().add(pane);
        return hBox;
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
        setupCells();
        handleMoveOnMap();
        handleMouseHoverOnCell();
        handleSelectCell();
        handleZoom();
    }

    private void handleZoom() {
        KeyCodeCombination zoomInCombination = new KeyCodeCombination(KeyCode.EQUALS, KeyCodeCombination.CONTROL_DOWN);
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (textureSize.get() / defaultTextureSize < 3)
                if (zoomInCombination.match(event)) zoom(10);
        });
        KeyCodeCombination zoomOutCombination = new KeyCodeCombination(KeyCode.MINUS, KeyCodeCombination.CONTROL_DOWN);
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (zoomOutCombination.match(event))
                if (textureSize.get() / defaultTextureSize > 1 / 3.0) {
                    zoom(-10);
                    if (!canZoom()) zoom(100 / 9.0);
                }
        });
    }

    private void updateScrollPanePicture(HBox hBox, Class<?> aClass) throws ReflectiveOperationException {
        Object arrayObject = aClass.getMethod("values").invoke(null);
        for (int i = 0; i < Array.getLength(arrayObject); i++) {
            ImageView imageView = new ImageView(
                    aClass == Texture.class ? ((Texture) Array.get(arrayObject, i)).getImage()
                            : aClass == Tree.class ? ((Tree) Array.get(arrayObject, i)).getImage()
                            : ((Rock) Array.get(arrayObject, i)).getImage());
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(80);
            imageView.addEventFilter(MouseDragEvent.DRAG_DETECTED, event -> {
                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(imageView.getImage());
                content.putString(imageView.getImage().getUrl());
                db.setContent(content);
                event.consume();
            });

            VBox vBox = new VBox();
            vBox.setSpacing(20);
            vBox.setStyle("-fx-background-color: transparent; -fx-padding: 10");
            vBox.setAlignment(Pos.CENTER);
            vBox.minHeightProperty().bind(hBox.minHeightProperty());
            Label label = new Label(aClass == Texture.class ? ((Texture) Array.get(arrayObject, i)).name().toLowerCase() :
                    aClass == Tree.class ? ((Tree) Array.get(arrayObject, i)).getName() :
                            ((Rock) Array.get(arrayObject, i)).getName());
            vBox.getChildren().addAll(imageView, label);
            hBox.getChildren().add(vBox);
        }
    }

    private void handleSelectCell() {
        mapPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            mapPane.requestFocus();
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                int indexI = (int) Math.ceil((event.getX() - map[0][0].getTranslateX()) / textureSize.get()) - 1;
                int indexJ = (int) Math.ceil((event.getY() - map[0][0].getTranslateY()) / textureSize.get()) - 1;
                NumberBinding cornerLeftX, cornerRightX, cornerTopY, cornerBottomY;
                if (selectedCell != null && selectedCell != Map.getMap()[indexI][indexJ]) return;
                if (selectedCell != null) {
                    mapPane.getChildren().removeAll(borderLines);
                    selectedCell = null;
                    return;
                }

                cornerLeftX = Bindings.add(map[indexI][indexJ].translateXProperty(), 0);
                cornerRightX = Bindings.add(map[indexI][indexJ].translateXProperty()
                        , map[indexI][indexJ].fitWidthProperty());
                cornerTopY = Bindings.add(map[indexI][indexJ].translateYProperty(), 0);
                cornerBottomY = Bindings.add(map[indexI][indexJ].translateYProperty()
                        , map[indexI][indexJ].fitHeightProperty());
                selectedCell = Map.getMap()[indexI][indexJ];
                Line line1 = getLine(cornerLeftX, cornerTopY, cornerLeftX, cornerBottomY);
                Line line2 = getLine(cornerLeftX, cornerTopY, cornerRightX, cornerTopY);
                Line line3 = getLine(cornerRightX, cornerTopY, cornerRightX, cornerBottomY);
                Line line4 = getLine(cornerRightX, cornerBottomY, cornerLeftX, cornerBottomY);
                borderLines.addAll(List.of(line1, line2, line3, line4));
                mapPane.getChildren().addAll(line1, line2, line3, line4);
            }
        });
        mapPane.requestFocus();
        mapPane.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (selectedCell != null && keyEvent.getCode() == KeyCode.DELETE) {
                for (int i = 0; i < MAP_SIZE; i++)
                    for (int j = 0; j < MAP_SIZE; j++)
                        if (Map.getMap()[i][j] == selectedCell) {
                            EnvironmentMenuController.clearBlock(i, j);
                            map[i][j].setImage(Texture.LAND.getImage());
                            for (ImageView tree : images) {
                                if (tree.getTranslateX() == map[i][j].getTranslateX() &&
                                        tree.getTranslateY() == map[i][j].getTranslateY()) {
                                    images.remove(tree);
                                    mapPane.getChildren().remove(tree);
                                    break;
                                }
                            }
                        }
            }
        });
    }

    private void handleMouseHoverOnCell() {
        mapPane.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, event -> {
            hoverTimeline.stop();
            mapPane.getChildren().remove(showDetailsBox);
        });
        mapPane.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
            double mouseX = event.getX(), mouseY = event.getY();
            double x = map[0][0].getTranslateX();
            double y = map[0][0].getTranslateY();
            hoverX = (int) Math.ceil((mouseX - x) / textureSize.get()) - 1;
            hoverY = (int) Math.ceil((mouseY - y) / textureSize.get()) - 1;
            hoverTimeline.play();
        });
    }

    private void handleMoveOnMap() {
        AtomicReference<Double> startX = new AtomicReference<>((double) 0);
        AtomicReference<Double> startY = new AtomicReference<>((double) 0);
        mapPane.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                startX.set(event.getX());
                startY.set(event.getY());
            }
        });
        mapPane.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                deltaX.set(deltaX.get() + event.getX() - startX.get());
                deltaY.set(deltaY.get() + event.getY() - startY.get());
                changeMapSight();
            }
        });
    }

    private void setupCells() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = new ImageView(Texture.LAND.getImage());
                if (i == j) {
                    map[i][j] = new ImageView(Texture.SEA.getImage());
                    Map.getMap()[i][j].setTexture(Texture.SEA);
                }
                handleDragAndDrop(i, j);
                map[i][j].fitHeightProperty().bind(textureSize);
                map[i][j].fitWidthProperty().bind(textureSize);
                map[i][j].translateXProperty().bind(Bindings.add(textureSize.multiply(i), deltaX));
                map[i][j].translateYProperty().bind(Bindings.add(textureSize.multiply(j), deltaY));
                mapPane.getChildren().add(map[i][j]);
            }
        }
    }

    private void handleDragAndDrop(int i, int j) {
        int finalI = i;
        int finalJ = j;
        map[i][j].addEventFilter(DragEvent.DRAG_OVER, event -> {
            if (event.getDragboard().hasImage()) {
                String url = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
                String[] split = url.split("/");
                String type = "";
                String result = split[split.length - 1].replaceAll("%20", " ");
                for (int k = 0; k < result.length(); k++) {
                    if (result.charAt(k) != '.')
                        type += result.charAt(k);
                    else break;
                }
                switch (scrollPaneContent) {
                    case "texture" -> {
                        if (EnvironmentMenuController.checkTexture(finalI, finalJ, Texture.getTextureByName(type)))
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        else event.acceptTransferModes(TransferMode.NONE);
                    }
                    case "tree" -> {
                        if (EnvironmentMenuController.checkDropTree(finalI, finalJ, type))
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        else event.acceptTransferModes(TransferMode.NONE);
                    }
                    default -> {
                        if (EnvironmentMenuController.checkDropRock(finalI, finalJ, Rock.getRock(type)))
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        else event.acceptTransferModes(TransferMode.NONE);
                    }
                }
            }
            event.consume();
        });
        map[i][j].addEventFilter(DragEvent.DRAG_DROPPED, event -> {
            if (event.getDragboard().hasImage()) {
                String url = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
                String[] split = url.split("/");
                String type = "";
                String result = split[split.length - 1].replaceAll("%20", " ");
                for (int k = 0; k < result.length(); k++) {
                    if (result.charAt(k) != '.')
                        type += result.charAt(k);
                    else break;
                }
                ImageView imageView = new ImageView(event.getDragboard().getImage());
                imageView.fitWidthProperty().bind(textureSize);
                imageView.fitHeightProperty().bind(textureSize);
                imageView.translateXProperty().bind(map[finalI][finalJ].translateXProperty());
                imageView.translateYProperty().bind(map[finalI][finalJ].translateYProperty());
                switch (scrollPaneContent) {
                    case "texture" -> {
                        map[i][j].setImage(imageView.getImage());
                        EnvironmentMenuController.setTexture(finalI, finalJ, Texture.getTextureByName(type));
                    }
                    case "tree" -> {
                        images.add(imageView);
                        mapPane.getChildren().add(imageView);
                        EnvironmentMenuController.dropTree(finalI, finalJ, type);
                    }
                    default -> {
                        images.add(imageView);
                        mapPane.getChildren().add(imageView);
                        EnvironmentMenuController.dropRock(finalI, finalJ, Rock.getRock(type));
                    }
                }
            }
            event.consume();
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
        textureHBox.getChildren().addAll(new Label("Texture: "), new Label(cell.getTexture().getName()));
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
            showDetailsBox.getChildren().add(treeHBox);
        }

        mapPane.getChildren().add(showDetailsBox);
        showDetailsBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 10 ; -fx-padding: 8");
        showDetailsBox.translateXProperty().bind(Bindings.min((hoverX + 1) * textureSize.get() + map[0][0].translateXProperty().get()
                , showDetailsBox.widthProperty().multiply(-1).add(App.getWidth()).add(-10)));

        showDetailsBox.translateYProperty().bind(Bindings.max((hoverY - 1) * textureSize.get() + map[0][0].translateYProperty().get()
                , showDetailsBox.heightProperty().add(-20)));
    }
}
