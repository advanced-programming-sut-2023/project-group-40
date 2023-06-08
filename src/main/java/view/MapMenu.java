package view;

import controller.GameMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import model.buildings.Building;
import model.buildings.Buildings;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MapMenu extends Application {
    private final ArrayList<ImageView> buildings = new ArrayList<>();
    private final ArrayList<Line> borderLines = new ArrayList<>();
    private final SimpleDoubleProperty textureSize = new SimpleDoubleProperty();
    private final SimpleDoubleProperty deltaX = new SimpleDoubleProperty();
    private final SimpleDoubleProperty deltaY = new SimpleDoubleProperty();
    private final ArrayList<Cell> selectedCells = new ArrayList<>();
    private final int MAP_SIZE = 200;
    private final ImageView[][] map = new ImageView[MAP_SIZE][MAP_SIZE];
    Pane root;
    AnchorPane mapPane;
    private double defaultTextureSize;
    private int hoverX;
    private int hoverY;
    private VBox showDetailsBox = new VBox();
    private VBox showManyCellsDetailsBox = new VBox();
    private final Timeline hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> showDetails()));
    private Cell selectedCell;
    private int startIndexI, startIndexJ, endIndexI, endIndexJ;
    private Label popularityLabel, populationLabel, treasuryLabel;
    private Label fearRateLabel, taxRateLabel, foodRateLabel, sumRateLabel;
    private int SCROLL_PANE_HEIGHT = 200;
    private VBox clipBoardVbox;
    private Pane blackPane;

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
        mapPane.setMaxHeight(App.getHeight() - SCROLL_PANE_HEIGHT);
        mapPane.setPrefWidth(App.getWidth());
        mapPane.setPrefHeight(App.getHeight() - SCROLL_PANE_HEIGHT);
        setupMap();
        setUpBuildingScrollPane();
        setupScribeReport();
        handleNextTurn();
    }

    private void handleNextTurn() {
        root.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.N) {
                GameMenuController.nextTurn();
                updateSumLabel();
                updateLabel(GameMenuController.getCurrentGovernment().getFoodRate(), foodRateLabel);
                updateLabel(GameMenuController.getCurrentGovernment().getFearRate(), fearRateLabel);
                updateLabel(GameMenuController.getCurrentGovernment().getTaxRate(), taxRateLabel);
                updateScribeReport();
            }
        });
    }

    private void setupScribeReport() {
        foodRateLabel = new Label(" 0");
        fearRateLabel = new Label(" 0");
        taxRateLabel = new Label(" 0");
        sumRateLabel = new Label();
        VBox vBox = new VBox();
        vBox.translateYProperty().bind(vBox.heightProperty().divide(-1).add(App.getHeight()));
        vBox.translateXProperty().bind(vBox.widthProperty().divide(-1).add(App.getWidth()));
        vBox.setMaxWidth(100);
        vBox.setMinWidth(100);
        vBox.setMaxHeight(SCROLL_PANE_HEIGHT);
        vBox.setMinHeight(SCROLL_PANE_HEIGHT);
        vBox.setStyle("-fx-background-color: red");

        popularityLabel = new Label(String.valueOf(GameMenuController.getCurrentGovernment().getPopularity()));
        String population = String.valueOf(GameMenuController.getCurrentGovernment().getPopulation());
        String maxPopulation = String.valueOf(GameMenuController.getCurrentGovernment().getMaxPopulation());
        populationLabel = new Label(population + "/" + maxPopulation);
        treasuryLabel = new Label(String.valueOf(GameMenuController.getCurrentGovernment().getAmountOfGood(Good.GOLD)));
        popularityLabel.setMinWidth(vBox.getMinWidth());
        popularityLabel.setAlignment(Pos.CENTER);
        populationLabel.setMinWidth(vBox.getMinWidth());
        populationLabel.setAlignment(Pos.CENTER);
        popularityLabel.setOnMouseClicked(event -> {
            showPopularityFactors();
        });
        ImageView coinIcon = new ImageView(new Image(MapMenu.class.getResource("/images/coin.png").toString()));
        coinIcon.setPreserveRatio(true);
        coinIcon.fitHeightProperty().bind(treasuryLabel.heightProperty().divide(2));
        HBox hBox = new HBox(treasuryLabel, coinIcon);
        hBox.setSpacing(10);
        hBox.minWidth(vBox.getMinWidth());
        hBox.setMaxWidth(vBox.getMaxWidth());
        hBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(popularityLabel, populationLabel, hBox);
        root.getChildren().add(vBox);
    }

    private void showPopularityFactors() {
        VBox vBox = new VBox(new Label("Popularity Factors"));
        Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
        vBox.getStylesheets().add(MapMenu.class.getResource("/css/showFactors.css").toString());
        vBox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                , true, false))));
        vBox.setStyle("-fx-padding: 10");
        vBox.setMinWidth(500);
        vBox.setMaxWidth(500);
        vBox.setMinHeight(300);
        vBox.setMaxHeight(300);
        vBox.translateXProperty().bind(vBox.widthProperty().divide(-2).add(App.getWidth() / 2));
        vBox.setTranslateY(10);
        VBox labelVBox = new VBox(foodRateLabel, fearRateLabel, taxRateLabel);
        VBox textLabelVBox = new VBox(new Label("food rate"), new Label("fear rate"), new Label("tax rate"));
        foodRateLabel.setMinWidth(80);
        fearRateLabel.setMinWidth(80);
        taxRateLabel.setMinWidth(80);
        updateSumLabel();
        updateLabel(GameMenuController.getCurrentGovernment().getFoodRate(), foodRateLabel);
        updateLabel(GameMenuController.getCurrentGovernment().getFearRate(), fearRateLabel);
        updateLabel(GameMenuController.getCurrentGovernment().getTaxRate(), taxRateLabel);
        HBox sumHbox = new HBox(new Label("In The Coming Month"), sumRateLabel);
        sumHbox.setMinWidth(vBox.getMaxWidth());
        sumHbox.setAlignment(Pos.CENTER);
        sumHbox.setTranslateY(30);
        HBox hBox = new HBox();
        hBox.setMinWidth(vBox.getMinWidth());
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(labelVBox, textLabelVBox);
        vBox.getChildren().addAll(hBox, sumHbox);
        root.getChildren().add(vBox);
    }

    private void updateSumLabel() {
        int foodRate = GameMenuController.getCurrentGovernment().getFoodRate();
        int fearRate = GameMenuController.getCurrentGovernment().getFearRate();
        int taxRate = GameMenuController.getCurrentGovernment().getTaxRate();
        int preFoodRate = Integer.parseInt(foodRateLabel.getText().substring(1));
        int preFearRate = Integer.parseInt(fearRateLabel.getText().substring(1));
        int preTaxRate = Integer.parseInt(taxRateLabel.getText().substring(1));
        int sum = foodRate + fearRate + taxRate - preFoodRate - preTaxRate - preFearRate;
        updateLabel(sum, sumRateLabel);
    }

    private void updateLabel(int rate, Label label) {
        if (rate < 0) {
            label.setText(String.valueOf(rate));
            label.setStyle("-fx-text-fill: red");
        } else if (rate == 0) {
            label.setText(" 0");
            label.setStyle("-fx-text-fill: white");
        } else {
            label.setText("+" + rate);
            label.setStyle("text-emphasis: green;");
        }
    }

    private void updateScribeReport() {
        popularityLabel.setText(String.valueOf(GameMenuController.getCurrentGovernment().getPopularity()));
        String population = String.valueOf(GameMenuController.getCurrentGovernment().getPopulation());
        String maxPopulation = String.valueOf(GameMenuController.getCurrentGovernment().getMaxPopulation());
        populationLabel.setText(population + "/" + maxPopulation);
        treasuryLabel.setText(String.valueOf(GameMenuController.getCurrentGovernment().getAmountOfGood(Good.GOLD)));
    }


    private void setUpBuildingScrollPane() {
        ScrollPane pane = new ScrollPane();
        pane.getStylesheets().add(MapMenu.class.getResource("/css/scrollPane.css").toString());
        pane.translateYProperty().bind(pane.heightProperty().multiply(-1).add(App.getHeight()));
        pane.setMinHeight(SCROLL_PANE_HEIGHT);
        pane.setMaxWidth(App.getWidth() / 2);
        Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                , true, false))));
        hBox.setSpacing(20);
        for (Buildings value : Buildings.values()) {
            ImageView buildingImageView = new ImageView(value.getBuildingImage());
            buildingImageView.setPreserveRatio(true);
            buildingImageView.setFitHeight(80);
            buildingImageView.addEventFilter(MouseDragEvent.DRAG_DETECTED, event -> {
                Dragboard db = buildingImageView.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(buildingImageView.getImage());
                content.putString(buildingImageView.getImage().getUrl());
                db.setContent(content);
                event.consume();
            });

            VBox vBox = new VBox();
            vBox.setSpacing(20);
            vBox.setStyle("-fx-background-color: transparent; -fx-padding: 10");
            vBox.setAlignment(Pos.CENTER);
            vBox.minHeightProperty().bind(hBox.minHeightProperty());
            Label label = new Label(value.getFullName());
            label.setMaxWidth(150);
            label.setWrapText(true);
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
        setupCells();
        handleMoveOnMap();
        handleMouseHoverOnCell();
        handleSelectCell();
        handleZoom();
        handleCopyAndPaste();
    }

    private void handleCopyAndPaste() {
        KeyCodeCombination copyKeyCodeCombination = new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN);
        root.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (copyKeyCodeCombination.match(keyEvent)) {
                if (GameMenuController.getSelectedBuilding() == null) return;
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                Building building = GameMenuController.getSelectedBuilding();
                for (Buildings value : Buildings.values())
                    if (value.getFullName().equals(building.getName())) {
                        content.putString(value.getBuildingImage().getUrl());
                        break;
                    }
                clipboard.setContent(content);
            }
        });

        KeyCodeCombination pasteKeyCodeCombination = new KeyCodeCombination(KeyCode.V, KeyCodeCombination.CONTROL_DOWN);
        root.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (pasteKeyCodeCombination.match(keyEvent)) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                if (selectedCell == null) return;
                if (clipboard.hasString()) {
                    String url = clipboard.getString();
                    String[] split = url.split("/");
                    String result = split[split.length - 1].replaceAll("%20", " ");
                    String type = result.substring(0, result.length() - 4);
                    ImageView imageView = new ImageView(url.replaceAll("%20", " "));
                    buildings.add(imageView);
                    int width = Buildings.getBuildingObjectByType(type).getWidth();
                    int height = Buildings.getBuildingObjectByType(type).getHeight();
                    imageView.fitWidthProperty().bind(textureSize.multiply(width));
                    imageView.fitHeightProperty().bind(textureSize.multiply(height));
                    int finalI = 0, finalJ = 0;
                    for (int i = 0; i < Map.getMap().length; i++)
                        for (int j = 0; j < Map.getMap().length; j++)
                            if (selectedCell == Map.getMap()[i][j]) {
                                finalI = i;
                                finalJ = j;
                            }
                    imageView.translateXProperty().bind(map[finalI][finalJ].translateXProperty());
                    imageView.translateYProperty().bind(map[finalI][finalJ].translateYProperty());
                    if (GameMenuController.checkDropBuilding(finalI, finalJ, type)) {
                        mapPane.getChildren().add(imageView);
                        GameMenuController.dropBuilding(finalI, finalJ, type);
                        selectedCell = null;
                        mapPane.getChildren().removeAll(borderLines);
                        borderLines.clear();
                    } else {
                        for (Line borderLine : borderLines)
                            borderLine.setStyle("-fx-stroke: red");
                        new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
                            mapPane.getChildren().removeAll(borderLines);
                            borderLines.clear();
                            selectedCell = null;
                        })).play();
                    }
                }
            }
        });

        KeyCodeCombination showKeyCodeCombination = new KeyCodeCombination(KeyCode.P, KeyCodeCombination.CONTROL_DOWN);
        root.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            if (showKeyCodeCombination.match(keyEvent)) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                if (clipBoardVbox != null) {
                    root.getChildren().remove(blackPane);
                    root.getChildren().remove(clipBoardVbox);
                    clipBoardVbox = null;
                    return;
                }
                if (clipboard.hasString()) {
                    String url = clipboard.getString();
                    ImageView imageView = new ImageView(url.replaceAll("%20", " "));
                    clipBoardVbox = new VBox();
                    Label label = new Label("clipboard");
                    label.setMaxWidth(App.getWidth());
                    label.setAlignment(Pos.CENTER);
                    clipBoardVbox.getChildren().add(label);
                    clipBoardVbox.translateXProperty().bind(clipBoardVbox.widthProperty().divide(-2).add(App.getWidth() / 2));
                    clipBoardVbox.setTranslateY(20);
                    clipBoardVbox.getChildren().add(imageView);
                    blackPane = new Pane();
                    blackPane.setStyle("-fx-background-color: rgba(0,0,0,0.5)");
                    blackPane.setPrefWidth(App.getWidth());
                    blackPane.setPrefHeight(App.getHeight());
                    root.getChildren().add(blackPane);
                    root.getChildren().add(clipBoardVbox);
                }
            }
        });
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

    private void handleSelectCell() {
        mapPane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                double mouseX = event.getX(), mouseY = event.getY();
                double x = map[0][0].getTranslateX();
                double y = map[0][0].getTranslateY();
                int indexI = (int) Math.ceil((mouseX - x) / textureSize.get()) - 1;
                int indexJ = (int) Math.ceil((mouseY - y) / textureSize.get()) - 1;

                NumberBinding cornerLeftX, cornerRightX, cornerTopY, cornerBottomY;
                Building targetBuilding = Map.getMap()[indexI][indexJ].getBuilding();
                int buildingX, buildingY;
                if (targetBuilding != null) {
                    if (selectedCell != null) return;
                    if (GameMenuController.getSelectedBuilding() == targetBuilding) {
                        mapPane.getChildren().removeAll(borderLines);
                        GameMenuController.setSelectedBuilding(null);
                        return;
                    } else if (GameMenuController.getSelectedBuilding() != null) return;
                    buildingX = targetBuilding.getX1();
                    buildingY = targetBuilding.getY1();
                    cornerLeftX = Bindings.add(map[buildingX][buildingY].translateXProperty(), 0);
                    cornerRightX = Bindings.add(map[buildingX][buildingY].translateXProperty()
                            , map[buildingX][buildingX].fitWidthProperty().multiply(targetBuilding.getWidth()));
                    cornerTopY = Bindings.add(map[buildingX][buildingY].translateYProperty(), 0);
                    cornerBottomY = Bindings.add(map[buildingX][buildingY].translateYProperty()
                            , map[buildingX][buildingY].fitHeightProperty().multiply(targetBuilding.getHeight()));
                    GameMenuController.selectBuilding(buildingX, buildingY);

                } else {
                    if (GameMenuController.getSelectedBuilding() != null) return;

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
                }
                Line line1 = getLine(cornerLeftX, cornerTopY, cornerLeftX, cornerBottomY);
                Line line2 = getLine(cornerLeftX, cornerTopY, cornerRightX, cornerTopY);
                Line line3 = getLine(cornerRightX, cornerTopY, cornerRightX, cornerBottomY);
                Line line4 = getLine(cornerRightX, cornerBottomY, cornerLeftX, cornerBottomY);
                borderLines.addAll(List.of(line1, line2, line3, line4));

                mapPane.getChildren().addAll(line1, line2, line3, line4);
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
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                startX.set(event.getX());
                startY.set(event.getY());
            }
        });
        mapPane.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                deltaX.set(deltaX.get() + event.getX() - startX.get());
                deltaY.set(deltaY.get() + event.getY() - startY.get());
                changeMapSight();
            } else if (event.getButton().equals(MouseButton.SECONDARY)) {
                selectedCells.clear();
                showManyCellsDetailsBox.getChildren().clear();
                for (int i = Math.min(startIndexI, endIndexI); i <= Math.max(startIndexI, endIndexI); i++)
                    for (int j = Math.min(startIndexJ, endIndexJ); j <= Math.max(startIndexJ, endIndexJ); j++) {
                        map[i][j].setOpacity(1);
                    }
                if (selectedCell != null) return;

                double x = map[0][0].getTranslateX();
                double y = map[0][0].getTranslateY();
                startIndexI = (int) Math.ceil((startX.get() - x) / textureSize.get()) - 1;
                startIndexJ = (int) Math.ceil((startY.get() - y) / textureSize.get()) - 1;

                endIndexI = (int) Math.ceil((event.getX() - x) / textureSize.get()) - 1;
                endIndexJ = (int) Math.ceil((event.getY() - y) / textureSize.get()) - 1;

                for (int i = Math.min(startIndexI, endIndexI); i <= Math.max(startIndexI, endIndexI); i++)
                    for (int j = Math.min(startIndexJ, endIndexJ); j <= Math.max(startIndexJ, endIndexJ); j++) {
                        selectedCells.add(Map.getMap()[i][j]);
                        map[i][j].setOpacity(0.5);
                    }
                showDetailsOfManyCells();

            }
        });
    }

    private void showDetailsOfManyCells() {
        showManyCellsDetailsBox = new VBox();
        HashSet<Building> uniqueBuildings = new HashSet<>();
        for (Cell cell : selectedCells)
            if (cell.getBuilding() != null)
                uniqueBuildings.add(cell.getBuilding());
        int minProductRate = 0, maxProductRate = 0, sumOfProducts = 0, countOfProducts = 0;
        for (Building uniqueBuilding : uniqueBuildings) {
            try {
                Field field = uniqueBuilding.getClass().getDeclaredField("productRate");
                field.setAccessible(true);
                Integer productRate = (Integer) field.get(uniqueBuilding);
                if (minProductRate == 0) minProductRate = productRate;
                else if (productRate < minProductRate) minProductRate = productRate;
                if (productRate > maxProductRate) maxProductRate = productRate;
                sumOfProducts += productRate;
                countOfProducts++;
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        }
        HBox minProductRateHBox = new HBox();
        minProductRateHBox.setSpacing(10);
        minProductRateHBox.getChildren().addAll(new Label("min produce rate: "),
                new Label(String.valueOf(minProductRate)));
        showManyCellsDetailsBox.getChildren().add(minProductRateHBox);

        HBox maxProductRateHBox = new HBox();
        maxProductRateHBox.setSpacing(10);
        maxProductRateHBox.getChildren().addAll(new Label("max produce rate: "),
                new Label(String.valueOf(maxProductRate)));
        showManyCellsDetailsBox.getChildren().add(maxProductRateHBox);

        HBox averageProductRateHBox = new HBox();
        averageProductRateHBox.setSpacing(10);
        Double average = ((sumOfProducts + 0.0) / countOfProducts);
        if (countOfProducts == 0) average = 0.0;
        averageProductRateHBox.getChildren().addAll(new Label("average produce rate: "),
                new Label(String.format("%.2f", average)));
        showManyCellsDetailsBox.getChildren().add(averageProductRateHBox);
        showManyCellsDetailsBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 10 ; -fx-padding: 8");
        showManyCellsDetailsBox.translateXProperty().bind(showManyCellsDetailsBox.widthProperty().divide(-1).add(App.getWidth()));
        showManyCellsDetailsBox.setTranslateY(10);
        root.getChildren().add(showManyCellsDetailsBox);
    }

    private void setupCells() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                map[i][j] = new ImageView(Texture.DENSE_GRASS_LAND.getImage());
                Map.getMap()[i][j].setTexture(Texture.DENSE_GRASS_LAND);
                if (i == j) {
                    map[i][j] = new ImageView(Texture.SEA.getImage());
                    Map.getMap()[i][j].setTexture(Texture.SEA);
                }
                int finalI = i;
                int finalJ = j;
                map[i][j].addEventFilter(DragEvent.DRAG_OVER, event -> {
                    if (event.getDragboard().hasImage()) {
                        String url = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
                        String[] split = url.split("/");
                        String result = split[split.length - 1].replaceAll("%20", " ");
                        String type = result.substring(0, result.length() - 4);
                        boolean b = GameMenuController.checkDropBuilding(finalI, finalJ, type);
                        if (b)
                            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        else event.acceptTransferModes(TransferMode.NONE);
                    }
                    event.consume();
                });
                map[i][j].addEventFilter(DragEvent.DRAG_DROPPED, event -> {
                    if (event.getDragboard().hasImage()) {
                        String url = (String) event.getDragboard().getContent(DataFormat.PLAIN_TEXT);
                        String[] split = url.split("/");
                        String result = split[split.length - 1].replaceAll("%20", " ");
                        String type = result.substring(0, result.length() - 4);
                        ImageView imageView = new ImageView(event.getDragboard().getImage());
                        buildings.add(imageView);
                        int width = Buildings.getBuildingObjectByType(type).getWidth();
                        int height = Buildings.getBuildingObjectByType(type).getHeight();
                        imageView.fitWidthProperty().bind(textureSize.multiply(width));
                        imageView.fitHeightProperty().bind(textureSize.multiply(height));
                        imageView.translateXProperty().bind(map[finalI][finalJ].translateXProperty());
                        imageView.translateYProperty().bind(map[finalI][finalJ].translateYProperty());
                        mapPane.getChildren().add(imageView);
                        for (int k = finalI; k <= finalI + width; k++) {
                            for (int l = finalJ; l <= finalJ + height; l++) {
                                if (Map.getMap()[k][l].equals(selectedCell)) {
                                    selectedCell = null;
                                    mapPane.getChildren().removeAll(borderLines);
                                }
                            }
                        }
                        GameMenuController.dropBuilding(finalI, finalJ, type);
                    }
                    event.consume();
                });
                map[i][j].fitHeightProperty().bind(textureSize);
                map[i][j].fitWidthProperty().bind(textureSize);
                map[i][j].translateXProperty().bind(Bindings.add(textureSize.multiply(i), deltaX));
                map[i][j].translateYProperty().bind(Bindings.add(textureSize.multiply(j), deltaY));
                mapPane.getChildren().add(map[i][j]);
            }
        }
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
        mapPane.getChildren().add(showDetailsBox);
        showDetailsBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 10 ; -fx-padding: 8");
        showDetailsBox.translateXProperty().bind(Bindings.min((hoverX + 1) * textureSize.get() + map[0][0].translateXProperty().get()
                , showDetailsBox.widthProperty().multiply(-1).add(App.getWidth()).add(-10)));

        showDetailsBox.translateYProperty().bind(Bindings.max((hoverY - 1) * textureSize.get() + map[0][0].translateYProperty().get()
                , showDetailsBox.heightProperty().add(-20)));
    }


}
