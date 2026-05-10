package com.rummyq.features.passwordRecoveryScene;

import com.rummyq.core.ComponentFactory;
import com.rummyq.model.Usuario;
import com.rummyq.view.LoginScene;

import javafx.animation.FadeTransition;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RecoverySceneActions {
    private Usuario usuarioEncontrado = null;

    private Stage stage;
    private int step = 1;

    public RecoverySceneActions(Stage stage) {
        this.stage = stage;
    }

    public void nextStep(RecoverySceneFormInformation form) {
        switch (step) {
            case 1 -> verifyEmail(form);
            case 2 -> verifyAnswer(form);
            case 3 -> changePassword(form);
        }
    }

    private void verifyEmail(RecoverySceneFormInformation form) {
        String email = form.emailField.getText().trim();
        if (email.isEmpty()) {
            ComponentFactory.showMessage("Ingresa tu correo.", false, form.messageLabel);
            return;
        }

        usuarioEncontrado = null;
        if (usuarioEncontrado == null) {
            ComponentFactory.showMessage("No existe una cuenta con ese correo.", false, form.messageLabel);
            return;
        }

        // Mostrar pregunta de seguridad
        form.questionLabel.setText(usuarioEncontrado.getPreguntaSeguridad());
        showSection(form.answerSection);
        form.messageLabel.setText("");
        form.btnAccion.setText("Verificar respuesta");
        step = 2;
    }

    private void verifyAnswer(RecoverySceneFormInformation form) {
        String answer = form.answerField.getText().trim();
        if (answer.isEmpty()) {
            ComponentFactory.showMessage("Ingresa tu respuesta.", false, form.messageLabel);
            return;
        }

        boolean isCorrectAnswer = false;
        if (!isCorrectAnswer) {
            ComponentFactory.showMessage("Respuesta incorrecta. Intenta de nuevo.", false, form.messageLabel);
            return;
        }

        showSection(form.newPasswordSection);
        form.messageLabel.setText("");
        form.btnAccion.setText("Cambiar contraseña");
        step = 3;
    }

    private void changePassword(RecoverySceneFormInformation form) {
        String newPassword = form.newPasswordField.getText();
        String confirmPassword = form.confirmPasswordField.getText();

        if (newPassword.length() < 6) {
            ComponentFactory.showMessage("La contraseña debe tener al menos 6 caracteres.", false, form.messageLabel);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            ComponentFactory.showMessage("Las contraseñas no coinciden.", false, form.messageLabel);
            return;
        }

        boolean actualizado = false;

        if (actualizado) {
            ComponentFactory.showMessage("¡Contraseña actualizada! Redirigiendo al login…", true, form.messageLabel);
            form.btnAccion.setDisable(true);
            javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(Duration.seconds(2));
            pausa.setOnFinished(e -> new LoginScene(stage).showScreen());
            pausa.play();
        } else {
            ComponentFactory.showMessage("Error al actualizar. Intenta de nuevo.", false, form.messageLabel);
        }
    }

    private void showSection(VBox section) {
        section.setVisible(true);
        section.setManaged(true);
        FadeTransition fade = new FadeTransition(Duration.millis(400), section);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

}