package com.rummyq.view;

import com.rummyq.MainApp;
import com.rummyq.model.ScreenConfig;
import com.rummyq.model.Usuario;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Pantalla de ingreso (Login) del juego RummyQ.
 *
 * Permite al usuario:
 * - Iniciar sesión con correo y contraseña
 * - Navegar a la pantalla de registro
 * - Recuperar contraseña
 */
public class PantallaLogin {

    // ── Colores ──────────────────────────────────────────────────────────────
    private static final String FELT_OSCURO = "#0f2d1a";
    private static final String FELT_MEDIO = "#1a472a";
    private static final String FELT_CLARO = "#2a5c3a";
    private static final String ORO = "#c9a84c";
    private static final String ORO_CLARO = "#f0d080";
    private static final String ORO_OSCURO = "#8a6a20";
    private static final String CREMA = "#f5ead6";
    private static final String ROJO_ERROR = "#e74c3c";

    // ── Atributos ────────────────────────────────────────────────────────────
    private final Stage stage;
    private Scene escena;

    private final int screenWidth = ScreenConfig.getScreenWidth();
    private final int screenHeight = ScreenConfig.getScreenHeight();

    // Campos del formulario
    private TextField campoCorreo;
    private PasswordField campoContrasena;
    private Label lblMensaje;

    // ── Constructor ──────────────────────────────────────────────────────────
    public PantallaLogin(Stage stage) {
        this.stage = stage;
    }

    // ── Método principal ─────────────────────────────────────────────────────
    public void mostrar() {
        StackPane raiz = new StackPane();
        raiz.getChildren().addAll(
                crearFondo(),
                crearBorde(),
                crearContenido());

        escena = new Scene(raiz, screenWidth, screenHeight);
        stage.setScene(escena);
        stage.show();
    }

    // ── Fondo y borde ────────────────────────────────────────────────────────
    private Pane crearFondo() {
        Pane fondo = new Pane();
        fondo.setPrefSize(screenWidth, screenHeight);
        Rectangle rect = new Rectangle(screenWidth, screenHeight);
        rect.setFill(new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.web(FELT_CLARO)),
                new Stop(0.5, Color.web(FELT_MEDIO)),
                new Stop(1.0, Color.web(FELT_OSCURO))));
        fondo.getChildren().add(rect);
        return fondo;
    }

    private Pane crearBorde() {
        Pane capa = new Pane();
        capa.setPrefSize(screenWidth, screenHeight);
        capa.setMouseTransparent(true);
        Rectangle borde = new Rectangle(20, 20,
                screenWidth - 40, screenHeight - 40);
        borde.setFill(Color.TRANSPARENT);
        borde.setStroke(Color.web(ORO_OSCURO));
        borde.setStrokeWidth(2.5);
        borde.setArcWidth(60);
        borde.setArcHeight(60);
        capa.getChildren().add(borde);
        return capa;
    }

    // ── Contenido ────────────────────────────────────────────────────────────
    private HBox crearContenido() {
        // Panel izquierdo — logo decorativo
        VBox panelIzq = crearPanelLogo();

        // Separador vertical
        Region separador = new Region();
        separador.setPrefWidth(1);
        separador.setStyle("-fx-background-color: rgba(201,168,76,0.25);");

        // Panel derecho — formulario
        VBox panelDer = crearPanelFormulario();

        HBox contenido = new HBox(panelIzq, separador, panelDer);
        contenido.setAlignment(Pos.CENTER);

        animarEntrada(contenido);
        return contenido;
    }

    // ── Panel izquierdo (logo) ───────────────────────────────────────────────
    private VBox crearPanelLogo() {
        VBox panel = new VBox(16);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefWidth(screenWidth * 0.45);
        panel.setPadding(new Insets(40));

        Label bienvenido = new Label("BIENVENIDO A");
        bienvenido.setFont(Font.font("Georgia", FontWeight.LIGHT, 12));
        bienvenido.setTextFill(Color.web(ORO, 0.8));

        Label logo = new Label("RummyQ");
        logo.setFont(Font.font("Georgia", FontWeight.BOLD, 72));
        logo.setTextFill(Color.web(ORO_CLARO));

        // Efecto glow pulsante
        Glow glow = new Glow(0);
        DropShadow sombra = new DropShadow(12, Color.BLACK);
        sombra.setInput(glow);
        logo.setEffect(sombra);

        Timeline pulso = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(2), new KeyValue(glow.levelProperty(), 0.5)));
        pulso.setAutoReverse(true);
        pulso.setCycleCount(Animation.INDEFINITE);
        pulso.play();

        // Separador ornamental
        HBox ornamento = crearOrnamento();

        Label subtitulo = new Label("El clásico juego de fichas");
        subtitulo.setFont(Font.font("Georgia", FontWeight.LIGHT, 13));
        subtitulo.setTextFill(Color.web(CREMA, 0.55));

        // Fichas decorativas
        HBox fichas = crearFichasDecorativas();

        panel.getChildren().addAll(bienvenido, logo, ornamento, subtitulo, fichas);
        return panel;
    }

    private HBox crearOrnamento() {
        Region l1 = new Region();
        l1.setPrefSize(60, 1);
        l1.setStyle("-fx-background-color: linear-gradient(to right, transparent, " + ORO + ");");

        Label diamante = new Label("◆");
        diamante.setTextFill(Color.web(ORO));
        diamante.setFont(Font.font(9));
        diamante.setPadding(new Insets(0, 8, 0, 8));

        Region l2 = new Region();
        l2.setPrefSize(60, 1);
        l2.setStyle("-fx-background-color: linear-gradient(to left, transparent, " + ORO + ");");

        HBox h = new HBox(l1, diamante, l2);
        h.setAlignment(Pos.CENTER);
        return h;
    }

    private HBox crearFichasDecorativas() {
        String[] numeros = { "7", "☆", "3", "11" };
        String[] colores = { "#c0392b", "#c9a84c", "#1a5276", "#d35400" };
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20, 0, 0, 0));

        for (int i = 0; i < numeros.length; i++) {
            Label ficha = new Label(numeros[i]);
            ficha.setPrefSize(48, 62);
            ficha.setAlignment(Pos.CENTER);
            ficha.setFont(Font.font("Georgia", FontWeight.BOLD, 16));
            ficha.setTextFill(Color.web(colores[i]));
            ficha.setStyle(
                    "-fx-background-color: #f5ead6;" +
                            "-fx-background-radius: 6;" +
                            "-fx-border-color: #d4c8a0;" +
                            "-fx-border-width: 1.5;" +
                            "-fx-border-radius: 6;");
            DropShadow s = new DropShadow(6, Color.BLACK);
            ficha.setEffect(s);

            // Animación flotante
            TranslateTransition tt = new TranslateTransition(Duration.seconds(2 + i * 0.4), ficha);
            tt.setFromY(0);
            tt.setToY(-8);
            tt.setAutoReverse(true);
            tt.setCycleCount(Animation.INDEFINITE);
            tt.setDelay(Duration.seconds(i * 0.3));
            tt.play();

            hbox.getChildren().add(ficha);
        }
        return hbox;
    }

    // ── Panel derecho (formulario login) ─────────────────────────────────────
    private VBox crearPanelFormulario() {
        VBox panel = new VBox(18);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefWidth(screenWidth * 0.55);
        panel.setPadding(new Insets(50, 60, 50, 60));

        // Tarjeta del formulario
        VBox tarjeta = new VBox(16);
        tarjeta.setAlignment(Pos.CENTER_LEFT);
        tarjeta.setPadding(new Insets(36, 40, 36, 40));
        tarjeta.setStyle(
                "-fx-background-color: rgba(10,26,16,0.75);" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: rgba(201,168,76,0.3);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 14;");
        DropShadow sombra = new DropShadow(30, Color.BLACK);
        sombra.setSpread(0.1);
        tarjeta.setEffect(sombra);

        // Título
        Label titulo = new Label("Iniciar Sesión");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.web(ORO_CLARO));

        Label subtitulo = new Label("Ingresa tus credenciales para continuar");
        subtitulo.setFont(Font.font("Georgia", 12));
        subtitulo.setTextFill(Color.web(CREMA, 0.5));
        VBox.setMargin(subtitulo, new Insets(0, 0, 10, 0));

        // Campos
        VBox grupoCorreo = crearCampoTexto("Correo electrónico", "ejemplo@correo.com", false);
        campoCorreo = (TextField) grupoCorreo.lookup("#campo");

        VBox grupoContrasena = crearCampoTexto("Contraseña", "••••••••", true);
        campoContrasena = (PasswordField) grupoContrasena.lookup("#campo");

        // Mensaje de error/éxito
        lblMensaje = new Label("");
        lblMensaje.setFont(Font.font("Georgia", 12));
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(280);

        // Botón ingresar
        Button btnIngresar = crearBotonPrimario("Ingresar");
        btnIngresar.setMaxWidth(Double.MAX_VALUE);
        btnIngresar.setOnAction(e -> accionLogin());

        // Separador
        HBox sep = crearSeparadorTexto("¿No tienes cuenta?");

        // Botón registrarse
        Button btnRegistrar = crearBotonSecundario("Crear cuenta nueva");
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnRegistrar.setOnAction(e -> abrirRegistro());

        // Olvidé contraseña
        Button btnOlvide = crearBotonFantasma("Olvidé mi contraseña");
        btnOlvide.setOnAction(e -> abrirRecuperacion());

        // Enter activa login
        campoContrasena.setOnAction(e -> accionLogin());

        tarjeta.getChildren().addAll(
                titulo, subtitulo,
                grupoCorreo, grupoContrasena,
                lblMensaje,
                btnIngresar, sep, btnRegistrar, btnOlvide);

        panel.getChildren().add(tarjeta);
        return panel;
    }

    // ── Componentes del formulario ───────────────────────────────────────────

    private VBox crearCampoTexto(String etiqueta, String placeholder, boolean esPassword) {
        Label lbl = new Label(etiqueta);
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        lbl.setTextFill(Color.web(ORO, 0.9));

        String estiloBase = "-fx-background-color: rgba(245,234,214,0.07);" +
                "-fx-border-color: rgba(201,168,76,0.3);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;" +
                "-fx-text-fill: #f5ead6;" +
                "-fx-prompt-text-fill: rgba(245,234,214,0.35);" +
                "-fx-font-family: Georgia;" +
                "-fx-font-size: 13;" +
                "-fx-padding: 10 14;";

        String estiloFocus = "-fx-background-color: rgba(245,234,214,0.10);" +
                "-fx-border-color: rgba(201,168,76,0.75);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;" +
                "-fx-text-fill: #f5ead6;" +
                "-fx-prompt-text-fill: rgba(245,234,214,0.35);" +
                "-fx-font-family: Georgia;" +
                "-fx-font-size: 13;" +
                "-fx-padding: 10 14;";

        Control campo;
        if (esPassword) {
            PasswordField pf = new PasswordField();
            pf.setPromptText(placeholder);
            pf.setId("campo");
            pf.setStyle(estiloBase);
            pf.focusedProperty().addListener((o, a, focus) -> pf.setStyle(focus ? estiloFocus : estiloBase));
            campo = pf;
        } else {
            TextField tf = new TextField();
            tf.setPromptText(placeholder);
            tf.setId("campo");
            tf.setStyle(estiloBase);
            tf.focusedProperty().addListener((o, a, focus) -> tf.setStyle(focus ? estiloFocus : estiloBase));
            campo = tf;
        }

        campo.setPrefHeight(44);
        campo.setMaxWidth(Double.MAX_VALUE);

        VBox grupo = new VBox(6, lbl, campo);
        grupo.setMaxWidth(Double.MAX_VALUE);
        return grupo;
    }

    private HBox crearSeparadorTexto(String texto) {
        Region l1 = new Region();
        HBox.setHgrow(l1, Priority.ALWAYS);
        l1.setPrefHeight(1);
        l1.setStyle("-fx-background-color: rgba(201,168,76,0.2);");

        Label lbl = new Label("  " + texto + "  ");
        lbl.setFont(Font.font("Georgia", 11));
        lbl.setTextFill(Color.web(CREMA, 0.4));

        Region l2 = new Region();
        HBox.setHgrow(l2, Priority.ALWAYS);
        l2.setPrefHeight(1);
        l2.setStyle("-fx-background-color: rgba(201,168,76,0.2);");

        HBox h = new HBox(l1, lbl, l2);
        h.setAlignment(Pos.CENTER);
        return h;
    }

    // ── Estilos de botones ───────────────────────────────────────────────────
    private Button crearBotonPrimario(String texto) {
        Button btn = new Button(texto);
        btn.setPrefHeight(46);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        String estilo = "-fx-background-color: linear-gradient(to bottom, " + ORO + ", " + ORO_OSCURO + ");" +
                "-fx-text-fill: #1a0e00; -fx-background-radius: 6; -fx-cursor: hand;";
        String hover = "-fx-background-color: linear-gradient(to bottom, " + ORO_CLARO + ", " + ORO + ");" +
                "-fx-text-fill: #1a0e00; -fx-background-radius: 6; -fx-cursor: hand;";
        btn.setStyle(estilo);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(estilo));
        btn.setEffect(new DropShadow(10, Color.web(ORO, 0.35)));
        return btn;
    }

    private Button crearBotonSecundario(String texto) {
        Button btn = new Button(texto);
        btn.setPrefHeight(44);
        btn.setFont(Font.font("Georgia", 13));
        String estilo = "-fx-background-color: transparent; -fx-text-fill: " + CREMA + ";" +
                "-fx-border-color: rgba(201,168,76,0.4); -fx-border-width: 1.5;" +
                "-fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand;";
        String hover = "-fx-background-color: rgba(245,234,214,0.08); -fx-text-fill: " + CREMA + ";" +
                "-fx-border-color: rgba(201,168,76,0.7); -fx-border-width: 1.5;" +
                "-fx-border-radius: 6; -fx-background-radius: 6; -fx-cursor: hand;";
        btn.setStyle(estilo);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(estilo));
        return btn;
    }

    private Button crearBotonFantasma(String texto) {
        Button btn = new Button(texto);
        btn.setFont(Font.font("Georgia", 12));
        String estilo = "-fx-background-color: transparent; -fx-text-fill: rgba(245,234,214,0.4); -fx-cursor: hand;";
        String hover = "-fx-background-color: transparent; -fx-text-fill: " + ORO + "; -fx-cursor: hand;";
        btn.setStyle(estilo);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(estilo));
        return btn;
    }

    // ── Acciones ─────────────────────────────────────────────────────────────
    private void accionLogin() {
        String correo = campoCorreo.getText().trim();
        String contrasena = campoContrasena.getText();

        // Validaciones básicas
        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarMensaje("Por favor completa todos los campos.", false);
            return;
        }
        if (!correo.contains("@")) {
            mostrarMensaje("Ingresa un correo electrónico válido.", false);
            return;
        }

        // Consultar base de datos
        String hash = "";
        Usuario usuario = new Usuario();

        if (usuario != null) {
            mostrarMensaje("¡Bienvenido, " + usuario.getNombre() + "!", true);
            // TODO: navegar al lobby/menú principal
            // new PantallaInicio(stage).mostrar();
        } else {
            mostrarMensaje("Correo o contraseña incorrectos.", false);
            sacudirCampos();
        }
    }

    private void mostrarMensaje(String texto, boolean esExito) {
        lblMensaje.setText(texto);
        lblMensaje.setTextFill(esExito ? Color.web("#2ecc71") : Color.web(ROJO_ERROR));
    }

    /** Animación de sacudida cuando las credenciales son incorrectas. */
    private void sacudirCampos() {
        TranslateTransition sacudir = new TranslateTransition(Duration.millis(60), campoContrasena);
        sacudir.setFromX(0);
        sacudir.setByX(10);
        sacudir.setCycleCount(6);
        sacudir.setAutoReverse(true);
        sacudir.setOnFinished(e -> campoContrasena.setTranslateX(0));
        sacudir.play();
        campoContrasena.clear();
    }

    private void abrirRegistro() {
        new PantallaRegistro(stage).mostrar();
    }

    private void abrirRecuperacion() {
        new PantallaRecuperacion(stage).mostrar();
    }

    private void animarEntrada(HBox contenido) {
        contenido.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(700), contenido);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
