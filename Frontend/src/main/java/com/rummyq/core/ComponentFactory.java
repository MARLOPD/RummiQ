package com.rummyq.core;

import com.rummyq.features.gameScene.GameSceneIndividuals;
import com.rummyq.model.ScreenConfig;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

        double stroke = 2.5;

        double rectWidth = screenWidth - (screenWidth * 0.08) - stroke;
        double rectHeight = screenHeight - (screenHeight * 0.1) - stroke;

        Rectangle outside = createOutline(rectWidth, rectHeight, stroke, UIColors.COLOR_ORO_OSCURO, 60);

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

    public static Button createGhostGameButton(String imagePath, String text) {
        Button btn = new Button(text);
        btn.setPrefSize(75, 75);
        btn.setMaxSize(75, 75);
        btn.setContentDisplay(ContentDisplay.TOP);

        Image iconImg = new Image(GameSceneIndividuals.class.getResourceAsStream(imagePath));
        ImageView iconView = new ImageView(iconImg);
        iconView.setFitWidth(32);
        iconView.setFitHeight(32);
        iconView.setPreserveRatio(true);
        btn.setGraphic(iconView);
        btn.setGraphicTextGap(5);

        String base = "-fx-background-color: " + UIColors.toCSS(UIColors.COLOR_FELT_OSCURO.deriveColor(0, 1, 1, 0.85))
                + ";"
                + "-fx-background-radius: 18;" +
                "-fx-border-color: " + UIColors.toCSS(UIColors.COLOR_ORO_OSCURO.deriveColor(0, 1, 1, 0.25)) + ";" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 18;" +
                "-fx-cursor: hand;" +
                "-fx-text-fill: " + UIColors.toCSS(UIColors.COLOR_CREMA) + ";" +
                "-fx-font-family: Georgia;" +
                "-fx-font-size: 10;" +
                "-fx-font-weight: bold;";

        String hover = "-fx-background-color: " + UIColors.toCSS(UIColors.COLOR_FELT_MEDIO.deriveColor(0, 1, 1, 0.9))
                + ";" +
                "-fx-background-radius: 18;" +
                "-fx-border-color: " + UIColors.toCSS(UIColors.COLOR_ORO) + ";" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 18;" +
                "-fx-cursor: hand;" +
                "-fx-text-fill: " + UIColors.toCSS(UIColors.COLOR_CREMA) + ";" +
                "-fx-font-family: Georgia;" +
                "-fx-font-size: 10;" +
                "-fx-font-weight: bold;";

        btn.setStyle(base);
        hoverEffect(btn, hover, base);

        return btn;
    }

    public static void hoverEffect(Button btn, String hoverStyle, String normalStyle) {
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));
    }

    public static void showMessage(String text, boolean isSuccess, Label messageLabel) {
        messageLabel.setText(text);
        messageLabel.setTextFill(isSuccess ? UIColors.VERDE_EXITO : UIColors.ROJO_ERROR);
    }

    public static HBox createInformationBox(String label, String labelValue) {
        HBox idRow = new HBox(6);
        idRow.setAlignment(Pos.CENTER_LEFT);
        idRow.setPrefHeight(screenHeight * 0.055);
        idRow.setMaxWidth(screenWidth * 0.09);
        idRow.setPrefWidth(screenWidth * 0.09);
        idRow.setPadding(new Insets(0, 10, 0, 12));
        idRow.setStyle(
                "-fx-background-color: rgba(10, 35, 18, 0.88);" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: rgba(245, 234, 214, 0.2);" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 10;");

        VBox idText = new VBox(1);
        Label idTitle = new Label(label);
        idTitle.setTextFill(Color.web("#a0b8a0"));
        idTitle.setFont(Font.font("Georgia", 10));

        Label idValue = new Label(labelValue);
        idValue.setTextFill(UIColors.COLOR_CREMA);
        idValue.setFont(Font.font("Georgia", FontWeight.BOLD, 13));

        idText.getChildren().addAll(idTitle, idValue);

        idRow.getChildren().addAll(idText);

        return idRow;
    }

    public static HBox createUserPanel(String name) {
        HBox panel = new HBox(6);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefHeight(screenHeight * 0.08);
        panel.setMaxHeight(screenHeight * 0.08);
        panel.setMaxWidth(screenWidth * 0.15);
        panel.setPrefWidth(screenWidth * 0.15);
        panel.setPadding(new Insets(10));

        panel.setStyle(
                "-fx-background-color: rgba(10, 35, 18, 0.88);" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: " + UIColors.toCSS(UIColors.COLOR_ORO) + ";" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 10;");

        Label nameLabel = new Label(name);
        nameLabel.setTextFill(UIColors.COLOR_CREMA);
        nameLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 16));

        panel.getChildren().add(nameLabel);

        return panel;
    }

}