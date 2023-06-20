package view;

import controller.GameMenuController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.Map;
import model.buildings.Building;
import model.buildings.Buildings;
import model.troops.Troops;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
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
    private HBox scrollPaneHBox;
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
    private final Image redFace =new Image(MapMenu.class.getResource("/images/baseimages/redMask.png").toString(),30,30,false,false);
    private final Image yellowFace =new Image(MapMenu.class.getResource("/images/baseimages/yellowMask.png").toString(),30,30,false,false);
    private final Image greenFace =new Image(MapMenu.class.getResource("/images/baseimages/greenMask.png").toString(),30,30,false,false);
    private ArrayList<ImageView> faces = new ArrayList<>();
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
                updateLabel(GameMenuController.getCurrentGovernment().getFoodRate(), foodRateLabel,0);
                updateLabel(GameMenuController.getCurrentGovernment().getFearRate(), fearRateLabel,1);
                updateLabel(GameMenuController.getCurrentGovernment().getTaxRate(), taxRateLabel,2);
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
        vBox.setMaxWidth(200);
        vBox.setMinWidth(200);
        vBox.setMaxHeight(SCROLL_PANE_HEIGHT);
        vBox.setMinHeight(SCROLL_PANE_HEIGHT);
        Image image = new Image(MapMenu.class.getResource("/images/book.png").toString(),200,SCROLL_PANE_HEIGHT,false,false);
        vBox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(200, SCROLL_PANE_HEIGHT, false, false
                , true, false))));
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
        popularityLabel.setOnMouseClicked(null);
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
        faces.add(new ImageView());
        faces.add(new ImageView());
        faces.add(new ImageView());
        VBox textLabelVBox = new VBox(new Label("food rate"), new Label("fear rate"), new Label("tax rate"));
        foodRateLabel.setMinWidth(80);
        fearRateLabel.setMinWidth(80);
        taxRateLabel.setMinWidth(80);
        updateSumLabel();
        updateLabel(GameMenuController.getCurrentGovernment().getFoodRate(), foodRateLabel,0);
        updateLabel(GameMenuController.getCurrentGovernment().getFearRate(), fearRateLabel,1);
        updateLabel(GameMenuController.getCurrentGovernment().getTaxRate(), taxRateLabel,2);
        HBox sumHbox = new HBox(new Label("In The Coming Month"), sumRateLabel);
        sumHbox.setMinWidth(vBox.getMaxWidth());
        sumHbox.setAlignment(Pos.CENTER);
        sumHbox.setTranslateY(30);
        HBox hBox = new HBox();
        hBox.setMinWidth(vBox.getMinWidth());
        hBox.setAlignment(Pos.CENTER);
        VBox faceVbox = new VBox();
        faceVbox.setPadding(new Insets(10,0,0,0));
        faceVbox.setSpacing(15);
        faceVbox.getChildren().addAll(faces);
        hBox.getChildren().addAll(labelVBox, faceVbox,textLabelVBox);
        vBox.getChildren().addAll(hBox, sumHbox);
        root.getChildren().add(vBox);
        new Timeline(new KeyFrame(Duration.seconds(2),actionEvent -> {
            root.getChildren().remove(vBox);
            hBox.getChildren().clear();
            sumHbox.getChildren().clear();
            vBox.getChildren().clear();
            popularityLabel.setOnMouseClicked(event -> showPopularityFactors());
        })).play();
    }

    private void updateSumLabel() {
        int foodRate = GameMenuController.getCurrentGovernment().getFoodRate();
        int fearRate = GameMenuController.getCurrentGovernment().getFearRate();
        int taxRate = GameMenuController.getCurrentGovernment().getTaxRate();
        int preFoodRate = Integer.parseInt(foodRateLabel.getText().substring(1));
        int preFearRate = Integer.parseInt(fearRateLabel.getText().substring(1));
        int preTaxRate = Integer.parseInt(taxRateLabel.getText().substring(1));
        int sum = foodRate + fearRate + taxRate - preFoodRate - preTaxRate - preFearRate;
        updateLabel(sum, sumRateLabel,-1);
    }

    private void updateLabel(int rate, Label label,int index) {
        if (rate < 0) {
            label.setText(String.valueOf(rate));
            label.setStyle("-fx-text-fill: red");
            if (index == -1) return;
            faces.get(index).setImage(redFace);
        }
        else if (rate == 0) {
            label.setText(" 0");
            label.setStyle("-fx-text-fill: white");
            if (index == -1) return;
            faces.get(index).setImage(yellowFace);
        }
        else {
            label.setText("+" + rate);
            label.setStyle("text-emphasis: green;");
            if (index == -1) return;
            faces.get(index).setImage(greenFace);
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
        pane.requestFocus();
        pane.getStylesheets().add(EnvironmentMenu.class.getResource("/css/scrollPane.css").toString());
        pane.translateYProperty().bind(pane.heightProperty().multiply(-1).add(App.getHeight()));
        pane.setMinHeight(170);
        pane.setMinWidth(App.getWidth() / 2);
        pane.setMaxWidth(App.getWidth() / 2);
        Image image = new Image(EnvironmentMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
        scrollPaneHBox = new HBox();
        scrollPaneHBox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                , true, false))));
        scrollPaneHBox.setSpacing(20);
        try {
            updateScrollPanePicture(scrollPaneHBox, Buildings.class);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        pane.setContent(scrollPaneHBox);
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
                    if (value.getName().equals(building.getName())) {
                        content.putString(value.getImage().getUrl());
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
                    String type = getTypeByUrl(url);
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
                        try {
                            scrollPaneHBox.getChildren().clear();
                            updateScrollPanePicture(scrollPaneHBox, Buildings.class);
                        } catch (ReflectiveOperationException e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    }
                    else if (GameMenuController.getSelectedBuilding() != null) return;
                    buildingX = targetBuilding.getX1();
                    buildingY = targetBuilding.getY1();
                    cornerLeftX = Bindings.add(map[buildingX][buildingY].translateXProperty(), 0);
                    cornerRightX = Bindings.add(map[buildingX][buildingY].translateXProperty()
                            , map[buildingX][buildingX].fitWidthProperty().multiply(targetBuilding.getWidth()));
                    cornerTopY = Bindings.add(map[buildingX][buildingY].translateYProperty(), 0);
                    cornerBottomY = Bindings.add(map[buildingX][buildingY].translateYProperty()
                            , map[buildingX][buildingY].fitHeightProperty().multiply(targetBuilding.getHeight()));
                    GameMenuController.selectBuilding(buildingX, buildingY);
                    String name = GameMenuController.getSelectedBuilding().getName();
                    if (name.equals("mercenary post") || name.equals("barrack")) {
                        try {
                            scrollPaneHBox.getChildren().clear();
                            updateScrollPanePicture(scrollPaneHBox,Troops.class);
                        } catch (ReflectiveOperationException e) {
                            throw new RuntimeException(e);
                        }
                    }
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
                        String type = getTypeByUrl(url);
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
                        String type = getTypeByUrl(url);
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

    public static String getTypeByUrl(String url) {
        String[] split = url.split("/");
        String type = "";
        String result = split[split.length - 1].replaceAll("%20", " ");
        for (int k = 0; k < result.length(); k++) {
            if (result.charAt(k) != '.')
                type += result.charAt(k);
            else break;
        }
        return type;
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
        }
        mapPane.getChildren().add(showDetailsBox);
        showDetailsBox.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius: 10 ; -fx-padding: 8");
        showDetailsBox.translateXProperty().bind(Bindings.min((hoverX + 1) * textureSize.get() + map[0][0].translateXProperty().get()
                , showDetailsBox.widthProperty().multiply(-1).add(App.getWidth()).add(-10)));

        showDetailsBox.translateYProperty().bind(Bindings.max((hoverY - 1) * textureSize.get() + map[0][0].translateYProperty().get()
                , showDetailsBox.heightProperty().add(-20)));
    }

    private void updateScrollPanePicture(HBox hBox, Class<?> aClass) throws ReflectiveOperationException {
        Object arrayObject = aClass.getMethod("values").invoke(null);
        for (int i = 0; i < Array.getLength(arrayObject); i++) {
            ImageView imageView;
            if (aClass == Troops.class){
                if (((Troops) Array.get(arrayObject, i)).getRegion().equals("european") &&
                        GameMenuController.getSelectedBuilding().getName().equals("mercenary post"))
                    continue;
                if (((Troops) Array.get(arrayObject, i)).getRegion().equals("arabian") &&
                        GameMenuController.getSelectedBuilding().getName().equals("barrack"))
                    continue;
                if (((Troops) Array.get(arrayObject, i)).getName().equals("Black Monk") &&
                        !GameMenuController.getSelectedBuilding().getName().equals("Cathedral"))
                    continue;
                if (((Troops) Array.get(arrayObject, i)).getName().equals("engineer") &&
                        GameMenuController.getSelectedBuilding().getName().equals("engineer guild"))
                    continue;
                imageView = new ImageView(((Troops) Array.get(arrayObject, i)).getImage());
            }
            else  imageView = new ImageView(((Buildings) Array.get(arrayObject, i)).getImage());
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(80);
            if (aClass == Buildings.class) {
                imageView.addEventFilter(MouseDragEvent.DRAG_DETECTED, event -> {
                    Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage(imageView.getImage());
                    content.putString(imageView.getImage().getUrl());
                    db.setContent(content);
                    event.consume();
                });
            }
            else {
                imageView.addEventFilter(MouseDragEvent.MOUSE_CLICKED, event -> {
                    VBox troopVBox = new VBox();
                    Label  label = new Label();
                    troopVBox.setMinWidth(500);
                    troopVBox.setMaxWidth(500);
                    troopVBox.setMaxHeight(150);
                    troopVBox.setMinHeight(150);
                    troopVBox.setStyle("-fx-padding: 10");
                    troopVBox.setTranslateY(30);
                    Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
                    troopVBox.getStylesheets().add(MapMenu.class.getResource("/css/showFactors.css").toString());
                    troopVBox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                            , true, false))));
                    troopVBox.translateXProperty().bind(troopVBox.widthProperty().divide(-2).add(App.getWidth()/2));
                    HBox troopHBox = new HBox();
                    TextField countTextField = new TextField("0");
                    countTextField.setMaxWidth(100);
                    countTextField.setOnKeyPressed(keyEvent -> {
                        if (keyEvent.getCode() == KeyCode.ENTER) {
                            String url = imageView.getImage().getUrl();
                            String type = getTypeByUrl(url);
                            int count = Integer.parseInt(countTextField.getText());
                            String result = GameMenuController.createUnit(type,count,GameMenuController.getSelectedBuilding());
                            label.setText(result);
                            label.setStyle("-fx-text-fill: red");
                            label.setMinWidth(troopVBox.getMaxWidth());
                            label.setAlignment(Pos.CENTER);
                            if (result.equals("you successfully create unit"))
                                label.setStyle("-fx-text-fill: green");
                            new Timeline(new KeyFrame(Duration.seconds(1),actionEvent -> {
                                root.getChildren().remove(troopVBox);
                            }));
                        }
                    });
                    troopHBox.getChildren().add(new Label("count"));
                    troopHBox.getChildren().add(countTextField);
                    troopHBox.setSpacing(10);
                    troopHBox.setMinWidth(troopVBox.getMaxWidth());
                    troopHBox.setAlignment(Pos.CENTER);
                    troopVBox.getChildren().add(troopHBox);
                    troopVBox.getChildren().add(label);
                    root.getChildren().add(troopVBox);
                });
            }
            VBox vBox = new VBox();
            vBox.setSpacing(20);
            vBox.setStyle("-fx-background-color: transparent; -fx-padding: 10");
            vBox.setAlignment(Pos.CENTER);
            vBox.minHeightProperty().bind(hBox.minHeightProperty());
            Label label = new Label(aClass == Troops.class ?
                    ((Troops) Array.get(arrayObject, i)).name().toLowerCase() :
                    ((Buildings) Array.get(arrayObject, i)).getName());
            vBox.getChildren().addAll(imageView, label);
            hBox.getChildren().add(vBox);
        }
    }



}
