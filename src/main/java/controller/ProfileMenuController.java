package controller;

import com.auth0.jwt.JWT;
import model.User;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class ProfileMenuController {
    private static User currentUser;

    public static void changeUsername(String newUsername) {
        currentUser.setUsername(newUsername);
        try {
            String token = JWT.create().withSubject("change username")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withClaim("new username", newUsername)
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            MainController.dataOutputStream.writeUTF(token);
        } catch (IOException ignored) {
        }
    }

    public static void changePassword(String newPassword) {
        currentUser.setPassword(newPassword);
        try {
            String token = JWT.create().withSubject("change password")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withClaim("new password", UserController.generatePasswordHash(newPassword))
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            MainController.dataOutputStream.writeUTF(token);
        } catch (IOException ignored) {
        }
    }

    public static void changeNickname(String newNickname) {
        currentUser.setNickname(newNickname);
        try {
            String token = JWT.create().withSubject("change nickname")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withClaim("new nickname", newNickname)
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            MainController.dataOutputStream.writeUTF(token);
        } catch (IOException ignored) {
        }
    }

    public static void changeEmail(String newEmail) {
        currentUser.setEmail(newEmail);
        try {
            String token = JWT.create().withSubject("change email")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withClaim("new email", newEmail)
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            MainController.dataOutputStream.writeUTF(token);
        } catch (IOException ignored) {
        }
    }

    public static void changeSlogan(String newSlogan) {
        currentUser.setSlogan(newSlogan);
        try {
            String token = JWT.create().withSubject("change slogan")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withClaim("new slogan", newSlogan)
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            MainController.dataOutputStream.writeUTF(token);
        } catch (IOException ignored) {
        }
    }

    public static boolean isPasswordCorrect(String newPassword) {
        return currentUser.getPasswordHash().equals(UserController.generatePasswordHash(newPassword));
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        ProfileMenuController.currentUser = currentUser;
    }

    public static void changeAvatar(String avatarPath) {
        try {
            String token = JWT.create().withSubject("change avatar")
                    .withExpiresAt(MainController.getExpirationDate())
                    .withHeader(MainController.headerClaims)
                    .sign(MainController.tokenAlgorithm);
            MainController.dataOutputStream.writeUTF(token);

            BufferedImage bImage = ImageIO.read(new File(avatarPath));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos );
            byte [] data = bos.toByteArray();
            currentUser.setAvatarByteArray(data);
            MainController.dataOutputStream.write(data);

            LeaderBoardController.refresh();
        } catch (IOException ignored) {
        }
    }
}
