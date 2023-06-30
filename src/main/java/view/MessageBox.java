package view;

import controller.ConnectToServer;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Message;
import model.PrivateUser;

import java.util.List;

public class MessageBox {

    public static HBox createBox(Message message) {
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        List<PrivateUser> users = ConnectToServer.getUsers();
        PrivateUser user = users.stream().filter(privateUser ->
                privateUser.getUsername().equals(message.getSenderUsername())).findFirst().orElse(null);
        assert user != null;
        ImageView avatar = new ImageView(ProfileMenu.byteArrayToImage(user.getAvatarByteArray()));
        avatar.setFitHeight(70);
        avatar.setFitWidth(70);
        HBox messageDetailHBox = new HBox();
        Label senderName = new Label(message.getSenderUsername());
        senderName.setTranslateX(10);
        Label time = new Label(message.getTimeSent());
        senderName.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 20px");
        time.setStyle("-fx-text-fill: white; -fx-font-weight: bold;-fx-font-size: 20px");
        messageDetailHBox.setStyle("-fx-background-color: rgba(0,0,0,0.9);-fx-background-radius: 10px");
        ImageView tik = new ImageView(message.isSeen()
                ? MessageBox.class.getResource("/images/DoubleTik.png").toExternalForm()
                : MessageBox.class.getResource("/images/Tik.png").toExternalForm());
        tik.setFitHeight(20);
        tik.setFitWidth(20);
        tik.setTranslateY(5);
        tik.setTranslateX(-10);
        messageDetailHBox.setSpacing(25);
        messageDetailHBox.getChildren().addAll(senderName, time, tik);
        Label text = new Label(message.getText());
        text.setStyle("-fx-font-weight: bold;-fx-font-size: 25px");
        text.setMaxWidth(500);
        text.setWrapText(true);
        vBox.getChildren().addAll(text, messageDetailHBox);
        hBox.getChildren().addAll(avatar, vBox);
        hBox.setStyle("-fx-background-color: rgba(255,255,255,0.55);-fx-background-radius: 10px");
        hBox.setSpacing(10);
        return hBox;
    }

}
