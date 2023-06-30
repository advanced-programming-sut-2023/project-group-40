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

import java.util.Map;
import java.util.Objects;

public class TradeListMenu {
    private final Button backButton = new Button("back");
    private final Button enterButton = new Button("enter map menu");
    Pane root;
    VBox incomingRequests;
    public void start(Stage stage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(TradeListMenu.class.getResource("/css/tradeListMenu.css")).toExternalForm());
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
//        makeOutgoingRequestsVBox()
        incomingRequests = makeIncomingRequestsVBox();
        HBox hBox = new HBox(incomingRequests);
        root.getChildren().add(hBox);
        makeNewRequestsNotification();
    }

    private VBox makeOutgoingRequestsVBox() {
        return null;
    }

    private VBox makeIncomingRequestsVBox() {
        VBox vBox = new VBox(new Label("incoming Trade Requests"));
        HBox firstLine = new HBox(new Label("sender username"),new Label("seen/unseen"));
        firstLine.setSpacing(10);
        vBox.getChildren().add(firstLine);
        for (TradeRequest incomingRequest : TradeMenuController.getCurrentGovernment().getIncomingRequests()) {
            HBox line = new HBox();
            line.setSpacing(10);
            Button seeButton = new Button("see");
            line.getChildren().add(new Label(incomingRequest.getSenderUsername()));
            Label seenStatus = new Label("unseen");
            if (incomingRequest.getHasSeen()) {
                seenStatus.setText("seen");
                seeButton.setStyle("-fx-background-color: #372e2e");
            }
            line.getChildren().add(seenStatus);
            line.getChildren().add(seeButton);
            seeButton.setOnMouseClicked(event -> {
                if (incomingRequest.getHasSeen()) {
                    seeButton.setStyle("-fx-background-color: #372e2e");
                    return;
                };
                showDetail(incomingRequest);
                incomingRequest.setHasSeen(true);
                seenStatus.setText("seen");
                TradeMenuController.setSeen(incomingRequest);
            });
            vBox.getChildren().add(line);
        }
        return vBox;
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

    public void showDetail(TradeRequest incomingRequest) {
        VBox messageVbox = new VBox();
        Label label = new Label();
        ImageView closeIcon = new ImageView(new Image(MapMenu.class.getResource("/images/errorIcon.png").toString(), 45, 45, false, false));
        closeIcon.translateXProperty().bind(Bindings.add(0, messageVbox.widthProperty().divide(2).add(-30)));
        closeIcon.translateYProperty().bind(messageVbox.translateYProperty());
        messageVbox.getChildren().add(closeIcon);
        messageVbox.setMinWidth(700);
        messageVbox.setMaxWidth(700);
        messageVbox.setMaxHeight(400);
        messageVbox.setMinHeight(400);
        messageVbox.setTranslateY(30);
        Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString(), 500, 150, false, false);
        messageVbox.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(500, 150, true, true
                , true, false))));
        messageVbox.translateXProperty().bind(messageVbox.widthProperty().divide(-2).add(App.getWidth() / 2));
        label.setText("products");
        label.setMinWidth(messageVbox.getMaxWidth());
        label.setAlignment(Pos.CENTER);
        int totalPrice = 0;
        messageVbox.getChildren().add(label);
        for(Map.Entry<Good, Integer> entry : incomingRequest.getProductList().entrySet()) {
            Good key = entry.getKey();
            int value = entry.getValue();
            HBox line = new HBox();
            line.setStyle("-fx-alignment: center");
            line.getChildren().addAll(new Label("product : " + key.name().toLowerCase()));
            line.getChildren().addAll(new Label(" count : " + value));
            messageVbox.getChildren().add(line);
            if (!incomingRequest.getType().equals("donate"))
                totalPrice += key.getSellPrice() * value;
        }
        Button acceptButton = new Button("accept");
        Button rejectButton = new Button("reject");
        acceptButton.setStyle("-fx-background-color: green");
        rejectButton.setStyle("-fx-background-color: red");
        HBox hBox = new HBox(acceptButton,rejectButton);
        hBox.setStyle("-fx-alignment: center");
        hBox.setSpacing(10);
        messageVbox.getChildren().addAll(new Label("total price : "  + totalPrice));
        messageVbox.getChildren().addAll(hBox);

        int finalTotalPrice = totalPrice;
        acceptButton.setOnMouseClicked(event -> {
            incomingRequests = makeIncomingRequestsVBox();;
            Node[] nodes = MapMenu.makePopup(TradeMenuController.acceptTrade(incomingRequest.getId(), finalTotalPrice)
                    ,"Accept Trade Request Successful");
            root.getChildren().add(nodes[0]);
            nodes[1].setOnMouseClicked(event1 -> {
                root.getChildren().remove(nodes[0]);
            });
        });
        rejectButton.setOnMouseClicked(event -> {
            incomingRequests = makeIncomingRequestsVBox();
            root.getChildren().remove(messageVbox);
        });
        root.getChildren().add(messageVbox);
        closeIcon.setOnMouseClicked(mouseEvent -> {
            root.getChildren().remove(messageVbox);
        });
    }


}
