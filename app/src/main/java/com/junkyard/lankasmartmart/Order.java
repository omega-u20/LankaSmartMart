package com.junkyard.lankasmartmart;

public class Order {
    private String orderID;
    private String itemID;
    private String name;
    private String price;
    private float unitPrice;
    private float quantity;

    public Order(String orderID, String itemID, String name, float quantity, float unitPrice) {
        this.orderID = orderID;
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.price = String.valueOf(quantity * unitPrice);
    }

    public String getOrderID() {
        return orderID;
    }
    public String getItemID() {
        return itemID;
    }
    public String getName() {
        return name;
    }
    public String getPrice() {
        return price;
    }
    public float getUnitPrice() {
        return unitPrice;
    }
    public float getQuantity() {
        return quantity;
    }

}
