package view;

import controller.ConnectToServer;
import controller.GameMenuController;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Government;
import model.Map;
import model.PrivateUser;

import java.util.ArrayList;
import java.util.Objects;

public class SetupGameMenu extends Application {
    private static Stage primaryStage;
    private final ComboBox<String> gameModeComboBox = new ComboBox<>();
    private final ScrollPane scrollPane = new ScrollPane();
    private final VBox mainVbox = new VBox();
    private final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private final Button startGame = new Button("start game");
    private final Button firstSizeButton = new Button("200 x 200");
    private final Button secondSizeButton = new Button("400 x 400");
    private Pane root;
    private int countOfPlayers = 2;
    private int size = 200;

    @Override
    public void start(Stage primaryStage) throws Exception {
        SetupGameMenu.primaryStage = primaryStage;
        root = new Pane();
        Image image = new Image(SetupGameMenu.class.getResource("/images/backgrounds/loginMenuBackground.jpg").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(App.getWidth(), App.getHeight(), false, false
                , true, true))));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(MainMenu.class.getResource("/css/setupGameMenu.css")).toExternalForm());
        setupList();
        setupComboBox();
        Label countLabel = new Label("number of players");
        countLabel.setStyle("-fx-text-fill: white; -fx-font-size: 40; -fx-min-width: 400");
        Label sizeLabel = new Label("map size : ");
        sizeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 40; -fx-min-width: 400");
        HBox hBox = new HBox(countLabel, gameModeComboBox);
        HBox sizeHbox = new HBox(sizeLabel, firstSizeButton, secondSizeButton);
        mainVbox.getChildren().addAll(scrollPane, hBox, sizeHbox, startGame);
        root.getChildren().add(mainVbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        App.setupStage(primaryStage);
        App.setWindowSize(primaryStage.getWidth(), primaryStage.getHeight());
        setSizes();
        setActions();
    }

    private void setupComboBox() {
        for (int i = 2; i <= 8; i++)
            gameModeComboBox.getItems().add(i + " players");
        gameModeComboBox.setValue("2 players");
        gameModeComboBox.valueProperty().addListener((observableValue, s, t1) -> {
            countOfPlayers = Integer.parseInt(t1.substring(0, 1));
            for (CheckBox checkBox : checkBoxes) {
                checkBox.setSelected(false);
                Government.getGovernments().clear();
            }
        });
    }

    private void setupList() {
        VBox scrollVbox = new VBox();
        for (PrivateUser user : ConnectToServer.getUsers()) {
            if (Objects.equals(user.getUsername(), MainMenuController.getCurrentUser().getUsername()))
                continue;
            Label label = new Label(user.getUsername());
            HBox hBox = new HBox(label);
            CheckBox checkBox = new CheckBox();
            hBox.getChildren().add(checkBox);
            checkBox.setOnMouseClicked(event -> {
                if (checkBox.isSelected()) {
                    if (Government.getGovernments().size() == countOfPlayers - 1) {
                        checkBox.setSelected(false);
                        return;
                    }
                    Government.addGovernment(label.getText());
                } else {
                    Government.removeGovernment(label.getText());
                }
            });
            checkBoxes.add(checkBox);
            scrollVbox.getChildren().add(hBox);
        }
        scrollPane.setContent(scrollVbox);
        scrollPane.maxWidthProperty().bind(scrollVbox.widthProperty().add(60));
    }

    private void setSizes() {
        mainVbox.translateXProperty().bind(mainVbox.widthProperty().divide(-2).add(App.getWidth() / 2));
        mainVbox.translateYProperty().bind(mainVbox.heightProperty().divide(-2).add(App.getHeight() / 2));
        scrollPane.setMaxHeight(App.getHeight() / 2);
        scrollPane.translateXProperty().bind(Bindings.add(scrollPane.widthProperty().divide(-2), mainVbox.widthProperty().divide(2)));
        startGame.translateXProperty().bind(Bindings.add(startGame.widthProperty().divide(-2), mainVbox.widthProperty().divide(2)));
    }

    private void setActions() {
        startGame.setOnMouseClicked(event -> {
            Government.addGovernment(MainMenuController.getCurrentUser().getUsername());
            for (CheckBox checkBox : checkBoxes) checkBox.setSelected(false);
            if (Government.getGovernments().size() < countOfPlayers) {
                ErrorDialog dialog = new ErrorDialog(root, "you don't select all of player");
                dialog.make();
                Government.getGovernments().clear();
                return;
            }
            Map.initMap(size);
            GameMenuController.setCurrentGovernment(Government.getGovernmentByUser(MainMenuController.getCurrentUser()));
            GameMenuController.setNumberOfPlayers(countOfPlayers);
            // TODO: 6/7/2023
            try {
                new EnvironmentMenu().start(primaryStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        String selectStyle = "-fx-border-style: solid solid solid solid;" +
                "-fx-border-width: 7;-fx-border-color: black;";
        firstSizeButton.setStyle(selectStyle);
        String deselectStyle = "-fx-border-style: hidden hidden hidden hidden;";
        firstSizeButton.setOnMouseClicked(event -> {
            firstSizeButton.setStyle(selectStyle);
            secondSizeButton.setStyle(deselectStyle);
            size = 200;
        });
        secondSizeButton.setOnMouseClicked(event -> {
            secondSizeButton.setStyle(selectStyle);
            firstSizeButton.setStyle(deselectStyle);
            size = 400;
        });
    }

}