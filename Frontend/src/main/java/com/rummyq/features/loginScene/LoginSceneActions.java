package com.rummyq.features.loginScene;

import com.rummyq.core.ComponentFactory;
import com.rummyq.model.Usuario;
import com.rummyq.view.PasswordRecoveryScene;
import com.rummyq.view.SignUpScene;

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
            ComponentFactory.showMessage("Por favor completa todos los campos.", false, messageLabel);
            return;
        }
        if (!email.contains("@")) {
            ComponentFactory.showMessage("Ingresa un correo electrónico válido.", false, messageLabel);
            return;
        }

        Usuario user = new Usuario();
        boolean areCredentialsValid = false;

        if (areCredentialsValid) {
            ComponentFactory.showMessage("¡Bienvenido, " + user.getNombre() + "!", true, messageLabel);
            // new PantallaInicio(stage).mostrar();
        } else {
            ComponentFactory.showMessage("Correo o contraseña incorrectos.", false, messageLabel);
            LoginSceneAnimationEffects.shakeFields(passField);
        }
    }

    public void openSignUpScene() {
        new SignUpScene(stage).showScreen();
    }

    public void openRecoverScene() {
        new PasswordRecoveryScene(stage).showScreen();
    }
}
