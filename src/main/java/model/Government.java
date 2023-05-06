package model;

import model.buildings.Storage;
import view.TradeMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.stream.Stream;

public class Government {
    private static final ArrayList<Government> governments = new ArrayList<>();
    private final ArrayList<TradeRequest> requests =  new ArrayList<>();
    private final ArrayList<Storage<Material>> materialStorages = new ArrayList<>();
    private final ArrayList<Storage<Food>> foodStorages = new ArrayList<>();
    private final ArrayList<Storage<Weapon>> weaponStorages = new ArrayList<>();
    private User owner;
    private int foodRate;
    private int taxRate;
    private int popularity;
    private int fearRate;
    private int population;
    private Color color = null;

    public Government(User owner) {
        this.owner = owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
    public static ArrayList<Government> getGovernments() {
        return governments;
    }
    public <T> void addRequest(TradeRequest<T> tradeRequest) {
        requests.add(tradeRequest);
    }
    public ArrayList<TradeRequest> getRequests() {
        return requests;
    }
    public TradeRequest getRequestById(Integer id) {
        for (TradeRequest request : requests) {
            if(request.getId().equals(id))
                return request;
        }
        return null;
    }
    public void addFoodStorage(Storage storage) {
        foodStorages.add(storage);
    }
    public void addWeaponStorage(Storage storage) {
        weaponStorages.add(storage);
    }
    public void addMaterialStorage(Storage storage) {
        materialStorages.add(storage);
    }
    public ArrayList<Storage<Food>> getFoodStorages() {
        return foodStorages;
    }
    public ArrayList<Storage<Weapon>> getWeaponStorages() {
        return weaponStorages;
    }
    public ArrayList<Storage<Material>> getMaterialStorages() {
        return materialStorages;
    }

    public static Government getGovernmentByUser(User user) {
        Stream<Government> stream = governments.stream().filter(government -> government.owner == user);
        Optional<Government> government = stream.findAny();
        return government.orElse(null);
    }

    public static boolean checkAllGovernmentsChooseColor() {
        return governments.stream().anyMatch(government -> government.getColor() == null);
    }

    public static void addGovernment(String username) {
        Government government = new Government(User.getUserByUsername(username));
        governments.add(government);
    }

    public static int getGovernmentsSize() {
        return governments.size();
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
