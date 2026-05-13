package com.rummyq.features.gameScene;

import com.rummyq.view.MainScene;

import javafx.stage.Stage;

public class GameSceneActions {
    private final Stage stage;

    public GameSceneActions(Stage stage) {
        this.stage = stage;
    }

    public void onExitClick() {
        new MainScene(stage).showScreen();
    }
}
