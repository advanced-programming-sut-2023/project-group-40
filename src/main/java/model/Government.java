package model;

import controller.UserController;
import model.buildings.Building;
import model.buildings.GateHouse;
import model.buildings.Hovel;
import model.buildings.Storage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class Government {
    private static final ArrayList<Government> governments = new ArrayList<>();
    private final ArrayList<TradeRequest> requests = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private int countofhorses = 0;
    private int numberOfKnight = 0;
    private User owner;
    private int foodRate = -2;
    private int taxRate = 0;
    private int popularity = 0;
    private int fearRate;
    private Color color = null;
    private Castle castle = new Castle(0,0,0,0);
    private int emptySpace = 0;

    public Government(User owner) {
        this.owner = owner;
    }

    public static Government getGovernmentByUser(User user) {
        Stream<Government> stream = governments.stream().filter(government -> government.owner == user);
        Optional<Government> government = stream.findAny();
        return government.orElse(null);
    }

    public static boolean checkAllGovernmentsChoseColor() {
        return governments.stream().noneMatch(government -> government.getColor() == null);
    }

    public static void addGovernment(String username) {
        Government government = new Government(UserController.getUserByUsername(username));
        governments.add(government);
    }

    public static int getGovernmentsSize() {
        return governments.size();
    }

    public static ArrayList<Government> getGovernments() {
        return governments;
    }

    public static void removeGovernment(String username) {
        governments.removeIf(government -> government.getOwner() == UserController.getUserByUsername(username));
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAmountOfGood(Good good) {
        int count = 0;
        for (Building building : buildings) {
            if (building instanceof Storage storage) {
                Integer currentGoodCount = storage.getProducts().get(good);
                if (currentGoodCount != null) count += currentGoodCount;
            }
        }
        return count;
    }

    public Castle getCastle() {
        return castle;
    }

    public void setCastle(Castle castle) {
        this.castle = castle;
    }

    public String decreaseAmountOfGood(Good good, int count) {
        int deletedMaterials = count;
        int sumOfInventories = 0;

        for (Building building : buildings)
            if (building instanceof Storage storage && storage.getProductType().equals(good.getType()))
                sumOfInventories += storage.getCurrentAmount();

        if (count > sumOfInventories) return "you haven't enough " + good.name().toLowerCase();

        for (Building building : buildings)
            if (building instanceof Storage storage && storage.getProductType().equals(good.getType())) {
                if (deletedMaterials == 0) break;
                if (deletedMaterials < storage.getCurrentAmount()) {
                    storage.decreaseAmountOfProduct(good, count);
                    break;
                } else {
                    deletedMaterials -= storage.getCurrentAmount();
                    storage.removeProduct(good);
                }
            }

        return "you moved " + good.name().toLowerCase() + " successfully";
    }

    public String increaseAmountOfGood(Good good, int count) {
        int addedGoods = count;
        int sumOfEmptyCapacities = getNumOfEmptySpace(good.getType());

        if (addedGoods > sumOfEmptyCapacities) return "you can't save " + good.name().toLowerCase();

        for (Building building : buildings)
            if (building instanceof Storage storage && storage.getProductType().equals(good.getType())) {
                if (addedGoods == 0) break;
                int emptyCapacity = storage.getCapacity() - storage.getCurrentAmount();
                if (addedGoods < emptyCapacity) {
                    storage.addProduct(good, addedGoods);
                    break;
                } else {
                    addedGoods -= emptyCapacity;
                    storage.addProduct(good, emptyCapacity);
                }
            }

        return "you successfully save " + good.name().toLowerCase();
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

    public int getMaxPopulation() {
        return castle.getMaxPopulation();
    }

    public int getPopulation() {
        return castle.getPopulation();
    }


    public void decreaseAmountOfFood(int amount) {
        int remainFromFood1 = decreaseAmountOfOneFood(amount, Good.APPLE);
        if (remainFromFood1 == 0) return;
        int remainFromFood2 = decreaseAmountOfOneFood(remainFromFood1, Good.MEAT);
        if (remainFromFood2 == 0) return;
        int remainFromFood3 = decreaseAmountOfOneFood(remainFromFood1, Good.BREAD);
        if (remainFromFood3 == 0) return;
        decreaseAmountOfOneFood(remainFromFood2, Good.BEER);
    }

    public int decreaseAmountOfOneFood(int amount, Good food) {
        if (amount < getAmountOfGood(food)) {
            decreaseAmountOfGood(food, amount);
            return 0;
        } else {
            decreaseAmountOfGood(food, getAmountOfGood(food));
            return amount - getAmountOfGood(food);
        }
    }

    public int getNumberOfFoodVariety() {
        int result = 0;
        if (getAmountOfGood(Good.MEAT) != 0) result++;
        if (getAmountOfGood(Good.APPLE) != 0) result++;
        if (getAmountOfGood(Good.CHEESE) != 0) result++;
        if (getAmountOfGood(Good.BREAD) != 0) result++;
        if (result == 0) result = 1;
        return result;
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public int getEmptySpaces() {
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
            if (building.getName().equals("Small stone gatehouse") || building.getName().equals("big stone gatehouse")) {
                GateHouse gateHouse = (GateHouse) building;
                emptySpace = gateHouse.getMaxCapacity() - gateHouse.getCapacity();
                if (total >= emptySpace) {
                    gateHouse.setCapacity(gateHouse.getMaxCapacity());
                    castle.changePopulation(emptySpace);
                    total -= emptySpace;
                } else {
                    gateHouse.setCapacity(gateHouse.getCapacity() + total);
                    castle.changePopulation(total);
                    total = 0;
                }
            } else if (building.getName().equals("Hovel")) {
                Hovel hovel = (Hovel) building;
                emptySpace = hovel.getMaxCapacity() - hovel.getCapacity();
                if (total >= emptySpace) {
                    hovel.setCapacity(hovel.getMaxCapacity());
                    castle.changePopulation(emptySpace);
                    total -= emptySpace;
                } else {
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

    public int getCountOfBuilding(String buildingName) {
        int count = 0;
        for (Building building : buildings)
            if (building.getName().equals(buildingName))
                count++;
        return count;
    }

    public void updateCountOfHorses() {
        this.countofhorses = getCountOfBuilding("stable") * 4 - numberOfKnight;
    }

    public int getCountOfHorses() {
        return countofhorses;
    }

    public void changeCountOfHorses(int amount) {
        countofhorses -= amount;
        numberOfKnight += amount;
    }

    public int getNumOfEmptySpace(String type) {
        int count = 0;

        for (Building building : buildings)
            if (building instanceof Storage storage && storage.getProductType().equals(type))
                count += storage.getCapacity() - storage.getCurrentAmount();

        return count;
    }


}
