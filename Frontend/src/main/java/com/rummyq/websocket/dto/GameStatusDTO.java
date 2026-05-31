package com.rummyq.websocket.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameStatusDTO {

    @JsonAlias({ "type", "tipo" })
    private String tipo;
    private Estado estado;
    @JsonAlias({ "jugador", "player" })
    private String jugador;
    private String roomId;
    private boolean ok;
    private String reason;
    private List<TileDTO> fichas;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<TileDTO> getFichas() {
        return fichas;
    }

    public void setFichas(List<TileDTO> fichas) {
        this.fichas = fichas;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class Estado {
        private String roomId;
        private String status;
        private String currentPlayer;
        private int remainingTiles;
        private List<Jugador> players;
        private List<Object> mesa;

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCurrentPlayer() {
            return currentPlayer;
        }

        public void setCurrentPlayer(String currentPlayer) {
            this.currentPlayer = currentPlayer;
        }

        public int getRemainingTiles() {
            return remainingTiles;
        }

        public void setRemainingTiles(int remainingTiles) {
            this.remainingTiles = remainingTiles;
        }

        public List<Jugador> getPlayers() {
            return players;
        }

        public void setPlayers(List<Jugador> players) {
            this.players = players;
        }

        public List<Object> getMesa() {
            return mesa;
        }

        public void setMesa(List<Object> mesa) {
            this.mesa = mesa;
        }
    }

    public static class Jugador {
        private String nombre;
        private int cantFichas;
        private int puntos;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getCantFichas() {
            return cantFichas;
        }

        public void setCantFichas(int cantFichas) {
            this.cantFichas = cantFichas;
        }

        public int getPuntos() {
            return puntos;
        }

        public void setPuntos(int puntos) {
            this.puntos = puntos;
        }
    }
}