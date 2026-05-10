package com.rummyq.features.signUp;

import com.rummyq.core.ComponentFactory;
import com.rummyq.model.SignUpForm;
import com.rummyq.model.Usuario;
import com.rummyq.view.LoginScene;

import javafx.stage.Stage;
import javafx.util.Duration;

public class SignUpSceneActions {

    private Stage stage;

    public SignUpSceneActions(Stage stage) {
        this.stage = stage;
    }

    public void registerUser(SignUpForm form) {
        String name = form.name;
        String email = form.email;
        String password = form.password;
        String confirmPassword = form.confirmPassword;
        String answer = form.answer;

        System.out.println("Usuario: " + name + " Email: " + email + " Password: " + password
                + " Confirmar Password: " + confirmPassword + " Respuesta: " + answer);
        // Validaciones
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || answer.isEmpty()) {
            ComponentFactory.showMessage("Por favor completa todos los campos.", false, form.lblMessage);
            return;
        }
        if (name.length() < 3) {
            ComponentFactory.showMessage("El nombre en línea debe tener al menos 3 caracteres.", false,
                    form.lblMessage);
            return;
        }
        if (name.contains(" ")) {
            ComponentFactory.showMessage("El nombre en línea no puede tener espacios.", false, form.lblMessage);
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            ComponentFactory.showMessage("Ingresa un correo electrónico válido.", false, form.lblMessage);
            return;
        }
        if (password.length() < 6) {
            ComponentFactory.showMessage("La contraseña debe tener al menos 6 caracteres.", false, form.lblMessage);
            return;
        }
        if (!password.equals(confirmPassword)) {
            ComponentFactory.showMessage("Las contraseñas no coinciden.", false, form.lblMessage);
            return;
        }
        if (answer.isEmpty()) {
            ComponentFactory.showMessage("Completa la respuesta de seguridad.", false, form.lblMessage);
            return;
        }

        // Crear y guardar usuario
        Usuario nuevo = new Usuario(
                email,
                "",
                "",
                "");
        nuevo.setNombre(name); // nombre en línea elegido por el jugador

        boolean registrado = false;

        if (registrado) {
            ComponentFactory.showMessage("¡Cuenta creada exitosamente! Redirigiendo…", true, form.lblMessage);
            // Ir al login después de 1.5 segundos
            javafx.animation.PauseTransition pausa = new javafx.animation.PauseTransition(Duration.seconds(1.5));
            pausa.setOnFinished(e -> new LoginScene(stage).showScreen());
            pausa.play();
        } else {
            ComponentFactory.showMessage("Este correo ya está registrado. Intenta con otro.", false,
                    form.lblMessage);
        }
    }

}