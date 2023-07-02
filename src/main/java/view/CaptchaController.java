package view;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.Objects;
import java.util.Random;

public class CaptchaController {
    private static final Image reloadCaptchaImage = new Image(Objects.requireNonNull(RegisterMenu
            .class.getResource("/images/reloadCaptchaIcon.png")).toExternalForm());
    private static final ImageView captchaRefreshImageView = new ImageView(reloadCaptchaImage);
    private static final File captchaDirectory = new File(RegisterMenu.class.getResource("/captcha/").toExternalForm().substring(6));
    private static HBox captchaHBox;
    private static TextField captchaAnswerTextField = new TextField();
    private static Image captchaImage;
    private static ImageView captchaImageView;
    private static HBox imageHBox;

    public static void setUpCaptcha() {
        File file = new File(RegisterMenu.class.getResource("/captcha/").toExternalForm().substring(6));
        captchaImage = new Image("file:/" + Objects.
                requireNonNull(file.listFiles())[new Random().nextInt(0, Objects.requireNonNull(file.listFiles()).length)].getPath());
        captchaAnswerTextField = new TextField();
        captchaImageView = new ImageView(captchaImage);
        captchaImageView.fitHeightProperty().bind(captchaAnswerTextField.heightProperty());
        captchaRefreshImageView.setPreserveRatio(true);
        captchaRefreshImageView.setFitHeight(captchaImage.getHeight());
        captchaImageView.setStyle("-fx-padding: 5");
        captchaRefreshImageView.setStyle("-fx-padding: 5");
        imageHBox = new HBox(captchaRefreshImageView, captchaImageView);
        imageHBox.setSpacing(20);
        captchaHBox = new HBox(imageHBox, captchaAnswerTextField);
        captchaHBox.setSpacing(20);
        captchaRefreshImageView.setOnMouseClicked(event -> {
            refreshCaptcha();
        });
    }

    public static void refreshCaptcha() {
        captchaImage = new Image("file:/" + Objects.
                requireNonNull(captchaDirectory.listFiles())[new Random().nextInt(0, Objects.requireNonNull(captchaDirectory.listFiles()).length)].getPath());
        captchaImageView.setImage(captchaImage);
    }

    public static HBox getCaptchaHBox() {
        return captchaHBox;
    }

    public static HBox getImageHBox() {
        return imageHBox;
    }

    public static Image getCaptchaImage() {
        return captchaImage;
    }

    public static ImageView getCaptchaImageView() {
        return captchaImageView;
    }

    public static TextField getCaptchaAnswerTextField() {
        return captchaAnswerTextField;
    }

    public static void checkCaptcha() {
        String url = captchaImage.getUrl();
        if (url.substring(url.length() - 8, url.length() - 4).equals(captchaAnswerTextField.getText())) {
            captchaHBox.getChildren().remove(Errors.CAPTCHA_ERROR.getErrorLabel());
        } else {
            if (captchaHBox.getChildren().size() == 2)
                captchaHBox.getChildren().add(Errors.CAPTCHA_ERROR.getErrorLabel());
            refreshCaptcha();
            TextFieldController.setSuccessful(false);
        }
    }
}
