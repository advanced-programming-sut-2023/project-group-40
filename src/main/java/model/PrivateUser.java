package model;

import controller.UserController;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class PrivateUser {
    private int highScore;
    private int rank;
    private boolean isOnline;
    private String username;
    private String avatarPath;

    public PrivateUser(String username, int rank, boolean isOnline, int highScore, String avatarPath) {
        this.username = username;
        this.rank = rank;
        this.isOnline = isOnline;
        this.highScore = highScore;
        this.avatarPath = avatarPath;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getRank() {
        return rank;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }
}
