package com.rummyq;

import com.rummyq.model.ScreenConfig;
import com.rummyq.view.MainScene;
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

    @Override
    public void start(Stage stagePrincipal) {
        stagePrincipal.setTitle(ScreenConfig.gameTitle);
        stagePrincipal.setWidth(ScreenConfig.getScreenWidth());
        stagePrincipal.setHeight(ScreenConfig.getScreenHeight());
        stagePrincipal.setResizable(false);

        // Mostrar la pantalla inicial
        // PantallaRegistro pantallaRegistro = new PantallaRegistro(stagePrincipal);
        // pantallaRegistro.mostrar();

        MainScene pantallaInicio = new MainScene(stagePrincipal);
        pantallaInicio.showScreen();
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
