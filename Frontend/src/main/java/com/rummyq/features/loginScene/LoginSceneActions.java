package com.rummyq.features.loginScene;

import com.rummyq.core.UIColors;
import com.rummyq.model.Usuario;
import com.rummyq.view.PantallaRecuperacion;
import com.rummyq.view.PantallaRegistro;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginSceneActions {

    private Stage stage;

    public LoginSceneActions(Stage _stage) {
        stage = _stage;
    }

    public void onLoginClick(TextField emailField, PasswordField passField, Label messageLabel) {
        String email = emailField.getText().trim();
        String password = passField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showMessage("Por favor completa todos los campos.", false, messageLabel);
            return;
        }
        if (!email.contains("@")) {
            showMessage("Ingresa un correo electrónico válido.", false, messageLabel);
            return;
        }

        Usuario user = new Usuario();
        boolean areCredentialsValid = false;

        if (areCredentialsValid) {
            showMessage("¡Bienvenido, " + user.getNombre() + "!", true, messageLabel);
            // new PantallaInicio(stage).mostrar();
        } else {
            showMessage("Correo o contraseña incorrectos.", false, messageLabel);
            LoginSceneAnimationEffects.shakeFields(passField);
        }
    }

    private void showMessage(String text, boolean isSuccess, Label messageLabel) {
        messageLabel.setText(text);
        messageLabel.setTextFill(isSuccess ? UIColors.VERDE_EXITO : UIColors.ROJO_ERROR);
    }

    public void abrirRegistro() {
        new PantallaRegistro(stage).mostrar();
    }

    public void abrirRecuperacion() {
        new PantallaRecuperacion(stage).mostrar();
    }
}
