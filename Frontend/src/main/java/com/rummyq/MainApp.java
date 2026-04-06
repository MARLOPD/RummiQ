package com.rummyq;

import com.rummyq.view.PantallaLogin;
import com.rummyq.view.PantallaRecuperacion;
import com.rummyq.view.PantallaRegistro;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación RummyQ.
 * Punto de entrada del juego.
 */
public class MainApp extends Application {

    public static final String TITULO_JUEGO = "RummyQ";
    public static final int ANCHO_VENTANA = 1280;
    public static final int ALTO_VENTANA = 720;

    @Override
    public void start(Stage stagePrincipal) {
        stagePrincipal.setTitle(TITULO_JUEGO);
        stagePrincipal.setWidth(ANCHO_VENTANA);
        stagePrincipal.setHeight(ALTO_VENTANA);
        stagePrincipal.setResizable(false);

        // Mostrar la pantalla inicial
        // PantallaRegistro pantallaRegistro = new PantallaRegistro(stagePrincipal);
        // pantallaRegistro.mostrar();

        PantallaInicio pantallaInicio = new PantallaInicio(stagePrincipal);
        pantallaInicio.mostrar();
        // PantallaRecuperacion pantallaRecuperacion = new
        // PantallaRecuperacion(stagePrincipal);
        // pantallaRecuperacion.mostrar();

        // PantallaLogin pantallaLogin = new PantallaLogin(stagePrincipal);
        // pantallaLogin.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
