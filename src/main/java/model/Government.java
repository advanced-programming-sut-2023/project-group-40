package model;

import model.buildings.FoodProcessing;
import model.buildings.Storage;
import model.buildings.Storage;
import view.TradeMenu;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.stream.Stream;

public class Government {
    private static ArrayList<Government> governments = new ArrayList<>();
    private final ArrayList<TradeRequest> requests = new ArrayList<>();
    private final ArrayList<Storage> storages = new ArrayList<>();
    private User owner;
    private int foodRate;
    private int taxRate;
    private int popularity;
    private int fearRate;
    private int population;
    private Color color = null;
    private Castle castle;

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

    public void addStorage(Storage storage) {
        storages.add(storage);
    }

    public ArrayList<Storage> getMaterialStorages() {
        return storages;
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

    public int getAmountOfGood(Good good) {
        int amount = 0;
        for (Storage storage : storages)
            amount += storage.getSumOfProducts(good);
        return amount;
    }

    public Castle getCastle() {
        return castle;
    }

    public String decreaseAmountOfGood(Good good, int count) {
        int deletedMaterials = count;
        int sumOfInventories = 0;
        for (Storage storage : storages)
            sumOfInventories += storage.getSumOfProducts(good);
        if (count > sumOfInventories) return "you haven't enough" + good.name().toLowerCase();
        for (Storage storage : storages) {
            if (deletedMaterials == 0) break;
            if (deletedMaterials < storage.getSumOfProducts(good)) {
                storage.decreaseAmountOfProduct(good, count);
                break;
            } else {
                deletedMaterials -= storage.getSumOfProducts(good);
                storage.removeProduct(good);
            }
        }
        return "you moved " + good.name().toLowerCase() + "successfully";
    }

    public String increaseAmountOfGood(Good good, int count) {
        int addedFoods = count;
        int sumOfEmptyCapacities = 0;
        for (Storage storage : storages) {
            sumOfEmptyCapacities += storage.getCapacity() - storage.getSumOfProducts(good);
        }
        if (addedFoods > sumOfEmptyCapacities) return "you can't produce" + good.name().toLowerCase();
        for (Storage storage : storages) {
            if (addedFoods == 0) break;
            int emptyCapacity = storage.getCapacity() - storage.getSumOfProducts(good);
            if (addedFoods < emptyCapacity) {
                storage.addProduct(good, addedFoods);
                break;
            } else {
                addedFoods -= emptyCapacity;
                storage.addProduct(good, emptyCapacity);
            }
        }
        return "you successfully produce" + good.name().toLowerCase();
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
            if (request.getId().equals(id)) return request;
        }
        return null;
    }
}
