package com.rummyq.backend.models;

import java.time.LocalDateTime;

public class MensajeDTO {
   private Long gameId;
    private Long playerId;
    private String contenido;
    private LocalDateTime timestamp;

    public MensajeDTO() {}

    public MensajeDTO(Long gameId, Long playerId, String contenido, LocalDateTime timestamp) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.contenido = contenido;
        this.timestamp = timestamp;
    }
    // Getters y setters
    public Long getPlayerId() {
        return playerId;
    }
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}