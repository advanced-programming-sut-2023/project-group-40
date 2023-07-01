package view;

import controller.ConnectToServer;
import controller.GameMenuController;
import controller.ShopMenuController;
import controller.TradeMenuController;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;

public class TradeMenu {
    private static String requestType;
    private static HashMap<Good, Integer> productList = new HashMap<>();
    private final Button backButton = new Button("back");
    private final Button enterButton = new Button("enter map menu");
    BarPane bar;
    Pane root;

    public void start(Stage stage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(TradeMenu.class.getResource("/css/tradeMenu.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        App.setupStage(stage);
        App.setWindowSize(stage.getWidth(), stage.getHeight());
        Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                , true, false))));
        bar = new BarPane();
        bar.addPage("food", makeVBox("food", 0));
        bar.addPage("material", makeVBox("material", 1));
        bar.addPage("weapon", makeVBox("weapon", 2));
        bar.setMaxWidth(App.getWidth());
        bar.translateXProperty().bind(bar.widthProperty().divide(-2).add(App.getWidth() / 2));
        backButton.setTranslateX(20);
        backButton.setTranslateY(App.getHeight() - 100);
        enterButton.setTranslateX(App.getWidth() - 290);
        enterButton.setTranslateY(App.getHeight() - 100);
        backButton.setOnMouseClicked(event -> {
            try {
                new TradeListMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        enterButton.setOnMouseClicked(event -> {
            // TODO: 6/29/2023
        });
        root.getChildren().addAll(backButton, enterButton);
        VBox vbox = new VBox(bar);
        root.getChildren().add(vbox);
    }

    private VBox makeVBox(String type, int index) {
        List<PrivateUser> users = ConnectToServer.getUsers();
        ComboBox<String> userComboBox = new ComboBox<>();
        for (PrivateUser user : users) {
            userComboBox.getItems().add(user.getUsername());
        }
        userComboBox.setValue(userComboBox.getItems().get(0));
        TradeMenuController.setTargetGovernment(Government.getGovernmentByUser(userComboBox.getItems().get(0)));
        userComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            TradeMenuController.setTargetGovernment(Government.getGovernmentByUser(newValue));
        });
        Button donateButton = new Button("donate");
        Button requestButton = new Button("request");

        HBox buttonHbox = new HBox(new Label("trade request type: "), donateButton, requestButton);
        buttonHbox.setSpacing(10);
        donateButton.setOnMouseClicked(event -> {
            donateButton.setStyle("-fx-border-color: green");
            requestButton.setStyle("-fx-border-color: black");
            requestType = "donate";
        });
        requestButton.setOnMouseClicked(event -> {
            donateButton.setStyle("-fx-border-color: black");
            requestButton.setStyle("-fx-border-color: green");
            requestType = "request";
        });
        TextArea textArea = new TextArea();
        textArea.setTranslateY(20);
        textArea.setMaxHeight(80);
        textArea.setMaxWidth(400);
        Label messageLabel = new Label("message: ");
        HBox messageHBox = new HBox(messageLabel, textArea);
        messageLabel.translateYProperty().bind(Bindings.add(textArea.translateYProperty(), textArea.heightProperty().divide(4).add(-20)));
        HBox imageHBox1 = new HBox();
        HBox imageHBox2 = new HBox();
        int ImageSize = 100;
        for (Good good : Good.values()) {
            if (!good.getType().equals(type)) continue;
            ImageView imageView = new ImageView(good.getImage());
            imageView.setFitHeight(ImageSize);
            imageView.setFitWidth(ImageSize);
            ImageView miunusIcon = new ImageView(new Image(TradeMenu.class.getResource("/images/minusIcon.png").toString(), 40, 40, false, false));
            ImageView plusIcon = new ImageView(new Image(TradeMenu.class.getResource("/images/plusIcon.png").toString(), 40, 40, false, false));
            Label label = new Label("0");
            HBox hBox = new HBox(miunusIcon, label, plusIcon);
            hBox.setSpacing(0);
            plusIcon.setOnMouseClicked(event -> {
                int number = Integer.parseInt(label.getText());
                String[] url = imageView.getImage().getUrl().split("/");
                Good product = Good.getGoodByName(url[url.length - 1].split("\\.")[0]);
                label.setText(String.valueOf(number + 1));
                productList.merge(product, 1, Integer::sum);
            });
            miunusIcon.setOnMouseClicked(event -> {
                int number = Integer.parseInt(label.getText());
                String[] url = imageView.getImage().getUrl().split("/");
                Good product = Good.getGoodByName(url[url.length - 1].split("\\.")[0]);
                if (number == 0) return;
                label.setText(String.valueOf(number - 1));
                productList.merge(product, -1, Integer::sum);
            });
            VBox vBox = new VBox(imageView, hBox);
            if (imageHBox1.getChildren().size() == 4) imageHBox2.getChildren().add(vBox);
            else imageHBox1.getChildren().add(vBox);
        }
        VBox imageVBox = new VBox(imageHBox1, imageHBox2);
        Button submitButton = new Button("submit");
        submitButton.setTranslateY(40);
        submitButton.setOnMouseClicked(event -> {
            Node[] nodes = MapMenu.makePopup(TradeMenuController.sendRequest(requestType, productList, textArea.getText()), "request sent");
            root.getChildren().add(nodes[0]);
            nodes[1].setOnMouseClicked(mouseEvent -> {
                root.getChildren().remove(nodes[0]);
            });
        });
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ToolBar toolBar = (ToolBar) bar.getTop();
            toolBar.getItems().get(index).setOnMouseClicked(event -> {
                requestButton.setStyle("-fx-border-color: black");
                donateButton.setStyle("-fx-border-color: black");
                textArea.setText("");
                productList = new HashMap<>();
                requestType = null;
                userComboBox.setValue(userComboBox.getItems().get(0));
                TradeMenuController.setTargetGovernment(Government.getGovernmentByUser(userComboBox.getItems().get(0)));
            });
        });

        return new VBox(new HBox(userComboBox, imageVBox), buttonHbox, messageHBox, submitButton);
    }
}
