package com.junkyard.lankasmartmart;

public class CategoryItem {
    private String name;
    private int imageRes;
    private String colorHex; // For the background tint

    public CategoryItem(String name, int imageRes, String colorHex) {
        this.name = name;
        this.imageRes = imageRes;
        this.colorHex = colorHex;
    }

    public String getName() {
        return name;
    }

    public int getImageRes() {
        return imageRes;
    }
    public String getColorHex() {
        return colorHex;
    }
}