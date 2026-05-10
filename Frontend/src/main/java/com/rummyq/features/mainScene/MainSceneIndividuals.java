package com.rummyq.features.mainScene;

import com.rummyq.core.ComponentFactory;
import com.rummyq.core.UIColors;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MainSceneIndividuals {

    private MainSceneActions actions;

    public MainSceneIndividuals(MainSceneActions _actions) {
        actions = _actions;
    }

    public Label createSubTitle() {
        Label lbl = new Label("B I E N V E N I D O  A");
        lbl.setFont(Font.font("Georgia", FontWeight.LIGHT, 13));
        lbl.setTextFill(UIColors.COLOR_ORO);
        lbl.setOpacity(0.8);
        return lbl;
    }

    public Label createLogo() {
        Label logo = new Label("RummyQ");
        logo.setFont(Font.font("Georgia", FontWeight.BOLD, 96));
        logo.setTextFill(UIColors.COLOR_ORO_CLARO);
        logo.setTextAlignment(TextAlignment.CENTER);

        DropShadow sombra = new DropShadow();
        sombra.setColor(Color.BLACK.deriveColor(0, 1, 1, 0.7));
        sombra.setOffsetY(5);
        sombra.setRadius(15);

        Glow glow = new Glow(0.0);
        sombra.setInput(glow);
        logo.setEffect(sombra);

        Timeline pulso = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.levelProperty(), 0.5)));
        pulso.setAutoReverse(true);
        pulso.setCycleCount(Animation.INDEFINITE);
        pulso.play();

        return logo;
    }

    public HBox createOrnamentalSeparator() {
        // Línea izquierda
        Region lineaIzq = new Region();
        lineaIzq.setPrefWidth(100);
        lineaIzq.setPrefHeight(1);
        lineaIzq.setStyle("-fx-background-color: linear-gradient(to right, transparent, #c9a84c);");

        // Diamante central
        Label diamante = new Label("◆");
        diamante.setFont(Font.font(10));
        diamante.setTextFill(UIColors.COLOR_ORO);
        diamante.setPadding(new Insets(0, 10, 0, 10));

        // Línea derecha
        Region lineaDer = new Region();
        lineaDer.setPrefWidth(100);
        lineaDer.setPrefHeight(1);
        lineaDer.setStyle("-fx-background-color: linear-gradient(to left, transparent, #c9a84c);");

        HBox separador = new HBox(lineaIzq, diamante, lineaDer);
        separador.setAlignment(Pos.CENTER);
        return separador;
    }

    public Label createTagline() {
        Label lbl = new Label("E L   C L Á S I C O   J U E G O   D E   F I C H A S");
        lbl.setFont(Font.font("Georgia", FontWeight.LIGHT, 12));
        lbl.setTextFill(UIColors.COLOR_CREMA.deriveColor(0, 1, 1, 0.55));
        return lbl;
    }

    public VBox createButtons() {
        Button btnNuevaPartida = ComponentFactory.createPrimaryButton("▶   Nueva Partida");
        Button btnIngresarCuenta = ComponentFactory.createPrimaryButton("▶   Ingresar o crear cuenta");
        Button btnComoJugar = createSecondaryButton("¿Cómo jugar?");
        Button btnSalir = createGhostButton("Salir");

        // Actions
        btnIngresarCuenta.setOnAction(e -> actions.showLoginScene());
        btnNuevaPartida.setOnAction(e -> actions.startGame());
        btnComoJugar.setOnAction(e -> actions.showRules());
        btnSalir.setOnAction(e -> actions.exitGame());

        VBox vbox = new VBox(12, btnNuevaPartida, btnIngresarCuenta, btnComoJugar, btnSalir);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxWidth(260);
        return vbox;
    }

    public Label createFooter() {
        Label lbl = new Label("POO · Proyecto Final · 2026");
        lbl.setFont(Font.font("Georgia", 11));
        lbl.setTextFill(UIColors.COLOR_CREMA.deriveColor(0, 1, 1, 0.3));
        return lbl;
    }

    private Button createSecondaryButton(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(260);
        btn.setPrefHeight(48);
        btn.setFont(Font.font("Georgia", FontWeight.NORMAL, 14));
        btn.setStyle(
                "-fx-background-color: rgba(245,234,214,0.08);" +
                        "-fx-text-fill: #f5ead6;" +
                        "-fx-border-color: rgba(201,168,76,0.4);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-cursor: hand;");
        ComponentFactory.hoverEffect(btn,
                "-fx-background-color: rgba(245,234,214,0.16); -fx-border-color: rgba(201,168,76,0.7); -fx-border-width:1.5; -fx-background-radius:6; -fx-border-radius:6; -fx-text-fill:#f5ead6; -fx-cursor:hand;",
                "-fx-background-color: rgba(245,234,214,0.08); -fx-border-color: rgba(201,168,76,0.4); -fx-border-width:1.5; -fx-background-radius:6; -fx-border-radius:6; -fx-text-fill:#f5ead6; -fx-cursor:hand;");
        return btn;
    }

    private Button createGhostButton(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(260);
        btn.setPrefHeight(36);
        btn.setFont(Font.font("Georgia", 12));
        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: rgba(245,234,214,0.4);" +
                        "-fx-cursor: hand;");
        ComponentFactory.hoverEffect(btn,
                "-fx-background-color: transparent; -fx-text-fill: #f5ead6; -fx-cursor:hand;",
                "-fx-background-color: transparent; -fx-text-fill: rgba(245,234,214,0.4); -fx-cursor:hand;");
        return btn;
    }

}