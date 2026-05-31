package com.rummyq.features.gameScene;

import java.util.List;
import java.util.stream.Collectors;

import com.rummyq.core.ComponentFactory;
import com.rummyq.model.ScreenConfig;
import com.rummyq.websocket.dto.TileDTO;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class GameSceneIndividuals {

    // ─────────────────────────────────────────────
    // Constantes
    // ─────────────────────────────────────────────
    private static final double BOARD_WIDTH = 400;
    private static final double SCREEN_HEIGHT = ScreenConfig.getScreenHeight();
    private static final double SCREEN_WIDTH = ScreenConfig.getScreenWidth();

    private static final Image WOOD_TEXTURE = new Image(
            GameSceneIndividuals.class.getResourceAsStream("/assets/wood.png"));

    // ─────────────────────────────────────────────
    // Estado interno del tablero
    // ─────────────────────────────────────────────
    private final GameSceneActions actions;

    /**
     * Filas del tablero (StackPane con textura de madera).
     */
    private static StackPane topBoard;
    private static StackPane bottomBoard;

    /**
     * Contenedores de fichas dentro de cada fila. Se crean UNA SOLA VEZ en
     * {@link #initTileContainers()} y luego solo se reemplazan sus hijos en
     * {@link #refreshTiles}.
     */
    private static HBox topTilesContainer;
    private static HBox bottomTilesContainer;

    /**
     * Flag para garantizar que los contenedores se inicializen solo una vez.
     */
    private static boolean tilesContainersInitialized = false;

    /**
     * Referencia al base de la última fila creada (para drag handlers).
     */
    private static Rectangle base;

    // ─────────────────────────────────────────────
    // Constructor
    // ─────────────────────────────────────────────
    public GameSceneIndividuals(GameSceneActions actions) {
        this.actions = actions;
    }

    // ─────────────────────────────────────────────
    // Creación del tablero
    // ─────────────────────────────────────────────
    /**
     * Crea el tablero del jugador con dos filas y textura de madera. También
     * inicializa los contenedores de fichas.
     */
    public static Pane createPlayerBoard() {
        VBox boardRows = new VBox();
        boardRows.setAlignment(Pos.CENTER);
        boardRows.setSpacing(-5);

        topBoard = createBoardRow(BOARD_WIDTH);
        bottomBoard = createBoardRow(BOARD_WIDTH * 1.05);
        boardRows.getChildren().addAll(topBoard, bottomBoard);

        StackPane.setAlignment(boardRows, Pos.BOTTOM_CENTER);
        StackPane.setMargin(boardRows, new Insets(0, 0, SCREEN_HEIGHT * -0.55, 0));

        // Inicializar contenedores de fichas una sola vez
        initTileContainers();
        resetTileContainers();
        initTileContainers();
        return boardRows;
    }

    private static StackPane createBoardRow(double recWidth) {
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

        rowStack.getChildren().addAll(base, highlight);

        DropShadow ds = new DropShadow(15, Color.BLACK);
        rowStack.setEffect(ds);

        return rowStack;
    }

    // ─────────────────────────────────────────────
    // Inicialización de contenedores de fichas
    // ─────────────────────────────────────────────
    /**
     * Crea los {@link HBox} que alojarán las fichas y registra los
     * drag-handlers una sola vez.
     * <p>
     * Se llama automáticamente desde {@link #createPlayerBoard()}. Si se llama
     * múltiples veces, solo la primera vez ejecuta la inicialización.
     */
    public static void initTileContainers() {
        // Si ya se inicializó, no hacer nada
        if (tilesContainersInitialized) {
            System.out.println("[DEBUG] Contenedores ya inicializados, saltando...");
            return;
        }

        // Limpiar cualquier contenedor anterior
        if (topTilesContainer != null) {
            topTilesContainer.getChildren().clear();
        }
        if (bottomTilesContainer != null) {
            bottomTilesContainer.getChildren().clear();
        }

        topTilesContainer = buildAndAttachTileContainer(topBoard);
        bottomTilesContainer = buildAndAttachTileContainer(bottomBoard);

        tilesContainersInitialized = true;
        System.out.println("[DEBUG] Contenedores inicializados exitosamente");
    }

    /**
     * Crea un HBox vacío, lo agrega al rowStack y registra sus handlers.
     */
    private static HBox buildAndAttachTileContainer(StackPane rowStack) {
        HBox container = new HBox(10);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));
        container.setMinHeight(80);
        container.setPrefHeight(80);
        rowStack.getChildren().add(container);

        System.out.println("[DEBUG] buildAndAttachTileContainer - contenedor añadido a rowStack");
        System.out.println("[DEBUG] rowStack ahora tiene " + rowStack.getChildren().size() + " hijos");

        registerDragHandlers(rowStack, container);
        return container;
    }

    /**
     * Resetea los contenedores (útil para nuevas partidas). Limpia todos los
     * hijos y marca como no inicializados.
     */
    public static void resetTileContainers() {
        if (topTilesContainer != null) {
            topTilesContainer.getChildren().clear();
        }
        if (bottomTilesContainer != null) {
            bottomTilesContainer.getChildren().clear();
        }
        tilesContainersInitialized = false;
        System.out.println("[DEBUG] Contenedores reseteados");
    }

    // ─────────────────────────────────────────────
    // Actualización de fichas (sin re-render del tablero)
    // ─────────────────────────────────────────────
    /**
     * Actualiza las fichas visibles en el tablero. Las primeras 7 fichas van a
     * la fila superior; el resto a la inferior. Solo reemplaza los hijos del
     * HBox — el tablero (madera, sombras, etc.) NO se vuelve a renderizar.
     */
    public static void updateTiles(List<TileDTO> tiles) {
        // Garantizar que los contenedores estén inicializados
        if (!tilesContainersInitialized || topTilesContainer == null || bottomTilesContainer == null) {
            System.out.println("[DEBUG] Inicializando contenedores en updateTiles...");
            initTileContainers();
        }

        System.out.println("[DEBUG] updateTiles - topTilesContainer existe: " + (topTilesContainer != null));
        System.out.println("[DEBUG] updateTiles - topTilesContainer parent: " + (topTilesContainer.getParent() != null));
        if (topTilesContainer.getParent() != null) {
            System.out.println("[DEBUG] updateTiles - topBoard hijos: " + topBoard.getChildren().size());
        }

        List<TileDTO> topTiles = tiles.stream().limit(7).collect(Collectors.toList());
        List<TileDTO> bottomTiles = tiles.stream().skip(7).collect(Collectors.toList());

        Platform.runLater(() -> {
            System.out.println("topBoard hijos = " + topBoard.getChildren().size());
            System.out.println("topTilesContainer parent = " + topTilesContainer.getParent());
            refreshTiles(topTiles, topTilesContainer);
            refreshTiles(bottomTiles, bottomTilesContainer);

        });
    }

    /**
     * Limpia el contenedor y vuelve a poblar solo las fichas. Los drag-handlers
     * del StackPane padre ya están registrados y no se tocan.
     */
    private static void refreshTiles(List<TileDTO> tiles, HBox container) {
        System.out.println("[DEBUG] refreshTiles llamado con " + tiles.size() + " fichas");
        System.out.println("[DEBUG] Contenedor tiene " + container.getChildren().size() + " hijos antes de limpiar");
        System.out.println("[DEBUG] Contenedor tamaño: " + container.getWidth() + "x" + container.getHeight());

        container.getChildren().clear();
        System.out.println("[DEBUG] Contenedor limpiado, ahora tiene " + container.getChildren().size() + " hijos");

        for (TileDTO t : tiles) {
            StackPane tile = GameTiles.createTile(t.getNumero(), t.getColorJavaFX());
            System.out.println("[DEBUG] Ficha creada: número=" + t.getNumero() + ", tamaño=" + tile.getWidth() + "x" + tile.getHeight() + ", prefSize=" + tile.getPrefWidth() + "x" + tile.getPrefHeight());

            GameBoard.habilitarDragDesdeMano(tile);
            container.getChildren().add(tile);

            System.out.println("[DEBUG] Ficha añadida, tamaño actual después de añadir=" + tile.getWidth() + "x" + tile.getHeight());
        }

        System.out.println("[DEBUG] Después de añadir, contenedor tiene " + container.getChildren().size() + " hijos");
        System.out.println("[DEBUG] Contenedor tamaño final: " + container.getWidth() + "x" + container.getHeight());
        System.out.println("container parent = " + container.getParent());
        System.out.println("scene = " + container.getScene());
    }

    // ─────────────────────────────────────────────
    // Drag-and-drop handlers (registrados una sola vez)
    // ─────────────────────────────────────────────
    /**
     * Registra los handlers de drag-over y drag-dropped en el rowStack y su
     * HBox de fichas. Se llama exclusivamente desde
     * {@link #buildAndAttachTileContainer(StackPane)}.
     */
    private static void registerDragHandlers(StackPane rowStack, HBox tilesContainer) {
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
                    if (tile.getParent() instanceof Pane pane) {
                        pane.getChildren().remove(tile);
                    }
                }

                // Calcular posición de inserción según X del drop
                javafx.geometry.Point2D localPoint
                        = tilesContainer.sceneToLocal(e.getSceneX(), e.getSceneY());
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

        // Registrar en el StackPane, en el HBox y en el base (Rectangle)
        rowStack.setOnDragOver(dragOverHandler);
        rowStack.setOnDragDropped(dragDroppedHandler);

        tilesContainer.setOnDragOver(dragOverHandler);
        tilesContainer.setOnDragDropped(dragDroppedHandler);

        // base es la referencia estática al último Rectangle creado —
        // si necesitas por-fila, guarda la referencia en buildAndAttachTileContainer.
        if (base != null) {
            base.setOnDragOver(dragOverHandler);
            base.setOnDragDropped(dragDroppedHandler);
        }
    }

    // ─────────────────────────────────────────────
    // Panel de información
    // ─────────────────────────────────────────────
    public Pane createInfoPanel(String salaId, int rondaActual, int rondaTotal) {
        VBox panel = new VBox(6);
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setPrefWidth(145);

        Button exitButton = ComponentFactory.createPrimaryButton("← Salir");
        exitButton.setOnAction(e -> actions.onExitClick());
        exitButton.setMaxWidth(SCREEN_WIDTH * 0.09);
        exitButton.setPrefWidth(SCREEN_WIDTH * 0.09);

        HBox idRow = ComponentFactory.createInformationBox("ID Sala", salaId);

        Label copyIcon = new Label("⧉");
        copyIcon.setTextFill(Color.web("#a0b8a0"));
        copyIcon.setFont(Font.font(14));
        copyIcon.setStyle("-fx-cursor: hand;");
        copyIcon.setOnMouseClicked(e -> {
            javafx.scene.input.Clipboard clipboard
                    = javafx.scene.input.Clipboard.getSystemClipboard();
            javafx.scene.input.ClipboardContent content
                    = new javafx.scene.input.ClipboardContent();
            content.putString(salaId);
            clipboard.setContent(content);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        idRow.getChildren().addAll(spacer, copyIcon);

        HBox rondaRow = ComponentFactory.createInformationBox(
                "Ronda", rondaActual + " / " + rondaTotal);

        panel.getChildren().addAll(exitButton, idRow, rondaRow);
        return panel;
    }

    // ─────────────────────────────────────────────
    // Botones top-right
    // ─────────────────────────────────────────────
    public static Pane createTopRightButtons() {
        HBox container = new HBox(8);
        container.setAlignment(Pos.CENTER);

        container.getChildren().addAll(
                ComponentFactory.createGhostGameButton("/assets/jugadoresIcon.png", "Users"),
                ComponentFactory.createGhostGameButton("/assets/ajustesIcon.png", "Ajustes"));

        container.setMaxSize(SCREEN_WIDTH * 0.1, SCREEN_HEIGHT * 0.07);
        container.setPrefWidth(SCREEN_WIDTH * 0.1);
        container.setPrefHeight(SCREEN_HEIGHT * 0.05);
        container.setTranslateY(18);
        container.setTranslateX(-18);

        return container;
    }
}
