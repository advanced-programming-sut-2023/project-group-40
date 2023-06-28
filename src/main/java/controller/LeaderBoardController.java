package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.FriendStatus;
import model.PrivateUser;
import model.User;
import view.ProfileMenu;

import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.security.Key;
import java.util.List;


public class LeaderBoardController {
    public static final TableView<PrivateUser> tableView = new TableView<>();
    public static final TableColumn<PrivateUser, Integer> rankColumn = new TableColumn<>();
    public static final TableColumn<PrivateUser, ImageView> avatarColumn = new TableColumn<>();
    public static final TableColumn<PrivateUser, String> lastSeenColumn = new TableColumn<>();
    public static final TableColumn<PrivateUser, String> usernameColumn = new TableColumn<>();
    public static final TableColumn<PrivateUser, Integer> scoreColumn = new TableColumn<>();
    public static final TableColumn<PrivateUser, FriendStatus> friendStatusColumn = new TableColumn<>();
    public static List<PrivateUser> allUsers = ConnectToServer.getUsers();
    public static int start = 1;
    public static int state = 0;

    static {
        rankColumn.setText("Rank");
        rankColumn.setResizable(false);
        rankColumn.setPrefWidth(80);

        avatarColumn.setText("Avatar");
        avatarColumn.setResizable(false);
        avatarColumn.setPrefWidth(100);

        usernameColumn.setText("Username");
        usernameColumn.setResizable(false);
        usernameColumn.setPrefWidth(130);

        lastSeenColumn.setText("last seen");
        lastSeenColumn.setResizable(false);
        lastSeenColumn.setPrefWidth(200);

        friendStatusColumn.setText("status");
        scoreColumn.setResizable(false);
        scoreColumn.setPrefWidth(250);

        scoreColumn.setText("High score");
        scoreColumn.setResizable(false);
        scoreColumn.setPrefWidth(140);

        tableView.setMinWidth(450);
        tableView.setMinHeight(440);
        tableView.setSortPolicy(param -> false);
    }

    public static void showTableView(ProfileMenu profileMenu) {
        tableView.getItems().clear();
        rankColumn.setCellValueFactory(
                param -> new SimpleIntegerProperty(allUsers.indexOf(param.getValue()) + 1).asObject());

        avatarColumn.setCellValueFactory(param -> {
            ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(param.getValue().getAvatarByteArray()), 100, 100, false, false));
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            return new SimpleObjectProperty<>(imageView);
        });

        lastSeenColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getLastSeen()));

        usernameColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getUsername()));

        friendStatusColumn.setCellValueFactory(param -> {
            User user = MainMenuController.getCurrentUser();
            FriendStatus friendStatus = user.getRequestInbox().get(param.getValue().getUsername());
            if (friendStatus == null) return null;
            return new SimpleObjectProperty<>(friendStatus);
        });

        scoreColumn.setCellValueFactory(
                param -> new SimpleIntegerProperty(param.getValue().getHighScore()).asObject());

        tableView.getItems().addAll(get10Users(1));

        tableView.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0)
                if (start != 1) start -= 10;
            if (event.getDeltaY() < 0)
                if (start + 10 <= allUsers.size()) start += 10;
            tableView.getItems().clear();
            tableView.getItems().addAll(get10Users(start));
        });

        tableView.setRowFactory(param -> {
            TableRow<PrivateUser> row = new TableRow<>() {
                @Override
                protected void updateItem(PrivateUser user, boolean b) {
                    if (!b && user.isOnline())
                        setStyle("-fx-background-color: rgba(56,111,6,0.8)");
                    super.updateItem(user, b);
                }
            };

            row.setOnMouseClicked(event -> {
                row.requestFocus();
                row.addEventFilter(KeyEvent.KEY_PRESSED, event2 -> {
                    User user = MainMenuController.getCurrentUser();
                    System.out.println(event2.getCode());
                    if (event2.getCode() == KeyCode.F) {
                        user.getRequestOutbox().put(tableView.getItems().get(row.getIndex()).getUsername(), FriendStatus.NO_ACTION);
                        ConnectToServer.changeRequestStatus(tableView.getItems().get(row.getIndex()).getUsername(), "follow");
                    } else if (event2.getCode() == KeyCode.A) {
                        user.getFriends().add(tableView.getItems().get(row.getIndex()).getUsername());
                        user.getRequestInbox().put(tableView.getItems().get(row.getIndex()).getUsername(), FriendStatus.ACCEPTED);
                        ConnectToServer.changeRequestStatus(tableView.getItems().get(row.getIndex()).getUsername(), "accept");
                    } else if (event2.getCode() == KeyCode.R) {
                        user.getRequestInbox().put(tableView.getItems().get(row.getIndex()).getUsername(), FriendStatus.REJECTED);
                        ConnectToServer.changeRequestStatus(tableView.getItems().get(row.getIndex()).getUsername(), "reject");
                    }
                });
                if (event.getClickCount() == 2) {
                    ProfileMenuController.changeAvatar(tableView.getItems().get(row.getIndex()).getAvatarByteArray());
                    profileMenu.getAvatar().setImage(new Image(new ByteArrayInputStream(tableView.getItems().get(row.getIndex()).getAvatarByteArray()), 100, 100, true, true));
                }
            });
            return row;
        });
    }


    public static TableView<PrivateUser> getTableView() {
        return tableView;
    }

    public static List<PrivateUser> get10Users(int start) {
        return allUsers.subList(start - 1, Math.min(start + 9, allUsers.size()));
    }

    public static void setState(int state) {
        LeaderBoardController.state = state;
    }


}
