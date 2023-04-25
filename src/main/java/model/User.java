package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.text.RandomStringGenerator;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class User {
    private int highScore;
    private int rank;
    private String username;
    private String passwordHash;
    private String nickname;
    private String email;
    private String slogan;
    private String securityAnswer;
    private int securityQuestionNumber;
    private static final String PATH = "src/main/resources/users.json";
    private static ArrayList<User> users = new ArrayList<>();
    private boolean isStayLoggedIn;

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

    public void setPasswordHash(String password) {
        this.passwordHash = generatePasswordHash(password);
    }

    public static boolean isUsernameExists(String username){
        return users.stream().anyMatch(user -> user.username.equals(username));
    }
    public static boolean isEmailExists(String email){
        return users.stream().anyMatch(user -> user.email.equalsIgnoreCase(email));
    }



    public ArrayList<User> getUsers() {
        return users;
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public static User getUserByUsername(String username) {
        Stream<User> stream = users.stream().filter(user -> user.username.equals(username));
        Optional<User> user = stream.findAny();
        return user.orElse(null);
    }


    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public static User getStayedLoginUser(){
        Stream<User> stream = users.stream().filter(user -> user.isStayLoggedIn = true);
        Optional<User> user = stream.findAny();
        return user.orElse(null);
    }
    public static void fetchDatabase() {
        if(!new File(PATH).exists()) return;
        try (FileReader reader = new FileReader(PATH)) {
            users = new Gson().fromJson(reader, new TypeToken<List<User>>() {}.getType());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateDatabase() throws IOException {
        File file = new File(PATH);
        if(!file.exists()) file.createNewFile();
        try (FileWriter writer = new FileWriter(PATH,false)) {
            writer.write(new Gson().toJson(users));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

    public static String generatePasswordHash(String password){
        return new DigestUtils("SHA3-256").digestAsHex(password);
    }

    public void setStayLoggedIn(boolean stayLoggedIn) {
        isStayLoggedIn = stayLoggedIn;
    }

    public boolean checkPassword(String password) {
        return generatePasswordHash(password).equals(this.passwordHash);
    }
}
