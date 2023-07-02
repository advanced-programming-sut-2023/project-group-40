package model;

import controller.UserController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;

public class User {
    public static final byte[][] avatarsByteArray = new byte[10][];

    static {
        try {
            for (int i = 1; i <= 10; i++) {
                BufferedImage bImage = ImageIO.read(new File(PrivateUser.class.getResource("/avatars/" + i + ".png").toURI()));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", bos);
                avatarsByteArray[i - 1] = bos.toByteArray();
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private final String lastSeen;
    private int highScore;
    private int rank;
    private String username;
    private String passwordHash;
    private String nickname;
    private String email;
    private String slogan;
    private String securityAnswer;
    private int securityQuestionNo;
    private boolean isOnline;
    private byte[] avatarByteArray;
    private HashMap<String, FriendStatus> requestInbox = new HashMap<>();
    private HashMap<String, FriendStatus> requestOutbox = new HashMap<>();
    private HashSet<String> friends = new HashSet<>();

    public User(String username, String password, String nickname, String email, String slogan, String lastSeen) {
        this.username = username;
        this.passwordHash = UserController.generatePasswordHash(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.lastSeen = lastSeen;
    }


    public void setPassword(String password) {
        this.passwordHash = UserController.generatePasswordHash(password);
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        if (this.highScore < highScore)
            this.highScore = highScore;
    }

    public int getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getSecurityQuestionNo() {
        return securityQuestionNo;
    }

    public void setSecurityQuestionNo(int securityQuestionNo) {
        this.securityQuestionNo = securityQuestionNo;
    }

    public byte[] getAvatarByteArray() {
        return avatarByteArray;
    }

    public void setAvatarByteArray(byte[] avatarByteArray) {
        this.avatarByteArray = avatarByteArray;
    }

    public HashMap<String, FriendStatus> getRequestInbox() {
        return requestInbox;
    }

    public void setRequestInbox(HashMap<String, FriendStatus> requestInbox) {
        this.requestInbox = requestInbox;
    }

    public HashSet<String> getFriends() {
        return friends;
    }

    public void setFriends(HashSet<String> friends) {
        this.friends = friends;
    }

    public HashMap<String, FriendStatus> getRequestOutbox() {
        return requestOutbox;
    }

    public void setRequestOutbox(HashMap<String, FriendStatus> requestOutbox) {
        this.requestOutbox = requestOutbox;
    }
}
