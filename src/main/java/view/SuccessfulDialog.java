package view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

public class SuccessfulDialog {
    private final Label contentLabel;
    private final Pane root;
    private final Pane blackPane;
    private VBox vBox;

    public SuccessfulDialog(Pane root, String content) {
        this.root = root;
        this.contentLabel = new Label(content);
        blackPane = new Pane();
        blackPane.setStyle("-fx-background-color: rgba(0,0,0,0.5)");
        blackPane.setPrefWidth(App.getWidth());
        blackPane.setPrefHeight(App.getHeight());
        root.getChildren().add(blackPane);
    }

    public void make() {
        vBox = new VBox();
        vBox.getStylesheets().add(SuccessfulDialog.class.getResource("/css/successfulDialog.css").toString());
        vBox.translateXProperty().bind(vBox.widthProperty().divide(-2).add(App.getWidth() / 2));
        vBox.setMinWidth(500);
        vBox.setMaxWidth(500);
        vBox.setMinHeight(330);
        vBox.setMaxHeight(330);
        vBox.setTranslateY(20);
        vBox.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView(new Image(SuccessfulDialog.class.getResource("/images/tikIcon.png").toString(), 50, 50, true, true));
        imageView.setTranslateY(-20);
        Label label = new Label("Success");
        label.minWidthProperty().bind(vBox.widthProperty());
        label.maxWidthProperty().bind(vBox.widthProperty());
        contentLabel.setAlignment(Pos.CENTER);
        contentLabel.setMaxWidth(vBox.getMaxWidth());
        contentLabel.setMinWidth(vBox.getMinWidth());
        contentLabel.setTranslateY(20);
        Button okButton = new Button("OK");
        okButton.setTranslateY(40);
        okButton.setOnMouseClicked(mouseEvent -> {
            removeDialog();
        });
        vBox.getChildren().addAll(imageView, label, contentLabel, okButton);
        root.getChildren().add(vBox);
    }

    public void removeDialog() {
        root.getChildren().remove(vBox);
        root.getChildren().remove(blackPane);
    }
}
