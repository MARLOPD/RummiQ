package com.rummyq.features.signUp;

import com.rummyq.core.ComponentFactory;
import com.rummyq.core.UIColors;
import com.rummyq.model.SignUpForm;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SignUpSceneForms {
        private TextField name;
        private TextField email;
        private PasswordField password;
        private PasswordField confirmPassword;
        private TextField answer;
        private Label lblMessage;

        private SignUpSceneActions actions;

        public SignUpSceneForms(SignUpSceneActions actions) {
                this.actions = actions;
        }

        public VBox createCard() {

                VBox card = new VBox(14);
                card.setAlignment(Pos.CENTER_LEFT);
                card.setPadding(new Insets(36, 48, 36, 48));
                card.setMaxWidth(480);
                card.setStyle("-fx-background-color: rgba(10,26,16,0.80);" + "-fx-background-radius: 14;"
                                + "-fx-border-color: rgba(201,168,76,0.35);" + "-fx-border-width: 1.5;"
                                + "-fx-border-radius: 14;");
                card.setEffect(new DropShadow(30, Color.BLACK));

                Label title = new Label(
                                "Crear Cuenta");
                title.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
                title.setTextFill(UIColors.COLOR_ORO);

                Label subtitle = new Label(
                                "Completa los campos para registrarte");
                subtitle.setFont(Font.font("Georgia", 12));
                subtitle.setTextFill(UIColors.COLOR_CREMA.deriveColor(0, 1, 1, 0.5));
                VBox.setMargin(subtitle, new Insets(0, 0, 8, 0));

                // Campos
                VBox nameGroup = SignUpSceneIndividuals.createField("Nombre en línea", "Ej: ReyDelRummy, Tigre22…",
                                false);
                VBox emailGroup = SignUpSceneIndividuals.createField("Correo electrónico", "ejemplo@correo.com", false);
                VBox passwordGroup = SignUpSceneIndividuals.createField("Contraseña", "Mínimo 6 caracteres", true);
                VBox confirmPasswordGroup = SignUpSceneIndividuals.createField("Confirmar contraseña",
                                "Repite tu contraseña",
                                true);
                VBox questionGroup = SignUpSceneIndividuals.createQuestionCombo();
                VBox answerGroup = SignUpSceneIndividuals.createField("Respuesta de seguridad", "Tu respuesta", false);

                name = (TextField) nameGroup.lookup("#campo");
                email = (TextField) emailGroup.lookup("#campo");
                password = (PasswordField) passwordGroup.lookup("#campo");
                confirmPassword = (PasswordField) confirmPasswordGroup.lookup("#campo");
                answer = (TextField) answerGroup.lookup("#campo");

                // Mensaje
                lblMessage = new Label("");
                lblMessage.setFont(Font.font("Georgia", 12));
                lblMessage.setWrapText(true);
                lblMessage.setMaxWidth(380);

                // Botón registrar
                Button btnRegistrar = ComponentFactory.createPrimaryButton("Crear cuenta");
                btnRegistrar.setMaxWidth(Double.MAX_VALUE);

                btnRegistrar.setOnAction(e -> {
                        SignUpForm form = new SignUpForm();
                        form.name = name.getText().trim();
                        form.email = email.getText().trim();
                        form.password = password.getText();
                        form.confirmPassword = confirmPassword.getText();
                        form.answer = answer.getText().trim();
                        form.lblMessage = lblMessage;

                        actions.registerUser(form);
                });

                card.getChildren().addAll(
                                title, subtitle,
                                nameGroup, emailGroup, passwordGroup, confirmPasswordGroup,
                                questionGroup, answerGroup,
                                lblMessage, btnRegistrar);

                return card;
        }

}
