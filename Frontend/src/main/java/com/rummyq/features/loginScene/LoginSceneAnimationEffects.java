package com.rummyq.features.loginScene;

import javafx.animation.FadeTransition;
import javafx.scene.control.PasswordField;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class LoginSceneAnimationEffects {
    public static void shakeFields(PasswordField passField) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(60), passField);
        shake.setFromX(0);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.setOnFinished(e -> passField.setTranslateX(0));
        shake.play();
        passField.clear();
    }

    public static void startSceneAnimation(HBox box) {
        box.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(100), box);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}