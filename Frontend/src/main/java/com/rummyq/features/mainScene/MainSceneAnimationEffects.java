package com.rummyq.features.mainScene;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainSceneAnimationEffects {

    public void hoverEffect(Button btn, String hoverStyle, String normalStyle) {
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));
    }

    public void startAnimation(VBox content) {
        content.setOpacity(0);
        content.setTranslateY(24);

        FadeTransition fade = new FadeTransition(Duration.millis(800), content);
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition slide = new TranslateTransition(Duration.millis(800), content);
        slide.setFromY(24);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition entryTransition = new ParallelTransition(fade, slide);
        entryTransition.play();
    }
}
