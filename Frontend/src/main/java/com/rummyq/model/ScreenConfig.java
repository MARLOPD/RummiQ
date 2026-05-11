package com.rummyq.model;

public class ScreenConfig {
    public static final String gameTitle = "RummyQ";

    private static int screenWidth = 1280;
    private static int screenHeight = 720;

    public static void setScreenWidth(int width) {
        screenWidth = width;
    }

    public static void setScreenHeight(int height) {
        screenHeight = height;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }
}
