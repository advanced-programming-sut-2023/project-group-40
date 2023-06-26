package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class ConnectToServer {
    public static String register() throws IOException {
        Socket socket = new Socket("ap.ali83.ml", 80);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ", "JWT");
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        c.add(Calendar.SECOND, 20);
        Date expirationDate = c.getTime();
        String token = JWT.create().withSubject("register")
                .withExpiresAt(expirationDate)
                .withIssuer("username")
                .withIssuedAt(now)
                .withNotBefore(now)
                .withClaim("password", "password")
                .withHeader(headerClaims)
                .sign(Algorithm.HMAC256("ya sattar"));
        dataOutputStream.writeUTF(token);
        return dataInputStream.readUTF();
    }
}
