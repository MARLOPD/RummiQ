package com.rummyq.core;

import com.rummyq.features.mainScene.MainSceneColors;
import com.rummyq.model.ScreenConfig;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

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
                new Stop(0.0, MainSceneColors.COLOR_FELT_CLARO),
                new Stop(0.7, MainSceneColors.COLOR_FELT_MEDIO),
                new Stop(1.0, MainSceneColors.COLOR_FELT_OSCURO));
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

        Rectangle outside = createOutline(rectWidth, rectHeight, stroke, MainSceneColors.COLOR_ORO_OSCURO, 60);

        // Borde interior (más fino)
        stroke = 1;
        rectWidth = screenWidth - (screenWidth * 0.105) - 1;
        rectHeight = screenHeight - (screenHeight * 0.15) - 1;

        Rectangle inner = createOutline(rectWidth, rectHeight, stroke,
                MainSceneColors.COLOR_ORO.deriveColor(0, 1, 1, 0.3), 50);

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

}