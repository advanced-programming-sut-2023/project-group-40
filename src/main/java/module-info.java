module project.group {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.apache.commons.codec;
    requires org.apache.commons.text;
    requires com.google.gson;
    requires org.apache.commons.lang3;

    exports view;
    opens view to javafx.fxml;
}
