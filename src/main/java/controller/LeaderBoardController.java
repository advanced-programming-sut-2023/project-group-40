package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import model.User;
import view.ProfileMenu;

import java.util.Comparator;
import java.util.List;


public class LeaderBoardController {
    private final TableView<User> tableView = new TableView<>();
    private final TableColumn<User, Integer> rankColumn = new TableColumn<>();
    private final TableColumn<User, ImageView> avatarColumn = new TableColumn<>();
    private final TableColumn<User, String> usernameColumn = new TableColumn<>();
    private final TableColumn<User, Integer> scoreColumn = new TableColumn<>();
    private List<User> allUsers = User.getUsers().stream().sorted(Comparator.comparingInt(User::getHighScore).reversed())
            .toList();
    private int start = 1;

    {
        rankColumn.setText("Rank");
        rankColumn.setResizable(false);
        rankColumn.setPrefWidth(80);

        avatarColumn.setText("Avatar");
        avatarColumn.setResizable(false);
        avatarColumn.setPrefWidth(100);

        usernameColumn.setText("Username");
        usernameColumn.setResizable(false);
        usernameColumn.setPrefWidth(130);

        scoreColumn.setText("High score");
        scoreColumn.setResizable(false);
        scoreColumn.setPrefWidth(140);

        tableView.getColumns().addAll(rankColumn, avatarColumn, usernameColumn, scoreColumn);

        tableView.setMinWidth(450);
        tableView.setMinHeight(440);
        tableView.setSortPolicy(param -> false);
    }

    public void showTableView(ProfileMenu profileMenu) {
        tableView.getItems().clear();
        rankColumn.setCellValueFactory(
                param -> new SimpleIntegerProperty(allUsers.indexOf(param.getValue()) + 1).asObject());

        avatarColumn.setCellValueFactory(param -> {
            ImageView imageView = new ImageView(param.getValue().getAvatarPath());
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            return new SimpleObjectProperty<>(imageView);

        });

        usernameColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getUsername()));


        scoreColumn.setCellValueFactory(
                param -> new SimpleIntegerProperty(param.getValue().getHighScore()).asObject());


        tableView.getItems().addAll(getUsers(1));

        tableView.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0)
                if (start != 1) start -= 10;

            if (event.getDeltaY() < 0)
                if (start + 10 <= allUsers.size()) start += 10;

            tableView.getItems().clear();
            tableView.getItems().addAll(getUsers(start));
        });

        tableView.setRowFactory(param -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    profileMenu.changeAvatar(tableView.getItems().get(row.getIndex()).getAvatarPath());
                }
            });
            return row;
        });
    }

    public TableView<User> getTableView() {
        return tableView;
    }

    public List<User> getUsers(int start) {
        return allUsers.subList(start - 1, Math.min(start + 9, allUsers.size()));
    }

    public void refresh() {
        allUsers = User.getUsers().stream().sorted(Comparator.comparingInt(User::getHighScore).reversed()).toList();
        tableView.getItems().clear();
        tableView.getItems().addAll(getUsers(start));
        tableView.refresh();
    }
}
