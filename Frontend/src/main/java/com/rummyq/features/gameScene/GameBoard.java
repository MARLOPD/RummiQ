package com.rummyq.features.gameScene;

import com.rummyq.core.UIColors;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rummyq.websocket.dto.TileDTO;
import javafx.scene.control.Label;

/**
 * Representa el tablero principal (la mesa) de RummyQ usando una matriz
 * dinámica.
 * Permite colocar, mover y arrastrar fichas entre celdas. La matriz se expande
 * automáticamente si se colocan fichas cerca de los bordes.
 */
public class GameBoard extends ScrollPane {

    private static final int INITIAL_ROWS = 8;
    private static final int INITIAL_COLS = 16;

    private static final double CELL_WIDTH = 50;
    private static final double CELL_HEIGHT = 65;
    private static final double CELL_GAP = 6;

    private final GridPane gridPane;

    // Matriz lógica de celdas
    private int rows = INITIAL_ROWS;
    private int cols = INITIAL_COLS;

    // Almacena las fichas en cada coordenada (fila, columna)
    private final Map<String, Node> cellTiles = new HashMap<>();
    private final Map<String, CellPane> cellPanes = new HashMap<>();

    // Referencia estática para el drag-and-drop de fichas
    private static Node draggedTile = null;
    private static String draggedSourceKey = null; // null si viene de la mano
    private static GameBoard instance = null;

    public static GameBoard getInstance() {
        return instance;
    }

    public static Node getDraggedTile() {
        return draggedTile;
    }

    public static String getDraggedSourceKey() {
        return draggedSourceKey;
    }

    public void removerFicha(Node tile) {
        removerFichaDeMatriz(tile);
    }

    /**
     * Extrae las secuencias contiguas de fichas (grupos) que se encuentran de forma
     * horizontal en el tablero.
     */
    public List<List<TileDTO>> obtenerGrupos() {
        List<List<TileDTO>> groups = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            List<TileDTO> currentGroup = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                String key = getCellKey(r, c);
                Node tileNode = cellTiles.get(key);

                if (tileNode instanceof StackPane) {
                    TileDTO tileDto = extractTileFromNode((StackPane) tileNode);
                    if (tileDto != null) {
                        currentGroup.add(tileDto);
                    }
                } else {
                    if (currentGroup.size() > 1) { // Guardamos secuencias de 2 o más (el backend validará si son 3 o
                                                   // más)
                        groups.add(new ArrayList<>(currentGroup));
                    }
                    currentGroup.clear();
                }
            }
            if (currentGroup.size() > 1) {
                groups.add(new ArrayList<>(currentGroup));
            }
        }

        return groups;
    }

    private TileDTO extractTileFromNode(StackPane tilePane) {
        if (tilePane.getChildren().size() > 1 && tilePane.getChildren().get(1) instanceof Label) {
            Label lbl = (Label) tilePane.getChildren().get(1);
            String numberStr = lbl.getText();
            Color colorFill = (Color) lbl.getTextFill();

            Integer number = null;
            boolean isJoker = false;

            if (numberStr.equalsIgnoreCase("J") || numberStr.equalsIgnoreCase("Comodin")) {
                isJoker = true;
                number = 0;
            } else {
                try {
                    number = Integer.parseInt(numberStr);
                } catch (NumberFormatException e) {
                    return null;
                }
            }

            String colorStr = "BLACK";
            if (colorFill.equals(Color.RED)) {
                colorStr = "RED";
            } else if (colorFill.equals(Color.BLUE)) {
                colorStr = "BLUE";
            } else if (colorFill.equals(Color.BLACK)) {
                colorStr = "BLACK";
            } else {
                colorStr = "YELLOW";
            }

            return new TileDTO(String.valueOf(number), colorStr, isJoker);
        }
        return null;
    }

    public GameBoard() {
        instance = this;
        gridPane = new GridPane();
        gridPane.setHgap(CELL_GAP);
        gridPane.setVgap(CELL_GAP);
        gridPane.setPadding(new Insets(15));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: transparent;");

        // Configuración del ScrollPane
        this.setContent(gridPane);
        this.setFitToWidth(true);
        this.setFitToHeight(true);
        // setPannable(false): el modo pannable intercepta eventos de arrastre del mouse
        // impidiendo que lleguen a las CellPane hijas, rompiendo el Drag-and-Drop.
        this.setPannable(false);
        this.setStyle(
                "-fx-background: transparent;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-text-background-color: inherit;" // ← esta línea
        );

        // Ocultar scrollbars o darles estilo sutil
        this.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        // Reenviar drag events desde el ScrollPane y el GridPane hacia las CellPane
        // El ScrollPane tiene capas internas (viewport/skin) que pueden absorber
        // eventos;
        // al aceptar aquí aseguramos que los eventos lleguen a las celdas.
        this.setOnDragOver(e -> {
            if (draggedTile != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            // No consumir: dejar que el evento burbujee hacia los CellPane
        });

        // En el constructor, reemplaza el setOnDragOver/setOnDragDropped del ScrollPane
        // por estos dos handlers en el gridPane:

        gridPane.setOnDragOver(e -> {
            if (draggedTile != null) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        gridPane.setOnDragDropped(e -> {
            if (draggedTile == null)
                return;

            // Buscar qué CellPane está bajo el cursor
            javafx.geometry.Point2D local = gridPane.sceneToLocal(e.getSceneX(), e.getSceneY());

            CellPane targetCell = null;
            for (Map.Entry<String, CellPane> entry : cellPanes.entrySet()) {
                if (entry.getValue().getBoundsInParent().contains(local)) {
                    targetCell = entry.getValue();
                    break;
                }
            }

            boolean success = false;
            if (targetCell != null && !cellTiles.containsKey(getCellKey(targetCell.r, targetCell.c))) {

                final StackPane tile = (StackPane) draggedTile;
                final String srcKey = draggedSourceKey;
                final CellPane dest = targetCell;

                if (srcKey != null) {
                    cellTiles.remove(srcKey);
                    CellPane sourceCell = cellPanes.get(srcKey);
                    if (sourceCell != null)
                        sourceCell.clearTile();
                } else if (tile.getParent() instanceof javafx.scene.layout.Pane) {
                    ((javafx.scene.layout.Pane) tile.getParent()).getChildren().remove(tile);
                }

                colocarFicha(tile, dest.r, dest.c);
                success = true;
            }

            e.setDropCompleted(success);
            e.consume();
        });
        // Inicializar la cuadrícula
        reconstruirGrid();

        // DEBUG TEMPORAL
        javafx.application.Platform.runLater(() -> {
            // Ver toda la jerarquía de nodos encima del GameBoard
            javafx.scene.Node n = this.getParent();
            while (n != null) {
                n = n.getParent();
            }
        });

        this.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            if (newSkin != null) {
                Node viewport = this.lookup(".viewport");
                if (viewport != null) {
                    viewport.setPickOnBounds(false); // ← esta es la línea clave
                    viewport.setOnDragOver(e -> {
                        if (draggedTile != null) {
                            e.acceptTransferModes(TransferMode.MOVE);
                        }
                        // NO consumir
                    });
                }
            }
        });

    }

    /**
     * Construye o reconstruye los componentes visuales de la cuadrícula
     * basándose en las dimensiones rows y cols actuales, preservando las fichas
     * existentes.
     */
    private void reconstruirGrid() {
        gridPane.getChildren().clear();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String key = getCellKey(r, c);
                CellPane cell = cellPanes.computeIfAbsent(key, k -> new CellPane(finalRow(k), finalCol(k)));

                // Si la celda tenía una ficha, volver a ponerla
                Node tile = cellTiles.get(key);
                if (tile != null) {
                    cell.setTile(tile);
                } else {
                    cell.clearTile();
                }

                gridPane.add(cell, c, r);
            }
        }
    }

    private int finalRow(String key) {
        return Integer.parseInt(key.split(",")[0]);
    }

    private int finalCol(String key) {
        return Integer.parseInt(key.split(",")[1]);
    }

    private String getCellKey(int row, int col) {
        return row + "," + col;
    }

    /**
     * Coloca una ficha en una celda específica.
     */
    public void colocarFicha(StackPane tile, int row, int col) {
        // Asegurar que la celda está dentro del rango
        asegurarRango(row, col);
        String key = getCellKey(row, col);

        // Quitar de la celda vieja si estaba en el tablero
        removerFichaDeMatriz(tile);

        cellTiles.put(key, tile);
        CellPane cell = cellPanes.get(key);
        if (cell != null) {
            cell.setTile(tile);
        }

        configurarDragSource(tile, key);

        // Si se coloca cerca de los límites, expandir la matriz
        verificarYExpandir(row, col);
    }

    /**
     * Remueve una ficha del registro lógico de celdas.
     */
    private void removerFichaDeMatriz(Node tile) {
        String keyToRemove = null;
        for (Map.Entry<String, Node> entry : cellTiles.entrySet()) {
            if (entry.getValue() == tile) {
                keyToRemove = entry.getKey();
                break;
            }
        }
        if (keyToRemove != null) {
            cellTiles.remove(keyToRemove);
            CellPane cell = cellPanes.get(keyToRemove);
            if (cell != null) {
                cell.clearTile();
            }
        }
    }

    /**
     * Asegura que las dimensiones de la matriz alcancen para contener la celda
     * (row, col).
     */
    private void asegurarRango(int row, int col) {
        boolean cambio = false;
        if (row >= rows) {
            rows = row + 3;
            cambio = true;
        }
        if (col >= cols) {
            cols = col + 3;
            cambio = true;
        }
        if (cambio) {
            reconstruirGrid();
        }
    }

    /**
     * Expande la matriz automáticamente si la ficha se colocó cerca del borde.
     */
    private void verificarYExpandir(int row, int col) {
        boolean necesitaExpandir = false;
        if (row >= rows - 2) {
            rows += 4;
            necesitaExpandir = true;
        }
        if (col >= cols - 2) {
            cols += 4;
            necesitaExpandir = true;
        }
        if (necesitaExpandir) {
            reconstruirGrid();
        }
    }

    /**
     * Configura la ficha como origen para poder ser arrastrada a otra celda del
     * tablero.
     */
    private void configurarDragSource(StackPane tile, String sourceKey) {
        tile.setOnDragDetected(e -> {
            Dragboard db = tile.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            // Pasamos un identificador simple
            content.putString("TABLERO_TILE");
            db.setContent(content);

            draggedTile = tile;
            draggedSourceKey = sourceKey;

            tile.setOpacity(0.5); // Efecto visual al arrastrar
            e.consume();
        });

        tile.setOnDragDone(e -> {
            tile.setOpacity(1.0);
            draggedTile = null;
            draggedSourceKey = null;
            e.consume();
        });
    }

    /**
     * Permite registrar fichas externas (ej. de la mano del jugador) como
     * arrastrables hacia el tablero.
     */
    public static void habilitarDragDesdeMano(Node tile) {
        tile.setOnDragDetected(e -> {
            Dragboard db = tile.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("MANO_TILE");
            db.setContent(content);

            draggedTile = tile;
            draggedSourceKey = null; // Viene de la mano

            tile.setOpacity(0.5);
            e.consume();
        });

        tile.setOnDragDone(e -> {
            tile.setOpacity(1.0);
            draggedTile = null;
            draggedSourceKey = null;
            e.consume();
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Subclase: Celda del Tablero (Visual y Lógica de Drop)
    // ─────────────────────────────────────────────────────────────────────────

    private class CellPane extends StackPane {
        private final int r;
        private final int c;
        private final Rectangle backgroundSlot;

        public CellPane(int r, int c) {
            this.r = r;
            this.c = c;
            this.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
            this.setMinSize(CELL_WIDTH, CELL_HEIGHT);
            this.setMaxSize(CELL_WIDTH, CELL_HEIGHT);

            // Fondo visual del slot vacío
            backgroundSlot = new Rectangle(CELL_WIDTH - 2, CELL_HEIGHT - 2);
            backgroundSlot.setArcWidth(6);
            backgroundSlot.setArcHeight(6);
            backgroundSlot.setFill(Color.web("#143c22", 0.35)); // Verde oscuro translúcido
            backgroundSlot.setStroke(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.2));
            backgroundSlot.setStrokeWidth(1.2);
            // Efecto de línea discontinua
            backgroundSlot.getStrokeDashArray().addAll(5d, 5d);

            this.getChildren().add(backgroundSlot);

            // Hover cuando el mouse pasa sobre la celda vacía
            this.setOnMouseEntered(e -> {
                if (getChildren().size() == 1) { // Solo si está vacía
                    backgroundSlot.setStroke(UIColors.COLOR_ORO_CLARO);
                    backgroundSlot.setFill(Color.web("#225533", 0.5));
                }
            });
            this.setOnMouseExited(e -> {
                backgroundSlot.setStroke(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.2));
                backgroundSlot.setFill(Color.web("#143c22", 0.35));
            });

            // ── Implementación de Drag and Drop (Recepción) ───────────────────

            this.setOnDragOver(e -> {
                if (e.getGestureSource() != this && getChildren().size() == 1) {
                    // Acepta si viene del tablero o de la mano
                    e.acceptTransferModes(TransferMode.MOVE);
                }
                e.consume();
            });

            this.setOnDragEntered(e -> {
                if (e.getGestureSource() != this && getChildren().size() == 1) {
                    backgroundSlot.setStroke(UIColors.COLOR_ORO_CLARO);
                    backgroundSlot.setStrokeWidth(2.0);
                    backgroundSlot.setFill(Color.web("#2a683e", 0.6));
                }
                e.consume();
            });

            this.setOnDragExited(e -> {
                backgroundSlot.setStroke(UIColors.COLOR_ORO.deriveColor(0, 1, 1, 0.2));
                backgroundSlot.setStrokeWidth(1.2);
                backgroundSlot.setFill(Color.web("#143c22", 0.35));
                e.consume();
            });

            this.setOnDragDropped(e -> {
                boolean success = false;
                if (draggedTile != null && getChildren().size() == 1) {

                    // Si venía de otra celda en el tablero, limpiar esa celda
                    if (draggedSourceKey != null) {
                        cellTiles.remove(draggedSourceKey);
                        CellPane sourceCell = cellPanes.get(draggedSourceKey);
                        if (sourceCell != null) {
                            sourceCell.clearTile();
                        }
                    } else {
                        // Venía de la mano (fuera del tablero), remover de su contenedor visual
                        // original
                        if (draggedTile.getParent() instanceof javafx.scene.layout.Pane) {
                            // Remover del HBox/Pane donde estaba la mano
                            ((javafx.scene.layout.Pane) draggedTile.getParent()).getChildren().remove(draggedTile);
                        }
                    }

                    // Colocar en esta nueva celda
                    colocarFicha((StackPane) draggedTile, r, c);
                    success = true;
                }
                e.setDropCompleted(success);
                e.consume();
            });
        }

        public void setTile(Node tile) {
            // Limpiar si ya había algo
            if (this.getChildren().size() > 1) {
                this.getChildren().remove(1);
            }
            // Agregar la ficha encima del fondo discontínuo
            this.getChildren().add(tile);

            // Centrar
            StackPane.setAlignment(tile, Pos.CENTER);
        }

        public void clearTile() {
            if (this.getChildren().size() > 1) {
                this.getChildren().remove(1);
            }
        }
    }
}