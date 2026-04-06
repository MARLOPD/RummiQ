package com.rummyq.view;

import com.rummyq.MainApp;
import com.rummyq.model.Usuario;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Pantalla de registro de nuevo usuario.
 * Campos: correo, contraseña, confirmar contraseña,
 * pregunta de seguridad, respuesta de seguridad.
 */
public class PantallaRegistro {

    private static final String ORO = "#c9a84c";
    private static final String ORO_CLARO = "#f0d080";
    private static final String ORO_OSCURO = "#8a6a20";
    private static final String CREMA = "#f5ead6";
    private static final String ROJO = "#e74c3c";
    private static final String VERDE = "#2ecc71";

    private static final String[] PREGUNTAS_SEGURIDAD = {
            "¿Cuál es el nombre de tu primera mascota?",
            "¿En qué ciudad naciste?",
            "¿Cuál es el apellido de soltera de tu madre?",
            "¿Cuál fue tu primer trabajo?",
            "¿Cuál es el nombre de tu mejor amigo de infancia?"
    };

    private final Stage stage;

    // Campos del formulario
    private TextField campoNombre;
    private TextField campoCorreo;
    private PasswordField campoContrasena;
    private PasswordField campoConfirmar;
    private ComboBox<String> comboPregunta;
    private TextField campoRespuesta;
    private Label lblMensaje;

    public PantallaRegistro(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        StackPane raiz = new StackPane();
        raiz.getChildren().addAll(crearFondo(), crearContenido());

        Scene escena = new Scene(raiz, MainApp.ANCHO_VENTANA, MainApp.ALTO_VENTANA);
        stage.setScene(escena);
        stage.show();
    }

    // ── Fondo ────────────────────────────────────────────────────────────────
    private Pane crearFondo() {
        Pane fondo = new Pane();
        fondo.setPrefSize(MainApp.ANCHO_VENTANA, MainApp.ALTO_VENTANA);
        Rectangle rect = new Rectangle(MainApp.ANCHO_VENTANA, MainApp.ALTO_VENTANA);
        rect.setFill(new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.web("#2a5c3a")),
                new Stop(0.5, Color.web("#1a472a")),
                new Stop(1.0, Color.web("#0f2d1a"))));
        fondo.getChildren().add(rect);
        return fondo;
    }

    // ── Contenido ────────────────────────────────────────────────────────────
    private VBox crearContenido() {
        VBox contenido = new VBox(0);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(30));

        // Encabezado con botón volver
        HBox encabezado = crearEncabezado();

        // Tarjeta del formulario
        VBox tarjeta = new VBox(14);
        tarjeta.setAlignment(Pos.CENTER_LEFT);
        tarjeta.setPadding(new Insets(36, 48, 36, 48));
        tarjeta.setMaxWidth(480);
        tarjeta.setStyle(
                "-fx-background-color: rgba(10,26,16,0.80);" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: rgba(201,168,76,0.35);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 14;");
        tarjeta.setEffect(new DropShadow(30, Color.BLACK));

        // Título
        Label titulo = new Label("Crear Cuenta");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        titulo.setTextFill(Color.web(ORO_CLARO));

        Label subtitulo = new Label("Completa los campos para registrarte");
        subtitulo.setFont(Font.font("Georgia", 12));
        subtitulo.setTextFill(Color.web(CREMA, 0.5));
        VBox.setMargin(subtitulo, new Insets(0, 0, 8, 0));

        // Campos
        VBox grupoNombre = crearCampo("Nombre en línea", "Ej: ReyDelRummy, Tigre22…", false);
        VBox grupoCorreo = crearCampo("Correo electrónico", "ejemplo@correo.com", false);
        VBox grupoContrasena = crearCampo("Contraseña", "Mínimo 6 caracteres", true);
        VBox grupoConfirmar = crearCampo("Confirmar contraseña", "Repite tu contraseña", true);
        VBox grupoPregunta = crearComboPregunta();
        VBox grupoRespuesta = crearCampo("Respuesta de seguridad", "Tu respuesta", false);

        campoNombre = (TextField) grupoNombre.lookup("#campo");
        campoCorreo = (TextField) grupoCorreo.lookup("#campo");
        campoContrasena = (PasswordField) grupoContrasena.lookup("#campo");
        campoConfirmar = (PasswordField) grupoConfirmar.lookup("#campo");
        campoRespuesta = (TextField) grupoRespuesta.lookup("#campo");

        // Mensaje
        lblMensaje = new Label("");
        lblMensaje.setFont(Font.font("Georgia", 12));
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(380);

        // Botón registrar
        Button btnRegistrar = crearBotonPrimario("Crear cuenta");
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnRegistrar.setOnAction(e -> accionRegistrar());

        tarjeta.getChildren().addAll(
                titulo, subtitulo,
                grupoNombre, grupoCorreo, grupoContrasena, grupoConfirmar,
                grupoPregunta, grupoRespuesta,
                lblMensaje, btnRegistrar);

        VBox.setMargin(encabezado, new Insets(0, 0, 16, 0));
        contenido.getChildren().addAll(encabezado, tarjeta);

        FadeTransition fade = new FadeTransition(Duration.millis(600), contenido);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        return contenido;
    }

    private HBox crearEncabezado() {
        Button btnVolver = new Button("← Volver al login");
        btnVolver.setFont(Font.font("Georgia", 13));
        String estilo = "-fx-background-color: transparent; -fx-text-fill: rgba(201,168,76,0.7); -fx-cursor: hand;";
        String hover = "-fx-background-color: transparent; -fx-text-fill: " + ORO_CLARO + "; -fx-cursor: hand;";
        btnVolver.setStyle(estilo);
        btnVolver.setOnMouseEntered(e -> btnVolver.setStyle(hover));
        btnVolver.setOnMouseExited(e -> btnVolver.setStyle(estilo));
        btnVolver.setOnAction(e -> new PantallaLogin(stage).mostrar());

        HBox h = new HBox(btnVolver);
        h.setMaxWidth(480);
        return h;
    }

    // ── Campos ───────────────────────────────────────────────────────────────
    private VBox crearCampo(String etiqueta, String placeholder, boolean esPassword) {
        Label lbl = new Label(etiqueta);
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        lbl.setTextFill(Color.web(ORO, 0.9));

        String base = "-fx-background-color: rgba(245,234,214,0.07);" +
                "-fx-border-color: rgba(201,168,76,0.3); -fx-border-width:1;" +
                "-fx-border-radius:6; -fx-background-radius:6;" +
                "-fx-text-fill:#f5ead6; -fx-prompt-text-fill:rgba(245,234,214,0.35);" +
                "-fx-font-family:Georgia; -fx-font-size:13; -fx-padding:10 14;";
        String focus = "-fx-background-color: rgba(245,234,214,0.10);" +
                "-fx-border-color: rgba(201,168,76,0.75); -fx-border-width:1.5;" +
                "-fx-border-radius:6; -fx-background-radius:6;" +
                "-fx-text-fill:#f5ead6; -fx-prompt-text-fill:rgba(245,234,214,0.35);" +
                "-fx-font-family:Georgia; -fx-font-size:13; -fx-padding:10 14;";

        Control campo;
        if (esPassword) {
            PasswordField pf = new PasswordField();
            pf.setPromptText(placeholder);
            pf.setId("campo");
            pf.setStyle(base);
            pf.focusedProperty().addListener((o, a, f) -> pf.setStyle(f ? focus : base));
            campo = pf;
        } else {
            TextField tf = new TextField();
            tf.setPromptText(placeholder);
            tf.setId("campo");
            tf.setStyle(base);
            tf.focusedProperty().addListener((o, a, f) -> tf.setStyle(f ? focus : base));
            campo = tf;
        }
        campo.setPrefHeight(44);
        campo.setMaxWidth(Double.MAX_VALUE);

        VBox g = new VBox(5, lbl, campo);
        g.setMaxWidth(Double.MAX_VALUE);
        return g;
    }

    private VBox crearComboPregunta() {
        Label lbl = new Label("Pregunta de seguridad");
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        lbl.setTextFill(Color.web(ORO, 0.9));

        comboPregunta = new ComboBox<>();
        comboPregunta.getItems().addAll(PREGUNTAS_SEGURIDAD);
        comboPregunta.setPromptText("Selecciona una pregunta…");
        comboPregunta.setMaxWidth(Double.MAX_VALUE);
        comboPregunta.setPrefHeight(44);
        comboPregunta.setStyle(
                "-fx-background-color: rgba(245,234,214,0.07);" +
                        "-fx-border-color: rgba(201,168,76,0.3); -fx-border-width:1;" +
                        "-fx-border-radius:6; -fx-background-radius:6;" +
                        "-fx-font-family:Georgia; -fx-font-size:12;");

        VBox g = new VBox(5, lbl, comboPregunta);
        g.setMaxWidth(Double.MAX_VALUE);
        return g;
    }

    // ── Botón ────────────────────────────────────────────────────────────────
    private Button crearBotonPrimario(String texto) {
        Button btn = new Button(texto);
        btn.setPrefHeight(46);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        String e = "-fx-background-color: linear-gradient(to bottom," + ORO + "," + ORO_OSCURO + ");" +
                "-fx-text-fill:#1a0e00; -fx-background-radius:6; -fx-cursor:hand;";
        String h = "-fx-background-color: linear-gradient(to bottom," + ORO_CLARO + "," + ORO + ");" +
                "-fx-text-fill:#1a0e00; -fx-background-radius:6; -fx-cursor:hand;";
        btn.setStyle(e);
        btn.setOnMouseEntered(ev -> btn.setStyle(h));
        btn.setOnMouseExited(ev -> btn.setStyle(e));
        btn.setEffect(new DropShadow(10, Color.web(ORO, 0.35)));
        return btn;
    }

    // ── Acción registrar ─────────────────────────────────────────────────────
    private void accionRegistrar() {
        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();
        String pass = campoContrasena.getText();
        String confirmar = campoConfirmar.getText();
        String pregunta = comboPregunta.getValue();
        String respuesta = campoRespuesta.getText().trim();

        // Validaciones
        if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty() || confirmar.isEmpty() || respuesta.isEmpty()) {
            mostrarMensaje("Por favor completa todos los campos.", false);
            return;
        }
        if (nombre.length() < 3) {
            mostrarMensaje("El nombre en línea debe tener al menos 3 caracteres.", false);
            return;
        }
        if (nombre.contains(" ")) {
            mostrarMensaje("El nombre en línea no puede tener espacios.", false);
            return;
        }
        if (!correo.contains("@") || !correo.contains(".")) {
            mostrarMensaje("Ingresa un correo electrónico válido.", false);
            return;
        }
        if (pass.length() < 6) {
            mostrarMensaje("La contraseña debe tener al menos 6 caracteres.", false);
            return;
        }
        if (!pass.equals(confirmar)) {
            mostrarMensaje("Las contraseñas no coinciden.", false);
            return;
        }
        if (pregunta == null) {
            mostrarMensaje("Selecciona una pregunta de seguridad.", false);
            return;
        }

        // Crear y guardar usuario
        Usuario nuevo = new Usuario(
                correo,
                "",
                pregunta,
                "");
        nuevo.setNombre(nombre); // nombre en línea elegido por el jugador

        boolean registrado = false;

        if (registrado) {
            mostrarMensaje("¡Cuenta creada exitosamente! Redirigiendo…", true);
            // Ir al login después de 1.5 segundos
            javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(Duration.seconds(1.5));
            pausa.setOnFinished(e -> new PantallaLogin(stage).mostrar());
            pausa.play();
        } else {
            mostrarMensaje("Este correo ya está registrado. Intenta con otro.", false);
        }
    }

    private void mostrarMensaje(String texto, boolean esExito) {
        lblMensaje.setText(texto);
        lblMensaje.setTextFill(esExito ? Color.web(VERDE) : Color.web(ROJO));
    }
}
