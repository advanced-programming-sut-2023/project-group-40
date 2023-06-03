package view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class SuccessfulDialog {
    private Label contentLabel;
   private Pane root;
   private Pane blackPane;
   private VBox vBox;
    public SuccessfulDialog(Pane root,String content) {
        this.root = root;
        this.contentLabel = new Label(content);
        blackPane = new Pane();
        blackPane.setStyle("-fx-background-color: rgba(0,0,0,0.5)");
        blackPane.setPrefWidth(App.getWidth());
        blackPane.setPrefHeight(App.getHeight());
        root.getChildren().add(blackPane);
    }

    public VBox make(){
        vBox = new VBox();
        vBox.getStylesheets().add(SuccessfulDialog.class.getResource("/css/successfulDialog.css").toString());
        vBox.translateXProperty().bind(vBox.widthProperty().divide(-2).add(App.getWidth()/2));
        vBox.setPrefWidth(400);
        vBox.setPrefHeight(300);
        vBox.setTranslateY(20);
        ImageView imageView = new ImageView(new Image(SuccessfulDialog.class.getResource("/images/tikIcon.png").toString(),50,50,true,true));
        imageView.setTranslateX(175);
        imageView.setTranslateY(-25);
        Label label= new Label("Success");
        label.setTranslateX(140);
//        contentLabel.translateXProperty().bind(contentLabel.widthProperty().divide(-2).add(200));
        contentLabel.setTranslateX(60);
        contentLabel.setTranslateY(20);
        Button okButton = new Button("OK");
        okButton.setTranslateX(65);
        okButton.setTranslateY(50);
        okButton.setOnMouseClicked(mouseEvent -> {
           removeDialog();
        });
        vBox.getChildren().addAll(imageView,label,contentLabel,okButton);
        return vBox;
    }

    public void removeDialog(){
        root.getChildren().remove(vBox);
        root.getChildren().remove(blackPane);
    }
}
