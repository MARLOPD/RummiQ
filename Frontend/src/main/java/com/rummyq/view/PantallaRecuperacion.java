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
 * Pantalla de recuperación de contraseña.
 *
 * Flujo:
 * Paso 1 — El usuario ingresa su correo
 * Paso 2 — Se muestra la pregunta de seguridad; responde
 * Paso 3 — Ingresa y confirma la nueva contraseña
 */
public class PantallaRecuperacion {

    private static final String ORO = "#c9a84c";
    private static final String ORO_CLARO = "#f0d080";
    private static final String ORO_OSCURO = "#8a6a20";
    private static final String CREMA = "#f5ead6";
    private static final String ROJO = "#e74c3c";
    private static final String VERDE = "#2ecc71";

    private final Stage stage;

    // Estado del flujo
    private Usuario usuarioEncontrado = null;
    private int paso = 1;

    // Campos
    private TextField campoCorreo;
    private TextField campoRespuesta;
    private PasswordField campaNuevaPass;
    private PasswordField campoConfirmarPass;
    private Label lblPregunta;
    private Label lblMensaje;
    private Button btnAccion;

    // Secciones del formulario
    private VBox seccionCorreo;
    private VBox seccionRespuesta;
    private VBox seccionNuevaPass;

    public PantallaRecuperacion(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        StackPane raiz = new StackPane();
        raiz.getChildren().addAll(crearFondo(), crearContenido());

        Scene escena = new Scene(raiz, MainApp.ANCHO_VENTANA, MainApp.ALTO_VENTANA);
        stage.setScene(escena);
        stage.show();
    }

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

    private VBox crearContenido() {
        VBox contenido = new VBox(16);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(40));

        // Botón volver
        Button btnVolver = new Button("← Volver al login");
        btnVolver.setFont(Font.font("Georgia", 13));
        String ev = "-fx-background-color:transparent; -fx-text-fill:rgba(201,168,76,0.7); -fx-cursor:hand;";
        String hv = "-fx-background-color:transparent; -fx-text-fill:" + ORO_CLARO + "; -fx-cursor:hand;";
        btnVolver.setStyle(ev);
        btnVolver.setOnMouseEntered(e -> btnVolver.setStyle(hv));
        btnVolver.setOnMouseExited(e -> btnVolver.setStyle(ev));
        btnVolver.setOnAction(e -> new PantallaLogin(stage).mostrar());

        HBox encabezado = new HBox(btnVolver);
        encabezado.setMaxWidth(420);

        // Tarjeta
        VBox tarjeta = new VBox(16);
        tarjeta.setAlignment(Pos.CENTER_LEFT);
        tarjeta.setPadding(new Insets(36, 48, 36, 48));
        tarjeta.setMaxWidth(420);
        tarjeta.setStyle(
                "-fx-background-color: rgba(10,26,16,0.82);" +
                        "-fx-background-radius:14; -fx-border-color:rgba(201,168,76,0.35);" +
                        "-fx-border-width:1.5; -fx-border-radius:14;");
        tarjeta.setEffect(new DropShadow(30, Color.BLACK));

        Label titulo = new Label("Recuperar Contraseña");
        titulo.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        titulo.setTextFill(Color.web(ORO_CLARO));

        Label subtitulo = new Label("Sigue los pasos para restablecer tu acceso");
        subtitulo.setFont(Font.font("Georgia", 12));
        subtitulo.setTextFill(Color.web(CREMA, 0.5));

        // ── Paso 1: correo ──
        seccionCorreo = crearSeccion("Paso 1 — Ingresa tu correo");
        campoCorreo = crearTextField("tu@correo.com");
        seccionCorreo.getChildren().add(campoCorreo);

        // ── Paso 2: pregunta de seguridad ──
        seccionRespuesta = crearSeccion("Paso 2 — Pregunta de seguridad");
        lblPregunta = new Label("");
        lblPregunta.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        lblPregunta.setTextFill(Color.web(ORO, 0.9));
        lblPregunta.setWrapText(true);
        campoRespuesta = crearTextField("Tu respuesta");
        seccionRespuesta.getChildren().addAll(lblPregunta, campoRespuesta);
        seccionRespuesta.setVisible(false);
        seccionRespuesta.setManaged(false);

        // ── Paso 3: nueva contraseña ──
        seccionNuevaPass = crearSeccion("Paso 3 — Nueva contraseña");
        campaNuevaPass = crearPasswordField("Nueva contraseña (mín. 6 caracteres)");
        campoConfirmarPass = crearPasswordField("Confirmar nueva contraseña");
        seccionNuevaPass.getChildren().addAll(campaNuevaPass, campoConfirmarPass);
        seccionNuevaPass.setVisible(false);
        seccionNuevaPass.setManaged(false);

        // Mensaje
        lblMensaje = new Label("");
        lblMensaje.setFont(Font.font("Georgia", 12));
        lblMensaje.setWrapText(true);
        lblMensaje.setMaxWidth(320);

        // Botón de acción (cambia de texto según el paso)
        btnAccion = crearBotonPrimario("Continuar");
        btnAccion.setMaxWidth(Double.MAX_VALUE);
        btnAccion.setOnAction(e -> avanzarPaso());

        tarjeta.getChildren().addAll(
                titulo, subtitulo,
                seccionCorreo, seccionRespuesta, seccionNuevaPass,
                lblMensaje, btnAccion);

        contenido.getChildren().addAll(encabezado, tarjeta);

        FadeTransition fade = new FadeTransition(Duration.millis(600), contenido);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        return contenido;
    }

    // ── Helpers UI ───────────────────────────────────────────────────────────
    private VBox crearSeccion(String tituloSeccion) {
        Label lbl = new Label(tituloSeccion);
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        lbl.setTextFill(Color.web(ORO, 0.85));
        VBox v = new VBox(8, lbl);
        v.setMaxWidth(Double.MAX_VALUE);
        return v;
    }

    private TextField crearTextField(String placeholder) {
        TextField tf = new TextField();
        tf.setPromptText(placeholder);
        tf.setPrefHeight(44);
        tf.setMaxWidth(Double.MAX_VALUE);
        String base = "-fx-background-color:rgba(245,234,214,0.07); -fx-border-color:rgba(201,168,76,0.3);" +
                "-fx-border-width:1; -fx-border-radius:6; -fx-background-radius:6;" +
                "-fx-text-fill:#f5ead6; -fx-prompt-text-fill:rgba(245,234,214,0.35);" +
                "-fx-font-family:Georgia; -fx-font-size:13; -fx-padding:10 14;";
        tf.setStyle(base);
        return tf;
    }

    private PasswordField crearPasswordField(String placeholder) {
        PasswordField pf = new PasswordField();
        pf.setPromptText(placeholder);
        pf.setPrefHeight(44);
        pf.setMaxWidth(Double.MAX_VALUE);
        pf.setStyle("-fx-background-color:rgba(245,234,214,0.07); -fx-border-color:rgba(201,168,76,0.3);" +
                "-fx-border-width:1; -fx-border-radius:6; -fx-background-radius:6;" +
                "-fx-text-fill:#f5ead6; -fx-prompt-text-fill:rgba(245,234,214,0.35);" +
                "-fx-font-family:Georgia; -fx-font-size:13; -fx-padding:10 14;");
        return pf;
    }

    private Button crearBotonPrimario(String texto) {
        Button btn = new Button(texto);
        btn.setPrefHeight(46);
        btn.setFont(Font.font("Georgia", FontWeight.BOLD, 14));
        String e = "-fx-background-color:linear-gradient(to bottom," + ORO + "," + ORO_OSCURO + ");" +
                "-fx-text-fill:#1a0e00; -fx-background-radius:6; -fx-cursor:hand;";
        String h = "-fx-background-color:linear-gradient(to bottom," + ORO_CLARO + "," + ORO + ");" +
                "-fx-text-fill:#1a0e00; -fx-background-radius:6; -fx-cursor:hand;";
        btn.setStyle(e);
        btn.setOnMouseEntered(ev -> btn.setStyle(h));
        btn.setOnMouseExited(ev -> btn.setStyle(e));
        btn.setEffect(new DropShadow(10, Color.web(ORO, 0.35)));
        return btn;
    }

    // ── Lógica de pasos ──────────────────────────────────────────────────────
    private void avanzarPaso() {
        switch (paso) {
            case 1 -> verificarCorreo();
            case 2 -> verificarRespuesta();
            case 3 -> cambiarContrasena();
        }
    }

    private void verificarCorreo() {
        String correo = campoCorreo.getText().trim();
        if (correo.isEmpty()) {
            mostrarMensaje("Ingresa tu correo.", false);
            return;
        }

        usuarioEncontrado = null;
        if (usuarioEncontrado == null) {
            mostrarMensaje("No existe una cuenta con ese correo.", false);
            return;
        }

        // Mostrar pregunta de seguridad
        lblPregunta.setText(usuarioEncontrado.getPreguntaSeguridad());
        mostrarSeccion(seccionRespuesta);
        lblMensaje.setText("");
        btnAccion.setText("Verificar respuesta");
        paso = 2;
    }

    private void verificarRespuesta() {
        String respuesta = campoRespuesta.getText().trim();
        if (respuesta.isEmpty()) {
            mostrarMensaje("Ingresa tu respuesta.", false);
            return;
        }

        boolean correcta = false;
        if (!correcta) {
            mostrarMensaje("Respuesta incorrecta. Intenta de nuevo.", false);
            return;
        }

        mostrarSeccion(seccionNuevaPass);
        lblMensaje.setText("");
        btnAccion.setText("Cambiar contraseña");
        paso = 3;
    }

    private void cambiarContrasena() {
        String nueva = campaNuevaPass.getText();
        String confirmar = campoConfirmarPass.getText();

        if (nueva.length() < 6) {
            mostrarMensaje("La contraseña debe tener al menos 6 caracteres.", false);
            return;
        }
        if (!nueva.equals(confirmar)) {
            mostrarMensaje("Las contraseñas no coinciden.", false);
            return;
        }

        boolean actualizado = false;

        if (actualizado) {
            mostrarMensaje("¡Contraseña actualizada! Redirigiendo al login…", true);
            btnAccion.setDisable(true);
            javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(Duration.seconds(2));
            pausa.setOnFinished(e -> new PantallaLogin(stage).mostrar());
            pausa.play();
        } else {
            mostrarMensaje("Error al actualizar. Intenta de nuevo.", false);
        }
    }

    private void mostrarSeccion(VBox seccion) {
        seccion.setVisible(true);
        seccion.setManaged(true);
        FadeTransition fade = new FadeTransition(Duration.millis(400), seccion);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void mostrarMensaje(String texto, boolean esExito) {
        lblMensaje.setText(texto);
        lblMensaje.setTextFill(esExito ? Color.web(VERDE) : Color.web(ROJO));
    }
}
