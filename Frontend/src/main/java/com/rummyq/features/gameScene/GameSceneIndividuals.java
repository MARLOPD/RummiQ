package com.rummyq.features.gameScene;

import com.rummyq.model.ScreenConfig;
import com.rummyq.websocket.dto.TileDTO;
import com.rummyq.core.ComponentFactory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.Node;
import javafx.scene.input.TransferMode;
import javafx.scene.input.DragEvent;
import javafx.application.Platform;
import javafx.event.EventHandler;

import java.util.List;
import java.util.stream.Collectors;

public class GameSceneIndividuals {

    private static final double BOARD_WIDTH = 400;

    private static final double screenHeight = ScreenConfig.getScreenHeight();
    private static final double screenWidth = ScreenConfig.getScreenWidth();
    private static final Image WOOD_TEXTURE = new Image(
            GameSceneIndividuals.class.getResourceAsStream("/assets/wood.png"));

    private final GameSceneActions actions;
    private static StackPane topBoard;
    private static StackPane bottomBoard;
    private static Rectangle base;

    public GameSceneIndividuals(GameSceneActions actions) {
        this.actions = actions;
    }

    /**
     * Crea el tablero del jugador con dos niveles y textura de ruido.
     */
    public static Pane createPlayerBoard() {
        VBox boardRows = new VBox();
        boardRows.setAlignment(Pos.CENTER);
        boardRows.setSpacing(-5);

        topBoard = createBoardRow(true, BOARD_WIDTH);
        bottomBoard = createBoardRow(false, BOARD_WIDTH * 1.05);
        boardRows.getChildren().addAll(topBoard, bottomBoard);

        StackPane.setAlignment(boardRows, Pos.BOTTOM_CENTER);
        StackPane.setMargin(boardRows, new Insets(0, 0, screenHeight * -0.55, 0));

        return boardRows;
    }

    private static StackPane createBoardRow(boolean isTop, double recWidth) {
        StackPane rowStack = new StackPane();
        rowStack.setMaxWidth(BOARD_WIDTH);
        rowStack.setPrefHeight(90);

        base = new Rectangle(recWidth, 85);
        base.setArcWidth(5);
        base.setArcHeight(5);

        if (WOOD_TEXTURE != null && !WOOD_TEXTURE.isError()) {
            base.setFill(new ImagePattern(WOOD_TEXTURE));
        } else {
            base.setFill(javafx.scene.paint.LinearGradient.valueOf("to bottom, #5a3a2a, #2a150a"));
        }

        base.setStrokeWidth(1.5);

        Rectangle highlight = new Rectangle(BOARD_WIDTH - 4, 2);
        highlight.setFill(Color.web("#ffffff", 0.1));
        highlight.setTranslateY(-40);

        rowStack.getChildren().addAll(base, highlight);// , tilesContainer);

        DropShadow ds = new DropShadow(15, Color.BLACK);
        rowStack.setEffect(ds);

        return rowStack;
    }

    public Pane createInfoPanel(String salaId, int rondaActual, int rondaTotal) {

        return _createInfoPanel(salaId, rondaActual, rondaTotal);
    }

    public Pane _createInfoPanel(String salaId, int rondaActual, int rondaTotal) {
        VBox panel = new VBox(6);
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setPrefWidth(145);

        Button exitButton = ComponentFactory.createPrimaryButton("← Salir");
        exitButton.setOnAction(e -> actions.onExitClick());
        exitButton.setMaxWidth(screenWidth * 0.09);
        exitButton.setPrefWidth(screenWidth * 0.09);

        HBox idRow = ComponentFactory.createInformationBox("ID Sala", salaId);
        Label copyIcon = new Label("⧉");
        copyIcon.setTextFill(Color.web("#a0b8a0"));
        copyIcon.setFont(Font.font(14));
        copyIcon.setStyle("-fx-cursor: hand;");
        copyIcon.setOnMouseClicked(e -> {
            javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
            javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
            content.putString(salaId);
            clipboard.setContent(content);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        idRow.getChildren().addAll(spacer, copyIcon);

        HBox rondaRow = ComponentFactory.createInformationBox("Ronda", rondaActual + " / " + rondaTotal);

        panel.getChildren().addAll(exitButton, idRow, rondaRow);
        return panel;
    }

    public static Pane createTopRightButtons() {
        HBox container = new HBox(8);
        container.setAlignment(Pos.CENTER);

        container.getChildren().addAll(
                ComponentFactory.createGhostGameButton("/assets/jugadoresIcon.png", "Users"),
                ComponentFactory.createGhostGameButton("/assets/ajustesIcon.png", "Ajustes"));

        container.setMaxSize(screenWidth * 0.1, screenHeight * 0.07);
        container.setPrefWidth(screenWidth * 0.1);
        container.setPrefHeight(screenHeight * 0.05);
        container.setTranslateY(18);
        container.setTranslateX(-18);

        return container;
    }

    public static void updateTiles(List<TileDTO> tiles) {
        Platform.runLater(() -> {
            GameSceneIndividuals.createTiles(tiles.stream().limit(7).collect(Collectors.toList()), topBoard);
            GameSceneIndividuals.createTiles(tiles.stream().skip(7).collect(Collectors.toList()), bottomBoard);
        });
    }

    private static void createTiles(List<TileDTO> tiles, StackPane rowStack) {
        HBox tilesContainer = new HBox(10);
        tilesContainer.setAlignment(Pos.CENTER);
        tilesContainer.setPadding(new Insets(10));

        for (TileDTO t : tiles) {
            StackPane tile = GameTiles.createTile(t.getNumero(), t.getColorJavaFX());
            GameBoard.habilitarDragDesdeMano(tile);
            tilesContainer.getChildren().add(tile);
        }

        EventHandler<DragEvent> dragOverHandler = e -> {
            if (GameBoard.getDraggedTile() != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        };

        EventHandler<DragEvent> dragDroppedHandler = e -> {
            System.out.println("[DEBUG] DragDropped - draggedTile: " + GameBoard.getDraggedTile());
            Node tile = GameBoard.getDraggedTile();
            boolean success = false;
            if (tile != null) {
                String sourceKey = GameBoard.getDraggedSourceKey();
                if (sourceKey != null) {
                    GameBoard board = GameBoard.getInstance();
                    if (board != null) {
                        board.removerFicha(tile);
                    }
                } else {
                    if (tile.getParent() instanceof Pane) {
                        ((Pane) tile.getParent()).getChildren().remove(tile);
                    }
                }

                // Convertir la coordenada X del drop al espacio de tilesContainer
                javafx.geometry.Point2D localPoint = tilesContainer.sceneToLocal(e.getSceneX(), e.getSceneY());
                double dropX = localPoint.getX();

                int insertIndex = tilesContainer.getChildren().size();
                for (int i = 0; i < tilesContainer.getChildren().size(); i++) {
                    Node child = tilesContainer.getChildren().get(i);
                    double childCenterX = child.getBoundsInParent().getCenterX();
                    if (dropX < childCenterX) {
                        insertIndex = i;
                        break;
                    }
                }

                tilesContainer.getChildren().add(insertIndex, tile);
                GameBoard.habilitarDragDesdeMano(tile);
                success = true;
            }
            e.setDropCompleted(success);
            e.consume();
        };

        rowStack.setOnDragOver(dragOverHandler);
        rowStack.setOnDragDropped(dragDroppedHandler);

        tilesContainer.setOnDragOver(dragOverHandler);
        tilesContainer.setOnDragDropped(dragDroppedHandler);

        base.setOnDragOver(dragOverHandler);
        base.setOnDragDropped(dragDroppedHandler);

        rowStack.getChildren().add(tilesContainer);
    }
}