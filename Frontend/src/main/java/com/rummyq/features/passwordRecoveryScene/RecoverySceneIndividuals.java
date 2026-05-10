package com.rummyq.features.passwordRecoveryScene;

import com.rummyq.core.UIColors;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RecoverySceneIndividuals {
    public static VBox createSection(String tituloSeccion) {
        Label lbl = new Label(tituloSeccion);
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        lbl.setTextFill(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.85));
        VBox v = new VBox(8, lbl);
        v.setMaxWidth(Double.MAX_VALUE);
        return v;
    }

    public static TextField createTextField(String placeholder) {
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

    public static PasswordField createPasswordField(String placeholder) {
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

}