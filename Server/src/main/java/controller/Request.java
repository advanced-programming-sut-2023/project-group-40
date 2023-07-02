package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Request {
    protected final DataOutputStream dataOutputStream;
    protected final DataInputStream dataInputStream;
    protected final ConnectedClient client;

    public Request(ConnectedClient client, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        this.client = client;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
    }
}
