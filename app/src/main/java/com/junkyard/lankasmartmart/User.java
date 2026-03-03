package com.junkyard.lankasmartmart;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private String password;
    private String profileImage;
    private String phoneNumber;
    private String address1, address2, address3;
    private String lat, lng;
    private ArrayList<CartItem> cartList = new ArrayList<>();
    private ArrayList<String> orders = new ArrayList<>(); // orderID

    public User() {
        // Required for Firebase
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public ArrayList<CartItem> getCartList() {
        return cartList;
    }

    public void addToCart(CartItem item) {
        if (cartList == null) cartList = new ArrayList<>();
        cartList.add(item);
    }

    public ArrayList<String> getOrders() {
        return orders;
    }

    public void addOrder(String orderID) {
        if (orders == null) orders = new ArrayList<>();
        orders.add(orderID);
    }
}
