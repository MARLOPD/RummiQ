package com.rummyq.websocket.dto;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO {
    private static String roomId;
    private static int numPlayers;
    private static List<String> players;

    public RoomDTO(String roomId) {
        RoomDTO.roomId = roomId;
        RoomDTO.numPlayers = 1;
        RoomDTO.players = new ArrayList<>();
    }

    public RoomDTO(String roomId, int numPlayers, List<String> players) {
        RoomDTO.roomId = roomId;
        RoomDTO.numPlayers = numPlayers;
        RoomDTO.players = players;
    }

    public static String getRoomId() {
        return roomId;
    }

    public static void setRoomId(String roomId) {
        RoomDTO.roomId = roomId;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        RoomDTO.numPlayers = numPlayers;
    }

    public static List<String> getPlayers() {
        return RoomDTO.players;
    }

    public static void addPlayer(String playerName) {
        RoomDTO.players.add(playerName);
        RoomDTO.numPlayers++;
    }

    public static void setPlayers(List<String> players) {
        RoomDTO.players = players;
        RoomDTO.numPlayers = players.size();
    }
}