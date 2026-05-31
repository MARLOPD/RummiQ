package com.rummyq.backend.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rummyq.backend.models.Ficha;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameMessage {

    private String type;
    private String roomId;

    private String player;
    private List<List<Tile>> groups;
    private String message;
    private Boolean ok;
    private String reason;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public List<List<Tile>> getGroups() {
        return groups;
    }

    public void setGroups(List<List<Tile>> groups) {
        this.groups = groups;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static GameMessage error(String message) {
        GameMessage m = new GameMessage();
        m.setType("ERROR");
        m.setMessage(message);
        return m;
    }

    public static GameMessage resultado(boolean ok, String reason) {
        GameMessage m = new GameMessage();
        m.setType("RESULTADO_JUGADA");
        m.setOk(ok);
        m.setReason(reason);
        return m;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tile {
        private String numero;
        private String color;
        private boolean esComodin;

        public Tile() {
        }

        public Tile(Ficha ficha) {
            this.numero = ficha.getNumero();
            this.color = ficha.getColor().name();
            this.esComodin = ficha.isEsComodin();
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public boolean isEsComodin() {
            return esComodin;
        }

        public void setEsComodin(boolean esComodin) {
            this.esComodin = esComodin;
        }

        public Ficha toFicha() {
            Ficha.Color c = Ficha.Color.valueOf(this.color.toUpperCase());
            if (esComodin) {
                return new Ficha(c);
            }
            return new Ficha(numero, c);
        }
    }
}
