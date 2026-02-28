package com.junkyard.lankasmartmart;

public class Product {
    private String id;
    private String name;
    private int quantity;
    private double unitPrice;
    private String category;

    public Product() {
        // Required for Firebase
    }

    public Product(String id, String name, int quantity, double unitPrice, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
