package model;

import controller.UserController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class User {
    public static final byte[][] avatarsByteArray = new byte[10][];
    private static final String PATH = "src/main/resources/database/users.json";
    private static ArrayList<User> users = new ArrayList<>();

    static {
        try {
            for (int i = 1; i <= 10; i++) {
                BufferedImage bImage = ImageIO.read(new File(PrivateUser.class.getResource("/avatars/" + i + ".png").toURI()));
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "png", bos);
                avatarsByteArray[i - 1] = bos.toByteArray();
            }
        } catch (IOException | URISyntaxException e) {

        }
    }

    private final HashMap<String, FriendStatus> requestInbox = new HashMap<>();
    private final HashMap<String, FriendStatus> requestOutbox = new HashMap<>();
    private final HashSet<String> friends = new HashSet<>();
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
    private byte[] avatarByteArray = avatarsByteArray[0];
    private String lastSeen;

    public User(String username, String passwordHash, String nickname, String email, String slogan, String lastSeen) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
        this.lastSeen = lastSeen;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static void updateRank() {
        users = (ArrayList<User>) users.stream().sorted(Comparator.comparingInt(o -> o.highScore)).collect(Collectors.toList());
        for (User user : users)
            user.rank = users.size() - users.indexOf(user);
        UserController.updateDatabase();
    }

    public static String getPATH() {
        return PATH;
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

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public HashMap<String, FriendStatus> getRequestInbox() {
        return requestInbox;
    }

    public HashMap<String, FriendStatus> getRequestOutbox() {
        return requestOutbox;
    }

    public HashSet<String> getFriends() {
        return friends;
    }
}
