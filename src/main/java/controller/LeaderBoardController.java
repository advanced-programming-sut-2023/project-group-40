package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import model.User;


public class LeaderBoardController {
    private final TableView<User> tableView = new TableView<>();
    private final TableColumn<User, ImageView> avatarColumn = new TableColumn<>();
    private final TableColumn<User, String> usernameColumn = new TableColumn<>();
    private final TableColumn<User, Integer> scoreColumn = new TableColumn<>();

    {
        tableView.getColumns().add(avatarColumn);
        avatarColumn.setResizable(false);

        tableView.getColumns().add(usernameColumn);
        usernameColumn.setResizable(false);

        tableView.getColumns().add(scoreColumn);
        scoreColumn.setResizable(false);


        tableView.setSortPolicy(param -> false);
    }

    public void showTableView() {
        tableView.getItems().clear();
        avatarColumn.setCellValueFactory(
                param -> (ObservableValue<ImageView>) new ImageView(param.getValue().getAvatarPath()));


        usernameColumn.setCellValueFactory(
                param -> new SimpleStringProperty(param.getValue().getUsername()));


        scoreColumn.setCellValueFactory(
                param -> new SimpleIntegerProperty(param.getValue().getHighScore()).asObject());



        tableView.getItems().addAll(User.getUsers()/*.stream().sorted((o1, o2) -> {
            if (o1.getHighScoreByDifficulty(difficulty) < o2.getHighScoreByDifficulty(difficulty)) return 1;
            else if (o1.getHighScoreByDifficulty(difficulty).equals(o2.getHighScoreByDifficulty(difficulty)))
                return Integer.compare(o1.getBestTimeByDifficulty(difficulty), o2.getBestTimeByDifficulty(difficulty));
            return -1;
        }).limit(10).toList()*/);
    }

    public TableView<User> getTableView() {
        return tableView;
    }
}
