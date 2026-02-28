package com.junkyard.lankasmartmart;

import java.util.List;

public class Order {
    private String orderId;
    private List<CartItem> items;
    private double totalAmount;
    private String status;
    private String timestamp;

    public Order() {
        // Required for Firebase
    }

    public Order(String orderId, List<CartItem> items, double totalAmount, String status, String timestamp) {
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getOrderId() { return orderId; }
    public List<CartItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public String getTimestamp() { return timestamp; }
}
