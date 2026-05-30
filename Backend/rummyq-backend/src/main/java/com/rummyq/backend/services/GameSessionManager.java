package com.rummyq.backend.services;

import com.rummyq.backend.websocket.GameStatus;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GameSessionManager {

    private final Map<String, GameStatus> partidas = new ConcurrentHashMap<>();

    private final Map<String, String> sessionRoom = new ConcurrentHashMap<>();

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void registrarSesion(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    public String eliminarSesion(String sessionId) {
        sessions.remove(sessionId);
        return sessionRoom.remove(sessionId);
    }

    public WebSocketSession getSesion(String sessionId) {
        return sessions.get(sessionId);
    }

    public Collection<WebSocketSession> todasLasSesiones() {
        return sessions.values();
    }

    public GameStatus obtenerOCrearPartida(String roomId) {
        return partidas.computeIfAbsent(roomId, GameStatus::new);
    }

    public GameStatus obtenerPartida(String roomId) {
        return partidas.get(roomId);
    }

    public GameStatus obtenerPartidaPorSession(String sessionId) {
        String roomId = sessionRoom.get(sessionId);
        return roomId != null ? partidas.get(roomId) : null;
    }

    public void vincularSesionASala(String sessionId, String roomId) {
        sessionRoom.put(sessionId, roomId);
    }

    public String getRoomIdDeSesion(String sessionId) {
        return sessionRoom.get(sessionId);
    }

    public void eliminarPartida(String roomId) {
        partidas.remove(roomId);
    }
}
