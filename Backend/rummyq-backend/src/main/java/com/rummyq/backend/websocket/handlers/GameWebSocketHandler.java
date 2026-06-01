package com.rummyq.backend.websocket.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rummyq.backend.models.Ficha;
import com.rummyq.backend.models.Jugador;
import com.rummyq.backend.services.GameSessionManager;
import com.rummyq.backend.services.ValidateGame;
import com.rummyq.backend.websocket.GameStatus;
import com.rummyq.backend.websocket.GameMessage;
import com.rummyq.backend.websocket.GameMessage.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(GameWebSocketHandler.class);

    private final ObjectMapper mapper;
    private final GameSessionManager sessionManager;
    private final ValidateGame validador;

    public GameWebSocketHandler(GameSessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.mapper = new ObjectMapper();
        this.validador = new ValidateGame();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionManager.registrarSesion(session);
        log.info("WebSocket conectado: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String sessionId = session.getId();
        log.info("WebSocket desconectado: {} ({})", sessionId, status);

        String roomId = sessionManager.eliminarSesion(sessionId);
        if (roomId != null) {
            GameStatus estado = sessionManager.obtenerPartida(roomId);
            if (estado != null && estado.getStatus() == GameStatus.Status.EN_CURSO) {
                estado.removePlayer(sessionId);
                broadcastEstado(estado);
                if (estado.cantidadJugadores() < 2) {
                    estado.terminate();
                    broadcastMensaje(estado, buildFin("Partida terminada por desconexión."));
                }
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage raw) {
        String sessionId = session.getId();
        log.debug("Mensaje recibido de {}: {}", sessionId, raw.getPayload());

        GameMessage msg;
        try {
            msg = mapper.readValue(raw.getPayload(), GameMessage.class);
        } catch (Exception e) {
            sendError(session, "Mensaje JSON inválido: " + e.getMessage());
            return;
        }

        if (msg.getType() == null) {
            sendError(session, "El mensaje no tiene campo 'tipo'.");
            return;
        }

        switch (msg.getType().toUpperCase()) {
            case "UNIRSE" -> manejarUnirse(session, msg);
            case "INICIAR_PARTIDA" -> manejarIniciarPartida(session, msg);
            case "JUGAR_GRUPO" -> manejarJugarGrupo(session, msg);
            case "ROBAR_FICHA" -> manejarRobarFicha(session);
            case "PASAR_TURNO" -> manejarPasarTurno(session);
            default -> sendError(session, "Tipo de mensaje desconocido: " + msg.getType());
        }
    }

    private void manejarUnirse(WebSocketSession session, GameMessage msg) {
        String sessionId = session.getId();

        if (msg.getRoomId() == null || msg.getRoomId().isBlank()) {
            sendError(session, "Debes especificar un roomId para unirte.");
            return;
        }
        if (msg.getPlayer() == null || msg.getPlayer().isBlank()) {
            sendError(session, "Debes especificar un nombre de jugador.");
            return;
        }

        GameStatus estado = sessionManager.obtenerOCrearPartida(msg.getRoomId());

        boolean emailRegistrado = estado.getPlayers().stream()
                .anyMatch(p -> p.getNombre() != null && p.getNombre().equalsIgnoreCase(msg.getPlayer()));
        if (emailRegistrado) {
            sendError(session, "Este email ya está registrado en la partida.");
            return;
        }

        if (!estado.addPlayer(sessionId, msg.getPlayer())) {
            if (estado.getStatus() != GameStatus.Status.ESPERANDO) {
                sendError(session, "La partida ya ha comenzado.");
            } else {
                sendError(session, "La sala está llena (máximo 4 jugadores).");
            }
            return;
        }

        sessionManager.vincularSesionASala(sessionId, msg.getRoomId());
        log.info("Jugador '{}' unido a sala '{}'", msg.getPlayer(), msg.getRoomId());

        broadcastEstado(estado);
    }

    private void manejarIniciarPartida(WebSocketSession session, GameMessage msg) {
        GameStatus estado = resolverEstado(session, msg.getRoomId());
        if (estado == null)
            return;

        if (!estado.initialize()) {
            if (estado.cantidadJugadores() < 2) {
                sendError(session, "Se necesitan al menos 2 jugadores para iniciar.");
            } else {
                sendError(session, "La partida ya fue iniciada o no se puede iniciar.");
            }
            return;
        }

        log.info("Partida iniciada en sala '{}'", estado.getRoomId());

        for (String sid : estado.getSessionIds()) {
            WebSocketSession s = sessionManager.getSesion(sid);
            if (s != null && s.isOpen()) {
                sendMano(s, estado);
            }
        }

        broadcastEstado(estado);
        broadcastTurno(estado);
    }

    private void manejarJugarGrupo(WebSocketSession session, GameMessage msg) {
        String sessionId = session.getId();
        GameStatus estado = resolverEstadoPorSesion(session);
        if (estado == null)
            return;

        if (!estado.isTurnOfSession(sessionId)) {
            sendError(session, "No es tu turno.");
            return;
        }

        if (msg.getGroups() == null || msg.getGroups().isEmpty()) {
            sendError(session, "Debes enviar al menos un grupo de fichas.");
            return;
        }

        List<List<Ficha>> grupos;
        try {
            grupos = convertirGrupos(msg.getGroups());
        } catch (Exception e) {
            sendError(session, "Error al interpretar las fichas: " + e.getMessage());
            return;
        }

        List<Tile> manoDTO = estado.getManoDTO(sessionId);
        for (List<Ficha> grupo : grupos) {
            for (Ficha f : grupo) {
                boolean tieneEnMano = manoDTO.stream().anyMatch(dto -> {
                    try {
                        return dto.toFicha().esIgual(f);
                    } catch (Exception ex) {
                        return false;
                    }
                });
                boolean estaEnMesa = estado.hasFichaEnMesa(f);
                if (!tieneEnMano && !estaEnMesa) {
                    sendError(session, "No tienes la ficha " + f + " en tu mano ni en la mesa.");
                    return;
                }
            }
        }

        boolean esApertura = !estado.yaAbrio(sessionId);
        ValidateGame.Resultado resultado = validador.validar(grupos, esApertura);

        send(session, GameMessage.resultado(resultado.valido, resultado.motivo));

        if (!resultado.valido)
            return;

        estado.placeGroupsOnTable(sessionId, grupos);

        Jugador ganador = estado.getWinner();
        if (ganador != null) {
            estado.terminate();
            broadcastMensaje(estado, buildFin(ganador.getNombre()));
            return;
        }

        estado.nextTurn();
        broadcastEstado(estado);
        broadcastTurno(estado);
        sendMano(session, estado);
    }

    private void manejarRobarFicha(WebSocketSession session) {
        String sessionId = session.getId();
        GameStatus estado = resolverEstadoPorSesion(session);
        if (estado == null)
            return;

        if (!estado.isTurnOfSession(sessionId)) {
            sendError(session, "No es tu turno.");
            return;
        }

        Ficha robada = estado.drawTile(sessionId);
        if (robada == null) {
            sendError(session, "El mazo está vacío.");
            return;
        }

        log.debug("Jugador {} robó {}", sessionId, robada);

        estado.nextTurn();
        sendMano(session, estado);
        broadcastEstado(estado);
        broadcastTurno(estado);
    }

    private void manejarPasarTurno(WebSocketSession session) {
        String sessionId = session.getId();
        GameStatus estado = resolverEstadoPorSesion(session);
        if (estado == null)
            return;

        if (!estado.isTurnOfSession(sessionId)) {
            sendError(session, "No es tu turno.");
            return;
        }

        estado.nextTurn();
        broadcastEstado(estado);
        broadcastTurno(estado);
    }

    private void send(WebSocketSession session, Object payload) {
        if (session == null || !session.isOpen())
            return;
        try {
            String json = mapper.writeValueAsString(payload);
            synchronized (session) {
                session.sendMessage(new TextMessage(json));
            }
        } catch (IOException e) {
            log.error("Error enviando mensaje a {}: {}", session.getId(), e.getMessage());
        }
    }

    private void sendError(WebSocketSession session, String mensaje) {
        send(session, GameMessage.error(mensaje));
    }

    private void sendMano(WebSocketSession session, GameStatus estado) {
        List<Tile> mano = estado.getManoDTO(session.getId());
        mano = mano.stream().map(p -> {
            p.setNumero(p.isEsComodin() ? "☆" : p.getNumero());
            return p;
        }).collect(Collectors.toList());
        var respuesta = new java.util.HashMap<String, Object>();
        respuesta.put("tipo", "MANO_JUGADOR");
        respuesta.put("fichas", mano);
        send(session, respuesta);
    }

    private void broadcastEstado(GameStatus estado) {
        var ep = estado.toPublicState();
        var wrapper = new java.util.HashMap<String, Object>();
        wrapper.put("tipo", "ESTADO_PARTIDA");
        wrapper.put("estado", ep);
        broadcastMensaje(estado, wrapper);
    }

    private void broadcastTurno(GameStatus estado) {
        if (estado.getPlayers().isEmpty())
            return;
        String nombreTurno = estado.getPlayers().get(estado.getCurrentTurn()).getNombre();
        var m = new java.util.HashMap<String, Object>();
        m.put("tipo", "TURNO");
        m.put("jugador", nombreTurno);
        broadcastMensaje(estado, m);
    }

    private java.util.Map<String, Object> buildFin(String ganador) {
        var m = new java.util.HashMap<String, Object>();
        m.put("tipo", "FIN_PARTIDA");
        m.put("ganador", ganador);
        return m;
    }

    private void broadcastMensaje(GameStatus estado, Object payload) {
        for (String sid : estado.getSessionIds()) {
            WebSocketSession s = sessionManager.getSesion(sid);
            send(s, payload);
        }
    }

    private GameStatus resolverEstado(WebSocketSession session, String roomId) {
        if (roomId == null || roomId.isBlank()) {
            String rid = sessionManager.getRoomIdDeSesion(session.getId());
            if (rid == null) {
                sendError(session, "No estás en ninguna sala. Envía UNIRSE primero.");
                return null;
            }
            roomId = rid;
        }
        GameStatus estado = sessionManager.obtenerPartida(roomId);
        if (estado == null) {
            sendError(session, "La sala '" + roomId + "' no existe.");
            return null;
        }
        return estado;
    }

    private GameStatus resolverEstadoPorSesion(WebSocketSession session) {
        GameStatus estado = sessionManager.obtenerPartidaPorSession(session.getId());
        if (estado == null) {
            sendError(session, "No estás en ninguna sala. Envía UNIRSE primero.");
            return null;
        }
        if (estado.getStatus() == GameStatus.Status.ESPERANDO) {
            sendError(session, "La partida aún no ha comenzado.");
            return null;
        }
        if (estado.getStatus() == GameStatus.Status.TERMINADA) {
            sendError(session, "La partida ya terminó.");
            return null;
        }
        return estado;
    }

    private List<List<Ficha>> convertirGrupos(List<List<Tile>> grupos) {
        List<List<Ficha>> resultado = new ArrayList<>();
        for (List<Tile> grupo : grupos) {
            List<Ficha> fichas = grupo.stream()
                    .map(Tile::toFicha)
                    .collect(Collectors.toList());
            resultado.add(fichas);
        }
        return resultado;
    }
}
