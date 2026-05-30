
package com.rummyq.view;

import com.rummyq.features.gameScene.GameSceneActions;
import com.rummyq.features.gameScene.GameSceneIndividuals;
import com.rummyq.features.gameScene.GameTiles;
import com.rummyq.features.gameScene.GameBoard;
import com.rummyq.core.ComponentFactory;
import com.rummyq.model.ScreenConfig;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameScene {
    private final Stage stage;

    private final int screenWidth = ScreenConfig.getScreenWidth();
    private final int screenHeight = ScreenConfig.getScreenHeight();

    private final GameSceneActions gameSceneActions;
    private final GameSceneIndividuals gameSceneIndividuals;

    public GameScene(Stage stage) {
        this.stage = stage;
        this.gameSceneActions = new GameSceneActions(stage);
        this.gameSceneIndividuals = new GameSceneIndividuals(gameSceneActions);
    }

    public void showScreen() {
        StackPane root = createContent();
        Scene scene = new Scene(root, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.show();
    }

    private StackPane createContent() {
        StackPane root = new StackPane();
        root.setPickOnBounds(false);

        // Y a cada uno de los que van encima del gameBoard:
        Pane playerBoard = GameSceneIndividuals.createPlayerBoard();
        playerBoard.setPickOnBounds(false);

        StackPane secretTile = new StackPane();
        secretTile.setPickOnBounds(false);
        HBox tilesBox = GameTiles.secretTilesOnTheTable(6);

        secretTile.setMaxSize(tilesBox.getWidth(), tilesBox.getHeight());
        secretTile.setPrefSize(tilesBox.getWidth(), tilesBox.getHeight());

        StackPane.setAlignment(secretTile, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(secretTile, new Insets(0, screenWidth * 0.065, screenHeight * 0.11, 0));
        secretTile.getChildren().add(tilesBox);

        VBox topLeft = new VBox(8);
        topLeft.setPickOnBounds(false);

        topLeft.setMaxSize(screenWidth * 0.8, screenHeight * 0.2);
        topLeft.setPrefSize(screenWidth * 0.8, screenHeight * 0.2);
        topLeft.setPadding(new Insets(screenHeight * 0.065, 0, 0, screenWidth * 0.065));
        topLeft.getChildren().addAll(
                gameSceneIndividuals.createInfoPanel("#124578", 3, 10));
        StackPane.setAlignment(topLeft, Pos.TOP_LEFT);

        Pane topRightButtons = GameSceneIndividuals.createTopRightButtons();
        topRightButtons.setPickOnBounds(false);

        StackPane.setAlignment(topRightButtons, Pos.TOP_RIGHT);
        StackPane.setMargin(topRightButtons, new Insets(screenHeight * 0.04, screenWidth * 0.035, 0, 0));

        Button chatButton = ComponentFactory.createGhostGameButton("/assets/chatIcon.png", "Chat");
        StackPane.setAlignment(chatButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(chatButton, new Insets(0, 0, screenHeight * 0.065, screenWidth * 0.065));

        Pane player1 = ComponentFactory.createUserPanel("Jugador 1");
        player1.setPickOnBounds(false);
        com.rummyq.features.gameScene.PlayerPositions.positionUserPanel(player1, 1);

        Pane player2 = ComponentFactory.createUserPanel("Jugador 2");
        player2.setPickOnBounds(false);
        com.rummyq.features.gameScene.PlayerPositions.positionUserPanel(player2, 2);

        Pane player3 = ComponentFactory.createUserPanel("Jugador 3");
        player3.setPickOnBounds(false);
        com.rummyq.features.gameScene.PlayerPositions.positionUserPanel(player3, 3);

        GameBoard gameBoard = new GameBoard();
        gameBoard.setMaxSize(screenWidth * 0.58, screenHeight * 0.44);
        gameBoard.setPrefSize(screenWidth * 0.58, screenHeight * 0.44);
        StackPane.setAlignment(gameBoard, Pos.CENTER);
        StackPane.setMargin(gameBoard, new Insets(0, 0, screenHeight * 0.12, 0));

        root.getChildren().addAll(
                ComponentFactory.createBackground(),
                ComponentFactory.createDecoratedBorder(),
                gameBoard,
                GameSceneIndividuals.createPlayerBoard(),
                secretTile,
                topLeft,
                topRightButtons,
                chatButton,
                player1,
                player2,
                player3);

        root.getChildren().forEach(node -> node.setPickOnBounds(false));
        root.setPickOnBounds(false);
        return root;
    }
}