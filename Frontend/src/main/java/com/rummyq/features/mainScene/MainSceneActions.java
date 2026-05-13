package com.rummyq.features.mainScene;

import com.rummyq.core.DialogReglas;
import com.rummyq.view.GameScene;
import com.rummyq.view.LoginScene;

import javafx.animation.FadeTransition;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainSceneActions {

    private Stage stage;

    public MainSceneActions(Stage _stage) {
        stage = _stage;
    }

    public void startGame() {
        System.out.println("► FadeOut to GameScene...");

        GameScene gameScene = new GameScene(stage);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(800), stage.getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> gameScene.showScreen());
        fadeOut.play();
    }

    public void showLoginScene() {
        System.out.println("► FadeOut to LoginScene...");

        LoginScene loginScene = new LoginScene(stage);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(800), stage.getScene().getRoot());
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> loginScene.showScreen());
        fadeOut.play();
    }

    public void showRules() {
        DialogReglas dialog = new DialogReglas(stage);
        dialog.mostrar();
    }

    public void exitGame() {
        stage.close();
    }

}
