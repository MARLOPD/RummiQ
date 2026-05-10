package com.rummyq.features.signUp;

import com.rummyq.core.ComponentFactory;
import com.rummyq.core.UIColors;
import com.rummyq.view.LoginScene;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SignUpSceneIndividuals {

    private static ComboBox<String> questionCombo;

    public static HBox createHeader(Stage stage) {
        Button btnBackToLogin = new Button("← Volver al login");
        btnBackToLogin.setFont(Font.font("Georgia", 13));
        String style = "-fx-background-color: transparent; -fx-text-fill: rgba(201,168,76,0.7); -fx-cursor: hand;";
        String hover = "-fx-background-color: transparent; -fx-text-fill: " + UIColors.COLOR_ORO_CLARO.toString()
                + "; -fx-cursor: hand;";

        btnBackToLogin.setStyle(style);
        ComponentFactory.hoverEffect(btnBackToLogin, hover, style);
        btnBackToLogin.setOnAction(e -> new LoginScene(stage).showScreen());

        HBox header = new HBox(btnBackToLogin);
        header.setMaxWidth(480);
        return header;
    }

    public static VBox createField(String label, String placeholder, boolean isPassword) {
        Label lbl = new Label(label);
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        lbl.setTextFill(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.9));

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
        if (isPassword) {
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

    public static VBox createQuestionCombo() {
        Label lbl = new Label("Pregunta de seguridad");
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        lbl.setTextFill(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.9));

        questionCombo = new ComboBox<>();
        questionCombo.getItems().addAll(SecurityQuestions.getQuestions());
        questionCombo.setPromptText("Selecciona una pregunta…");
        questionCombo.setMaxWidth(Double.MAX_VALUE);
        questionCombo.setPrefHeight(44);
        questionCombo.setStyle(
                "-fx-background-color: rgba(245,234,214,0.07);" +
                        "-fx-border-color: rgba(201,168,76,0.3); -fx-border-width:1;" +
                        "-fx-border-radius:6; -fx-background-radius:6;" +
                        "-fx-font-family:Georgia; -fx-font-size:12;");

        VBox g = new VBox(5, lbl, questionCombo);
        g.setMaxWidth(Double.MAX_VALUE);
        return g;
    }

}