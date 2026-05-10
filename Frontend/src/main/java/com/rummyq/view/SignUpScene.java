package com.rummyq.view;

import com.rummyq.core.ComponentFactory;
import com.rummyq.features.signUp.SignUpSceneActions;
import com.rummyq.features.signUp.SignUpSceneForms;
import com.rummyq.features.signUp.SignUpSceneIndividuals;
import com.rummyq.model.ScreenConfig;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Pantalla de registro de nuevo usuario.
 * Campos: correo, contraseña, confirmar contraseña,
 * pregunta de seguridad, respuesta de seguridad.
 */
public class SignUpScene {

    private final Stage stage;

    private final int screenWidth = ScreenConfig.getScreenWidth();
    private final int screenHeight = ScreenConfig.getScreenHeight();

    private SignUpSceneActions actions;
    private SignUpSceneForms forms;

    public SignUpScene(Stage stage) {
        this.stage = stage;
        this.actions = new SignUpSceneActions(stage);
        this.forms = new SignUpSceneForms(actions);
    }

    public void showScreen() {
        StackPane raiz = new StackPane();
        raiz.getChildren().addAll(ComponentFactory.createBackground(), ComponentFactory.createDecoratedBorder(),
                createContent());

        Scene escena = new Scene(raiz, screenWidth, screenHeight);
        stage.setScene(escena);
        stage.show();
    }

    private VBox createContent() {
        VBox content = new VBox(0);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));
        content.setScaleX(0.85);
        content.setScaleY(0.85);

        // Encabezado con botón volver
        HBox header = SignUpSceneIndividuals.createHeader(stage);
        // Tarjeta del formulario
        VBox card = forms.createCard();

        VBox.setMargin(header, new Insets(0, 0, 16, 0));
        content.getChildren().addAll(header, card);

        FadeTransition fade = new FadeTransition(Duration.millis(600), content);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        return content;
    }

}