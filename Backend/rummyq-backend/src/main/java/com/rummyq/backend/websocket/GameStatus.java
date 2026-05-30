package com.rummyq.backend.websocket;

import com.rummyq.backend.models.Ficha;
import com.rummyq.backend.models.Jugador;
import com.rummyq.backend.services.TileBag;
import com.rummyq.backend.websocket.GameMessage.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameStatus {

    public enum Status {
        ESPERANDO, EN_CURSO, TERMINADA
    }

    private final String roomId;
    private final List<Jugador> players = new ArrayList<>();

    private final List<String> sessionIds = new ArrayList<>();
    private final List<Boolean> yaAbrio = new ArrayList<>();

    private TileBag bolsa;

    private final List<List<Ficha>> mesa = new ArrayList<>();

    private int turnoActual = 0;
    private Status status = Status.ESPERANDO;

    public GameStatus(String roomId) {
        this.roomId = roomId;
    }

    public boolean addPlayer(String sessionId, String nombre) {
        if (status != Status.ESPERANDO)
            return false;
        if (players.size() >= 4)
            return false;

        players.add(new Jugador(nombre));
        sessionIds.add(sessionId);
        yaAbrio.add(false);
        return true;
    }

    public void removePlayer(String sessionId) {
        int idx = sessionIds.indexOf(sessionId);
        if (idx >= 0) {
            players.remove(idx);
            sessionIds.remove(idx);
            yaAbrio.remove(idx);
            if (turnoActual >= players.size() && !players.isEmpty()) {
                turnoActual = 0;
            }
        }
    }

    public boolean initialize() {
        if (status != Status.ESPERANDO || players.size() < 2)
            return false;

        bolsa = new TileBag();
        for (Jugador j : players) {
            for (int i = 0; i < 14; i++) {
                Ficha f = bolsa.robarFicha();
                if (f != null)
                    j.agregarFicha(f);
            }
        }
        turnoActual = 0;
        status = Status.EN_CURSO;
        return true;
    }

    public void placeGroupsOnTable(String sessionId, List<List<Ficha>> groups) {
        int idx = sessionIds.indexOf(sessionId);
        Jugador player = players.get(idx);

        for (List<Ficha> grupo : groups) {
            for (Ficha f : grupo) {
                player.getMano().removeIf(mf -> mf.esIgual(f));
            }
            mesa.add(new ArrayList<>(grupo));
        }
        if (!yaAbrio.get(idx)) {
            yaAbrio.set(idx, true);
        }
    }

    public Ficha drawTile(String sessionId) {
        int idx = sessionIds.indexOf(sessionId);
        if (idx < 0)
            return null;
        Ficha f = bolsa.robarFicha();
        if (f != null)
            players.get(idx).agregarFicha(f);
        return f;
    }

    public void nextTurn() {
        turnoActual = (turnoActual + 1) % players.size();
    }

    public boolean isTurnOfSession(String sessionId) {
        int idx = sessionIds.indexOf(sessionId);
        return idx == turnoActual;
    }

    public boolean yaAbrio(String sessionId) {
        int idx = sessionIds.indexOf(sessionId);
        return idx >= 0 && yaAbrio.get(idx);
    }

    public Jugador getWinner() {
        for (Jugador j : players) {
            if (j.gano())
                return j;
        }
        return null;
    }

    public boolean isTerminated() {
        return status == Status.TERMINADA;
    }

    public void terminate() {
        this.status = Status.TERMINADA;
    }

    public PublicState toPublicState() {
        PublicState ep = new PublicState();
        ep.roomId = roomId;
        ep.status = status.name();
        ep.currentPlayer = players.isEmpty() ? null : players.get(turnoActual).getNombre();
        ep.remainingTiles = bolsa != null ? bolsa.cantidadRestante() : 0;

        ep.players = players.stream()
                .map(j -> {
                    PublicState.PlayerInfo ji = new PublicState.PlayerInfo();
                    ji.nombre = j.getNombre();
                    ji.cantFichas = j.cantidadFichas();
                    ji.puntos = j.obtenerValorTotal();
                    return ji;
                })
                .collect(Collectors.toList());

        ep.mesa = mesa.stream()
                .map(grupo -> grupo.stream()
                        .map(Tile::new)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return ep;
    }

    public List<Tile> getManoDTO(String sessionId) {
        int idx = sessionIds.indexOf(sessionId);
        if (idx < 0)
            return List.of();
        return players.get(idx).getMano().stream()
                .map(Tile::new)
                .collect(Collectors.toList());
    }

    public String getRoomId() {
        return roomId;
    }

    public Status getStatus() {
        return status;
    }

    public List<Jugador> getPlayers() {
        return players;
    }

    public List<String> getSessionIds() {
        return sessionIds;
    }

    public int getCurrentTurn() {
        return turnoActual;
    }

    public List<List<Ficha>> getMesa() {
        return mesa;
    }

    public int cantidadJugadores() {
        return players.size();
    }

    public static class PublicState {
        public String roomId;
        public String status;
        public String currentPlayer;
        public int remainingTiles;
        public List<PlayerInfo> players;
        public List<List<Tile>> mesa;

        public static class PlayerInfo {
            public String nombre;
            public int cantFichas;
            public int puntos;
        }
    }
}
