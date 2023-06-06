package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    Pane root;
    double textureSize;
    private Stage stage;
    private int hoverX;
    private int hoverY;
    private final Timeline hoverTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> showDetails()));
    private VBox showDetailsBox = new VBox();
    private Cell selectedCell;
    private ArrayList<Line> borderLines = new ArrayList<>();
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
        hBox.setBackground(new Background(new BackgroundImage(image,null,null,null,new BackgroundSize(100,100,true,true
                ,false,false))));
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
            vBox.getChildren().addAll(buildingImageView,label);
            hBox.getChildren().add(vBox);
        }
        pane.setContent(hBox);
        root.getChildren().add(pane);
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
                map[i][j] = new ImageView(Texture.LAND.getImage());
                map[i][j].setFitWidth(textureSize);
                map[i][j].setFitHeight(textureSize);
                Map.getMap()[i][j].setTexture(Texture.LAND);
                if (i == j) {
                    map[i][j] = new ImageView(Texture.GRASS_LAND.getImage());
                    map[i][j].setFitWidth(textureSize);
                    map[i][j].setFitHeight(textureSize);
                    Map.getMap()[i][j].setTexture(Texture.GRASS_LAND);
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
                if (selectedCell != null && selectedCell != Map.getMap()[indexI][indexJ]) return;
                if (selectedCell != null) {
                    for (Line borderLine : borderLines)
                        root.getChildren().remove(borderLine);
                    selectedCell = null;
                    return;
                }
                NumberBinding cornerLeftX =  Bindings.add(map[indexI][indexJ].translateXProperty(),0);
                NumberBinding cornerRightX = Bindings.add(map[indexI][indexJ].translateXProperty(), map[indexI][indexJ].fitWidthProperty());
                NumberBinding cornerTopY = Bindings.add(map[indexI][indexJ].translateYProperty(),0);
                NumberBinding cornerBottomY = Bindings.add(map[indexI][indexJ].translateYProperty(), map[indexI][indexJ].fitHeightProperty());
                Line line1 = getLine(cornerLeftX,cornerTopY,cornerLeftX, cornerBottomY);
                Line line2 = getLine(cornerLeftX,cornerTopY, cornerRightX,cornerTopY);
                Line line3 = getLine(cornerRightX,cornerTopY,cornerRightX,cornerBottomY);
                Line line4 = getLine(cornerRightX,cornerBottomY,cornerLeftX,cornerBottomY);
                borderLines.addAll(List.of(line1,line2,line3,line4));

                root.getChildren().addAll(line1,line2,line3,line4);
                selectedCell = Map.getMap()[indexI][indexJ];
            }
        });
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
