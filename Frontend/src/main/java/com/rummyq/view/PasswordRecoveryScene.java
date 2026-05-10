package com.rummyq.view;

import com.rummyq.core.ComponentFactory;
import com.rummyq.core.UIColors;
import com.rummyq.features.passwordRecoveryScene.RecoverySceneActions;
import com.rummyq.features.passwordRecoveryScene.RecoverySceneForms;
import com.rummyq.model.ScreenConfig;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Pantalla de recuperación de contraseña.
 *
 * Flujo:
 * Paso 1 — El usuario ingresa su correo
 * Paso 2 — Se muestra la pregunta de seguridad; responde
 * Paso 3 — Ingresa y confirma la nueva contraseña
 */
public class PasswordRecoveryScene {

    private final Stage stage;

    private final int screenWidth = ScreenConfig.getScreenWidth();
    private final int screenHeight = ScreenConfig.getScreenHeight();

    private RecoverySceneActions actions;
    private RecoverySceneForms forms;

    public PasswordRecoveryScene(Stage stage) {
        this.stage = stage;
        this.actions = new RecoverySceneActions(stage);
        this.forms = new RecoverySceneForms(actions);
    }

    public void showScreen() {
        StackPane raiz = new StackPane();
        raiz.getChildren().addAll(ComponentFactory.createBackground(), ComponentFactory.createDecoratedBorder(),
                createContent());

        Scene scene = new Scene(raiz, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.show();
    }

    private VBox createContent() {
        VBox content = new VBox(16);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        // Botón volver
        Button btnBack = new Button("← Volver al login");
        btnBack.setFont(Font.font("Georgia", 13));
        String ev = "-fx-background-color:transparent; -fx-text-fill:rgba(201,168,76,0.7); -fx-cursor:hand;";
        String hv = "-fx-background-color:transparent; -fx-text-fill:" + UIColors.COLOR_ORO_CLARO.toString()
                + "; -fx-cursor:hand;";
        btnBack.setStyle(ev);
        btnBack.setOnMouseEntered(e -> btnBack.setStyle(hv));
        btnBack.setOnMouseExited(e -> btnBack.setStyle(ev));
        btnBack.setOnAction(e -> new LoginScene(stage).showScreen());

        HBox header = new HBox(btnBack);
        header.setMaxWidth(420);

        VBox card = forms.CreateForm();

        content.getChildren().addAll(header, card);

        FadeTransition fade = new FadeTransition(Duration.millis(600), content);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
        return content;
    }

}