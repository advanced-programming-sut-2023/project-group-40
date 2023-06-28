package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.FriendStatus;
import model.PrivateUser;
import model.User;
import view.ProfileMenu;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Stream;


public class LeaderBoardController {
    private static final TableView<PrivateUser> tableView = new TableView<>();
    private static final TableColumn<PrivateUser, Integer> rankColumn = new TableColumn<>();
    private static final TableColumn<PrivateUser, ImageView> avatarColumn = new TableColumn<>();
    private static final TableColumn<PrivateUser, String> lastSeenColumn = new TableColumn<>();
    private static final TableColumn<PrivateUser, String> usernameColumn = new TableColumn<>();
    private static final TableColumn<PrivateUser, Integer> scoreColumn = new TableColumn<>();
    private static final TableColumn<PrivateUser, FriendStatus> friendStatusColumn = new TableColumn<>();
    private static final TableColumn<PrivateUser, VBox> actionColumn = new TableColumn<>();
    private static final TableColumn<PrivateUser, Button> sendRequestColumn = new TableColumn<>();
    private static final Button followButton = new Button("follow");

    private static List<PrivateUser> allUsers = ConnectToServer.getUsers();
    private static int start = 1;
    private static int state = 0;

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
        scoreColumn.setPrefWidth(150);

        actionColumn.setText("Accept/Reject");
        actionColumn.setResizable(false);
        actionColumn.setPrefWidth(200);

        sendRequestColumn.setText("send Request");
        sendRequestColumn.setResizable(false);
        sendRequestColumn.setPrefWidth(200);

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

        actionColumn.setCellValueFactory(param -> {
            User user = MainMenuController.getCurrentUser();
            FriendStatus friendStatus = user.getRequestInbox().get(param.getValue().getUsername());
            if (friendStatus != FriendStatus.NO_ACTION) return null;

            Button accept = new Button("accept");
            accept.setStyle("-fx-text-fill: white ;-fx-background-color: green; -fx-min-width: 100");
            Button reject = new Button("reject");
            accept.setStyle("-fx-text-fill: white ;-fx-background-color: red; -fx-min-width: 100");
            accept.setOnMouseClicked(event -> {
                user.getFriends().add(param.getValue().getUsername());
                user.getRequestInbox().put(param.getValue().getUsername(), FriendStatus.ACCEPTED);
                ConnectToServer.changeRequestStatus(param.getValue().getUsername(), "accept");
            });
            reject.setOnMouseClicked(event -> {
                user.getRequestInbox().put(param.getValue().getUsername(), FriendStatus.REJECTED);
                ConnectToServer.changeRequestStatus(param.getValue().getUsername(), "reject");
            });
            VBox vBox = new VBox();
            vBox.getChildren().addAll(accept, reject);
            vBox.setSpacing(5);
            return new SimpleObjectProperty<>(vBox);
        });

        sendRequestColumn.setCellValueFactory(param -> {
            User user = MainMenuController.getCurrentUser();
            FriendStatus friendStatus = user.getRequestInbox().get(param.getValue().getUsername());
//            if (friendStatus != FriendStatus.NO_ACTION) return null;
//            followButton.setStyle("-fx-text-fill: white ;-fx-background-color: #07074c;-fx-background-radius: 5 ;-fx-padding: 0; -fx-font-size: 25");
//            followButton.setPrefWidth(150);
//            followButton.setOnMouseClicked(event -> {
//                user.getRequestOutbox().put(param.getValue().getUsername(),FriendStatus.NO_ACTION);
//                ConnectToServer.changeRequestStatus(param.getValue().getUsername(),"follow");
//            });
            VBox vBox = new VBox();
            vBox.getChildren().addAll(followButton,new Button("sa"));
            return new SimpleObjectProperty<>(followButton);
        });

        scoreColumn.setCellValueFactory(
                param -> new SimpleIntegerProperty(param.getValue().getHighScore()).asObject());


        tableView.getItems().addAll(getUsers(allUsers, 1));

        tableView.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0)
                if (start != 1) start -= 10;
            if (event.getDeltaY() < 0)
                if (start + 10 <= allUsers.size()) start += 10;
            tableView.getItems().clear();
            tableView.getItems().addAll(getUsers(allUsers, start));
        });
        tableView.setRowFactory(param -> {
            TableRow<PrivateUser> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    ProfileMenuController.changeAvatar(tableView.getItems().get(row.getIndex()).getAvatarByteArray());
                    profileMenu.getAvatar().setImage(new Image(new ByteArrayInputStream(tableView.getItems().get(row.getIndex()).getAvatarByteArray()), 100, 100, true, true));
                }
            });
            return row;
        });


        tableView.setRowFactory(userTableView -> new TableRow<>() {
            @Override
            protected void updateItem(PrivateUser user, boolean b) {
                if (!b && user.isOnline())
                    setStyle("-fx-background-color: rgba(56,111,6,0.8)");
                super.updateItem(user, b);
            }
        });


        addButton();
    }

    private static void addButton() {
        Callback<TableColumn<PrivateUser, Button>, TableCell<PrivateUser, Button>> cellFactory = new Callback<>() {
            @Override
            public TableCell<PrivateUser, Button> call(final TableColumn<PrivateUser, Button> param) {
                final TableCell<PrivateUser, Button> cell = new TableCell<>() {
                    {
                        followButton.setStyle("-fx-text-fill: white ;-fx-background-color: #07074c;-fx-background-radius: 5 ;-fx-padding: 0; -fx-font-size: 25");
                        followButton.setPrefWidth(150);
                    }

                    @Override
                    public void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(followButton);
                        }
                    }
                };
                return cell;
            }
        };
        sendRequestColumn.setCellFactory(cellFactory);
    }


    public static TableView<PrivateUser> getTableView() {
        return tableView;
    }

    public static List<PrivateUser> getUsers(List<PrivateUser> users, int start) {
        return users.subList(start - 1, Math.min(start + 9, users.size()));
    }

    public static void refresh() {
        allUsers = ConnectToServer.getUsers();
        Platform.runLater(() -> {
            tableView.getColumns().clear();
        });
        tableView.getItems().clear();
        if (state == 0) {
            Platform.runLater(() -> {
                tableView.getColumns().addAll(rankColumn, avatarColumn, usernameColumn, scoreColumn, lastSeenColumn, sendRequestColumn);
                tableView.getItems().addAll(getUsers(allUsers, start));
            });

        }
        else if (state == 1) {
            User user = MainMenuController.getCurrentUser();
            Stream<PrivateUser> privateUsers = allUsers.stream().filter(privateUser -> user.getFriends().contains(privateUser.getUsername()));
            Platform.runLater(() -> {
                tableView.getColumns().addAll(rankColumn, avatarColumn, usernameColumn, scoreColumn, lastSeenColumn);
                tableView.getItems().addAll(getUsers(privateUsers.toList(), start));
            });
        } else {
            User user = MainMenuController.getCurrentUser();
            Stream<PrivateUser> privateUsers = allUsers.stream().filter(privateUser -> user.getRequestInbox().containsKey(privateUser.getUsername()));
            Platform.runLater(() -> {
                tableView.getColumns().addAll(rankColumn, avatarColumn, usernameColumn, scoreColumn, lastSeenColumn, friendStatusColumn, actionColumn);
                tableView.getItems().addAll(getUsers(allUsers, start));
            });
        }
        tableView.refresh();
    }

    public static void setState(int state) {
        LeaderBoardController.state = state;
    }
}
