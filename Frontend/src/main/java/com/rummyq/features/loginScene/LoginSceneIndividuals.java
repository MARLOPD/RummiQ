package com.rummyq.features.loginScene;

import com.rummyq.core.UIColors;
import com.rummyq.model.ScreenConfig;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class LoginSceneIndividuals {

    private static final int screenWidth = ScreenConfig.getScreenWidth();

    public static VBox createPanelLogo() {
        VBox panel = new VBox(16);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefWidth(screenWidth * 0.45);
        panel.setPadding(new Insets(40));

        Label welcomeLabel = new Label("BIENVENIDO A");
        welcomeLabel.setFont(Font.font("Georgia", FontWeight.LIGHT, 12));
        welcomeLabel.setTextFill(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.8));

        Label logoLabel = new Label("RummyQ");
        logoLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 72));
        logoLabel.setTextFill(UIColors.COLOR_ORO_CLARO);

        // Efecto glow pulsante
        Glow glow = new Glow(0);
        DropShadow shadow = new DropShadow(12, Color.BLACK);
        shadow.setInput(glow);
        logoLabel.setEffect(shadow);

        Timeline pulse = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.levelProperty(), 0.5)));
        pulse.setAutoReverse(true);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.play();

        HBox ornamental = createOrnamental();

        Label subtitleLabel = new Label("El clásico juego de fichas");
        subtitleLabel.setFont(Font.font("Georgia", FontWeight.LIGHT, 13));
        subtitleLabel.setTextFill(UIColors.COLOR_CREMA.deriveColor(0, 1, 1, 0.55));

        HBox tiles = createDecorativeTiles();

        panel.getChildren().addAll(welcomeLabel, logoLabel, ornamental, subtitleLabel, tiles);
        return panel;
    }

    public static Button createSecondaryButton(String texto) {
        Button btn = new Button(texto);
        btn.setPrefHeight(44);
        btn.setFont(Font.font("Georgia", 13));
        String estilo = "-fx-background-color: transparent; -fx-text-fill: " + UIColors.COLOR_ORO.toString() + ";" +
                "-fx-border-color: rgba(201,168,76,0.4); -fx-border-width: 1.5;" +
                "-fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand;";
        String hover = "-fx-background-color: rgba(245,234,214,0.08); -fx-text-fill: " + UIColors.COLOR_ORO.toString()
                + ";" +
                "-fx-border-color: rgba(201,168,76,0.7); -fx-border-width: 1.5;" +
                "-fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand;";
        btn.setStyle(estilo);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(estilo));
        return btn;
    }

    public static Button createPhantomButton(String texto) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Georgia", 12));
        String estilo = "-fx-background-color: transparent; -fx-text-fill: rgba(245,234,214,0.4); -fx-cursor: hand;";
        String hover = "-fx-background-color: transparent; -fx-text-fill: " + UIColors.COLOR_ORO.toString()
                + "; -fx-cursor: hand;";
        btn.setStyle(estilo);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(estilo));
        return btn;
    }

    private static HBox createOrnamental() {
        Region l1 = new Region();
        l1.setPrefSize(60, 1);
        l1.setStyle(
                "-fx-background-color: linear-gradient(to right, transparent, " + UIColors.COLOR_ORO.toString() + ");");

        Label diamondLabel = new Label("◆");
        diamondLabel.setTextFill(UIColors.COLOR_ORO);
        diamondLabel.setFont(Font.font(9));
        diamondLabel.setPadding(new Insets(0, 8, 0, 8));

        Region l2 = new Region();
        l2.setPrefSize(60, 1);
        l2.setStyle(
                "-fx-background-color: linear-gradient(to left, transparent, " + UIColors.COLOR_ORO.toString() + ");");

        HBox h = new HBox(l1, diamondLabel, l2);
        h.setAlignment(Pos.CENTER);
        return h;
    }

    private static HBox createDecorativeTiles() {
        String[] numeros = { "7", "☆", "3", "11" };
        String[] colores = { "#c0392b", "#c9a84c", "#1a5276", "#d35400" };
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20, 0, 0, 0));

        for (int i = 0; i < numeros.length; i++) {
            Label tile = new Label(numeros[i]);
            tile.setPrefSize(48, 62);
            tile.setAlignment(Pos.CENTER);
            tile.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
            tile.setTextFill(Color.web(colores[i]));
            tile.setStyle(
                    "-fx-background-color: #f5ead6;" +
                            "-fx-background-radius: 6;" +
                            "-fx-border-color: #d4c8a0;" +
                            "-fx-border-width: 1.5;" +
                            "-fx-border-radius: 6;");
            DropShadow s = new DropShadow(6, Color.BLACK);
            tile.setEffect(s);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(2 + i * 0.4), tile);
            tt.setFromY(0);
            tt.setToY(-8);
            tt.setAutoReverse(true);
            tt.setCycleCount(Animation.INDEFINITE);
            tt.setDelay(Duration.seconds(i * 0.3));
            tt.play();

            hbox.getChildren().add(tile);
        }
        return hbox;
    }

}