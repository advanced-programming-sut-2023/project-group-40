package model;

import controller.UserController;

import java.util.ArrayList;

public class User {
    private int highScore;
    private int rank;
    private String username;
    private String passwordHash;
    private String nickname;
    private String email;
    private String slogan;
    private String securityAnswer;
    private int securityQuestionNo;
    private static final String PATH = "src/main/resources/users.json";
    private static ArrayList<User> users = new ArrayList<>();
    private boolean isStayLoggedIn;

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.passwordHash = UserController.generatePasswordHash(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
    }

    public void setPassword(String password) {
        this.passwordHash = UserController.generatePasswordHash(password);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }


    public String getSecurityAnswer() {
        return securityAnswer;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getSlogan() {
        return slogan;
    }

    public static String getPATH() {
        return PATH;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public void setSecurityQuestionNo(int securityQuestionNo) {
        this.securityQuestionNo = securityQuestionNo;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        isStayLoggedIn = stayLoggedIn;
    }

    public boolean isStayLoggedIn() {
        return isStayLoggedIn;
    }

    public int getSecurityQuestionNo() {
        return securityQuestionNo;
    }
}
