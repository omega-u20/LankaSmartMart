package com.junkyard.lankasmartmart;

public class Branch {
    private String id;
    private String name;
    private double latitude;
    private double longitude;
    private String address;

    public Branch() {
        // Required for Firebase
    }

    public Branch(String id, String name, double latitude, double longitude, String address) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getAddress() { return address; }
}
