package com.rummyq.core;

import javafx.scene.paint.Color;

public class UIColors {
    // Background colors
    public static final Color COLOR_FELT_OSCURO = Color.web("#0f2d1a");
    public static final Color COLOR_FELT_MEDIO = Color.web("#1a472a");
    public static final Color COLOR_FELT_CLARO = Color.web("#2a5c3a");
    // Gold colors
    public static final Color COLOR_ORO = Color.web("#c9a84c");
    public static final Color COLOR_ORO_CLARO = Color.web("#f0d080");
    public static final Color COLOR_ORO_OSCURO = Color.web("#8a6a20");
    // Accent colors
    public static final Color COLOR_CREMA = Color.web("#f5ead6");
    // Validations Colors
    public static final Color ROJO_ERROR = Color.web("#e74c3c");
    public static final Color VERDE_EXITO = Color.web("#2ecc71");

    public static String toCSS(Color c) {
        return String.format(java.util.Locale.US, "rgba(%d,%d,%d,%.2f)",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255),
                c.getOpacity());
    }
}