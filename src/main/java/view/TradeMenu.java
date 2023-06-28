package view;

import controller.GameMenuController;
import controller.ShopMenuController;
import controller.TradeMenuController;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Good;
import view.enums.Commands;

import java.util.Objects;
import java.util.regex.Matcher;

public class TradeMenu {
    private static String targetUsername;
    private final Button backButton = new Button("back");
    private final Button enterButton = new Button("enter map menu");
    Pane root;

    public static String sendRequest(Matcher matcher) {
        String resourceType = Commands.eraseQuot(matcher.group("resourceType"));
        int resourceAmount = Integer.parseInt(matcher.group("resourceAmount"));
        int price = Integer.parseInt(matcher.group("price"));
        String message = Commands.eraseQuot(matcher.group("message"));
        return TradeMenuController.sendRequest(resourceType, resourceAmount, price, message, targetUsername);
    }

    public static String showTradeList(Matcher matcher) {
        return TradeMenuController.showTradeList();
    }

    public static String acceptTrade(Matcher matcher) {
        int id = Integer.parseInt(matcher.group("id"));
        String message = Commands.eraseQuot(matcher.group("message"));
        return TradeMenuController.acceptTrade(id, message);
    }

    public static String showTradeHistory(Matcher matcher) {
        return TradeMenuController.showTradeHistory();
    }

    public void start(Stage stage) throws Exception {
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(LoginMenu.class.getResource("/css/shopMenu.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        App.setupStage(stage);
        App.setWindowSize(stage.getWidth(), stage.getHeight());
        Image image = new Image(MapMenu.class.getResource("/images/backgrounds/oldPaperBackground.png").toString());
        root.setBackground(new Background(new BackgroundImage(image, null, null, null, new BackgroundSize(100, 100, true, true
                , true, false))));
        BarPane bar = new BarPane();
        bar.addPage("food", makeVBox("food"));
        bar.addPage("material", makeVBox("material"));
        bar.addPage("weapon", makeVBox("weapon"));
        bar.setMaxWidth(App.getWidth());
        bar.translateXProperty().bind(bar.widthProperty().divide(-2).add(App.getWidth() / 2));
        backButton.setTranslateX(20);
        backButton.setTranslateY(App.getHeight() - 100);
        enterButton.setTranslateX(App.getWidth() - 290);
        enterButton.setTranslateY(App.getHeight() - 100);
        backButton.setOnMouseClicked(event -> {
            try {
                new ShopMenu().start(stage);
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

    private VBox makeVBox(String type) {
        HBox imageHBox1 = new HBox();
        HBox imageHBox2 = new HBox();
        HBox goodVBox = new HBox();
        VBox labelVBox = new VBox();
        VBox buttonVBox = new VBox();
        Button buyButton = new Button();
        Button sellButton = new Button();
        int gold = GameMenuController.getCurrentGovernment().getAmountOfGood(Good.GOLD);
        Label treasuryLabel = new Label(String.valueOf(gold));
        ImageView coinIcon = new ImageView(new Image(MapMenu.class.getResource("/images/coin.png").toString()));
        coinIcon.setPreserveRatio(true);
        coinIcon.fitHeightProperty().bind(treasuryLabel.heightProperty().divide(2));
        HBox coinHBox = new HBox(treasuryLabel, coinIcon);
        int ImageSize = 130;
        for (Good good : Good.values()) {
            if (!good.getType().equals(type)) continue;
            ImageView imageView = new ImageView(good.getImage());
            imageView.setFitHeight(ImageSize);
            imageView.setFitWidth(ImageSize);
            if (imageHBox1.getChildren().size() == 4) imageHBox2.getChildren().add(imageView);
            else imageHBox1.getChildren().add(imageView);
            imageView.setOnMouseClicked(event -> {
                goodVBox.getChildren().clear();
                labelVBox.getChildren().clear();
                buttonVBox.getChildren().clear();
                labelVBox.getChildren().add(new Label(good.name().toLowerCase()));
                Label balanceLabel = new Label(String.valueOf(GameMenuController.getCurrentGovernment().getAmountOfGood(good)));
                labelVBox.getChildren().add(balanceLabel);
                sellButton.setText("Sell " + good.getSellPrice());
                buyButton.setText("Buy " + good.getBuyPrice());
                buttonVBox.getChildren().addAll(sellButton, buyButton);
                sellButton.setOnMouseClicked(event1 -> {
                    Node[] nodes = MapMenu.makePopup(ShopMenuController.sell(good.name(), 1), "sell successful");
                    root.getChildren().add(nodes[0]);
                    nodes[1].setOnMouseClicked(mouseEvent -> {
                        root.getChildren().remove(nodes[0]);
                    });
                    balanceLabel.setText(String.valueOf(GameMenuController.getCurrentGovernment().getAmountOfGood(good)));
                    treasuryLabel.setText(String.valueOf(GameMenuController.getCurrentGovernment().getAmountOfGood(Good.GOLD)));
                });
                buyButton.setOnMouseClicked(event1 -> {
                    Node[] nodes = MapMenu.makePopup(ShopMenuController.buy(good.name(), 1), "buy successful");
                    nodes[1].setOnMouseClicked(mouseEvent -> {
                        root.getChildren().remove(nodes[0]);
                    });
                    root.getChildren().add(nodes[0]);
                    balanceLabel.setText(String.valueOf(GameMenuController.getCurrentGovernment().getAmountOfGood(good)));
                    treasuryLabel.setText(String.valueOf(GameMenuController.getCurrentGovernment().getAmountOfGood(Good.GOLD)));
                });
                goodVBox.getChildren().addAll(labelVBox, buttonVBox, coinHBox);
            });
        }
        VBox vBox = new VBox(imageHBox1, imageHBox2, goodVBox);
        return vBox;
    }
}
