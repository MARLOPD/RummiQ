package com.rummyq;

import com.rummyq.model.ScreenConfig;
import com.rummyq.view.GameScene;
import com.rummyq.view.LoginScene;

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

        GameScene firstScene = new GameScene(stagePrincipal);
        firstScene.showScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
