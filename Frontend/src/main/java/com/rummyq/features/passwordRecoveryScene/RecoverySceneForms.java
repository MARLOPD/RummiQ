package com.rummyq.features.passwordRecoveryScene;

import com.rummyq.core.ComponentFactory;
import com.rummyq.core.UIColors;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RecoverySceneForms {

    private TextField emailField;
    private TextField answerField;
    private PasswordField newPasswordField;
    private PasswordField confirmPasswordField;

    private Label questionLabel;
    private Label messageLabel;

    private VBox emailSection;
    private VBox answerSection;
    private VBox newPasswordSection;

    public RecoverySceneActions actions;

    public RecoverySceneForms(RecoverySceneActions actions) {
        this.actions = actions;
    }

    public VBox CreateForm() {
        VBox card = new VBox(16);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(36, 48, 36, 48));
        card.setMaxWidth(420);
        card.setStyle(
                "-fx-background-color: rgba(10,26,16,0.82);" +
                        "-fx-background-radius:14; -fx-border-color:rgba(201,168,76,0.35);" +
                        "-fx-border-width:1.5; -fx-border-radius:14;");
        card.setEffect(new DropShadow(30, Color.BLACK));

        Label title = new Label("Recuperar Contraseña");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
        title.setTextFill(UIColors.COLOR_ORO_CLARO);

        Label subtitle = new Label("Sigue los pasos para restablecer tu acceso");
        subtitle.setFont(Font.font("Georgia", 12));
        subtitle.setTextFill(UIColors.COLOR_CREMA.deriveColor(0, 1, 1, 0.5));

        emailSection = RecoverySceneIndividuals.createSection("Paso 1 — Ingresa tu correo");
        emailField = RecoverySceneIndividuals.createTextField("tu@correo.com");
        emailSection.getChildren().add(emailField);

        answerSection = RecoverySceneIndividuals.createSection("Paso 2 — Pregunta de seguridad");
        questionLabel = new Label("");
        questionLabel.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        questionLabel.setTextFill(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.9));
        questionLabel.setWrapText(true);
        answerField = RecoverySceneIndividuals.createTextField("Tu respuesta");
        answerSection.getChildren().addAll(questionLabel, answerField);
        answerSection.setVisible(false);
        answerSection.setManaged(false);

        // ── Paso 3: nueva contraseña ──
        newPasswordSection = RecoverySceneIndividuals.createSection("Paso 3 — Nueva contraseña");
        newPasswordField = RecoverySceneIndividuals.createPasswordField("Nueva contraseña (mín. 6 caracteres)");
        confirmPasswordField = RecoverySceneIndividuals.createPasswordField("Confirmar nueva contraseña");
        newPasswordSection.getChildren().addAll(newPasswordField, confirmPasswordField);
        newPasswordSection.setVisible(false);
        newPasswordSection.setManaged(false);

        // Mensaje
        messageLabel = new Label("");
        messageLabel.setFont(Font.font("Georgia", 12));
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(320);

        // Botón de acción (cambia de texto según el paso)
        Button btnAccion = ComponentFactory.createPrimaryButton("Continuar");
        btnAccion.setMaxWidth(Double.MAX_VALUE);

        RecoverySceneFormInformation formInfo = new RecoverySceneFormInformation();
        formInfo.emailField = emailField;
        formInfo.answerField = answerField;
        formInfo.newPasswordField = newPasswordField;
        formInfo.confirmPasswordField = confirmPasswordField;
        formInfo.questionLabel = questionLabel;
        formInfo.answerSection = answerSection;
        formInfo.newPasswordSection = newPasswordSection;

        btnAccion.setOnAction(e -> actions.nextStep(formInfo));

        card.getChildren().addAll(
                title, subtitle,
                emailSection, answerSection, newPasswordSection,
                messageLabel, btnAccion);

        return card;
    }

}