package com.rummyq.view;

import com.rummyq.core.ComponentFactory;
import com.rummyq.features.loginScene.*;
import com.rummyq.model.ScreenConfig;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Pantalla de ingreso (Login) del juego RummyQ.
 *
 * Permite al usuario:
 * - Iniciar sesión con correo y contraseña
 * - Navegar a la pantalla de registro
 * - Recuperar contraseña
 */
public class LoginScene {

    private final Stage stage;

    private final int screenWidth = ScreenConfig.getScreenWidth();
    private final int screenHeight = ScreenConfig.getScreenHeight();

    private LoginSceneActions actions;
    private LoginSceneForms forms;

    public LoginScene(Stage stage) {
        this.stage = stage;
        this.actions = new LoginSceneActions(stage);
        this.forms = new LoginSceneForms(actions);
    }

    public void showScreen() {
        StackPane pane = new StackPane();
        pane.getChildren().addAll(
                ComponentFactory.createBackground(),
                ComponentFactory.createDecoratedBorder(),
                createContent());

        Scene scene = new Scene(pane, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.show();
    }

    private HBox createContent() {
        VBox leftPanel = LoginSceneIndividuals.createPanelLogo();

        Region separator = new Region();
        separator.setPrefWidth(1);
        separator.setStyle("-fx-background-color: rgba(201,168,76,0.25);");

        VBox rightPanel = forms.createFormPanel();
        rightPanel.setTranslateX(screenWidth * -0.02);
        rightPanel.translateXProperty();

        HBox content = new HBox(leftPanel, separator, rightPanel);
        content.setAlignment(Pos.CENTER);

        LoginSceneAnimationEffects.startSceneAnimation(content);
        return content;
    }
}