package com.junkyard.lankasmartmart;

public class ItemOnboarding{
    private String name;
    private String price;
    private int imageRes;
    private int quantity;

    public ItemOnboarding(String name, String price, int imageRes, int quantity) {
        this.name = name;
        this.price = price;
        this.imageRes = imageRes;
        this.quantity = quantity;
    }

    // Getters
    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageRes() { return imageRes; }
    public int getQuantity() { return quantity; }
}
