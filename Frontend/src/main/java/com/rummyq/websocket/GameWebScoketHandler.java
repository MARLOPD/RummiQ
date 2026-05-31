package com.rummyq.websocket;

import java.util.List;

import com.rummyq.core.WaitingRoom;
import com.rummyq.features.gameScene.GameSceneIndividuals;
import com.rummyq.model.User;
import com.rummyq.websocket.dto.GameStatusDTO;
import com.rummyq.websocket.dto.RoomDTO;
import com.rummyq.websocket.dto.TileDTO;
public class GameWebScoketHandler {
    public static void identifyMessage(GameStatusDTO msg) {
        if (msg.getTipo().equals("ESTADO_PARTIDA") && msg.getEstado().getStatus().equals("ESPERANDO")) {
            addNewPlayer(msg);
        }
        if (msg.getTipo().equals("MANO_JUGADOR")) {
            getTilesBoard(msg);
        }
        //if (msg.getTipo().equals("RESULTADO_JUGADA")) {
        //    getTilesBoard(msg);
        //}
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
        List<TileDTO> fichasUsuario = User.getTiles();
        
        if (fichasUsuario == null) {
            User.setTiles(fichasRecibidas);
        } else if (fichasRecibidas != null) {
            for (TileDTO fichaRecibida : fichasRecibidas) {
                if (!fichasUsuario.contains(fichaRecibida)) {
                    fichasUsuario.add(fichaRecibida);
                }
            }
        }
        
        GameSceneIndividuals.updateTiles(User.getTiles());
    }
}