module project.group {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.apache.commons.codec;
    requires org.apache.commons.text;
    requires com.google.gson;
    requires org.apache.commons.lang3;
    requires com.auth0.jwt;

    exports view;
    opens view to javafx.fxml;
    opens model to com.google.gson;
}
