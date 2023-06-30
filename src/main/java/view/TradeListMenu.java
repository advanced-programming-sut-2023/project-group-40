package view;

import controller.TradeMenuController;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Good;
import model.TradeRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TradeListMenu {
    private static String targetUsername;
    private static String message;
    private static String requestType;
    private static HashMap<Good,Integer> productList = new HashMap<>();
    private final Button backButton = new Button("back");
    private final Button enterButton = new Button("enter map menu");
    Pane root;
    public void start(Stage stage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(TradeListMenu.class.getResource("/css/tradeMenu.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        App.setupStage(stage);
        App.setWindowSize(stage.getWidth(), stage.getHeight());
        Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                , true, false))));
        backButton.setTranslateX(20);
        backButton.setTranslateY(App.getHeight() - 100);
        enterButton.setTranslateX(App.getWidth() - 290);
        enterButton.setTranslateY(App.getHeight() - 100);
        backButton.setOnMouseClicked(event -> {
            try {
                new TradeMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        enterButton.setOnMouseClicked(event -> {
            // TODO: 6/29/2023
        });
        root.getChildren().addAll(backButton, enterButton);
        makeNewRequestsNotification();
        HBox hBox = new HBox(makeIncomingRequestsVBox(),makeOutgoingRequestsVBox());
    }

    private VBox makeOutgoingRequestsVBox() {
        VBox vBox = new VBox(new Label("Outgoing Trade Requests"));
        for (TradeRequest outgoingRequest : TradeMenuController.getCurrentGovernment().getOutgoingRequests()) {
            HBox line = new HBox();
            Button seeButton = new Button();
            line.getChildren().add(new Label(outgoingRequest.getReceiver().getUsername()));
            line.getChildren().add(seeButton);
            seeButton.setOnMouseClicked(event -> {
                showDetail(outgoingRequest);
//                Node[] nodes = MapMenu.makePopup(t, "");
//                root.getChildren().add(nodes[0]);
//                nodes[1].setOnMouseClicked(mouseEvent -> {
//                    root.getChildren().remove(nodes[0]);
//                });
            });
            vBox.getChildren().add(line);
        }
        return vBox;
    }

    private VBox makeIncomingRequestsVBox() {
        return null;
    }

    private void makeNewRequestsNotification() {
        String notification = TradeMenuController.calculateUnseenRequests();
        if (notification != null) {
            Node[] nodes = MapMenu.makePopup(notification, "");
            root.getChildren().add(nodes[0]);
            nodes[1].setOnMouseClicked(mouseEvent -> {
                root.getChildren().remove(nodes[0]);
            });
        }
    }

    public static Node[] showDetail(TradeRequest outgoingRequest) {
        VBox messageVbox = new VBox();
        Label label = new Label();
        ImageView closeIcon = new ImageView(new Image(MapMenu.class.getResource("/images/errorIcon.png").toString(), 45, 45, false, false));
        closeIcon.translateXProperty().bind(Bindings.add(0, messageVbox.widthProperty().divide(2).add(-30)));
        closeIcon.setTranslateY(-3);
        messageVbox.getChildren().add(closeIcon);
        messageVbox.setMinWidth(700);
        messageVbox.setMaxWidth(700);
        messageVbox.setMaxHeight(550);
        messageVbox.setMinHeight(550);
        messageVbox.setTranslateY(30);
        Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString(), 500, 150, false, false);
        messageVbox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(500, 150, true, true
                , true, false))));
        messageVbox.translateXProperty().bind(messageVbox.widthProperty().divide(-2).add(App.getWidth() / 2));
        label.setText("products");
        label.setStyle("-fx-text-fill: green");
        label.setMinWidth(messageVbox.getMaxWidth());
        label.setAlignment(Pos.CENTER);
        label.setTranslateY(-30);
        for(Map.Entry<Good, Integer> entry : outgoingRequest.getProductList().entrySet()) {
            Good key = entry.getKey();
            int value = entry.getValue();
            messageVbox.getChildren().add(new Label("product : " + key.name().toLowerCase()));
            messageVbox.getChildren().add(new Label());
        }
            label.setStyle("-fx-text-fill: green");
        messageVbox.getChildren().add(label);
        return new Node[]{messageVbox, closeIcon};
    }


}
