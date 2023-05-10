package model;

import model.buildings.Storage;
import model.buildings.*;

import java.util.*;
import java.util.stream.Stream;
import java.util.Optional;

public class Government {
    private static ArrayList<Government> governments = new ArrayList<>();
    private final ArrayList<TradeRequest> requests = new ArrayList<>();
    private final ArrayList<Storage> storages = new ArrayList<>();
    private Building shop = Buildings.getBuildingObjectByType("shop");
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

    public ArrayList<Storage> getStorages() {
        return storages;
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


    public Castle getCastle() {
        return castle;
    }

    public int getNumOfInStorages(Good good) {
        int count = 0;
        for (Storage storage : this.storages) {
            if (storage.getProducts().get(good) != null) {
                count += storage.getProducts().get(good);
            }
        }
        return count;
    }

    public int getNumOfEmptySpace(String type) {
        int count = 0;
        List<Storage> filteredStorages = storages.stream().filter(storage -> storage.getProductType().equals(type)).toList();
        for (Storage storage : filteredStorages) {
            for (Integer value : storage.getProducts().values()) {
                count += value;
            }
            count = storage.getCapacity() - count;
        }
        return count;
    }

    public void decreaseAmountOfGood(Good good, int count) {
        int deletedMaterials = count;
        int sumOfInventories = 0;
        for (Storage storage : storages)
            sumOfInventories += getNumOfInStorages(good);
        if (count > sumOfInventories) return;
        for (Storage storage : storages) {
            if (deletedMaterials == 0) break;
            if (deletedMaterials < storage.getProducts().get(good)) {
                storage.decreaseAmountOfProduct(good, count);
                break;
            } else {
                deletedMaterials -= storage.getProducts().get(good);
                storage.removeProduct(good);
            }
        }
    }

    public void increaseAmountOfGood(Good good, int count) {
        int addedGoods = count;
        for (Storage storage : storages) {
            if (addedGoods == 0) break;
            int emptyCapacity = storage.getCapacity() - storage.getProducts().get(good);
            if (addedGoods < emptyCapacity) {
                storage.addProduct(good, addedGoods);
                break;
            } else {
                addedGoods -= emptyCapacity;
                storage.addProduct(good, emptyCapacity);
            }
        }
    }

    public static ArrayList<Government> getGovernments() {
        return governments;
    }

    public void addRequest(TradeRequest tradeRequest) {
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
