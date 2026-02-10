package com.junkyard.lankasmartmart;

public class OnboardingItem {
    private String title;
    private String description;
    private int imageResId;      // int for the image ID
    private int backgroundColor; // int for the color ID

    // Constructor
    public OnboardingItem(String title, String description, int imageResId, int backgroundColor) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.backgroundColor = backgroundColor;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
    public int getBackgroundColor() { return backgroundColor; }
}