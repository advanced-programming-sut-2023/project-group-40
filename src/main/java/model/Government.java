package model;

import view.TradeMenu;

import java.util.ArrayList;

public class Government {
    private static ArrayList<Government> governments = new ArrayList<>();
    private ArrayList<TradeRequest> requests =  new ArrayList<>();
    private User owner;
    private int foodRate;
    private int taxRate;
    private int popularity;
    private int fearRate;

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
}
