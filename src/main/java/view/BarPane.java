package view;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class BarPane extends BorderPane {

    private ToolBar buttonBar;
    private Node currentPagePane;

    public BarPane() {
        buildView();
    }

    private void buildView() {
        getStyleClass().setAll("button-bar-pane");
        buttonBar = new ToolBar();
        setTop(buttonBar);
    }

    public void addPage(String title, Node pagePane) {
        Button btn = new Button(title);
        btn.setContentDisplay(ContentDisplay.TOP);
        btn.setOnAction((ActionEvent event) -> {
            setSelectedButton(btn);
            showPage(pagePane);
        });
        buttonBar.getItems().add(btn);
        btn.setPrefWidth(300);

        if (buttonBar.getItems().size() == 1) {
            setSelectedButton(btn);
            showPage(pagePane);
        }
    }

    private void setSelectedButton(Button btnSelected) {
        buttonBar.getItems().forEach((node) -> {
            node.getStyleClass().removeAll("selected");
        });
        btnSelected.getStyleClass().add("selected");
    }

    private void showPage(Node pagePane) {
        if (currentPagePane != null) {
            setCenter(null);
        }
        this.currentPagePane = pagePane;
        setCenter(pagePane);
    }

}