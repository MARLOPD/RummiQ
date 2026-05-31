package com.rummyq.websocket.dto;

import java.util.List;

import com.rummyq.model.User;

public class GameMessageDTO {
    private String type;
    private String roomId;
    private String player;
    private List<List<TileDTO>> groups;

    public GameMessageDTO() {
    }

    public static GameMessageDTO createJoinGame() {
        GameMessageDTO msg = new GameMessageDTO();
        msg.setType("UNIRSE");
        msg.setRoomId(RoomDTO.getRoomId());
        msg.setPlayer(User.getEmail());
        return msg;
    }

    public static GameMessageDTO createStartGame() {
        GameMessageDTO msg = new GameMessageDTO();
        msg.setType("INICIAR_PARTIDA");
        msg.setRoomId(RoomDTO.getRoomId());
        return msg;
    }

    public static GameMessageDTO crearJugada(List<List<TileDTO>> groups) {
        GameMessageDTO msg = new GameMessageDTO();
        msg.setType("JUGAR_GRUPO");
        msg.setGroups(groups);
        return msg;
    }

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

    public List<List<TileDTO>> getGroups() {
        return groups;
    }

    public void setGroups(List<List<TileDTO>> groups) {
        this.groups = groups;
    }
}
