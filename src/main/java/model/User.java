package model;

import controller.UserController;

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
    private boolean isOnline;
    private String avatarPath;

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

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

}
