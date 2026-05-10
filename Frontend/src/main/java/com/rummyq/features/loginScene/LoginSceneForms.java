package com.rummyq.features.loginScene;

import com.rummyq.core.ComponentFactory;
import com.rummyq.core.UIColors;
import com.rummyq.model.ScreenConfig;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginSceneForms {
    private final int screenWidth = ScreenConfig.getScreenWidth();

    private TextField emailInputField;
    private PasswordField passwordInputField;
    private Label messageLabel;
    private LoginSceneActions actions;

    public LoginSceneForms(LoginSceneActions actions) {
        this.actions = actions;
    }

    public VBox createFormPanel() {
        VBox panel = new VBox(18);
        panel.setAlignment(Pos.CENTER);
        panel.setPrefWidth(screenWidth * 0.55);
        panel.setPadding(new Insets(50, 60, 50, 60));

        VBox card = new VBox(16);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(36, 40, 36, 40));
        card.setStyle(
                "-fx-background-color: rgba(10,26,16,0.75);" +
                        "-fx-background-radius: 14;" +
                        "-fx-border-color: rgba(201,168,76,0.3);" +
                        "-fx-border-width: 1.5;" +
                        "-fx-border-radius: 14;");
        DropShadow shadow = new DropShadow(30, Color.BLACK);
        shadow.setSpread(0.1);
        card.setEffect(shadow);

        Label titleLabel = new Label("Iniciar Sesión");
        titleLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        titleLabel.setTextFill(UIColors.COLOR_ORO_CLARO);

        Label subtitleLabel = new Label("Ingresa tus credenciales para continuar");
        subtitleLabel.setFont(Font.font("Georgia", 12));
        subtitleLabel.setTextFill(UIColors.COLOR_CREMA.deriveColor(0, 1, 1, 0.5));
        VBox.setMargin(subtitleLabel, new Insets(0, 0, 10, 0));

        VBox emailField = createInputField("Correo electrónico", "ejemplo@correo.com", false);
        emailInputField = (TextField) emailField.lookup("#campo");

        VBox passwordField = createInputField("Contraseña", "••••••••", true);
        passwordInputField = (PasswordField) passwordField.lookup("#campo");

        messageLabel = new Label("");
        messageLabel.setFont(Font.font("Georgia", 12));
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(280);

        Button btnIngresar = ComponentFactory.createPrimaryButton("Ingresar");
        btnIngresar.setMaxWidth(Double.MAX_VALUE);
        btnIngresar.setOnAction(e -> actions.onLoginClick(emailInputField, passwordInputField, messageLabel));

        HBox sep = createTextSeparator("¿No tienes cuenta?");

        Button btnRegistrar = LoginSceneIndividuals.createSecondaryButton("Crear cuenta nueva");
        btnRegistrar.setMaxWidth(Double.MAX_VALUE);
        btnRegistrar.setOnAction(e -> actions.abrirRegistro());

        Button btnOlvide = LoginSceneIndividuals.createPhantomButton("Olvidé mi contraseña");
        btnOlvide.setOnAction(e -> actions.abrirRecuperacion());

        // Enter activa login
        passwordInputField.setOnAction(e -> actions.onLoginClick(emailInputField, passwordInputField, messageLabel));

        card.getChildren().addAll(
                titleLabel, subtitleLabel,
                emailField, passwordField,
                messageLabel,
                btnIngresar, sep, btnRegistrar, btnOlvide);

        panel.getChildren().add(card);
        return panel;
    }

    private VBox createInputField(String labelText, String placeholder, boolean isPassword) {
        Label lbl = new Label(labelText);
        lbl.setFont(Font.font("Georgia", FontWeight.BOLD, 12));
        lbl.setTextFill(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.9));

        String baseStyle = "-fx-background-color: rgba(245,234,214,0.07);" +
                "-fx-border-color: rgba(201,168,76,0.3);" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;" +
                "-fx-text-fill: #f5ead6;" +
                "-fx-prompt-text-fill: rgba(245,234,214,0.35);" +
                "-fx-font-family: Georgia;" +
                "-fx-font-size: 13;" +
                "-fx-padding: 10 14;";

        String focusStyle = "-fx-background-color: rgba(245,234,214,0.10);" +
                "-fx-border-color: rgba(201,168,76,0.75);" +
                "-fx-border-width: 1.5;" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;" +
                "-fx-text-fill: #f5ead6;" +
                "-fx-prompt-text-fill: rgba(245,234,214,0.35);" +
                "-fx-font-family: Georgia;" +
                "-fx-font-size: 13;" +
                "-fx-padding: 10 14;";

        Control field;
        if (isPassword) {
            PasswordField pf = new PasswordField();
            pf.setPromptText(placeholder);
            pf.setId("campo");
            pf.setStyle(baseStyle);
            pf.focusedProperty().addListener((o, a, focus) -> pf.setStyle(focus ? focusStyle : baseStyle));
            field = pf;
        } else {
            TextField tf = new TextField();
            tf.setPromptText(placeholder);
            tf.setId("campo");
            tf.setStyle(baseStyle);
            tf.focusedProperty().addListener((o, a, focus) -> tf.setStyle(focus ? focusStyle : baseStyle));
            field = tf;
        }

        field.setPrefHeight(44);
        field.setMaxWidth(Double.MAX_VALUE);

        VBox grupo = new VBox(6, lbl, field);
        grupo.setMaxWidth(Double.MAX_VALUE);
        return grupo;
    }

    private HBox createTextSeparator(String texto) {
        Region l1 = new Region();
        HBox.setHgrow(l1, Priority.ALWAYS);
        l1.setPrefHeight(1);
        l1.setStyle("-fx-background-color: rgba(201,168,76,0.2);");

        Label lbl = new Label("  " + texto + "  ");
        lbl.setFont(Font.font("Georgia", 11));
        lbl.setTextFill(UIColors.COLOR_CREMA.deriveColor(0, 1, 1, 0.4));

        Region l2 = new Region();
        HBox.setHgrow(l2, Priority.ALWAYS);
        l2.setPrefHeight(1);
        l2.setStyle("-fx-background-color: rgba(201,168,76,0.2);");

        HBox h = new HBox(l1, lbl, l2);
        h.setAlignment(Pos.CENTER);
        return h;
    }

}