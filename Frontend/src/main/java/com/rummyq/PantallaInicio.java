package com.rummyq;

import com.rummyq.MainApp;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Pantalla inicial del juego RummyQ.
 *
 * Responsabilidades:
 * - Mostrar el título y logo del juego
 * - Ofrecer opciones: Nueva Partida, Cómo Jugar, Salir
 * - Navegar a las demás pantallas
 */
public class PantallaInicio {

    // ── Colores del tema (tapete de casino) ──────────────────────────────────
    private static final Color COLOR_FELT_OSCURO = Color.web("#0f2d1a");
    private static final Color COLOR_FELT_MEDIO = Color.web("#1a472a");
    private static final Color COLOR_FELT_CLARO = Color.web("#2a5c3a");
    private static final Color COLOR_ORO = Color.web("#c9a84c");
    private static final Color COLOR_ORO_CLARO = Color.web("#f0d080");
    private static final Color COLOR_ORO_OSCURO = Color.web("#8a6a20");
    private static final Color COLOR_CREMA = Color.web("#f5ead6");
    private static final Color COLOR_ROJO = Color.web("#c0392b");
    private static final Color COLOR_FONDO = Color.web("#0a1a10");

    // ── Atributos ────────────────────────────────────────────────────────────
    private final Stage stage;
    private Scene escena;

    // ── Constructor ──────────────────────────────────────────────────────────
    public PantallaInicio(Stage stage) {
        this.stage = stage;
    }

    // ── Método principal ─────────────────────────────────────────────────────

    /** Construye y muestra la pantalla inicial en el Stage. */
    public void mostrar() {
        StackPane raiz = new StackPane();

        // Capas (de atrás hacia adelante)
        raiz.getChildren().addAll(
                crearFondoTapete(),
                crearBordeDecorado(),
                crearContenidoCentral());

        escena = new Scene(raiz, MainApp.ANCHO_VENTANA, MainApp.ALTO_VENTANA);
        stage.setScene(escena);
        stage.show();
    }

    // ── Capas de fondo ───────────────────────────────────────────────────────

    /** Crea el fondo degradado verde tipo tapete de casino. */
    private Pane crearFondoTapete() {
        Pane fondo = new Pane();
        fondo.setPrefSize(MainApp.ANCHO_VENTANA, MainApp.ALTO_VENTANA);

        // Degradado radial simulado con rectangulo + efecto
        Rectangle rect = new Rectangle(MainApp.ANCHO_VENTANA, MainApp.ALTO_VENTANA);
        LinearGradient gradiente = new LinearGradient(
                0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, COLOR_FELT_CLARO),
                new Stop(0.5, COLOR_FELT_MEDIO),
                new Stop(1.0, COLOR_FELT_OSCURO));
        rect.setFill(gradiente);
        fondo.getChildren().add(rect);
        return fondo;
    }

    /** Crea el borde dorado decorativo del tapete. */
    private Pane crearBordeDecorado() {
        Pane capa = new Pane();
        capa.setPrefSize(MainApp.ANCHO_VENTANA, MainApp.ALTO_VENTANA);
        capa.setMouseTransparent(true);

        // Borde exterior
        Rectangle borde = new Rectangle(
                20, 20,
                MainApp.ANCHO_VENTANA - 40,
                MainApp.ALTO_VENTANA - 40);
        borde.setFill(Color.TRANSPARENT);
        borde.setStroke(COLOR_ORO_OSCURO);
        borde.setStrokeWidth(2.5);
        borde.setArcWidth(60);
        borde.setArcHeight(60);

        // Borde interior (más fino)
        Rectangle bordeInterno = new Rectangle(
                30, 30,
                MainApp.ANCHO_VENTANA - 60,
                MainApp.ALTO_VENTANA - 60);
        bordeInterno.setFill(Color.TRANSPARENT);
        bordeInterno.setStroke(COLOR_ORO.deriveColor(0, 1, 1, 0.3));
        bordeInterno.setStrokeWidth(1);
        bordeInterno.setArcWidth(50);
        bordeInterno.setArcHeight(50);

        capa.getChildren().addAll(borde, bordeInterno);
        return capa;
    }

    // ── Contenido central ────────────────────────────────────────────────────

    /** Construye el VBox central con logo, separador y botones. */
    private VBox crearContenidoCentral() {
        VBox contenido = new VBox(0);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(40));

        Label subtitulo = crearSubtitulo();
        Label logo = crearLogo();
        HBox separador = crearSeparadorOrnamental();
        Label tagline = crearTagline();
        VBox botones = crearBotones();
        Label footer = crearFooter();

        VBox.setMargin(subtitulo, new Insets(0, 0, 6, 0));
        VBox.setMargin(logo, new Insets(0, 0, 10, 0));
        VBox.setMargin(separador, new Insets(0, 0, 10, 0));
        VBox.setMargin(tagline, new Insets(0, 0, 40, 0));
        VBox.setMargin(botones, new Insets(0, 0, 30, 0));

        contenido.getChildren().addAll(subtitulo, logo, separador, tagline, botones, footer);

        // Animación de entrada
        animarEntrada(contenido);
        return contenido;
    }

    // ── Componentes individuales ─────────────────────────────────────────────

    private Label crearSubtitulo() {
        Label lbl = new Label("B I E N V E N I D O  A");
        lbl.setFont(Font.font("Georgia", FontWeight.LIGHT, 13));
        lbl.setTextFill(COLOR_ORO);
        lbl.setOpacity(0.8);
        return lbl;
    }

    private Label crearLogo() {
        Label logo = new Label("RummyQ");
        logo.setFont(Font.font("Georgia", FontWeight.BOLD, 96));
        logo.setTextFill(COLOR_ORO_CLARO);
        logo.setTextAlignment(TextAlignment.CENTER);

        // Sombra del texto
        DropShadow sombra = new DropShadow();
        sombra.setColor(Color.BLACK.deriveColor(0, 1, 1, 0.7));
        sombra.setOffsetY(5);
        sombra.setRadius(15);

        // Brillo animado
        Glow glow = new Glow(0.0);
        sombra.setInput(glow);
        logo.setEffect(sombra);

        // Animación de pulso de brillo
        Timeline pulso = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.levelProperty(), 0.5)));
        pulso.setAutoReverse(true);
        pulso.setCycleCount(Animation.INDEFINITE);
        pulso.play();

        return logo;
    }

    private HBox crearSeparadorOrnamental() {
        // Línea izquierda
        Region lineaIzq = new Region();
        lineaIzq.setPrefWidth(100);
        lineaIzq.setPrefHeight(1);
        lineaIzq.setStyle("-fx-background-color: linear-gradient(to right, transparent, #c9a84c);");

        // Diamante central
        Label diamante = new Label("◆");
        diamante.setFont(Font.font(10));
        diamante.setTextFill(COLOR_ORO);
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

    private Label crearTagline() {
        Label lbl = new Label("E L   C L Á S I C O   J U E G O   D E   F I C H A S");
        lbl.setFont(Font.font("Georgia", FontWeight.LIGHT, 12));
        lbl.setTextFill(COLOR_CREMA.deriveColor(0, 1, 1, 0.55));
        return lbl;
    }

    private VBox crearBotones() {
        Button btnNuevaPartida = crearBotonPrimario("▶   Nueva Partida");
        Button btnComoJugar = crearBotonSecundario("?   ¿Cómo jugar?");
        Button btnSalir = crearBotonFantasma("Salir");

        // Acciones
        btnNuevaPartida.setOnAction(e -> accionNuevaPartida());
        btnComoJugar.setOnAction(e -> accionComoJugar());
        btnSalir.setOnAction(e -> accionSalir());

        VBox vbox = new VBox(12, btnNuevaPartida, btnComoJugar, btnSalir);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxWidth(260);
        return vbox;
    }

    private Label crearFooter() {
        Label lbl = new Label("POO · Proyecto Final · 2025");
        lbl.setFont(Font.font("Georgia", 11));
        lbl.setTextFill(COLOR_CREMA.deriveColor(0, 1, 1, 0.3));
        return lbl;
    }

    // ── Estilos de botones ───────────────────────────────────────────────────

    private Button crearBotonPrimario(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(260);
        btn.setPrefHeight(48);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        btn.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #c9a84c, #8a6a20);" +
                        "-fx-text-fill: #1a0e00;" +
                        "-fx-background-radius: 6;" +
                        "-fx-cursor: hand;");
        aplicarEfectoHover(btn,
                "-fx-background-color: linear-gradient(to bottom, #f0d080, #c9a84c);",
                "-fx-background-color: linear-gradient(to bottom, #c9a84c, #8a6a20);");

        DropShadow sombra = new DropShadow(14, Color.web("#c9a84c", 0.4));
        btn.setEffect(sombra);
        return btn;
    }

    private Button crearBotonSecundario(String texto) {
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
        aplicarEfectoHover(btn,
                "-fx-background-color: rgba(245,234,214,0.16); -fx-border-color: rgba(201,168,76,0.7); -fx-border-width:1.5; -fx-background-radius:6; -fx-border-radius:6; -fx-text-fill:#f5ead6; -fx-cursor:hand;",
                "-fx-background-color: rgba(245,234,214,0.08); -fx-border-color: rgba(201,168,76,0.4); -fx-border-width:1.5; -fx-background-radius:6; -fx-border-radius:6; -fx-text-fill:#f5ead6; -fx-cursor:hand;");
        return btn;
    }

    private Button crearBotonFantasma(String texto) {
        Button btn = new Button(texto);
        btn.setPrefWidth(260);
        btn.setPrefHeight(36);
        btn.setFont(Font.font("Georgia", 12));
        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: rgba(245,234,214,0.4);" +
                        "-fx-cursor: hand;");
        aplicarEfectoHover(btn,
                "-fx-background-color: transparent; -fx-text-fill: #f5ead6; -fx-cursor:hand;",
                "-fx-background-color: transparent; -fx-text-fill: rgba(245,234,214,0.4); -fx-cursor:hand;");
        return btn;
    }

    /** Aplica cambio de estilo al entrar/salir el cursor. */
    private void aplicarEfectoHover(Button btn, String estiloHover, String estiloNormal) {
        btn.setOnMouseEntered(e -> btn.setStyle(estiloHover));
        btn.setOnMouseExited(e -> btn.setStyle(estiloNormal));
    }

    // ── Animaciones ──────────────────────────────────────────────────────────

    /** Fade-in + slide-up al mostrar la pantalla. */
    private void animarEntrada(VBox contenido) {
        contenido.setOpacity(0);
        contenido.setTranslateY(24);

        FadeTransition fade = new FadeTransition(Duration.millis(800), contenido);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(800), contenido);
        slide.setFromY(24);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition entrada = new ParallelTransition(fade, slide);
        entrada.play();
    }

    // ── Acciones ─────────────────────────────────────────────────────────────

    private void accionNuevaPartida() {
        // TODO: cuando tengas la pantalla del tablero, reemplaza esta línea:
        // new PantallaJuego(stage).mostrar();
        System.out.println("► Navegar a PantallaJuego");

        // Animación de salida
        FadeTransition salida = new FadeTransition(Duration.millis(400), escena.getRoot());
        salida.setFromValue(1);
        salida.setToValue(0);
        salida.setOnFinished(e -> System.out.println("→ PantallaJuego lista"));
        salida.play();
    }

    private void accionComoJugar() {
        // TODO: puedes crear una PantallaReglas o un Dialog
        DialogReglas dialog = new DialogReglas(stage);
        dialog.mostrar();
    }

    private void accionSalir() {
        stage.close();
    }
}
