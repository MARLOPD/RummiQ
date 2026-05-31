package com.rummyq.websocket;

import java.util.List;

import com.rummyq.core.WaitingRoom;
import com.rummyq.features.gameScene.GameBoard;
import com.rummyq.features.gameScene.GameSceneIndividuals;
import com.rummyq.model.User;
import com.rummyq.websocket.dto.GameStatusDTO;
import com.rummyq.websocket.dto.RoomDTO;
import com.rummyq.websocket.dto.TileDTO;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
public class GameWebScoketHandler {
    public static void identifyMessage(GameStatusDTO msg) {
        if (msg.getTipo().equals("ESTADO_PARTIDA")) {
            handleEstadoPartida(msg);
        }
        if (msg.getTipo().equals("MANO_JUGADOR")) {
            getTilesBoard(msg);
        }
        if (msg.getTipo().equals("TURNO")) {
            User.setCurrentPlayer(msg.getJugador());
            GameSceneIndividuals.updateFinalizarTurnoButtonVisibility();

            // Si el turno comienza para este usuario, capturamos un snapshot
            // del tablero para poder restaurarlo en caso de jugada inválida.
            if (User.getEmail() != null && User.getEmail().equals(msg.getJugador())) {
                GameBoard board = GameBoard.getInstance();
                if (board != null) {
                    board.snapshotBoard();
                }
            }
        }
        if (msg.getTipo().equals("RESULTADO_JUGADA")) {
            validateGame(msg);
        }

        if (msg.getTipo().equals("FIN_PARTIDA")) {
            handleFinPartida(msg);
        }
    }

    private static void addNewPlayer(GameStatusDTO msg) {
        var players = msg.getEstado().getPlayers();
        List<String> playerNames = players.stream().distinct().map(p -> p.getNombre()).toList(); // Cambiar a
                                                                                                 // collect
        RoomDTO.setPlayers(playerNames);
        WaitingRoom.updatePlayers();
    }

    private static void getTilesBoard(GameStatusDTO msg) {
        List<TileDTO> fichasRecibidas = msg.getFichas();
        
        if (fichasRecibidas != null) {
            // Reemplazar completamente las fichas del usuario
            User.setTiles(fichasRecibidas);
        }
        
        GameSceneIndividuals.updateTiles(User.getTiles());
    }

    private static void handleEstadoPartida(GameStatusDTO msg) {
        if (msg.getEstado() == null) {
            return;
        }

        addNewPlayer(msg);

        List<List<TileDTO>> mesa = msg.getEstado().getMesa();
        if (mesa != null) {
            GameBoard board = GameBoard.getInstance();
            if (board != null) {
                board.loadBoardFromGroups(mesa);
            }
        }
    }

    private static void validateGame(GameStatusDTO msg)
    {
        if (!msg.isOk()) {
            GameBoard board = GameBoard.getInstance();
            if (board != null) {
                board.restoreSnapshot();
            }
            GameWebSocketClient.getInstance().passTurn();
        }
    }

    private static void handleFinPartida(GameStatusDTO msg) {
        String ganador = msg.getGanador();
        boolean esGanador = ganador != null && ganador.equals(User.getEmail());

        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Fin de la partida");
            alert.setHeaderText(esGanador ? "¡Felicidades!" : "Fin de la partida");
            alert.setContentText(esGanador ? "Has ganado la partida." : "Has perdido. Ganador: " + (ganador == null ? "--" : ganador));

            alert.showAndWait();

            // Al aceptar, volver a la pantalla principal
            Stage stage = null;
            for (Window w : Window.getWindows()) {
                if (w instanceof Stage && w.isShowing()) {
                    stage = (Stage) w;
                    break;
                }
            }
            if (stage != null) {
                new com.rummyq.view.MainScene(stage).showScreen();
            }
        });
    }

    
}