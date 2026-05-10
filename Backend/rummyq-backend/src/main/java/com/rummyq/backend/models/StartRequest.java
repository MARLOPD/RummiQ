package com.rummyq.backend.models;

import java.util.List;

public class StartRequest {
    private Long roomId;
    private List<Long> players;

    // Getters and setters
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public List<Long> getPlayers() {
        return players;
    }

    public void setPlayers(List<Long> players) {
        this.players = players;
    }
}