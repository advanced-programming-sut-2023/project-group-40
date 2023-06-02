package view;

import controller.MapMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import view.enums.Commands;

import java.util.Objects;
import java.util.regex.Matcher;

public class MapMenu extends Application {
    private Stage stage;
    private ImageView[][] map = new ImageView[200][200];
    Pane root;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        root = new Pane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects
                .requireNonNull(LoginMenu.class.getResource("/css/loginMenu.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();
        App.setupStage(stage);
        App.setWindowSize(stage.getWidth(), stage.getHeight());
        setupMap();
    }

    private void setupMap() {
        for (int i = 0 ; i < 50 ; i ++) {
            for (int j = 0 ; j < 50 ; j++){
                map[i][j] = new ImageView(MapMenu.class.getResource("/textures/land.jpg").toString());
                map[i][j].setFitWidth(stage.getScene().getWidth() / 50);
                map[i][j].setFitHeight(stage.getScene().getHeight() / 50);
                map[i][j].setTranslateX((stage.getScene().getWidth() / 50 )* i);
                map[i][j].setTranslateY((stage.getScene().getHeight() / 50 )* j);
                root.getChildren().add(map[i][j]);
            }
        }
    }

    public static String showMap(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return MapMenuController.showMap(x, y);
    }

    public static String changeSightArea(Matcher matcher) {
        int leftNumber = 0, topNumber = 0, rightNumber = 0, downNumber = 0;
        //left in another first
        while (true) {
            if (matcher.group("left") != null)
                leftNumber = StringUtils.isNotBlank(matcher.group("leftNumber")) ? Integer.parseInt(matcher.group("leftNumber")) : 1;
            if (matcher.group("top") != null)
                topNumber = StringUtils.isNotBlank(matcher.group("topNumber")) ? Integer.parseInt(matcher.group("topNumber")) : 1;
            if (matcher.group("right") != null)
                rightNumber = StringUtils.isNotBlank(matcher.group("rightNumber")) ? Integer.parseInt(matcher.group("rightNumber")) : 1;
            if (matcher.group("down") != null)
                downNumber = StringUtils.isNotBlank(matcher.group("downNumber")) ? Integer.parseInt(matcher.group("downNumber")) : 1;
            boolean res = matcher.find();
            if (!res) break;
        }

        return MapMenuController.showMap(MapMenuController.getCenterX() - topNumber + downNumber,
                MapMenuController.getCenterY() - leftNumber + rightNumber);
    }

    public static String showDetails(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        return MapMenuController.showDetails(x, y);
    }
}
