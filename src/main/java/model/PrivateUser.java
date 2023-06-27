package model;

public class PrivateUser {
    private final int highScore;
    private final int rank;
    private final boolean isOnline;
    private final String username;
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

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
