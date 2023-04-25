package model;

import java.util.ArrayList;
enum DefaultSlogans{
    s1("");

    private final String slogan;
    DefaultSlogans(String slogan) {
        this.slogan = slogan;
    }

    public String getSlogan() {
        return slogan;
    }
}
public class User {
    private int highScore;
    private int rank;
    private boolean isLoginStayed;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String slogan;
    private String securityAnswer;
    private int securityQuestionNumber;
    private final String PATH = "src/main/resources/users.json";
    private static ArrayList<User> users = new ArrayList<>();

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
    }

    public static String generateRandomPassword(){
        return null;
    }
    public static String generateRandomSlogan(){
        return null;
    }

    public static boolean checkUsernameFormat(String username) {
        return true;
    }

    public static boolean checkPasswordFormat(String password) {
        return true;
    }

    public static boolean isUsernameExists(){
       return true;
    }

    public static boolean isEmailExists(){
        return true;
    }


    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User getUserByUsername(String username) {
        return null;
    }

    public static void fetchDatabase(){

    }

    public static void updateDatabase(){

    }

    public void setUsername(String username){

    }

    public void setPassword(String password){

    }

    public void setNickname(String nickname){

    }

    public void setEmail(String email){

    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public String getPassword() {
        return password;
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
}
