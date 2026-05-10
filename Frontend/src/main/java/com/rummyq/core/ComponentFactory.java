package com.rummyq.core;

import com.rummyq.model.ScreenConfig;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ComponentFactory {

    private static final int screenWidth = ScreenConfig.getScreenWidth();
    private static final int screenHeight = ScreenConfig.getScreenHeight();

    public static Pane createBackground() {
        Pane background = new Pane();
        background.setPrefSize(screenWidth, screenHeight);

        // Degradado radial simulado con rectangulo + efecto
        Rectangle rect = new Rectangle(screenWidth, screenHeight);
        LinearGradient gradiente = new LinearGradient(
                0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, UIColors.COLOR_FELT_CLARO),
                new Stop(0.7, UIColors.COLOR_FELT_MEDIO),
                new Stop(1.0, UIColors.COLOR_FELT_OSCURO));
        rect.setFill(gradiente);
        background.getChildren().add(rect);
        return background;
    }

    public static Pane createDecoratedBorder() {
        Pane layerPane = new Pane();
        layerPane.setPrefSize(screenWidth, screenHeight);
        layerPane.setMouseTransparent(false);

        // borde
        double stroke = 2.5;

        double rectWidth = screenWidth - (screenWidth * 0.08) - stroke;
        double rectHeight = screenHeight - (screenHeight * 0.1) - stroke;

        Rectangle outside = createOutline(rectWidth, rectHeight, stroke, UIColors.COLOR_ORO_OSCURO, 60);

        // Borde interior (más fino)
        stroke = 1;
        rectWidth = screenWidth - (screenWidth * 0.105) - 1;
        rectHeight = screenHeight - (screenHeight * 0.15) - 1;

        Rectangle inner = createOutline(rectWidth, rectHeight, stroke,
                UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.3), 50);

        layerPane.getChildren().addAll(outside, inner);
        return layerPane;
    }

    private static Rectangle createOutline(double rectW, double rectH, double stroke, Color color, double arc) {

        Rectangle outline = new Rectangle(rectW, rectH);

        outline.setX((screenWidth - rectW) / 2);
        outline.setY((screenHeight - rectH) / 2);

        outline.setFill(Color.TRANSPARENT);
        outline.setStroke(color);
        outline.setStrokeWidth(stroke);
        outline.setTranslateY(screenHeight * -0.025);
        outline.translateYProperty();
        outline.setArcWidth(arc);
        outline.setArcHeight(arc);

        return outline;
    }

    public static Button createPrimaryButton(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(260);
        btn.setPrefHeight(48);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        btn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #c9a84c, #8a6a20);" +
                        "-fx-text-fill: #1a0e00;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;");
        hoverEffect(btn,
                "-fx-background-color: linear-gradient(to bottom, #f0d080, #c9a84c);",
                "-fx-background-color: linear-gradient(to bottom, #c9a84c, #8a6a20);");

        DropShadow sombra = new DropShadow(14, Color.web("#c9a84c", 0.4));
        btn.setEffect(sombra);
        return btn;
    }

    public static void hoverEffect(Button btn, String hoverStyle, String normalStyle) {
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));
    }
}