package model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.RandomStringGenerator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private int highScore;
    private int rank;
    private boolean isLoginStayed;
    private String username;
    private String passwordHash;
    private String nickname;
    private String email;
    private String slogan;
    private String securityAnswer;
    private int securityQuestionNumber;
    private final String PATH = "src/main/resources/users.json";
    private static ArrayList<User> users = new ArrayList<>();

    public User(String username, String password, String nickname, String email, String slogan) {
        this.username = username;
        this.passwordHash = generatePasswordHash(password);
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
    }

    public static String generateRandomPassword(){
        String chars = "abcdefghijklmnopqrstuvwxyz";
        chars += chars.toUpperCase();
        chars += "0123456789!@#$%";
        RandomStringGenerator passwordGenerator = new RandomStringGenerator.Builder().selectFrom(chars.toCharArray()).build();
        String password;
        while(true){
            password = passwordGenerator.generate(16);
            if(checkPasswordFormat(password)) return password;
        }
    }
    public static String generateRandomSlogan(){
        return null;
    }

    public static boolean checkUsernameFormat(String username) {
        return username.matches("\\w+");
    }

    public static boolean checkPasswordFormat(String password) {
        return password.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*\\W)^.{6,}");
    }

    public static boolean checkEmailFormat(String email){
        Matcher matcher = Pattern
                .compile("([0-9a-zA-Z_.]*)@([0-9a-zA-Z_.]*)\\.([0-9a-zA-Z_.]*)").matcher(email);
        if (!matcher.matches()) return false;
        for (int i = 1; i <= 3; i++) if (matcher.group(i).equals("")) return false;
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

    public static String generatePasswordHash(String password){
        return new DigestUtils("SHA3-256").digestAsHex(password);
    }
}
