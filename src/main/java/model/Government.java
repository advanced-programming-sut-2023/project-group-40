package model;

import model.buildings.*;
import model.buildings.Storage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class Government {
    private static ArrayList<Government> governments = new ArrayList<>();
    private final ArrayList<TradeRequest> requests = new ArrayList<>();
    private final ArrayList<Storage> storages = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private int countofhorses = 0;
    private int numberOfKnight = 0;
    private User owner;
    private int foodRate = -2;
    private int taxRate = 0;
    private int popularity;
    private int fearRate;
    private Color color = null;
    private Castle castle;
    private int emptySpace = 0;

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

    public void changePopularity(int amount) {
        this.popularity += amount;
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

    public int getPopulation() {
        return castle.getPopulation();
    }

    public void decreaseAmountOfFood(int amount) {
        int remainFromFood1 = decreaseAmountOfOneFood(amount,Good.FOOD1);
        if (remainFromFood1 == 0) return;
        int remainFromFood2 = decreaseAmountOfOneFood(remainFromFood1,Good.FOOD1);
        if (remainFromFood2 == 0) return;
        decreaseAmountOfOneFood(remainFromFood2,Good.FOOD1);
    }

    public int decreaseAmountOfOneFood(int amount ,Good food) {
        if (amount < getAmountOfGood(food)) {
            decreaseAmountOfGood(food, amount);
            return 0;
        }
        else {
            decreaseAmountOfGood(food, getAmountOfGood(food));
            return amount - getAmountOfGood(food);
        }
    }

    public int getNumberOfFoodVariety(){
        int result = 0;
        if (getAmountOfGood(Good.FOOD1) != 0) result ++;
        if (getAmountOfGood(Good.FOOD2) != 0) result ++;
        if (getAmountOfGood(Good.FOOD3) != 0) result ++;
        if (getAmountOfGood(Good.FOOD4) != 0) result ++;
        if (result == 0) result = 1;
        return result;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public int getEmptySpaces(){
        for (Building building : buildings) {
            if (building.getName().equals("Small stone gatehouse") || building.getName().equals("big stone gatehouse")) {
                GateHouse gateHouse = (GateHouse) building;
                emptySpace += gateHouse.getMaxCapacity() - gateHouse.getCapacity();
            }
            if (building.getName().equals("Hovel")) {
                Hovel hovel = (Hovel) building;
                emptySpace += hovel.getMaxCapacity() - hovel.getCapacity();
            }
        }
        return emptySpace;
    }

    public void increasePopulation(int amount) {
        int total = amount;
        int emptySpace = 0;
        for (Building building : buildings) {
            if (total == 0) return;
            if (building.getName().equals("Small stone gatehouse") || building.getName().equals("big stone gatehouse")){
                GateHouse gateHouse = (GateHouse) building;
                emptySpace = gateHouse.getMaxCapacity() - gateHouse.getCapacity();
                if (total >= emptySpace) {
                    gateHouse.setCapacity(gateHouse.getMaxCapacity());
                    castle.changePopulation(emptySpace);
                    total -= emptySpace;
                }
                else {
                    gateHouse.setCapacity(gateHouse.getCapacity() + total);
                    castle.changePopulation(total);
                    total = 0;
                }
            }
            else if (building.getName().equals("Hovel")) {
                Hovel hovel = (Hovel) building;
                emptySpace = hovel.getMaxCapacity() - hovel.getCapacity() ;
                if (total >= emptySpace) {
                    hovel.setCapacity(hovel.getMaxCapacity());
                    castle.changePopulation(emptySpace);
                    total -= emptySpace;
                }
                else {
                    hovel.setCapacity(hovel.getCapacity() + total);
                    castle.changePopulation(total);
                    total = 0;
                }
            }
        }
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public int getCountOfBuilding(String buildingName){
        int count = 0;
        for (Building building : buildings)
            if (building.getName().equals(buildingName))
                count ++;
        return count;
    }

    public void updateCountOfHorses() {
        this.countofhorses = getCountOfBuilding("stable") * 4 - numberOfKnight;
    }
    public int getCountOfHorses() {
        return countofhorses;
    }

    public void changeCountOfHorses(int amount){
        countofhorses -= amount;
        numberOfKnight += amount;
    }

    public int getNumberOfEngineer(){
        int count = 0;
        for (Building building : buildings) {
            if (building instanceof EngineerGuild) {
                EngineerGuild engineerGuild = (EngineerGuild) building;
                count += engineerGuild.getCostOfEngineer();
            }
        }
        return count;
    }
}
