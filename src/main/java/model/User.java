package model;

import controller.UserController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class User {
    private static final String PATH = "src/main/resources/database/users.json";
    private static ArrayList<User> users = new ArrayList<>();
    private int highScore;
    private int rank;
    private String username;
    private String passwordHash;
    private String nickname;
    private String email;
    private String slogan;
    private String securityAnswer;
    private int securityQuestionNo;
    private boolean isStayLoggedIn;
    private String avatarPath = User.class.getResource("/avatars/1.png").toString();

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.passwordHash = UserController.generatePasswordHash(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
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

    public boolean isStayLoggedIn() {
        return isStayLoggedIn;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        isStayLoggedIn = stayLoggedIn;
    }

    public int getSecurityQuestionNo() {
        return securityQuestionNo;
    }

    public void setSecurityQuestionNo(int securityQuestionNo) {
        this.securityQuestionNo = securityQuestionNo;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
