package com.rummyq.view;

import com.rummyq.core.ComponentFactory;
import com.rummyq.features.mainScene.MainSceneActions;
import com.rummyq.features.mainScene.MainSceneAnimationEffects;
import com.rummyq.features.mainScene.MainSceneIndividuals;
import com.rummyq.model.ScreenConfig;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Pantalla inicial
 *
 * Responsabilidades:
 * - Mostrar el título y logo del juego
 * - Ofrecer opciones: Nueva Partida, Cómo Jugar, Salir
 * - Ingresar a la cuenta
 */
public class MainScene {

    private final Stage stage;
    private final MainSceneActions actions;
    private final MainSceneIndividuals components;
    private final MainSceneAnimationEffects animationEffects = new MainSceneAnimationEffects();

    private final int screenWidth = ScreenConfig.getScreenWidth();
    private final int screenHeight = ScreenConfig.getScreenHeight();

    public MainScene(Stage stage) {
        this.stage = stage;
        actions = new MainSceneActions(stage);
        components = new MainSceneIndividuals(actions);
    }

    /** Construye y muestra la pantalla inicial en el Stage. */
    public void showScreen() {
        StackPane pane = new StackPane();

        // Capas (de atrás hacia adelante)
        pane.getChildren().addAll(
                ComponentFactory.createBackground(),
                ComponentFactory.createDecoratedBorder(),
                crearContenidoCentral());

        Scene scene = new Scene(pane, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.show();
    }

    // ── Contenido central ────────────────────────────────────────────────────

    /** Construye el VBox central con logo, separador y botones. */
    private VBox crearContenidoCentral() {
        VBox contenido = new VBox(0);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(40));

        Label subtitle = components.createSubTitle();
        Label logo = components.createLogo();
        HBox separator = components.createOrnamentalSeparator();
        Label tagline = components.createTagline();
        VBox buttons = components.createButtons();
        Label footer = components.createFooter();

        VBox.setMargin(subtitle, new Insets(0, 0, 6, 0));
        VBox.setMargin(logo, new Insets(0, 0, 10, 0));
        VBox.setMargin(separator, new Insets(0, 0, 10, 0));
        VBox.setMargin(tagline, new Insets(0, 0, 40, 0));
        VBox.setMargin(buttons, new Insets(0, 0, 30, 0));

        contenido.getChildren().addAll(subtitle, logo, separator, tagline, buttons, footer);

        // Animación de entrada
        animationEffects.startAnimation(contenido);
        return contenido;
    }

}