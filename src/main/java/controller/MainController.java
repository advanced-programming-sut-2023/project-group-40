package controller;

import com.auth0.jwt.algorithms.Algorithm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;
    static Calendar c;
    static Map<String, Object> headerClaims = new HashMap<>() {{put("typ", "JWT");}};
    static final Algorithm tokenAlgorithm = Algorithm.HMAC256("ya sattar");

    public static void setupCalender(){
        c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 20);
    }
    public static Date getExpirationDate(){
        return c.getTime();
    }

}
