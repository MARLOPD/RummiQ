package com.rummyq.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rummyq.core.WaitingRoom;
import com.rummyq.websocket.dto.GameMessageDTO;
import com.rummyq.websocket.dto.GameStatusDTO;
import com.rummyq.websocket.dto.RoomDTO;
import com.rummyq.websocket.dto.TileDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.List;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameWebSocketClient implements WebSocket.Listener {

    private static final Logger log = LoggerFactory.getLogger(GameWebSocketClient.class);
    private static GameWebSocketClient instance;
    private WebSocket webSocket;
    private final ObjectMapper mapper = new ObjectMapper();

    private GameWebSocketClient() {
    }

    public static GameWebSocketClient getInstance() {
        if (instance == null) {
            instance = new GameWebSocketClient();
        }
        return instance;
    }

    public void connect(String uriStr) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            client.newWebSocketBuilder()
                    .buildAsync(URI.create(uriStr), this)
                    .thenAccept(ws -> {
                        this.webSocket = ws;
                        log.info("[WebSocket] Conectado exitosamente a " + uriStr);
                    })
                    .exceptionally(ex -> {
                        log.error("[WebSocket] Error al conectar: " + ex.getMessage());
                        return null;
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        if (webSocket == null) {
            System.err.println("[WebSocket] No se puede iniciar la partida: no hay conexión.");
            return;
        }
        try {
            GameMessageDTO msg = GameMessageDTO.createStartGame();
            String json = mapper.writeValueAsString(msg);
            webSocket.sendText(json, true);
            log.info("[WebSocket] Partida iniciada: " + json);
        } catch (Exception e) {
            log.error("[WebSocket] Error al iniciar partida: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void joinGame() {
        if (webSocket == null) {
            System.err.println("[WebSocket] No se puede enviar jugada: no hay conexión.");
            return;
        }

        try {
            GameMessageDTO msg = GameMessageDTO.createJoinGame();
            String json = mapper.writeValueAsString(msg);
            webSocket.sendText(json, true);
            log.info("[WebSocket] Jugador aceptado: " + json);
        } catch (Exception e) {
            log.error("[WebSocket] Error al unir usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void enviarJugada(List<List<TileDTO>> groups) {
        if (webSocket == null) {
            System.err.println("[WebSocket] No se puede enviar jugada: no hay conexión.");
            return;
        }

        try {
            GameMessageDTO msg = GameMessageDTO.crearJugada(groups);
            String json = mapper.writeValueAsString(msg);
            webSocket.sendText(json, true);
            log.info("[WebSocket] Jugada enviada: " + json);
        } catch (Exception e) {
            log.error("[WebSocket] Error al enviar jugada: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        WebSocket.Listener.super.onOpen(webSocket);
        log.info("[WebSocket] Conexión abierta");
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        log.info("[WebSocket] Mensaje recibido del servidor: " + data);

        try {
            ObjectMapper mapper = new ObjectMapper();
            GameStatusDTO msg = mapper.readValue(data.toString(), GameStatusDTO.class);
            log.info("[WebSocket] Mensaje parseado: " + msg);
            GameWebScoketHandler.identifyMessage(msg);
        } catch (Exception e) {
            log.error("[WebSocket] Error al parsear mensaje: " + e.getMessage());
            e.printStackTrace();
        }
        // Aqui puedes parsear los mensajes del servidor para actualizar la UI
        // Asegurate de usar Platform.runLater() si actualizas elementos de JavaFX.

        webSocket.request(1);
        return null;
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        log.info("[WebSocket] Conexión cerrada. Codigo: " + statusCode + ", Razón: " + reason);
        this.webSocket = null;
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println("[WebSocket] Error: " + error.getMessage());
        WebSocket.Listener.super.onError(webSocket, error);
    }
}
