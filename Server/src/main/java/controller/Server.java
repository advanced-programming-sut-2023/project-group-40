package controller;

import com.auth0.jwt.algorithms.Algorithm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static Algorithm ALGORITHM = Algorithm.HMAC256("ya sattar");

    public Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            Socket socket = serverSocket.accept();
            ConnectedClient connectedClient = new ConnectedClient(socket);
            connectedClient.start();
        }

    }

}
