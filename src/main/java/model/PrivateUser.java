package model;

public class PrivateUser {
    private final int highScore;
    private final int rank;
    private final boolean isOnline;
    private final String username;
    private final String lastSeen;
    private byte[] avatarByteArray;

    public PrivateUser(String username, int rank, boolean isOnline, int highScore, byte[] avatarPath, String lastSeen) {
        this.username = username;
        this.rank = rank;
        this.isOnline = isOnline;
        this.highScore = highScore;
        this.avatarByteArray = avatarPath;
        this.lastSeen = lastSeen;
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

    public byte[] getAvatarByteArray() {
        return avatarByteArray;
    }

    public void setAvatarByteArray(byte[] avatarByteArray) {
        this.avatarByteArray = avatarByteArray;
    }

    public String getLastSeen() {
        return lastSeen;
    }
}
