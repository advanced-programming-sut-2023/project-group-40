package view;

import controller.GameMenuController;
import controller.TradeMenuController;
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
        scene.getStylesheets().add(Objects.requireNonNull(LoginMenu.class.getResource("/css/tradeMenu.css")).toExternalForm());
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
            ImageView miunusIcon = new ImageView(new Image(TradeMenu.class.getResource("/images/minusIcon.png").toString(),40,40,false,false));
            ImageView plusIcon = new ImageView(new Image(TradeMenu.class.getResource("/images/plusIcon.png").toString(),40,40,false,false));
            Label label = new Label("0");
            HBox hBox = new HBox(miunusIcon,label,plusIcon);
            hBox.setSpacing(0);
            plusIcon.setOnMouseClicked(event -> {
                int number = Integer.parseInt(label.getText());
                String[] url = imageView.getImage().getUrl().split("/");
                Good product = Good.getGoodByName(url[url.length - 1].split("\\.")[0]);
                if (GameMenuController.getCurrentGovernment().getAmountOfGood(product) == number)
                    return;
                label.setText(String.valueOf(number + 1));
            });
            miunusIcon.setOnMouseClicked(event -> {
                int number = Integer.parseInt(label.getText());
                if (number == 0) return;
                label.setText(String.valueOf(number - 1));
            });
            VBox vBox = new VBox(imageView,hBox);
            if (imageHBox1.getChildren().size() == 4) imageHBox2.getChildren().add(vBox);
            else imageHBox1.getChildren().add(vBox);
        }
        VBox vBox = new VBox(imageHBox1, imageHBox2, goodVBox);
        return vBox;
    }
}
