package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Government {
    private static final String PATH = "src/main/resources/database/governments.json";
    private static ArrayList<Government> governments = new ArrayList<>();
    private final ArrayList<TradeRequest> incomingRequests = new ArrayList<>();
    private final ArrayList<TradeRequest> outgoingRequests = new ArrayList<>();
    private final transient ArrayList<Building> buildings = new ArrayList<>();
    private final int countofhorses = 0;
    private final int numberOfKnight = 0;
    private final String username;
    private final int foodRate = -2;
    private final int taxRate = 0;
    private final int popularity = 0;
    private int fearRate;
    private final transient Color color = null;
    private final transient Castle castle = new Castle(0, 0, 0, 0);
    private final int emptySpace = 0;

    public Government(String username) {
        this.username = username;
    }

    public static void fetchDatabase() {
        if (!new File(PATH).exists()) return;
        try (FileReader reader = new FileReader(PATH)) {
            ArrayList<Government> copy = new Gson().fromJson(reader, new TypeToken<List<Government>>() {
            }.getType());
            if (copy != null) governments = copy;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateDatabase() {
        File file = new File(PATH);
        try {
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            System.out.println("update database failed");
        }
        try (FileWriter writer = new FileWriter(PATH, false)) {
            writer.write(new Gson().toJson(governments));
        } catch (IOException e) {
            System.out.println("update database failed");
        }
    }

    public static ArrayList<Government> getGovernments() {
        return governments;
    }

    public static Government getGovernmentByUser(String username) {
        Stream<Government> stream = governments.stream().filter(government -> government.getUsername().equals(username));
        Optional<Government> government = stream.findAny();
        return government.orElse(null);
    }

    public ArrayList<TradeRequest> getOutgoingRequests() {
        return outgoingRequests;
    }

    public ArrayList<TradeRequest> getIncomingRequests() {
        return incomingRequests;
    }

    public String getUsername() {
        return username;
    }
}
